package me.heitx.maserow.item;

import javafx.scene.Parent;
import javafx.util.Pair;
import me.heitx.maserow.common.model.Item;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.ui.SideElement;
import me.heitx.maserow.common.services.ISidebar;
import me.heitx.maserow.common.services.ISidebarPlugin;
import me.heitx.maserow.item.search.ItemSearchController;
import me.heitx.maserow.item.editor.ItemEditorController;
import me.heitx.maserow.core.mainpage.MainPageController;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class ItemSidebarPlugin implements ISidebarPlugin {
	private Parent searchParent;
	private Parent editorParent;
	private ItemSearchController searchController;
	private ItemEditorController editorController;

	private ISidebar sidebar;
	private SideElement category;
	private SideElement search;
	private SideElement editor;

	@Override
	public void onStart(MainPageController mainPageController, ISidebar sidebar) {
		this.sidebar = sidebar;

		StackedFontIcon cIcon = new StackedFontIcon();
		StackedFontIcon sIcon = new StackedFontIcon();
		StackedFontIcon eIcon = new StackedFontIcon();

		cIcon.setIconCodeLiterals(MaterialDesign.MDI_SWORD.getDescription());
		sIcon.setIconCodeLiterals(FontAwesomeSolid.SEARCH.getDescription());
		eIcon.setIconCodeLiterals(FontAwesomeSolid.EDIT.getDescription());

		category = new SideElement(cIcon, "Item");
		search = new SideElement(sIcon, "Search");
		editor = new SideElement(eIcon, "Editor");

		category.addElement(search);
		category.addElement(editor);

		sidebar.addElement(true, category);

		search.getButton().setOnAction(actionEvent -> onSearchAction(mainPageController));
		editor.getButton().setOnAction(actionEvent -> onEditorAction(mainPageController));
	}

	private void onSearchAction(MainPageController controller) {
		if(searchParent == null && searchController == null) {
			Pair<Parent, ItemSearchController> layout = LayoutUtil.loadLayout(ItemSearchController.class, "itemsearch.fxml");
			searchParent = layout.getKey();
			searchController = layout.getValue();

			searchController.setDoubleClickRowCallback(item -> {
				loadOrUpdateEditor(null);
				editorController.setItem(item);
				sidebar.setSelected(editor);

				controller.setContent(editorParent);
			});
		} else {
			searchController.update();
		}

		controller.setContent(searchParent);
	}

	private void onEditorAction(MainPageController controller) {
		loadOrUpdateEditor(new Item());
		controller.setContent(editorParent);
	}

	private void loadOrUpdateEditor(Item item) {
		if(editorParent == null && editorController == null) {
			Pair<Parent, ItemEditorController> layout = LayoutUtil.loadLayout(ItemEditorController.class, "itemeditor.fxml");
			editorParent = layout.getKey();
			editorController = layout.getValue();
			if(item != null) editorController.setItem(item);
		} else {
//			editorController.update();
		}

	}
}
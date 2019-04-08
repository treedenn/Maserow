package me.heitx.maserow.creature;

import javafx.scene.Parent;
import javafx.util.Pair;
import me.heitx.maserow.common.services.ISidebar;
import me.heitx.maserow.common.services.ISidebarPlugin;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.ui.SideElement;
import me.heitx.maserow.core.mainpage.MainPageController;
import me.heitx.maserow.creature.editor.CreatureEditorController;
import me.heitx.maserow.creature.search.CreatureSearchController;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class CreatureSidebarPlugin implements ISidebarPlugin {
	private Parent searchParent;
	private Parent editorParent;
	private CreatureSearchController searchController;
	private CreatureEditorController editorController;

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

		cIcon.setIconCodeLiterals(MaterialDesign.MDI_GHOST.getDescription());
		sIcon.setIconCodeLiterals(FontAwesomeSolid.SEARCH.getDescription());
		eIcon.setIconCodeLiterals(FontAwesomeSolid.EDIT.getDescription());

		category = new SideElement(cIcon, "Creature");
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
			Pair<Parent, CreatureSearchController> layout = LayoutUtil.loadLayout(CreatureSearchController.class, "creaturesearch.fxml");
			searchParent = layout.getKey();
			searchController = layout.getValue();

			searchController.setDoubleClickRowCallback(creature -> {
				onEditorAction(controller);
				editorController.setCreature(creature);
				sidebar.setSelected(editor);
			});
		} else {
			searchController.update();
		}

		controller.setContent(searchParent);
	}

	private void onEditorAction(MainPageController controller) {
		if(editorParent == null && editorController == null) {
			Pair<Parent, CreatureEditorController> layout = LayoutUtil.loadLayout(CreatureEditorController.class, "creatureeditor.fxml");
			editorParent = layout.getKey();
			editorController = layout.getValue();
		} else {
			editorController.update();
		}

		controller.setContent(editorParent);
	}
}
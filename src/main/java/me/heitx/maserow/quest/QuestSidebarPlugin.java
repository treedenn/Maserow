package me.heitx.maserow.quest;

import javafx.scene.Parent;
import javafx.util.Pair;
import me.heitx.maserow.common.services.ISidebar;
import me.heitx.maserow.common.services.ISidebarPlugin;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.ui.SideElement;
import me.heitx.maserow.core.mainpage.MainPageController;
import me.heitx.maserow.quest.editor.QuestEditorController;
import me.heitx.maserow.quest.search.QuestSearchController;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.kordamp.ikonli.material.Material;

public class QuestSidebarPlugin implements ISidebarPlugin {
	private Parent searchParent;
	private Parent editorParent;
	private QuestSearchController searchController;
	private QuestEditorController editorController;

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

		cIcon.setIconCodeLiterals(Material.PRIORITY_HIGH.getDescription());
		sIcon.setIconCodeLiterals(FontAwesomeSolid.SEARCH.getDescription());
		eIcon.setIconCodeLiterals(FontAwesomeSolid.EDIT.getDescription());

		category = new SideElement(cIcon, "Quest");
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
			Pair<Parent, QuestSearchController> layout = LayoutUtil.loadLayout(QuestSearchController.class, "questsearch.fxml");
			searchParent = layout.getKey();
			searchController = layout.getValue();

			searchController.setDoubleClickRowCallback(quest -> {
				onEditorAction(controller);
				editorController.setQuest(quest);
				sidebar.setSelected(editor);
			});
		} else {
			searchController.update();
		}

		controller.setContent(searchParent);
	}

	private void onEditorAction(MainPageController controller) {
		if(editorParent == null && editorController == null) {
			Pair<Parent, QuestEditorController> layout = LayoutUtil.loadLayout(QuestEditorController.class, "questeditor.fxml");
			editorParent = layout.getKey();
			editorController = layout.getValue();
		} else {
			editorController.update();
		}

		controller.setContent(editorParent);
	}
}
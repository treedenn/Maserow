package me.heitx.maserow.common.lookup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import me.heitx.maserow.common.io.Identifier;
import me.heitx.maserow.common.io.config.Config;
import me.heitx.maserow.common.io.config.ConfigKey;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.lookup.multiselection.LookupMultiController;
import me.heitx.maserow.common.lookup.singleselection.LookupSingleController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class LookupManager {
	private static final Logger LOGGER = LogManager.getLogger(LookupManager.class.getName());
	private static LookupManager ourInstance;

	public static LookupManager getInstance() {
		if(ourInstance == null) {
			ourInstance = new LookupManager();
		}

		ourInstance.sidebar = Boolean.valueOf(Config.getInstance().get(ConfigKey.LOOKUP_SIDEBAR));
		return ourInstance;
	}

	private boolean sidebar;
	private BorderPane sidebarContainer;
	private Stage currentPopup;

	public void setSidebarContainer(BorderPane container) {
		this.sidebarContainer = container;
	}

	public boolean isLookupVisible() {
		return currentPopup != null || sidebarContainer.getRight() != null;
	}

	public void showSingleLookup(String title, String stageTitle, boolean returnEntry, Function<String, List<Identifier>> filter, Consumer<Long> onSuccess) {
		Pair<Parent, LookupSingleController> pair = loadSingleLayout();
		if(pair != null) {
			LookupSingleController controller = pair.getValue();
			controller.labelTitle.setText(title);
			controller.setParent(sidebarContainer);
			controller.setReturnEntry(returnEntry);
			controller.filterFunction = filter;
			controller.onSuccess = onSuccess;

			show(stageTitle, pair.getKey(), controller);
		}
	}

	public void showSingleLookup(String title, String stageTitle, boolean returnEntry, List<Identifier> identifiers, Integer selectedIndex, Consumer<Long> onSuccess) {
		Pair<Parent, LookupSingleController> pair = loadSingleLayout();
		if(pair != null) {
			LookupSingleController controller = pair.getValue();
			controller.labelTitle.setText(title);
			controller.setParent(sidebarContainer);
			controller.setReturnEntry(returnEntry);
			controller.setIdentifiers(identifiers);
			controller.setSelected(selectedIndex);
			controller.onSuccess = onSuccess;

			show(stageTitle, pair.getKey(), controller);
		}
	}

	public void showMultiLookup(String title, String stageTitle, boolean returnEntry, Function<String, List<Identifier>> filter, Consumer<List<Long>> onSuccess) {
		Pair<Parent, LookupMultiController> pair = loadMultiLayout();
		if(pair != null) {
			LookupMultiController controller = pair.getValue();
			controller.labelTitle.setText(title);
			controller.setParent(sidebarContainer);
			controller.setReturnEntry(returnEntry);
			controller.filterFunction = filter;
			controller.onSuccess = onSuccess;

			show(stageTitle, pair.getKey(), controller);
		}
	}

	public void showMultiLookup(String title, String stageTitle, boolean returnEntry, List<Identifier> identifiers, List<Integer> selectedIndices, Consumer<List<Long>> onSuccess) {
		Pair<Parent, LookupMultiController> pair = loadMultiLayout();
		if(pair != null) {
			LookupMultiController controller = pair.getValue();
			controller.labelTitle.setText(title);
			controller.setParent(sidebarContainer);
			controller.setReturnEntry(returnEntry);
			controller.setIdentifiers(identifiers);
			controller.select(selectedIndices);
			controller.onSuccess = onSuccess;

			show(stageTitle, pair.getKey(), controller);
		}
	}

	private void show(String stageTitle, Parent p, LookupController controller) {
		if(!isLookupVisible()) {
			LayoutUtil.toggle(controller.btnEscape, sidebar);

			if(sidebar) {
				sidebarContainer.setRight(p);
			} else {
				Scene scene = new Scene(p);
				scene.getStylesheets().add(getClass().getClassLoader().getResource("darktheme_OLD.css").toExternalForm());

				currentPopup = new Stage();
				currentPopup.setOnCloseRequest(windowEvent -> {
					if(currentPopup == windowEvent.getSource()) {
						controller.clean();
						currentPopup = null;
						System.out.println("Closed!?");
					}
				});
				currentPopup.setScene(scene);
				currentPopup.setTitle(stageTitle);
				currentPopup.show();
			}
		}
	}

	private Pair<Parent, LookupSingleController> loadSingleLayout() {
		Pair<Parent, LookupSingleController> pair = null;

		try {
			FXMLLoader loader = new FXMLLoader(LookupSingleController.class.getResource("lookupsingle.fxml"));
			pair = new Pair<>(loader.load(), loader.getController());
		} catch(IOException e) {
			e.printStackTrace();
			LOGGER.warn("Cannot load single lookup layout! The error message: " + e.getMessage());
		}

		return pair;
	}

	private Pair<Parent, LookupMultiController> loadMultiLayout() {
		Pair<Parent, LookupMultiController> pair = null;

		try {
			FXMLLoader loader = new FXMLLoader(LookupMultiController.class.getResource("lookupmulti.fxml"));
			pair = new Pair<>(loader.load(), loader.getController());
		} catch(IOException e) {
			LOGGER.warn("Cannot load multi lookup layout! The error message: " + e.getMessage());
		}

		return pair;
	}
}
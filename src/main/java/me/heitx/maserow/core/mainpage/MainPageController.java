package me.heitx.maserow.core.mainpage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.services.ISidebarPlugin;
import me.heitx.maserow.common.lookup.LookupManager;
import me.heitx.maserow.core.sidebar.SidebarController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ServiceLoader;

public class MainPageController implements Initializable {
	@FXML private BorderPane bpApp;
	@FXML private SidebarController sidebarController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setupSidebar();

		LookupManager lm = LookupManager.getInstance();
		lm.setSidebarContainer(bpApp);
	}

	public void setContent(Parent parent) {
		bpApp.setCenter(parent);
	}

	private void setupSidebar() {
		ServiceLoader<ISidebarPlugin> sidebarPlugins = ServiceLoader.load(ISidebarPlugin.class);

		Database.getInstance().setClient(null);

		for(ISidebarPlugin plugin : sidebarPlugins) {
			plugin.onStart(this, sidebarController);
		}

		sidebarController.hideAllElements();
	}
}
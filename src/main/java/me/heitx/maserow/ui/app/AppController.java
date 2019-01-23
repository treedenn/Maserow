package me.heitx.maserow.ui.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import me.heitx.maserow.ui.item.search.ItemSearchController;
import me.heitx.maserow.ui.login.LoginController;
import me.heitx.maserow.ui.sidemenu.SidemenuController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
	@FXML BorderPane bpApp;
	@FXML SidemenuController smController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setupSidemenu();
	}

	private void setupSidemenu() {
		smController.setLoginCallback(() -> {
			setCenter(LoginController.class, "login");
		});
		smController.setItemSearchCallback(() -> {
			setCenter(ItemSearchController.class, "itemsearch");
		});
	}

	private void setCenter(Class anyClass, String filename) {
		try {
			bpApp.setCenter(FXMLLoader.load(anyClass.getResource(filename + ".fxml")));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
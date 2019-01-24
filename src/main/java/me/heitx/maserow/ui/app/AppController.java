package me.heitx.maserow.ui.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.Callback;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.ui.item.search.ItemSearchController;
import me.heitx.maserow.ui.item.template.ItemTemplateController;
import me.heitx.maserow.ui.login.LoginController;
import me.heitx.maserow.ui.sidemenu.SidemenuController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
	@FXML private BorderPane bpApp;
	@FXML private SidemenuController smController;

	private Parent login;
	private LoginController loginController;
	private Parent itemSearch;
	private ItemSearchController itemSearchController;
	private Parent itemTemplate;
	private ItemTemplateController itemTemplateController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setupSidemenu();
	}

	private void setupSidemenu() {
		smController.setLoginCallback(() -> {
			if(login == null) {
				create(LoginController.class, "login", (p, c) -> {
					login = p;
					loginController = c;
				});
			}

			bpApp.setCenter(login);
		});

		smController.setItemSearchCallback(() -> {
			if(itemSearch == null) {
				create(ItemSearchController.class, "itemsearch", (p, c) -> {
					itemSearch = p;
					itemSearchController = c;
				});

				itemSearchController.setRowSelectionCallback(item -> {
					createOrUpdateItemTemplate();
					itemTemplateController.setItem(item);
					bpApp.setCenter(itemTemplate);
					return null;
				});
			} else {
				itemSearchController.update();
			}

			bpApp.setCenter(itemSearch);
		});

		smController.setItemTemplateCallback(() -> {
			createOrUpdateItemTemplate();

			bpApp.setCenter(itemTemplate);

			if(itemTemplateController.getItem() == null) {
				itemTemplateController.setItem(new Item());
			}
		});
	}

	private void createOrUpdateItemTemplate() {
		if(itemTemplate == null) {
			create(ItemTemplateController.class, "itemtemplate", (p, c) -> {
				itemTemplate = p;
				itemTemplateController = c;
			});
		} else {
			itemTemplateController.update();
		}
	}

	// C is the controller, P is the parent.
	private <P extends Parent, C> void create(Class<C> controllerClass, String filename, ILayoutCallback<P, C> callback) {
		try {
			FXMLLoader loader = new FXMLLoader(controllerClass.getResource(filename + ".fxml"));
			callback.call(loader.load(), loader.getController());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: Settings -> Add option to clear or keep controllers / reuse controllers.
	private void clearControllers() {
		loginController = null;
		itemSearchController = null;
		itemTemplateController = null;
	}

	private interface ILayoutCallback<P extends Parent, C> {
		void call(P p, C c);
	}
}
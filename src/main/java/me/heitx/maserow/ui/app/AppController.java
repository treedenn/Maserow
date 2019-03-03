package me.heitx.maserow.ui.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.ui.creature.search.CreatureSearchController;
import me.heitx.maserow.ui.creature.template.CreatureTemplateController;
import me.heitx.maserow.ui.item.search.ItemSearchController;
import me.heitx.maserow.ui.item.template.ItemTemplateController;
import me.heitx.maserow.ui.login.LoginController;
import me.heitx.maserow.ui.quest.search.QuestSearchController;
import me.heitx.maserow.ui.quest.template.QuestTemplateController;
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
	private Parent questSearch;
	private QuestSearchController questSearchController;
	private Parent questTemplate;
	private QuestTemplateController questTemplateController;
	private Parent creatureSearch;
	private CreatureSearchController creatureSearchController;
	private Parent creatureTemplate;
	private CreatureTemplateController creatureTemplateController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setupSidemenu();
	}

	private void setupSidemenu() {
		// Login
		smController.setLoginCallback(() -> {
			if(login == null) {
				create(LoginController.class, "login", (p, c) -> {
					login = p;
					loginController = c;
				});
			}

			bpApp.setCenter(login);
		});

		// Item -> Search and Template
		smController.setItemSearchCallback(() -> {
			if(itemSearch == null) {
				create(ItemSearchController.class, "itemsearch", (p, c) -> {
					itemSearch = p;
					itemSearchController = c;
				});

				itemSearchController.setDoubleClickRowCallback(item -> {
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
		});

		// Quest -> Search and Template
		smController.setQuestSearchCallback(() -> {
			if(questSearch == null) {
				create(QuestSearchController.class, "questsearch", (p, c) -> {
					questSearch = p;
					questSearchController = c;
				});

				questSearchController.setDoubleClickRowCallback(quest -> {
					createOrUpdateQuestTemplate();
					questTemplateController.setQuest(quest);
					bpApp.setCenter(questTemplate);
					return null;
				});
			} else {
				questSearchController.update();
			}

			bpApp.setCenter(questSearch);
		});

		smController.setQuestTemplateCallback(() -> {
			createOrUpdateQuestTemplate();
			bpApp.setCenter(questTemplate);
		});

		// Creature -> Search and Template
		smController.setCreatureSearchCallback(() -> {
			if(creatureSearch == null) {
				create(CreatureSearchController.class, "creaturesearch", (p, c) -> {
					creatureSearch = p;
					creatureSearchController = c;
				});

				creatureSearchController.setDoubleClickRowCallback(creature -> {
					createOrUpdateCreatureTemplate();
					creatureTemplateController.setCreature(creature);
					bpApp.setCenter(creatureTemplate);
					return null;
				});
			} else {
				creatureSearchController.update();
			}

			bpApp.setCenter(creatureSearch);
		});

		smController.setCreatureTemplateCallback(() -> {
			createOrUpdateCreatureTemplate();
			bpApp.setCenter(creatureTemplate);
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

	private void createOrUpdateQuestTemplate() {
		if(questTemplate == null) {
			create(QuestTemplateController.class, "questtemplate", (p, c) -> {
				questTemplate = p;
				questTemplateController = c;
			});
		} else {
			questTemplateController.update();
		}
	}

	private void createOrUpdateCreatureTemplate() {
		if(creatureTemplate == null) {
			create(CreatureTemplateController.class, "creaturetemplate", (p, c) -> {
				creatureTemplate = p;
				creatureTemplateController = c;
			});
		} else {
			creatureTemplateController.update();
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
package me.heitx.maserow_new.quest;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import me.heitx.maserow_new.common.service.ITablePlugin;
import me.heitx.maserow_new.core.mainpage.MainPageController;

import java.util.Map;
import java.util.Set;

public class QuestTablePlugin implements ITablePlugin {
	private MainPageController mainPageController;

	private QuestTemplateController questTemplateController;

	@Override
	public void onStart(MainPageController mainPageController, Map<String, Set<Button>> tables) {
		this.mainPageController = mainPageController;

		questTemplateController = new QuestTemplateController(mainPageController);

		Button btnQuestTemplate = new Button("quest___template");
		btnQuestTemplate.setOnAction(this::handleQuestTemplateAction);

		mainPageController.addMenu(btnQuestTemplate);
	}

	private void handleQuestTemplateAction(ActionEvent event) {
//		mainPageController.setContent(questTemplateController.getContainer());
	}
}
package me.heitx.maserow_new.quest;

import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import me.heitx.maserow_new.common.util.CSVPath;
import me.heitx.maserow_new.core.mainpage.MainPageController;

public class QuestTemplateController {
	private final String csvQuestFolder = CSVPath.QUEST;

	private TilePane container;
	private Button btnQuestType;
	private Button btnQuestSortId;
	private Button btnQuestInfoId;
	private Button btnFlags;
	private Button btnSpecialFlags;

	private MainPageController mainPageController;

	public QuestTemplateController(MainPageController mainPageController) {
		this.mainPageController = mainPageController;


	}
}

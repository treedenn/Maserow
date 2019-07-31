package me.heitx.maserow_new.item;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import me.heitx.maserow_new.common.service.ITablePlugin;
import me.heitx.maserow_new.core.mainpage.MainPageController;
import me.heitx.maserow_new.item.item_template.ItemTemplateController;

import java.util.*;

public class ItemTablePlugin implements ITablePlugin {
	private MainPageController mainPageController;

	private ItemTemplateController itemTemplateController;

	@Override
	public void onStart(MainPageController mainPageController, Map<String, Set<Button>> tables) {
		this.mainPageController = mainPageController;

		itemTemplateController = new ItemTemplateController(mainPageController);

		Button btnItemTemplate = new Button("item___template");
		btnItemTemplate.setOnAction(this::handleItemTemplateAction);

		mainPageController.addTable(btnItemTemplate);
	}

	private void handleItemTemplateAction(ActionEvent event) {
		mainPageController.setContent(itemTemplateController.getContainer());
	}
}
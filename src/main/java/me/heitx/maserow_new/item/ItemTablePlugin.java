package me.heitx.maserow_new.item;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import me.heitx.maserow_new.common.io.DelimiterReader;
import me.heitx.maserow_new.common.io.ICSV;
import me.heitx.maserow_new.common.io.Identifier;
import me.heitx.maserow_new.common.services.ITablePlugin;
import me.heitx.maserow_new.common.views.tableview.TableViewController;
import me.heitx.maserow_new.core.mainpage.MainPageController;
import me.heitx.maserow_new.item.item_template.class_subclass.ClassSubclassController;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

public class ItemTablePlugin implements ITablePlugin {
	private MainPageController mainPageController;

	@Override
	public void onStart(MainPageController mainPageController, Map<String, Set<Button>> tables) {
		this.mainPageController = mainPageController;

		Button btnItemTemplate = new Button("item___template");
		btnItemTemplate.setOnAction(this::handleItemTemplateAction);

		mainPageController.addTable(btnItemTemplate);
	}

	private void handleItemTemplateAction(ActionEvent event) {
		final String csvItemPath = ICSV.CSV_FOLDER_NAME + File.separator + "item" + File.separator;

		TilePane tp = new TilePane();

		Button btnClassSubclass = new Button("class & subclass");
		Button btnStatType = new Button("stat type");
		Button btnFlags = new Button("flags");
		Button btnInventoryType = new Button("inventory type");
		Button btnQuality = new Button("quality");
		Button btnSpellTrigger = new Button("spell trigger");
		Button btnBonding = new Button("bonding");
		Button btnSheath = new Button("sheath");
		Button btnRepRank = new Button("reputation rank");

		// Actions

		btnClassSubclass.setOnAction(action -> loadView(tp, ClassSubclassController.class, "class_subclass.fxml"));

		btnStatType.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_stat_types", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
		}));

//		btnFlags.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
//			List<Identifier> identifiers = DelimiterReader.readColumns(ICSV.CSV_FOLDER_NAME + File.separator + "item" + File.separator + "item_stat_types", true, false);
//			controller.setTableContent(identifiers);
//			controller.hideColumnValue();
//		}));

		btnInventoryType.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_inventory_type", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
		}));

		btnQuality.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_quality", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
		}));

		btnSpellTrigger.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_spell_trigger", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
		}));

		btnBonding.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_bonding", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
		}));

		btnSheath.setOnAction(action -> loadView(tp, TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_sheath", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
		}));

		// Buttons to TilePane to MainPage

		Set<Button> columns = new LinkedHashSet<>(Arrays.asList(btnClassSubclass, btnStatType, btnFlags, btnInventoryType, btnQuality,
				btnSpellTrigger, btnBonding, btnSheath, btnRepRank));

		for(Button column : columns) {
			column.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		}

		tp.getChildren().addAll(columns);

		mainPageController.setContent(tp);
	}

	private <T> void loadView(Region previous, Class<T> clazz, String fxml) {
		loadView(previous, clazz, fxml, null);
	}

	private <T> void loadView(Region previous, Class<T> clazz, String fxml, BiConsumer<Region, T> consumer) {
		FXMLLoader loader = new FXMLLoader(clazz.getResource(fxml));
		try {
			Region load = loader.load();
			if(consumer != null) consumer.accept(load, loader.getController());
			mainPageController.setContent(previous, load);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
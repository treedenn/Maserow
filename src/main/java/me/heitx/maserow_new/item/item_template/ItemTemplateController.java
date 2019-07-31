package me.heitx.maserow_new.item.item_template;

import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import me.heitx.maserow_new.common.io.DelimiterReader;
import me.heitx.maserow_new.common.io.ICSV;
import me.heitx.maserow_new.common.io.Identifier;
import me.heitx.maserow_new.common.util.JavaFXUtil;
import me.heitx.maserow_new.common.view.tableview.TableViewController;
import me.heitx.maserow_new.common.view.tableview_multi.TableViewMultiController;
import me.heitx.maserow_new.core.mainpage.MainPageController;
import me.heitx.maserow_new.item.item_template.class_subclass.ClassSubclassController;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ItemTemplateController {
	final String csvItemPath = ICSV.CSV_FOLDER_NAME + File.separator + "item" + File.separator;

	private TilePane container;
	private Button btnClassSubclass;
	private Button btnStatType;
	private Button btnFlags;
	private Button btnInventoryType;
	private Button btnQuality;
	private Button btnSpellTrigger;
	private Button btnBonding;
	private Button btnSheath;
	private Button btnRepRank;

	private MainPageController mainPageController;

	public ItemTemplateController(MainPageController mainPageController) {
		this.mainPageController = mainPageController;

		container = new TilePane();
		btnClassSubclass = new Button("class & subclass");
		btnStatType = new Button("stat type");
		btnFlags = new Button("flags");
		btnInventoryType = new Button("inventory type");
		btnQuality = new Button("quality");
		btnSpellTrigger = new Button("spell trigger");
		btnBonding = new Button("bonding");
		btnSheath = new Button("sheath");
		btnRepRank = new Button("reputation rank");

		handleActions();

		Set<Button> columns = new LinkedHashSet<>(Arrays.asList(btnClassSubclass, btnStatType, btnFlags, btnInventoryType, btnQuality,
				btnSpellTrigger, btnBonding, btnSheath, btnRepRank));

		for(Button column : columns) {
			column.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		}

		container.getChildren().addAll(columns);
	}

	public TilePane getContainer() {
		return container;
	}

	private void handleActions() {
		btnClassSubclass.setOnAction(event -> JavaFXUtil.loadView(ClassSubclassController.class, "class_subclass.fxml", (region, controller)
				-> mainPageController.setContent(container, region)));

		btnStatType.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_stat_types", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));

		btnFlags.setOnAction(event -> JavaFXUtil.loadView(TableViewMultiController.class, "tableview_multi.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_flags", false, true);
			controller.setTableContent(identifiers);
			controller.hideColumnId();
			mainPageController.setContent(container, region);
		}));

		btnInventoryType.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_inventory_type", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));

		btnQuality.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_quality", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));

		btnSpellTrigger.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_spell_trigger", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));

		btnBonding.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_bonding", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));

		btnSheath.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_sheath", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));

		btnRepRank.setOnAction(event -> JavaFXUtil.loadView(TableViewController.class, "tableview.fxml", (region, controller) -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvItemPath + "item_required_reputation_faction", true, false);
			controller.setTableContent(identifiers);
			controller.hideColumnValue();
			mainPageController.setContent(container, region);
		}));
	}
}

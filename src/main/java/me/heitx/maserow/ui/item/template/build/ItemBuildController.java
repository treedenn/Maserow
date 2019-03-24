package me.heitx.maserow.ui.item.template.build;

import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import me.heitx.maserow.io.CommonCSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.io.ItemCSV;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.Callback;
import me.heitx.maserow.ui.LayoutUtil;
import me.heitx.maserow.utils.MoneyUtil;
import me.heitx.maserow.utils.ResourceUtil;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.*;

@SuppressWarnings("Duplicates")
public class ItemBuildController implements Initializable {
	@FXML private VBox vboxPreview;
	@FXML private VBox vboxSelection;
	@FXML private HBox hboxEntry;
	@FXML private HBox hboxName;
	@FXML private HBox hboxBonding;
	@FXML private AnchorPane apSlotType;
	@FXML private AnchorPane apDamageSpeed;
	@FXML private HBox hboxArmor;
	@FXML private HBox hboxBlock;
	@FXML private HBox hboxStats;
	@FXML private HBox hboxResistance;
	@FXML private HBox hboxSockets;
	@FXML private HBox hboxClasses;
	@FXML private HBox hboxRaces;
	@FXML private HBox hboxDurability;
	@FXML private HBox hboxReqLevel;
	@FXML private HBox hboxItemLevel;
	@FXML private HBox hboxSellPrice;
	@FXML private HBox hboxBuyPrice;
	@FXML private HBox hboxDescription;

	@FXML private GridPane gpEdit;

	@FXML private ImageView ivSellGold;
	@FXML private ImageView ivSellSilver;
	@FXML private ImageView ivSellCopper;
	@FXML private ImageView ivBuyGold;
	@FXML private ImageView ivBuySilver;
	@FXML private ImageView ivBuyCopper;

	private Item item;

	private Set<String> editing;
	private boolean autoGeneratingEntry;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		editing = new HashSet<>();

		hboxEntry.setOnMouseClicked(this::onMouseClickEntry);
		hboxName.setOnMouseClicked(this::onMouseClickName);
		hboxBonding.setOnMouseClicked(this::onMouseClickBonding);
		apSlotType.setOnMouseClicked(this::onMouseClickSlotType);
		apDamageSpeed.setOnMouseClicked(this::onMouseClickDamageSpeed);
		hboxArmor.setOnMouseClicked(this::onMouseClickArmor);
		hboxBlock.setOnMouseClicked(this::onMouseClickBlock);
		hboxStats.setOnMouseClicked(this::onMouseClickStats);
		hboxResistance.setOnMouseClicked(this::onMouseClickResistance);
		hboxSockets.setOnMouseClicked(this::onMouseClickSockets);
		hboxClasses.setOnMouseClicked(this::onMouseClickClasses);
		hboxRaces.setOnMouseClicked(this::onMouseClickRaces);
		hboxDurability.setOnMouseClicked(this::onMouseClickDurability);
		hboxReqLevel.setOnMouseClicked(this::onMouseClickRequiredLevel);
		hboxItemLevel.setOnMouseClicked(this::onMouseClickItemLevel);
		hboxSellPrice.setOnMouseClicked(this::onMouseClickSellPrice);
		hboxBuyPrice.setOnMouseClicked(this::onMouseClickBuyPrice);
		hboxDescription.setOnMouseClicked(this::onMouseClickDescription);

		Image goldCoin = new Image(getClass().getClassLoader().getResource("currency/money-gold.png").toExternalForm());
		Image silverCoin = new Image(getClass().getClassLoader().getResource("currency/money-silver.png").toExternalForm());
		Image copperCoin = new Image(getClass().getClassLoader().getResource("currency/money-copper.png").toExternalForm());

		ivSellGold.setImage(goldCoin);
		ivSellSilver.setImage(silverCoin);
		ivSellCopper.setImage(copperCoin);
		ivBuyGold.setImage(goldCoin);
		ivBuySilver.setImage(silverCoin);
		ivBuyCopper.setImage(copperCoin);
	}

	public void setItem(Item item) {
		this.item = item;
	}

	private void onMouseClickEntry(MouseEvent event) {
		final String s = "Entry:";

		if(!editing.contains(s)) {
			editing.add(s);
			Label l = new Label(s);
			TextField tf = new TextField(String.valueOf(item.getEntry()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> { item.setEntry(Integer.parseInt(tf.getText())); });
		}
	}

	private void onMouseClickName(MouseEvent event) {
		final String name = "Name:";
		final String display = "Display:";
		final String quality = "Quality:";

		if(!editing.contains(name)) {
			editing.add(name);
			Label l = new Label(name);
			TextField tf = new TextField(item.getName());
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setName(tf.getText()));
		}

		if(!editing.contains(display)) {
			editing.add(display);
			Label l = new Label(display);
			TextField tf = new TextField(String.valueOf(item.getDisplayId()));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setDisplayId(Integer.parseInt(tf.getText())));
		}

		if(!editing.contains(quality)) {
			editing.add(quality);
			Label l = new Label(quality);

			List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_QUALITY);
			ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
			cb.getSelectionModel().select(Identifier.findsById(identifiers, item.getQuality()));
			cb.setMaxWidth(Double.MAX_VALUE);
			LayoutUtil.showOnlyNameOnCombobox(cb);

			addRow(l, cb, () -> item.setQuality(cb.getSelectionModel().getSelectedItem().getId()));
		}
	}

	private void onMouseClickBonding(MouseEvent event) {
		final String s = "Bonding:";

		if(!editing.contains(s)) {
			editing.add(s);
			Label l = new Label(s);

			List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_BONDING);
			ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
			cb.getSelectionModel().select(Identifier.findsById(identifiers, item.getBonding()));
			cb.setMaxWidth(Double.MAX_VALUE);
			LayoutUtil.showOnlyNameOnCombobox(cb);

			addRow(l, cb, () -> item.setBonding(cb.getSelectionModel().getSelectedItem().getId()));
		}
	}

	private void onMouseClickSlotType(MouseEvent event) {
		final String slot = "Slot:";
		final String type = "SelectionType:";

		if(!editing.contains(slot)) {
			editing.add(slot);
			Label l = new Label(slot);

			List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_INVENTORY_TYPE);
			ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
			cb.getSelectionModel().select(Identifier.findsById(identifiers, item.getInventoryType()));
			cb.setMaxWidth(Double.MAX_VALUE);
			LayoutUtil.showOnlyNameOnCombobox(cb);

			addRow(l, cb, () -> item.setInventoryType(cb.getSelectionModel().getSelectedItem().getId()));
		}

		if(!editing.contains(type)) {
			editing.add(type);
			Label l = new Label(type);

			List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_CLASSES);
			List<Identifier> subIdentifiers = DelimiterReader.getSubclasses(item.get_class());

			ComboBox<Identifier> cbClasses = new ComboBox<>(FXCollections.observableArrayList(identifiers));
			ComboBox<Identifier> cbSubclasses = new ComboBox<>(FXCollections.observableArrayList(subIdentifiers));

			cbClasses.getSelectionModel().select(Identifier.findsById(identifiers, item.get_class()));
			cbSubclasses.getSelectionModel().select(Identifier.findsByValue(subIdentifiers, item.getSubclass()));

			LayoutUtil.showOnlyNameOnCombobox(cbClasses);
			LayoutUtil.showOnlyNameOnCombobox(cbSubclasses);

			cbClasses.setOnAction(event1 -> {
				// Gets the subclasses of the selected class
				List<Identifier> clickedIdentifier = DelimiterReader.getSubclasses(cbClasses.getSelectionModel().getSelectedItem().getId());

				cbSubclasses.getItems().clear();
				cbSubclasses.getItems().addAll(clickedIdentifier);
				cbSubclasses.getSelectionModel().select(0);
			});

			HBox hbox = new HBox(2, cbClasses, cbSubclasses);

			addRow(l, hbox, () -> {
				item.set_class(cbClasses.getSelectionModel().getSelectedItem().getId());
				item.setSubclass((int) cbSubclasses.getSelectionModel().getSelectedItem().getValue());
			});
		}
	}

	private void onMouseClickDamageSpeed(MouseEvent event) {
		final String damageType1 = "Damage SelectionType 1:";
		final String damageType2 = "Damage SelectionType 2:";
		final String damage1 = "Damage 1:";
		final String damage2 = "Damage 2:";
		final String speed = "Speed (ms):";

		List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_DAMAGE_TYPE);

		if(!editing.contains(damageType1)) {
			editing.add(damageType1);
			Label l = new Label(damageType1);

			ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
			cb.getSelectionModel().select(Identifier.findsById(identifiers, item.getDamageType1()));
			cb.setMaxWidth(Double.MAX_VALUE);
			LayoutUtil.showOnlyNameOnCombobox(cb);

			addRow(l, cb, () -> item.setDamageType1(cb.getSelectionModel().getSelectedItem().getId()));
		}

		if(!editing.contains(damageType2)) {
			editing.add(damageType2);
			Label l = new Label(damageType2);

			ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
			cb.getSelectionModel().select(Identifier.findsById(identifiers, item.getDamageType2()));
			cb.setMaxWidth(Double.MAX_VALUE);
			LayoutUtil.showOnlyNameOnCombobox(cb);

			addRow(l, cb, () -> item.setDamageType2(cb.getSelectionModel().getSelectedItem().getId()));
		}

		if(!editing.contains(damage1)) {
			editing.add(damage1);
			Label l = new Label(damage1);

			TextField tfMinimum = new TextField(String.valueOf(item.getDamageMinimum1()));
			TextField tfMaximum = new TextField(String.valueOf(item.getDamageMaximum1()));
			tfMinimum.positionCaret(tfMinimum.getText().length());
			tfMaximum.positionCaret(tfMaximum.getText().length());

			tfMinimum.setTextFormatter(new TextFormatter<>(new FloatStringConverter(), 0F));
			tfMaximum.setTextFormatter(new TextFormatter<>(new FloatStringConverter(), 0F));

			HBox hbox = new HBox(5, tfMinimum, new Label(" - "), tfMaximum);

			addRow(l, hbox, () -> {
				item.setDamageMinimum1(Float.parseFloat(tfMinimum.getText()));
				item.setDamageMaximum1(Float.parseFloat(tfMaximum.getText()));
			});
		}

		if(!editing.contains(damage2)) {
			editing.add(damage2);
			Label l = new Label(damage2);

			TextField tfMinimum = new TextField(String.valueOf(item.getDamageMinimum2()));
			TextField tfMaximum = new TextField(String.valueOf(item.getDamageMaximum2()));
			tfMinimum.positionCaret(tfMinimum.getText().length());
			tfMaximum.positionCaret(tfMaximum.getText().length());

			tfMinimum.setTextFormatter(new TextFormatter<>(new FloatStringConverter(), 0F));
			tfMaximum.setTextFormatter(new TextFormatter<>(new FloatStringConverter(), 0F));

			HBox hbox = new HBox(5, tfMinimum, new Label(" - "), tfMaximum);

			addRow(l, hbox, () -> {
				item.setDamageMinimum2(Float.parseFloat(tfMinimum.getText()));
				item.setDamageMaximum2(Float.parseFloat(tfMaximum.getText()));
			});
		}

		if(!editing.contains(speed)) {
			editing.add(speed);
			Label l = new Label(speed);
			TextField tf = new TextField(String.valueOf(item.getDelay()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1000));

			addRow(l, tf, () -> item.setDelay(Integer.parseInt(tf.getText())));
		}
	}

	private void onMouseClickArmor(MouseEvent event) {
		final String armor = "Armor:";

		if(!editing.contains(armor)) {
			editing.add(armor);
			Label l = new Label(armor);
			TextField tf = new TextField(String.valueOf(item.getArmor()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setArmor(Integer.parseInt(tf.getText())));
		}
	}
	private void onMouseClickBlock(MouseEvent event) {
		final String block = "Block:";

		if(!editing.contains(block)) {
			editing.add(block);
			Label l = new Label(block);
			TextField tf = new TextField(String.valueOf(item.getBlock()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setBlock(Integer.parseInt(tf.getText())));
		}
	}

	private void onMouseClickStats(MouseEvent event) {
		final String stats = "Stats:";

		if(!editing.contains(stats)) {
			editing.add(stats);
			Label l = new Label(stats);

			StatsContainer statsContainer = new StatsContainer(item.getStatTypes(), item.getStatValues());

			addRow(l, statsContainer, () -> {
				int[] newTypes = statsContainer.getTypes();
				int[] newValues = statsContainer.getValues();

				item.setStatType1(newTypes[0]); item.setStatType2(newTypes[1]); item.setStatType3(newTypes[2]);
				item.setStatType4(newTypes[3]); item.setStatType5(newTypes[4]); item.setStatType6(newTypes[5]);
				item.setStatType7(newTypes[6]); item.setStatType8(newTypes[7]); item.setStatType9(newTypes[8]);
				item.setStatType10(newTypes[9]);

				item.setStatValue1(newValues[0]); item.setStatValue2(newValues[1]); item.setStatValue3(newValues[2]);
				item.setStatValue4(newValues[3]); item.setStatValue5(newValues[4]); item.setStatValue6(newValues[5]);
				item.setStatValue7(newValues[6]); item.setStatValue8(newValues[7]); item.setStatValue9(newValues[8]);
				item.setStatValue10(newValues[9]);

				item.setStatsCount(statsContainer.getStatsCount());
			});
		}
	}

	private void onMouseClickResistance(MouseEvent event) {
		final String resistance = "Resistance:";

		if(!editing.contains(resistance)) {
			editing.add(resistance);
			Label l = new Label(resistance);

			int[] resistanceValues = new int[] {
					item.getHolyRes(), item.getFireRes(), item.getFrostRes(),
					item.getNatureRes(), item.getShadowRes(), item.getArcaneRes()
			};

			TextField[] tfs = new TextField[resistanceValues.length];

			VBox container = new VBox(5);
			for(int i = 0; i < resistanceValues.length / 3; i++) {
				HBox hboxRow = new HBox(15);

				for(int j = 0; j < resistanceValues.length / 2; j++) {
					int index = i * 3 + j;

					HBox hbox = new HBox();

					tfs[index] = new TextField(String.valueOf(resistanceValues[index]));
					tfs[index].setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
					tfs[index].positionCaret(tfs[index].getText().length());

					String path = ResourceUtil.getResistancePath(index);
					ImageView image = new ImageView(getClass().getClassLoader().getResource(path).toExternalForm());
					image.setFitWidth(24);
					image.setFitHeight(24);

					hbox.getChildren().addAll(tfs[index], image);

					hboxRow.getChildren().addAll(hbox);
				}

				container.getChildren().add(hboxRow);
			}

			addRow(l, container, () -> {
				item.setHolyRes(Integer.parseInt(tfs[0].getText()));
				item.setShadowRes(Integer.parseInt(tfs[1].getText()));
				item.setFireRes(Integer.parseInt(tfs[2].getText()));
				item.setFrostRes(Integer.parseInt(tfs[3].getText()));
				item.setNatureRes(Integer.parseInt(tfs[4].getText()));
				item.setArcaneRes(Integer.parseInt(tfs[5].getText()));
			});
		}
	}

	private void onMouseClickSockets(MouseEvent event) {
		final String sockets = "Sockets:";

		if(!editing.contains(sockets)) {
			editing.add(sockets);
			Label l = new Label(sockets);

			List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_SOCKET_COLOR);

			GridPane gridPane = new GridPane();
			gridPane.setHgap(10);
			gridPane.setVgap(5);

			// links a radio button to a identifier for receiving a value for a given radio button
			Map<Toggle, Identifier> linker = new HashMap<>();

			ToggleGroup[] toggleGroups = new ToggleGroup[3];
			for(int i = 0; i < identifiers.size(); i++) {
				// Label title = new Label(identifiers.get(i).getName());

				ImageView image = new ImageView(getClass().getClassLoader().getResource("socket/" + identifiers.get(i).getName().toLowerCase() + ".png").toExternalForm());
				image.setFitWidth(24);
				image.setFitHeight(24);

				HBox hbox = new HBox(5);
				hbox.getChildren().addAll(/*title, */image);

				gridPane.add(hbox, i, 0);

				// centers the node within a column
				ColumnConstraints cc = new ColumnConstraints();
				cc.setHalignment(HPos.CENTER);
				gridPane.getColumnConstraints().add(cc);
			}

			for(int i = 0; i < toggleGroups.length; i++) {
				ToggleGroup tg = toggleGroups[i] = new ToggleGroup();

				for(int j = 0; j < identifiers.size(); j++) {
					RadioButton rd = new RadioButton();
					rd.setToggleGroup(tg);
					gridPane.add(rd, j, i + 1);

					linker.put(rd, identifiers.get(j));
				}
			}

			int[] socketColors = new int[] {
					item.getSocketColor_1(),
					item.getSocketColor_2(),
					item.getSocketColor_3(),
			};

			for(int i = 0; i < socketColors.length; i++) {
				toggleGroups[i].getToggles().get(identifiers.indexOf(Identifier.findsByValue(identifiers, socketColors[i]))).setSelected(true);
			}

			addRow(l, gridPane, () -> {
				item.setSocketColor_1((int) linker.get(toggleGroups[0].getSelectedToggle()).getValue());
				item.setSocketColor_2((int) linker.get(toggleGroups[1].getSelectedToggle()).getValue());
				item.setSocketColor_3((int) linker.get(toggleGroups[2].getSelectedToggle()).getValue());
			});
		}
	}

	private void onMouseClickClasses(MouseEvent event) {
		final String classes = "Classes:";

		if(!editing.contains(classes)) {
			editing.add(classes);
			Label l = new Label(classes);

			List<Identifier> identifiers = DelimiterReader.readColumns(CommonCSV.CLASSES);
			List<Integer> ids = Identifier.findIndicesByValue(identifiers, item.getAllowableClass());
			CheckComboBox<Identifier> ccb = new CheckComboBox<>();
			ccb.setPrefWidth(300);
			ccb.getItems().addAll(identifiers);
			LayoutUtil.showOnlyNameOnCombobox(ccb);

			for(Integer id : ids) {
				ccb.getCheckModel().check(id);
			}

			addRow(l, ccb, () -> {
				int value = (int) Identifier.calculateValue(ccb.getCheckModel().getCheckedItems(), identifiers);
				item.setAllowableClass(value);
			});
		}
	}

	private void onMouseClickRaces(MouseEvent event) {
		final String races = "Races:";

		if(!editing.contains(races)) {
			editing.add(races);
			Label l = new Label(races);

			List<Identifier> identifiers = DelimiterReader.readColumns(CommonCSV.RACES);
			List<Integer> ids = Identifier.findIndicesByValue(identifiers, item.getAllowableRace());
			CheckComboBox<Identifier> ccb = new CheckComboBox<>();
			ccb.setPrefWidth(300);
			ccb.getItems().addAll(identifiers);
			LayoutUtil.showOnlyNameOnCombobox(ccb);

			for(Integer id : ids) {
				ccb.getCheckModel().check(id);
			}

			addRow(l, ccb, () -> {
				int value = (int) Identifier.calculateValue(ccb.getCheckModel().getCheckedItems(), identifiers);
				item.setAllowableRace(value);
			});
		}
	}

	private void onMouseClickDurability(MouseEvent event) {
		final String durability = "Durability:";

		if(!editing.contains(durability)) {
			editing.add(durability);
			Label l = new Label(durability);
			TextField tf = new TextField(String.valueOf(item.getMaxDurability()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setMaxDurability(Integer.parseInt(tf.getText())));
		}
	}

	private void onMouseClickRequiredLevel(MouseEvent event) {
		final String reqLevel = "Req Level:";

		if(!editing.contains(reqLevel)) {
			editing.add(reqLevel);
			Label l = new Label(reqLevel);
			TextField tf = new TextField(String.valueOf(item.getRequiredLevel()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setRequiredLevel(Integer.parseInt(tf.getText())));
		}
	}

	private void onMouseClickItemLevel(MouseEvent event) {
		final String itemLevel = "Item Level:";

		if(!editing.contains(itemLevel)) {
			editing.add(itemLevel);
			Label l = new Label(itemLevel);
			TextField tf = new TextField(String.valueOf(item.getItemLevel()));
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
			tf.positionCaret(tf.getText().length());

			addRow(l, tf, () -> item.setItemLevel(Integer.parseInt(tf.getText())));
		}
	}

	private void onMouseClickSellPrice(MouseEvent event) {
		final String sell = "Sell:";

		if(!editing.contains(sell)) {
			editing.add(sell);
			Label l = new Label(sell);
			long[] money = MoneyUtil.totalToGSC(item.getSellPrice());

			moneyLayout(l, money, newTotal -> {
				item.setSellPrice(newTotal);
				return null;
			});
		}
	}

	private void onMouseClickBuyPrice(MouseEvent event) {
		final String buy = "Buy:";

		if(!editing.contains(buy)) {
			editing.add(buy);
			Label l = new Label(buy);
			long[] money = MoneyUtil.totalToGSC(item.getBuyPrice());

			moneyLayout(l, money, newTotal -> {
				item.setBuyPrice(newTotal);
				return null;
			});
		}
	}

	private void onMouseClickDescription(MouseEvent event) {
		final String description = "Description:";

		if(!editing.contains(description)) {
			editing.add(description);
			Label l = new Label(description);
			TextArea ta = new TextArea(item.getDescription());
			ta.positionCaret(ta.getText().length());
			ta.setWrapText(true);
			ta.setPrefWidth(300);
			ta.setPrefHeight(45);

			addRow(l, ta, () -> item.setDescription(ta.getText()));
		}
	}

	private void moneyLayout(Label l, long[] money, javafx.util.Callback<Long, Void> callback) {
		String[] moneyStrings = new String[] { "gold", "silver", "copper" };

		HBox hbox = new HBox(5);
		hbox.setAlignment(Pos.CENTER_LEFT);

		TextField[] tfMoney = new TextField[moneyStrings.length];
		for(int i = 0; i < tfMoney.length; i++) {
			tfMoney[i] = new TextField(String.valueOf(money[i]));
			tfMoney[i].setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));

			ImageView iv = new ImageView(getClass().getClassLoader().getResource("currency/money-" + moneyStrings[i] + ".png").toExternalForm());

			if(i == 0) {
				tfMoney[i].setPrefWidth(100);
			} else {
				tfMoney[i].setPrefWidth(25);
			}

			hbox.getChildren().addAll(tfMoney[i], iv);
		}

		addRow(l, hbox, () -> {
			long newTotal = MoneyUtil.gscToTotal(Integer.parseInt(tfMoney[0].getText()),
					Integer.parseInt(tfMoney[1].getText()),
					Integer.parseInt(tfMoney[2].getText()));

			callback.call(newTotal);
		});
	}

	private void addRow(Label l, Node middleNode, Callback onSaveCallback) {
		int newIndex = gpEdit.getRowConstraints().size();

		Button btnEscape = new Button("", new MaterialIconView(MaterialIcon.CLEAR));
		Button btnSave = new Button("", new MaterialIconView(MaterialIcon.EDIT));

		HBox hbox = new HBox(2, btnEscape, btnSave);
		hbox.setAlignment(Pos.CENTER);
		btnEscape.setOnAction(actionEvent -> {
			editing.remove(l.getText());
			removeRow(GridPane.getRowIndex(l));
		});
		btnSave.setOnAction(actionEvent -> {
			onSaveCallback.call();
			editing.remove(l.getText());
			removeRow(GridPane.getRowIndex(l));
		});
		middleNode.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
				if(event.getCode() == KeyCode.ENTER) {
					onSaveCallback.call();
				}

				editing.remove(l.getText());
				removeRow(GridPane.getRowIndex(l));
			}
		});

		RowConstraints rc = new RowConstraints();
		rc.setValignment(VPos.CENTER);

		gpEdit.getRowConstraints().add(rc);

		gpEdit.add(l, 0, newIndex);
		gpEdit.add(middleNode, 1, newIndex);
		gpEdit.add(hbox, 2, newIndex);

		middleNode.requestFocus();
	}

	private void removeRow(int row) {
		Set<Node> deleteNodes = new HashSet<>();

		for(Node child : gpEdit.getChildren()) {
			Integer rowIndex = GridPane.getRowIndex(child);

			rowIndex = rowIndex == null ? 0 : rowIndex;

			if(rowIndex > row) {
				GridPane.setRowIndex(child, rowIndex - 1);
			} else if (rowIndex == row){
				deleteNodes.add(child);
			}
		}

		gpEdit.getChildren().removeAll(deleteNodes);
		gpEdit.getRowConstraints().remove(row);
	}
}
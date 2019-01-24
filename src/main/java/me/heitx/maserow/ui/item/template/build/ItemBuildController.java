package me.heitx.maserow.ui.item.template.build;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import me.heitx.maserow.io.CSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.model.Resistance;
import me.heitx.maserow.ui.Callback;
import me.heitx.maserow.ui.NodeUtil;
import me.heitx.maserow.utils.MoneyUtil;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@SuppressWarnings("Duplicates")
public class ItemBuildController implements Initializable {
	@FXML VBox vboxBuild;
	@FXML private Label labelEntry;
	@FXML private Label labelName;
	@FXML private Label labelBonding;
	@FXML private AnchorPane anchorSlotAndType;
	@FXML private Label labelSlot;
	@FXML private Label labelType;
	@FXML private AnchorPane anchorDamageAndDelay;
	@FXML private Label labelDamage;
	@FXML private Label labelDelay;
	@FXML private Label labelArmor;
	@FXML private Label labelBlock;
	@FXML private Label labelStats;
	@FXML private Label labelResistance;
	@FXML private Label labelSocket;
	@FXML private Label labelClasses;
	@FXML private Label labelRaces;
	@FXML private Label labelDurability;
	@FXML private Label labelRequiredLevel;
	@FXML private Label labelItemLevel;
	@FXML private Label labelBuySell;
	@FXML private Label labelDescription;

	private Item item;

	private boolean autoGeneratingEntry;
	private boolean editing;

	@SuppressWarnings("Duplicates")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelEntry.setOnMouseClicked(this::onMouseClickLabelEntry);
		labelName.setOnMouseClicked(this::onMouseClickLabelName);
		labelBonding.setOnMouseClicked(this::onMouseClickLabelBonding);
		labelSlot.setOnMouseClicked(this::onMouseClickLabelSlot);
		labelType.setOnMouseClicked(this::onMouseClickLabelType);
		labelDamage.setOnMouseClicked(this::onMouseClickLabelDamage);
		labelDelay.setOnMouseClicked(this::onMouseClickLabelDelay);
		labelArmor.setOnMouseClicked(this::onMouseClickLabelArmor);
		labelBlock.setOnMouseClicked(this::onMouseClickLabelBlock);
		labelStats.setOnMouseClicked(this::onMouseClickLabelStats);
		labelResistance.setOnMouseClicked(this::onMouseClickLabelResistance);
		labelSocket.setOnMouseClicked(this::onMouseClickLabelSocket);
		labelClasses.setOnMouseClicked(this::onMouseClickLabelClasses);
		labelRaces.setOnMouseClicked(this::onMouseClickLabelRaces);
		labelDurability.setOnMouseClicked(this::onMouseClickLabelDurability);
		labelRequiredLevel.setOnMouseClicked(this::onMouseClickLabelRequiredLevel);
		labelItemLevel.setOnMouseClicked(this::onMouseClickLabelItemLevel);
		labelBuySell.setOnMouseClicked(this::onMouseClickLabelBuySell);
		labelDescription.setOnMouseClicked(this::onMouseClickLabelDescription);
	}

	public void setItem(Item item) {
		this.item = item;
	}

	private void onMouseClickLabelEntry(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getEntry()));
		CheckBox cb = new CheckBox("Auto Generate Entry");
		cb.setDisable(true);

		handleNodes(event, () -> {
			item.setEntry(Integer.parseInt(tf.getText()));
		}, tf, cb);
	}

	private void onMouseClickLabelName(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_QUALITY);

		TextField tfName = new TextField(item.getName());
		TextField tfDisplay = new TextField(String.valueOf(item.getDisplayId()));
		ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
		cb.getSelectionModel().select(Identifier.findById(identifiers, item.getQuality()));

		NodeUtil.showOnlyNameOnCombobox(cb);

		handleNodes(event, () -> {
			item.setName(tfName.getText());
			item.setDisplayId(Integer.parseInt(tfDisplay.getText()));
			item.setQuality(cb.getSelectionModel().getSelectedItem().getId());
		}, tfName, tfDisplay, cb);
	}

	private void onMouseClickLabelBonding(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_BONDING);

		ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
		cb.getSelectionModel().select(Identifier.findById(identifiers, item.getBonding()));
		NodeUtil.showOnlyNameOnCombobox(cb);

		handleNodes(event, () -> {
			item.setBonding(cb.getSelectionModel().getSelectedItem().getId());
			// System.out.println("Bonding: " + cb.getSelectionModel().getSelectedItem().getName());
		}, cb);
	}

	private void onMouseClickLabelSlot(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_INVENTORY_TYPE);

		ComboBox<Identifier> cb = new ComboBox<>(FXCollections.observableArrayList(identifiers));
		cb.getSelectionModel().select(Identifier.findById(identifiers, item.getInventoryType()));
		NodeUtil.showOnlyNameOnCombobox(cb);

		handleNodes(event, () -> {
			item.setInventoryType(cb.getSelectionModel().getSelectedItem().getId());
			// System.out.println("Slot: " + cb.getSelectionModel().getSelectedItem().getName());
		}, cb);
	}

	private void onMouseClickLabelType(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_CLASSES);
		List<Identifier> subIdentifiers = DelimiterReader.getSubclasses(item.getiClass());

		ComboBox<Identifier> cbClasses = new ComboBox<>(FXCollections.observableArrayList(identifiers));
		ComboBox<Identifier> cbSubclasses = new ComboBox<>(FXCollections.observableArrayList(subIdentifiers));

		cbClasses.getSelectionModel().select(Identifier.findById(identifiers, item.getiClass()));
		cbSubclasses.getSelectionModel().select(Identifier.findByValue(subIdentifiers, item.getSubclass()));

		NodeUtil.showOnlyNameOnCombobox(cbClasses);
		NodeUtil.showOnlyNameOnCombobox(cbSubclasses);

		cbClasses.setOnAction(event1 -> {
			// Gets the subclasses of the selected class
			List<Identifier> clickedIdentifier = DelimiterReader.getSubclasses(cbClasses.getSelectionModel().getSelectedItem().getId());

			cbSubclasses.getItems().clear();
			cbSubclasses.getItems().addAll(clickedIdentifier);
			cbSubclasses.getSelectionModel().select(0);
		});

		handleNodes(event, () -> {
			item.setiClass(cbClasses.getSelectionModel().getSelectedItem().getId());
			item.setSubclass((int) cbSubclasses.getSelectionModel().getSelectedItem().getValue());
		}, cbClasses, cbSubclasses);
	}

	private void onMouseClickLabelDamage(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_DAMAGE_TYPE);

		ComboBox<Identifier> cbDamageType1 = new ComboBox<>(FXCollections.observableArrayList(identifiers));
		ComboBox<Identifier> cbDamageType2 = new ComboBox<>(FXCollections.observableArrayList(identifiers));

		NodeUtil.showOnlyNameOnCombobox(cbDamageType1);
		NodeUtil.showOnlyNameOnCombobox(cbDamageType2);

		cbDamageType1.getSelectionModel().select(Identifier.findById(identifiers, item.getDamageType1()));
		cbDamageType2.getSelectionModel().select(Identifier.findById(identifiers, item.getDamageType2()));

		TextField tfDamageMin1 = new TextField(String.valueOf(item.getDamageMinimum1()));
		TextField tfDamageMin2 = new TextField(String.valueOf(item.getDamageMinimum2()));

		TextField tfDamageMax1 = new TextField(String.valueOf(item.getDamageMaximum1()));
		TextField tfDamageMax2 = new TextField(String.valueOf(item.getDamageMaximum2()));

		HBox hboxDamageContainer1 = new HBox(5, cbDamageType1, tfDamageMin1, new Label(" - "), tfDamageMax1);
		HBox hboxDamageContainer2 = new HBox(5, cbDamageType2, tfDamageMin2, new Label(" - "), tfDamageMax2);

		VBox container = new VBox(5, hboxDamageContainer1, hboxDamageContainer2);

		handleNodes(event, () -> {
			item.setDamageType1(cbDamageType1.getSelectionModel().getSelectedItem().getId());
			item.setDamageType2(cbDamageType2.getSelectionModel().getSelectedItem().getId());
			item.setDamageMinimum1(Float.parseFloat(tfDamageMin1.getText()));
			item.setDamageMinimum2(Float.parseFloat(tfDamageMin2.getText()));
			item.setDamageMaximum1(Float.parseFloat(tfDamageMax1.getText()));
			item.setDamageMaximum2(Float.parseFloat(tfDamageMax2.getText()));
		}, container);
	}

	private void onMouseClickLabelDelay(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getDelay()));

		handleNodes(event, () -> {
			item.setDelay(Integer.parseInt(tf.getText()));
		}, tf);
	}

	private void onMouseClickLabelArmor(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getArmor()));

		handleNodes(event, () -> {
			item.setArmor(Integer.parseInt(tf.getText()));
		}, tf);
	}

	private void onMouseClickLabelBlock(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getBlock()));

		handleNodes(event, () -> {
			item.setBlock(Integer.parseInt(tf.getText()));
		}, tf);
	}

	private void onMouseClickLabelStats(MouseEvent event) {
		StatsContainer statsContainer = new StatsContainer(item.getStats());

		handleNodes(event, () -> {
			item.setStats(statsContainer.getItemStats());
			item.setStatsCount(statsContainer.getStatsCount());
		}, statsContainer);
	}

	private void onMouseClickLabelResistance(MouseEvent event) {
		int[] resistanceValues = new int[] {
				item.getResistance().getHoly(), item.getResistance().getShadow(), item.getResistance().getFire(),
				item.getResistance().getFrost(), item.getResistance().getNature(), item.getResistance().getArcane()
		};

		TextField[] tfs = new TextField[resistanceValues.length];

		VBox container = new VBox(5);
		for(int i = 0; i < resistanceValues.length / 3; i++) {
			HBox hboxRow = new HBox(15);

			for(int j = 0; j < resistanceValues.length / 2; j++) {
				int index = i * 3 + j;

				HBox hbox = new HBox();

				tfs[index] = new TextField(String.valueOf(resistanceValues[index]));

				String path = "resistance/" + Resistance.NAMES[index].toLowerCase() + ".png";
				ImageView image = new ImageView(getClass().getClassLoader().getResource(path).toExternalForm());
				image.setFitWidth(24);
				image.setFitHeight(24);

				hbox.getChildren().addAll(tfs[index], image);

				hboxRow.getChildren().addAll(hbox);
			}

			container.getChildren().add(hboxRow);
		}

		handleNodes(event, () -> {
			item.getResistance().setHoly(Integer.parseInt(tfs[0].getText()));
			item.getResistance().setShadow(Integer.parseInt(tfs[1].getText()));
			item.getResistance().setFire(Integer.parseInt(tfs[2].getText()));
			item.getResistance().setFrost(Integer.parseInt(tfs[3].getText()));
			item.getResistance().setNature(Integer.parseInt(tfs[4].getText()));
			item.getResistance().setArcane(Integer.parseInt(tfs[5].getText()));
		}, container);
	}

	private void onMouseClickLabelSocket(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_SOCKET_COLOR);

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
			toggleGroups[i].getToggles().get(identifiers.indexOf(Identifier.findByValue(identifiers, socketColors[i]))).setSelected(true);
		}

		handleNodes(event, () -> {
			item.setSocketColor_1((int) linker.get(toggleGroups[0].getSelectedToggle()).getValue());
			item.setSocketColor_2((int) linker.get(toggleGroups[1].getSelectedToggle()).getValue());
			item.setSocketColor_3((int) linker.get(toggleGroups[2].getSelectedToggle()).getValue());
		}, gridPane);
	}

	private void onMouseClickLabelClasses(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.CLASSES);

		List<Integer> ids = Identifier.findIdsByValue(identifiers, item.getAllowableClass());

		CheckComboBox<Identifier> ccb = new CheckComboBox<>();
		ccb.getItems().addAll(identifiers);

		for(Integer id : ids) {
			ccb.getCheckModel().check(id);
		}

		handleNodes(event, () -> {
			int value = (int) Identifier.calculateValue(ccb.getCheckModel().getCheckedItems(), identifiers);
			item.setAllowableClass(value);
		}, ccb);
	}

	private void onMouseClickLabelRaces(MouseEvent event) {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.RACES);

		List<Integer> ids = Identifier.findIdsByValue(identifiers, item.getAllowableRace());

		CheckComboBox<Identifier> ccb = new CheckComboBox<>();
		ccb.getItems().addAll(identifiers);

		for(Integer id : ids) {
			ccb.getCheckModel().check(id);
		}

		handleNodes(event, () -> {
			int value = (int) Identifier.calculateValue(ccb.getCheckModel().getCheckedItems(), identifiers);
			item.setAllowableRace(value);
		}, ccb);
	}

	private void onMouseClickLabelDurability(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getMaxDurability()));

		handleNodes(event, () -> {
			item.setMaxDurability(Integer.parseInt(tf.getText()));
		}, tf);
	}

	private void onMouseClickLabelRequiredLevel(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getRequiredLevel()));

		handleNodes(event, () -> {
			item.setRequiredLevel(Integer.parseInt(tf.getText()));
		}, tf);
	}

	private void onMouseClickLabelItemLevel(MouseEvent event) {
		TextField tf = new TextField(String.valueOf(item.getItemLevel()));

		handleNodes(event, () -> {
			item.setItemLevel(Integer.parseInt(tf.getText()));
		}, tf);
	}

	private void onMouseClickLabelBuySell(MouseEvent event) {
		String[] strings = new String[] { "Buy: ", "Sell: " };
		String[] moneyStrings = new String[] { "gold", "silver", "copper" };

		TextField[][] textFields = new TextField[strings.length][moneyStrings.length];
		long[][] money = new long[][] {
				MoneyUtil.totalToGSC(item.getBuyPrice()),
				MoneyUtil.totalToGSC(item.getSellPrice())
		};

		VBox container = new VBox(5);
		for(int i = 0; i < strings.length; i++) {
			HBox hbox = new HBox(5);
			hbox.setAlignment(Pos.CENTER_LEFT);

			Label label = new Label(strings[i]);
			label.setMinWidth(Region.USE_PREF_SIZE);
			hbox.getChildren().add(label);

			for(int j = 0; j < moneyStrings.length; j++) {
				ImageView iv = new ImageView(getClass().getClassLoader().getResource("currency/money-" + moneyStrings[j] + ".png").toExternalForm());
				TextField tf = new TextField(String.valueOf(money[i][j]));

				textFields[i][j] = tf;

				hbox.getChildren().addAll(tf, iv);
			}

			container.getChildren().add(hbox);
		}

		handleNodes(event, () -> {
			long buyTotal = MoneyUtil.gscToTotal(Long.parseLong(textFields[0][0].getText()), Integer.parseInt(textFields[0][1].getText()), Integer.parseInt(textFields[0][2].getText()));
			long sellTotal = MoneyUtil.gscToTotal(Long.parseLong(textFields[1][0].getText()), Integer.parseInt(textFields[1][1].getText()), Integer.parseInt(textFields[1][2].getText()));

			item.setBuyPrice(buyTotal);
			item.setSellPrice(sellTotal);
		}, container);
	}


	private void onMouseClickLabelDescription(MouseEvent event) {
		TextField tf = new TextField(item.getDescription());

		handleNodes(event, () -> {
			item.setDescription(tf.getText());
		}, tf);
	}

	/* Methods to use for the label clicking */

	private void handleNodes(MouseEvent event, Callback callback, Node... nodesToAdd) {
		if(!editing) {
			editing = true;

			Label label = (Label) event.getSource();
			HBox container = (HBox) label.getParent();

			container.getChildren().clear();
			container.getChildren().addAll(nodesToAdd);
			nodesToAdd[0].requestFocus();

			onKeyPressedEnter(label, container, callback);
		}
	}

	private void onKeyPressedEnter(Label labelHolder, Pane container, Callback callback) {
		container.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
				if(event.getCode() == KeyCode.ENTER) {
					callback.call();
				}

				editing = false;

				container.getChildren().clear();
				container.getChildren().addAll(labelHolder);
				container.setOnKeyPressed(null);
			}
		});
	}
}
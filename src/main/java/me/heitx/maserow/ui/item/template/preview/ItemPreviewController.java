package me.heitx.maserow.ui.item.template.preview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import me.heitx.maserow.io.CommonCSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.io.ItemCSV;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.LayoutUtil;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.utils.MoneyUtil;
import me.heitx.maserow.utils.NumberUtil;
import me.heitx.maserow.utils.ResourceUtil;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ItemPreviewController implements Initializable, Updateable {
	@FXML public VBox vboxPreview;
	@FXML private Label labelName;
	@FXML private Label labelBonding;
	@FXML private Label labelUnique;
	@FXML private GridPane gpSlotType;
	@FXML private Label labelSlot;
	@FXML private Label labelType;
	@FXML private GridPane paneDamage;
	@FXML private Label labelDamageMin;
	@FXML private Label labelDamageMax;
	@FXML private Label labelDelay;
	@FXML private Label labelDPS;
	@FXML private HBox hboxArmor;
	@FXML private Label labelArmor;
	@FXML private HBox hboxBlock;
	@FXML private Label labelBlock;
	@FXML private VBox vboxStats;
	@FXML private VBox vboxResistance;
	@FXML private VBox vboxSocket;
	@FXML private HBox hboxClasses;
	@FXML private Label labelClasses;
	@FXML private HBox hboxRaces;
	@FXML private Label labelRaces;
	@FXML private HBox hboxDurability;
	@FXML private Label labelDurability;
	@FXML private HBox hboxRequiredLevel;
	@FXML private Label labelRequiredLevel;
	@FXML private HBox hboxItemLevel;
	@FXML private Label labelItemLevel;
	@FXML private VBox vboxEquipStats;

	@FXML private HBox hboxSellPrice;
	@FXML private Label labelSellGold;
	@FXML private Label labelSellSilver;
	@FXML private Label labelSellCopper;
	@FXML private ImageView ivSellGold;
	@FXML private ImageView ivSellSilver;
	@FXML private ImageView ivSellCopper;

	@FXML private HBox hboxBuyPrice;
	@FXML private Label labelBuyGold;
	@FXML private Label labelBuySilver;
	@FXML private Label labelBuyCopper;
	@FXML private ImageView ivBuyGold;
	@FXML private ImageView ivBuySilver;
	@FXML private ImageView ivBuyCopper;

	@FXML private Label labelDescription;

	private static final String[] QUALITY_HEX = new String[] {
		"#9d9d9d",
		"#ffffff",
		"#1eff00",
		"#0070dd",
		"#a335ee",
		"#ff8000",
		"#e6cc80",
		"#00ccff"
	};

	private Item item;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labelDescription.setStyle("-fx-text-fill: limegreen;");

		// TODO: make so the fxml reads correctly!
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

	@Override
	public void update() {
		if(item != null) {
			updateContent();
			updateVisibility();
		}
	}

	public void setItem(Item item) {
		this.item = item;
	}

	private float calculateDPS() {
		float minimum = 0, maximum = 0;

		minimum += item.getDamageMinimum1() + item.getDamageMinimum2();
		maximum += item.getDamageMaximum1() + item.getDamageMaximum2();

		return ((minimum + maximum) / 2) / (item.getDelay() / 1000F);
	}

	private String getMinimumDamage() {
		return String.valueOf(item.getDamageMinimum1() + item.getDamageMinimum2());
	}

	private String getMaximumDamage() {
		return String.valueOf(item.getDamageMaximum1() + item.getDamageMaximum2());
	}

	private void handleStats() {
		List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_STAT_TYPES);

		vboxStats.getChildren().clear();
		vboxEquipStats.getChildren().clear();

		int[] types = item.getStatTypes();
		int[] values = item.getStatValues();

		for(int i = 0; i < types.length; i++) {
			int type = types[i];
			int value = values[i];

			if(value != 0) {
				String typeName = Identifier.findsById(identifiers, type).getName();
				String s;

				if(type <= 7) {
					s = "+" + value + " " + typeName;
				} else {
					String word = type < 37 ? "Improves " : "Increases ";
					s = "Equip: " + word + typeName.toLowerCase() + " by " + value + ".";
				}

				Label label = new Label(s);

				if(type <= 7) {
					label.setStyle("-fx-text-fill: white;");
					vboxStats.getChildren().add(label);
				} else {
					label.setStyle("-fx-text-fill: limegreen;");
					vboxEquipStats.getChildren().add(label);
				}
			}
		}
	}

	private void handleResistance() {
		vboxResistance.getChildren().clear();

		int[] resistance = item.getResistance();

		if(NumberUtil.isAllSameNumber(resistance)) {
			if(resistance[0] != 0) {
				Label l = new Label("+" + resistance[0] + " All Resistance");
				l.setStyle("-fx-text-fill: white;");
				vboxResistance.getChildren().add(l);
			}
		} else {
			for(int i = 0; i < resistance.length; i++) {
				if(resistance[i] != 0) {
					Label l = new Label("+" + resistance[i] + " " + ResourceUtil.RESISTANCE_NAMES[i] + " Resistance");
					l.setStyle("-fx-text-fill: white;");
					vboxResistance.getChildren().add(l);
				}
			}
		}
	}

	private void handleSocket() {
		List<Identifier> identifiers = DelimiterReader.readColumns(ItemCSV.ITEM_SOCKET_COLOR);

		vboxSocket.getChildren().clear();

		int[] sockets = new int[] {
				item.getSocketColor_1(),
				item.getSocketColor_2(),
				item.getSocketColor_3()
		};

		for(int socket : sockets) {
			if(socket != 0) {
				String socketName = Identifier.findsByValue(identifiers, socket).getName();
				ImageView image = new ImageView(getClass().getClassLoader().getResource("socket/" + socketName.toLowerCase() + ".png").toString());
				Label label = new Label(socketName + " Socket");

				HBox hbox = new HBox(10, image, label);
				vboxSocket.getChildren().add(hbox);
			}
		}
	}

	private boolean hideEquipStats() {
		boolean hide = true;

		for(int type : item.getStatTypes()) {
			if(type > 7) {
				hide = false;
				break;
			}
		}

		return hide;
	}

	private boolean hideResistance() {
		int[] resistance = item.getResistance();
		return resistance[0] == 0 && NumberUtil.isAllSameNumber(resistance);
	}

	private boolean hideSocket() {
		return item.getSocketColor_1() == 0 && // 0 = none
				item.getSocketColor_2() == 0 &&
				item.getSocketColor_3() == 0;
	}

	private String getCharacterClasses() {
		List<Identifier> identifiers = DelimiterReader.readColumns(CommonCSV.CLASSES);

		StringBuilder builder = new StringBuilder();

		List<Integer> indices = Identifier.findIndicesByValue(identifiers, item.getAllowableClass());
		appendStrings(identifiers, builder, indices);

		return builder.toString();
	}

	private String getCharacterRaces() {
		List<Identifier> identifiers = DelimiterReader.readColumns(CommonCSV.RACES);

		StringBuilder builder = new StringBuilder();

		List<Integer> indices = Identifier.findIndicesByValue(identifiers, item.getAllowableRace());
		appendStrings(identifiers, builder, indices);

		return builder.toString();
	}

	private void appendStrings(List<Identifier> identifiers, StringBuilder builder, List<Integer> indices) {
		if(indices.size() == identifiers.size()) {
			builder.append("All");
		} else {
			for(Integer id : indices) {
				builder.append(identifiers.get(id).getName());
				builder.append(", ");
			}

			builder.delete(builder.length() - 2, builder.length());
		}
	}

	private void updateContent() {
		List<Identifier> bondings = DelimiterReader.readColumns(ItemCSV.ITEM_BONDING);
		List<Identifier> inventoryType = DelimiterReader.readColumns(ItemCSV.ITEM_INVENTORY_TYPE);
		List<Identifier> subclass = DelimiterReader.getSubclasses(item.get_class());

		long[] sellMoney = MoneyUtil.totalToGSC(item.getSellPrice());
		long[] buyMoney = MoneyUtil.totalToGSC(item.getBuyPrice());

		String durability = String.valueOf(item.getMaxDurability());

		labelName.setText(item.getName());
		labelName.setStyle("-fx-text-fill: " + QUALITY_HEX[item.getQuality()] + ";");
		labelBonding.setText(Identifier.findsById(bondings, item.getBonding()).getName());
		labelUnique.setText(item.getMaxCount() == 1 ? "Unique" : "Unique (" + item.getMaxCount() + ")");
		labelSlot.setText(Identifier.findsById(inventoryType, item.getInventoryType()).getName());
		labelType.setText(Identifier.findsByValue(subclass, item.getSubclass()).getName());
		labelDamageMin.setText(getMinimumDamage());
		labelDamageMax.setText(getMaximumDamage());
		labelDelay.setText(String.format(Locale.US, "%.2f", item.getDelay() / 1000.0));
		labelDPS.setText(String.format(Locale.US, "(%.2f damage per second)", calculateDPS()));
		labelArmor.setText(String.valueOf(item.getArmor()));
		labelBlock.setText(String.valueOf(item.getBlock()));
		handleStats();
		handleResistance();
		handleSocket();
		labelClasses.setText(getCharacterClasses());
		labelRaces.setText(getCharacterRaces());
		labelDurability.setText(durability + " / " + durability);
		labelRequiredLevel.setText(String.valueOf(item.getRequiredLevel()));
		labelItemLevel.setText(String.valueOf(item.getItemLevel()));
		labelSellGold.setText(String.valueOf(sellMoney[0]));
		labelSellSilver.setText(String.valueOf(sellMoney[1]));
		labelSellCopper.setText(String.valueOf(sellMoney[2]));
		labelBuyGold.setText(String.valueOf(buyMoney[0]));
		labelBuySilver.setText(String.valueOf(buyMoney[1]));
		labelBuyCopper.setText(String.valueOf(buyMoney[2]));
		labelDescription.setText("\"" + item.getDescription() + "\"");
	}

	private void updateVisibility() {
		LayoutUtil.toggle(labelBonding, item.getBonding() != 0); // 0 = NO_BOUNDS
		LayoutUtil.toggle(labelUnique, item.getMaxCount() != 0); // no max count
		LayoutUtil.toggle(labelSlot, item.getInventoryType() != 0); // 0 = NON_EQUIPABLE
		LayoutUtil.toggle(labelType, item.get_class() != 12); //
		LayoutUtil.toggle(gpSlotType, labelSlot.isVisible() || labelType.isVisible());
		LayoutUtil.toggle(paneDamage, item.get_class() == 2); // 2 = WEAPON
		LayoutUtil.toggle(labelDPS, item.get_class() == 2); // 2 = WEAPON
		LayoutUtil.toggle(hboxArmor, item.getArmor() != 0);
		LayoutUtil.toggle(hboxBlock, item.getBlock() != 0);
		LayoutUtil.toggle(vboxStats, item.getStatsCount() != 0);
		LayoutUtil.toggle(vboxEquipStats, !hideEquipStats());
		LayoutUtil.toggle(vboxResistance, !hideResistance());
		LayoutUtil.toggle(vboxSocket, !hideSocket());
		LayoutUtil.toggle(hboxClasses, item.getAllowableClass() != -1); // -1 = all classes
		LayoutUtil.toggle(hboxRaces, item.getAllowableRace() != -1); // -1 = all races
		LayoutUtil.toggle(hboxDurability, item.getMaxDurability() > 0);
		LayoutUtil.toggle(hboxRequiredLevel, item.getRequiredLevel() > 0);
		LayoutUtil.toggle(hboxItemLevel, item.getRequiredLevel() > 0);
		LayoutUtil.toggle(hboxSellPrice, item.getSellPrice() > 0);
		LayoutUtil.toggle(hboxBuyPrice, item.getBuyPrice() > 0);
		LayoutUtil.toggle(labelDescription, !item.getDescription().isEmpty());
	}
}
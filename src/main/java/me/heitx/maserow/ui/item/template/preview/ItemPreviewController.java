package me.heitx.maserow.ui.item.template.preview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.utils.MoneyUtil;
import me.heitx.maserow.io.CSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.model.ItemStat;
import me.heitx.maserow.model.Resistance;
import me.heitx.maserow.ui.NodeUtil;

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

	private static final String[] hexQualities = new String[] {
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
		labelDescription.setTextFill(Color.LIMEGREEN);

		String path = "currency/money-";
		ivSellGold.setImage(new Image(getClass().getClassLoader().getResource(path + "gold.png").toExternalForm()));
		ivBuyGold.setImage(new Image(getClass().getClassLoader().getResource(path + "gold.png").toExternalForm()));
		ivSellSilver.setImage(new Image(getClass().getClassLoader().getResource(path + "silver.png").toExternalForm()));
		ivBuySilver.setImage(new Image(getClass().getClassLoader().getResource(path + "silver.png").toExternalForm()));
		ivSellCopper.setImage(new Image(getClass().getClassLoader().getResource(path + "copper.png").toExternalForm()));
		ivBuyCopper.setImage(new Image(getClass().getClassLoader().getResource(path + "copper.png").toExternalForm()));
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
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_STAT_TYPES);

		vboxStats.getChildren().clear();
		vboxEquipStats.getChildren().clear();

		for(ItemStat itemStat : item.getStats()) {
			if(itemStat.getValue() != 0) {
				Label label = new Label(itemStat.getDisplayText(Identifier.findById(identifiers, itemStat.getType()).getName()));

				if(itemStat.getType() <= 7) {
					label.setTextFill(Color.WHITE);
					vboxStats.getChildren().add(label);
				} else {
					label.setTextFill(Color.LIMEGREEN);
					vboxEquipStats.getChildren().add(label);
				}
			}
		}
	}

	private void handleResistance() {
		vboxResistance.getChildren().clear();

		Resistance resistance = item.getResistance();

		if(resistance.allSame()) {
			if(resistance.getHoly() != 0) {
				Label l = new Label("+" + resistance.getHoly() + " All Resistance");
				l.setTextFill(Color.WHITE);
				vboxResistance.getChildren().add(l);
			}
		} else {
			int[] resistValues = new int[] {
					resistance.getHoly(), resistance.getShadow(), resistance.getFire(),
					resistance.getFrost(), resistance.getNature(), resistance.getArcane()
			};

			for(int i = 0; i < resistValues.length; i++) {
				if(resistValues[i] != 0) {
					Label l = new Label("+" + resistValues[i] + " " + Resistance.NAMES[i] + " Resistance");
					l.setTextFill(Color.WHITE);
					vboxResistance.getChildren().add(l);
				}
			}
		}
	}

	private void handleSocket() {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.ITEM_SOCKET_COLOR);

		vboxSocket.getChildren().clear();

		int[] sockets = new int[] {
				item.getSocketColor_1(),
				item.getSocketColor_2(),
				item.getSocketColor_3()
		};

		for(int socket : sockets) {
			if(socket != 0) {
				String socketName = Identifier.findByValue(identifiers, socket).getName();
				ImageView image = new ImageView(getClass().getClassLoader().getResource("socket/" + socketName.toLowerCase() + ".png").toString());
				Label label = new Label(socketName + " Socket");

				HBox hbox = new HBox(10, image, label);
				vboxSocket.getChildren().add(hbox);
			}
		}
	}

	private boolean hideEquipStats() {
		boolean hide = true;

		for(ItemStat itemStat : item.getStats()) {
			if(itemStat.getType() > 7) {
				hide = false;
				break;
			}
		}

		return hide;
	}

	private boolean hideResistance() {
		return item.getResistance().allZero();
	}

	private boolean hideSocket() {
		return item.getSocketColor_1() == 0 && // 0 = none
				item.getSocketColor_2() == 0 &&
				item.getSocketColor_3() == 0;
	}

	private String getCharacterClasses() {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.CLASSES);

		StringBuilder builder = new StringBuilder();

		List<Integer> ids = Identifier.findIdsByValue(identifiers, item.getAllowableClass());
		appendStrings(identifiers, builder, ids);

		return builder.toString();
	}

	private String getCharacterRaces() {
		List<Identifier> identifiers = DelimiterReader.readColumns(CSV.RACES);

		StringBuilder builder = new StringBuilder();

		List<Integer> ids = Identifier.findIdsByValue(identifiers, item.getAllowableRace());
		appendStrings(identifiers, builder, ids);

		return builder.toString();
	}

	private void appendStrings(List<Identifier> identifiers, StringBuilder builder, List<Integer> ids) {
		if(ids.size() == identifiers.size()) {
			builder.append("All");
		} else {
			for(int i = 0; i < ids.size(); i++) {
				builder.append(identifiers.get(ids.get(i)).getName());
				builder.append(", ");
			}

			builder.delete(builder.length() - 2, builder.length());
		}
	}

	private void updateContent() {
		List<Identifier> bondings = DelimiterReader.readColumns(CSV.ITEM_BONDING);
		List<Identifier> inventoryType = DelimiterReader.readColumns(CSV.ITEM_INVENTORY_TYPE);
		List<Identifier> subclass = DelimiterReader.getSubclasses(item.getiClass());

		long[] sellMoney = MoneyUtil.totalToGSC(item.getSellPrice());
		long[] buyMoney = MoneyUtil.totalToGSC(item.getBuyPrice());

		String durability = String.valueOf(item.getMaxDurability());

		labelName.setText(item.getName());
		labelName.setTextFill(Color.web(hexQualities[item.getQuality()]));
		labelBonding.setText(Identifier.findById(bondings, item.getBonding()).getName());
		labelUnique.setText(item.getMaxCount() == 1 ? "Unique" : "Unique (" + item.getMaxCount() + ")");
		labelSlot.setText(Identifier.findById(inventoryType, item.getInventoryType()).getName());
		labelType.setText(Identifier.findByValue(subclass, item.getSubclass()).getName());
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
		NodeUtil.toggle(labelBonding, item.getBonding() != 0); // 0 = NO_BOUNDS
		NodeUtil.toggle(labelUnique, item.getMaxCount() != 0); // no max count
		NodeUtil.toggle(labelSlot, item.getInventoryType() != 0); // 0 = NON_EQUIPABLE
		NodeUtil.toggle(labelType, item.getiClass() != 12); //
		NodeUtil.toggle(gpSlotType, labelSlot.isVisible() || labelType.isVisible());
		NodeUtil.toggle(paneDamage, item.getiClass() == 2); // 2 = WEAPON
		NodeUtil.toggle(labelDPS, item.getiClass() == 2); // 2 = WEAPON
		NodeUtil.toggle(hboxArmor, item.getArmor() != 0);
		NodeUtil.toggle(hboxBlock, item.getBlock() != 0);
		NodeUtil.toggle(vboxStats, item.getStatsCount() != 0);
		NodeUtil.toggle(vboxEquipStats, !hideEquipStats());
		NodeUtil.toggle(vboxResistance, !hideResistance());
		NodeUtil.toggle(vboxSocket, !hideSocket());
		NodeUtil.toggle(hboxClasses, item.getAllowableClass() != -1); // -1 = all classes
		NodeUtil.toggle(hboxRaces, item.getAllowableRace() != -1); // -1 = all races
		NodeUtil.toggle(hboxDurability, item.getMaxDurability() > 0);
		NodeUtil.toggle(hboxRequiredLevel, item.getRequiredLevel() > 0);
		NodeUtil.toggle(hboxItemLevel, item.getRequiredLevel() > 0);
		NodeUtil.toggle(hboxSellPrice, item.getSellPrice() > 0);
		NodeUtil.toggle(hboxBuyPrice, item.getBuyPrice() > 0);
		NodeUtil.toggle(labelDescription, !item.getDescription().isEmpty());
	}
}
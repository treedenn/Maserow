package me.heitx.maserow.ui.creature.template;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Creature;
import me.heitx.maserow.ui.Updateable;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatureTemplateController implements Initializable, Updateable {
	@FXML private TilePane tpLayout; // TODO: Make change this to a flow pane so the grid panes can be different sizes.
	@FXML private TextField tfEntry;
	@FXML private TextField tfName;
	@FXML private TextField tfSubname;
	@FXML private TextField tfModelID1;
	@FXML private TextField tfModelID2;
	@FXML private TextField tfModelID3;
	@FXML private TextField tfModelID4;
	@FXML private TextField tfMinLevel;
	@FXML private TextField tfMaxLevel;
	@FXML private TextField tfMinGold;
	@FXML private TextField tfMinSilver;
	@FXML private TextField tfMinCopper;
	@FXML private TextField tfMaxGold;
	@FXML private TextField tfMaxSilver;
	@FXML private TextField tfMaxCopper;
	@FXML private CheckComboBox<Identifier> ccbFaction;
	@FXML private CheckComboBox<Identifier> ccbFlags;
	@FXML private CheckComboBox<Identifier> ccbFlagsExtra;
	@FXML private CheckComboBox<Identifier> ccbDynamicFlags;
	@FXML private CheckComboBox<Identifier> ccbMechanicImmune;
	@FXML private CheckComboBox<Identifier> ccbSpellImmune;
	@FXML private CheckBox cbRegenHealth;
	@FXML private TextField tfDifficultyEntry1;
	@FXML private TextField tfDifficultyEntry2;
	@FXML private TextField tfDifficultyEntry3;
	@FXML private TextField tfKillCredit1;
	@FXML private TextField tfKillCredit2;
	@FXML private ComboBox<Identifier> cbExpansion;
	@FXML private TextField tfSpeedWalk;
	@FXML private TextField tfSpeedRun;
	@FXML private TextField tfScale;
	@FXML private ComboBox<Identifier> cbRank;
	@FXML private ComboBox<Identifier> cbDamageSchool;
	@FXML private TextField tfBaseAttackTime;
	@FXML private TextField tfRangeAttackTime;
	@FXML private TextField tfBaseVariance;
	@FXML private TextField tfRangeVariance;
	@FXML private ComboBox<Identifier> cbUnitClass;
	@FXML private CheckComboBox<Identifier> ccbUnitFlags1;
	@FXML private CheckComboBox<Identifier> ccbUnitFlags2;
	@FXML private TextField tfLootID;
	@FXML private TextField tfPickpocketID;
	@FXML private TextField tfSkinID;
	@FXML private ComboBox<Identifier> cbFamily;
	@FXML private ComboBox<Identifier> cbType;
	@FXML private CheckComboBox<?> ccbTypeFlags;
	@FXML private ComboBox<Identifier> cbMovementID;
	@FXML private ComboBox<Identifier> cbMovementType;
	@FXML private TextField tfHoverHeight;
	@FXML private TextField tfModifierHealth;
	@FXML private TextField tfModifierMana;
	@FXML private TextField tfModifierArmor;
	@FXML private TextField tfModifierDamage;
	@FXML private TextField tfModifierExperience;
	@FXML private CheckBox cbRacialLeader;
	@FXML private TextField tfPetSpellDataID;
	@FXML private TextField tfVehicleID;

	private Creature creature;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		tpLayout.widthProperty().addListener((observableValue, number, t1) -> {
			recursiveToFitTilePane(0);
		});
	}

	@Override
	public void update() {

	}

	public void setCreature(Creature creature) {
		this.creature = creature;
	}

	private void updateLayout() {

	}

	private void updateModel() {
		
	}

	private void recursiveToFitTilePane(int index) {
		if(index == tpLayout.getChildren().size()) return;

		double width = tpLayout.widthProperty().get() / (tpLayout.getChildren().size() - index) - tpLayout.getHgap();

		if(width >= 300) {
			fitTilePane(width);
		} else {
			recursiveToFitTilePane(++index);
		}
	}

	private void fitTilePane(double width) {
		tpLayout.setPrefTileWidth(width);

		for(Node child : tpLayout.getChildren()) {
			if(child instanceof GridPane) {
				GridPane gp = (GridPane) child;
				gp.setPrefWidth(width);
			}
		}
	}
}
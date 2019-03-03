package me.heitx.maserow.ui.creature.template;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Window;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.dao.ICreatureDAO;
import me.heitx.maserow.database.dao.IQuestDAO;
import me.heitx.maserow.io.CommonCSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.ICSV;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Creature;
import me.heitx.maserow.ui.LayoutUtil;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.utils.MoneyUtil;
import me.heitx.maserow.utils.query.TrinityCreatureQuery;
import me.heitx.maserow.utils.query.TrinityQuestQuery;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreatureTemplateController implements Initializable, Updateable {
	@FXML private Button btnExecute;
	@FXML private MenuItem miInsert;
	@FXML private MenuItem miUpdate;
	@FXML private MenuItem miDelete;

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
	@FXML private TextField tfFaction;
	@FXML private CheckComboBox<Identifier> ccbFlags;
	@FXML private CheckComboBox<Identifier> ccbFlagsExtra;
	@FXML private CheckComboBox<Identifier> ccbDynamicFlags;
	@FXML private CheckComboBox<Identifier> ccbMechanicImmune;
	@FXML private TextField tfSpellImmune;
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
	@FXML private CheckComboBox<Identifier> ccbTypeFlags;
	@FXML private TextField tfMovementID;
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
	@FXML private TextField tfHolyResist;
	@FXML private TextField tfFireResist;
	@FXML private TextField tfNatureResist;
	@FXML private TextField tfFrostResist;
	@FXML private TextField tfShadowResist;
	@FXML private TextField tfArcaneResist;
	@FXML private TextField tfSpell1;
	@FXML private TextField tfSpell2;
	@FXML private TextField tfSpell3;
	@FXML private TextField tfSpell4;
	@FXML private TextField tfSpell5;
	@FXML private TextField tfSpell6;
	@FXML private TextField tfSpell7;
	@FXML private TextField tfSpell8;
	@FXML private TextField tfAIName;
	@FXML private TextField tfIconName;
	@FXML private TextField tfGossipMenu;
	@FXML private TextField tfScriptName;

	private final String csvPath = ICSV.CSV_FOLDER_NAME + File.separator + "creature" + File.separator;

	private List<Identifier> flags;
	private List<Identifier> extraFlags;
	private List<Identifier> dynamicFlags;
	private List<Identifier> mechanicFlags;
	private List<Identifier> expansions;
	private List<Identifier> ranks;
	private List<Identifier> damageSchools;
	private List<Identifier> unitClass;
	private List<Identifier> unitFlags;
	private List<Identifier> unitFlags2;
	private List<Identifier> family;
	private List<Identifier> type;
	private List<Identifier> typeFlags;
	private List<Identifier> movementTypes;

	private Creature creature;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		creature = new Creature();

		btnExecute.setOnAction(this::onExecuteButtonAction);
		miInsert.setOnAction(this::onSaveSqlButtonAction);
		miUpdate.setOnAction(this::onSaveSqlButtonAction);
		miDelete.setOnAction(this::onSaveSqlButtonAction);

		tpLayout.widthProperty().addListener((observableValue, number, t1) -> {
			recursiveToFitTilePane(0);
		});

		loadAllIdentifiers();
	}

	@Override
	public void update() {
		btnExecute.setDisable(!Database.isLoggedIn());
	}

	public void setCreature(Creature creature) {
		this.creature = creature;
		updateLayout();
	}

	private void onExecuteButtonAction(ActionEvent actionEvent) {
		if(creature != null) {
			updateModel();
			ICreatureDAO dao = Database.getInstance().getCreatureDAO();
			Map<String, Object> attributes = ConverterUtil.toAttributes(creature);

			if(dao.exists(creature.getEntry())) {
				Optional<ButtonType> alert = LayoutUtil.showAlert(Alert.AlertType.CONFIRMATION, "Conflict", "Identifier already exists..", "There exists already a creature with given identifier! " +
						"Do you want to overwrite the old creature with the new one?", ButtonType.NO, ButtonType.YES);
				if(alert.isPresent() && alert.get() == ButtonType.YES) {
					dao.update(attributes);
				}
			} else {
				dao.insert(attributes);
			}
		}
	}

	private void onSaveSqlButtonAction(ActionEvent actionEvent) {
		if(creature != null) {
			updateModel();

			Map<String, Object> attributes = ConverterUtil.toAttributes(creature);
			final String initialFileName = "creature:" + creature.getName().replaceAll(" ", "-") + ":" + creature.getEntry();
			final Window window = btnExecute.getScene().getWindow();

			if(actionEvent.getSource() == miInsert) {
				LayoutUtil.showSaveSqlWindow(window, "Save Insert Query", initialFileName, TrinityCreatureQuery.getInsertQuery(attributes, true));
			} else if(actionEvent.getSource() == miUpdate) {
				LayoutUtil.showSaveSqlWindow(window, "Save Update Query", initialFileName, TrinityCreatureQuery.getInsertQuery(attributes, true));
			} else if(actionEvent.getSource() == miDelete) {
				LayoutUtil.showSaveSqlWindow(window, "Save Delete Query", initialFileName, TrinityCreatureQuery.getInsertQuery(attributes, true));
			}
		}
	}

	private void loadAllIdentifiers() {
		flags = DelimiterReader.readColumns(csvPath + "npc_flag", false, true);
		extraFlags = DelimiterReader.readColumns(csvPath + "flags_extra", false, true);
		dynamicFlags = DelimiterReader.readColumns(csvPath + "dynamic_flags", false, true);
		mechanicFlags = DelimiterReader.readColumns(csvPath + "mechanic_immune_mask", false, true);
		expansions = DelimiterReader.readColumns(CommonCSV.EXPANSIONS);
		ranks = DelimiterReader.readColumns(csvPath + "rank", true, false);
		damageSchools = DelimiterReader.readColumns(csvPath + "damage_school", true, false);
		unitClass = DelimiterReader.readColumns(csvPath + "unit_class", false, true);
		unitFlags = DelimiterReader.readColumns(csvPath + "unit_flags", false, true);
		unitFlags2 = DelimiterReader.readColumns(csvPath + "unit_flags2", false, true);
		family = DelimiterReader.readColumns(csvPath + "family", true, false);
		type = DelimiterReader.readColumns(csvPath + "type", true, false);
		typeFlags = DelimiterReader.readColumns(csvPath + "type_flags", false, true);
		movementTypes = DelimiterReader.readColumns(csvPath + "movement_type", true, false);

		ccbFlags.getItems().setAll(flags);
		ccbFlagsExtra.getItems().setAll(extraFlags);
		ccbDynamicFlags.getItems().setAll(dynamicFlags);
		ccbMechanicImmune.getItems().setAll(mechanicFlags);
		cbExpansion.getItems().setAll(expansions);
		cbRank.getItems().setAll(ranks);
		cbDamageSchool.getItems().setAll(damageSchools);
		cbUnitClass.getItems().setAll(unitClass);
		ccbUnitFlags1.getItems().setAll(unitFlags);
		ccbUnitFlags2.getItems().setAll(unitFlags2);
		cbFamily.getItems().setAll(family);
		cbType.getItems().setAll(type);
		ccbTypeFlags.getItems().setAll(typeFlags);
		cbMovementType.getItems().setAll(movementTypes);

		// ComboCheckboxes
		LayoutUtil.showOnlyNameOnCombobox(ccbFlags, ccbFlagsExtra, ccbDynamicFlags,
				ccbMechanicImmune, ccbUnitFlags1, ccbUnitFlags2, ccbTypeFlags);

		ccbDynamicFlags.getCheckModel().check(0);

		// Comboboxes
		LayoutUtil.showOnlyNameOnCombobox(cbExpansion, cbRank, cbDamageSchool,
				cbUnitClass, cbFamily, cbType, cbMovementType);

		cbExpansion.getSelectionModel().select(expansions.size() - 1);

		LayoutUtil.selectFirstOnComboboxes(cbRank, cbDamageSchool,
				cbUnitClass, cbFamily, cbType, cbMovementType);
	}

	private void updateLayout() {
		ccbFlags.getCheckModel().clearChecks();
		ccbFlagsExtra.getCheckModel().clearChecks();
		ccbDynamicFlags.getCheckModel().clearChecks();
		ccbMechanicImmune.getCheckModel().clearChecks();
		ccbUnitFlags1.getCheckModel().clearChecks();
		ccbUnitFlags2.getCheckModel().clearChecks();
		ccbTypeFlags.getCheckModel().clearChecks();

		tfEntry.setText(String.valueOf(creature.getEntry()));
		tfName.setText(creature.getName());
		tfSubname.setText(creature.getSubname());
		tfModelID1.setText(String.valueOf(creature.getModelid1()));
		tfModelID2.setText(String.valueOf(creature.getModelid2()));
		tfModelID3.setText(String.valueOf(creature.getModelid3()));
		tfModelID4.setText(String.valueOf(creature.getModelid4()));
		tfMinLevel.setText(String.valueOf(creature.getMinlevel()));
		tfMaxLevel.setText(String.valueOf(creature.getMaxlevel()));

		long[] minGold = MoneyUtil.totalToGSC(creature.getMingold());
		tfMinGold.setText(String.valueOf(minGold[0]));
		tfMinSilver.setText(String.valueOf(minGold[1]));
		tfMinCopper.setText(String.valueOf(minGold[2]));
		long[] maxGold = MoneyUtil.totalToGSC(creature.getMaxgold());
		tfMaxGold.setText(String.valueOf(maxGold[0]));
		tfMaxSilver.setText(String.valueOf(maxGold[1]));
		tfMaxCopper.setText(String.valueOf(maxGold[2]));

		tfFaction.setText(String.valueOf(creature.getFaction()));
		ccbFlags.getCheckModel().checkIndices(ConverterUtil.toPrimitive(Identifier.findIndicesByValue(flags, creature.getNpcflag())));
		ccbFlagsExtra.getCheckModel().checkIndices(ConverterUtil.toPrimitive(Identifier.findIndicesByValue(extraFlags, creature.getFlagsExtra())));
		ccbMechanicImmune.getCheckModel().checkIndices(ConverterUtil.toPrimitive(Identifier.findIndicesByValue(mechanicFlags, creature.getMechanicImmuneMask())));
		tfSpellImmune.setText(String.valueOf(creature.getSpellSchoolImmuneMask()));
		cbRegenHealth.setSelected(creature.getRegenHealth() > 0);
		tfDifficultyEntry1.setText(String.valueOf(creature.getDifficultyEntry1()));
		tfDifficultyEntry2.setText(String.valueOf(creature.getDifficultyEntry2()));
		tfDifficultyEntry3.setText(String.valueOf(creature.getDifficultyEntry3()));
		tfKillCredit1.setText(String.valueOf(creature.getKillCredit1()));
		tfKillCredit2.setText(String.valueOf(creature.getKillCredit2()));
		cbExpansion.getSelectionModel().select(Identifier.findById(expansions, creature.getExp()));
		tfSpeedWalk.setText(String.valueOf(creature.getSpeedWalk()));
		tfSpeedRun.setText(String.valueOf(creature.getSpeedRun()));
		tfScale.setText(String.valueOf(creature.getScale()));
		cbRank.getSelectionModel().select(Identifier.findById(ranks, creature.getRank()));
		cbDamageSchool.getSelectionModel().select(Identifier.findById(damageSchools, creature.getDmgschool()));
		tfBaseAttackTime.setText(String.valueOf(creature.getBaseAttackTime()));
		tfRangeAttackTime.setText(String.valueOf(creature.getRangeAttackTime()));
		tfBaseVariance.setText(String.valueOf(creature.getBaseVariance()));
		tfRangeVariance.setText(String.valueOf(creature.getRangeVariance()));
		cbUnitClass.getSelectionModel().select(Identifier.findById(unitClass, creature.getUnitClass()));
		ccbUnitFlags1.getCheckModel().checkIndices(ConverterUtil.toPrimitive(Identifier.findIndicesByValue(unitFlags, creature.getUnitFlags())));
		ccbUnitFlags2.getCheckModel().checkIndices(ConverterUtil.toPrimitive(Identifier.findIndicesByValue(unitFlags2, creature.getUnitFlags2())));
		tfLootID.setText(String.valueOf(creature.getLootid()));
		tfPickpocketID.setText(String.valueOf(creature.getPickpocketloot()));
		tfSkinID.setText(String.valueOf(creature.getSkinloot()));
		cbFamily.getSelectionModel().select(Identifier.findById(family, creature.getFamily()));
		cbType.getSelectionModel().select(Identifier.findById(type, creature.getType()));
		ccbTypeFlags.getCheckModel().checkIndices(ConverterUtil.toPrimitive(Identifier.findIndicesByValue(typeFlags, creature.getTypeFlags())));
		tfMovementID.setText(String.valueOf(creature.getMovementId()));
		cbMovementType.getSelectionModel().select(Identifier.findById(movementTypes, creature.getMovementType()));
		tfHoverHeight.setText(String.valueOf(creature.getHoverHeight()));
		tfModifierHealth.setText(String.valueOf(creature.getHealthModifier()));
		tfModifierMana.setText(String.valueOf(creature.getManaModifier()));
		tfModifierArmor.setText(String.valueOf(creature.getArmorModifier()));
		tfModifierDamage.setText(String.valueOf(creature.getDamageModifier()));
		tfModifierExperience.setText(String.valueOf(creature.getExperienceModifier()));
		cbRacialLeader.setSelected(creature.getRacialLeader() > 0);
		tfPetSpellDataID.setText(String.valueOf(creature.getPetSpellDataId()));
		tfVehicleID.setText(String.valueOf(creature.getVehicleId()));
		tfHolyResist.setText(String.valueOf(creature.getResistance1()));
		tfFireResist.setText(String.valueOf(creature.getResistance2()));
		tfNatureResist.setText(String.valueOf(creature.getResistance3()));
		tfFrostResist.setText(String.valueOf(creature.getResistance4()));
		tfShadowResist.setText(String.valueOf(creature.getResistance5()));
		tfArcaneResist.setText(String.valueOf(creature.getResistance6()));
		tfSpell1.setText(String.valueOf(creature.getSpell1()));
		tfSpell2.setText(String.valueOf(creature.getSpell2()));
		tfSpell3.setText(String.valueOf(creature.getSpell3()));
		tfSpell4.setText(String.valueOf(creature.getSpell4()));
		tfSpell5.setText(String.valueOf(creature.getSpell5()));
		tfSpell6.setText(String.valueOf(creature.getSpell6()));
		tfSpell7.setText(String.valueOf(creature.getSpell7()));
		tfSpell8.setText(String.valueOf(creature.getSpell8()));
		tfAIName.setText(creature.getAIName());
		tfIconName.setText(creature.getIconName());
		tfGossipMenu.setText(String.valueOf(creature.getGossipMenuId()));
		tfScriptName.setText(creature.getScriptName());
	}

	private void updateModel() {
		creature.setEntry(Integer.parseInt(tfEntry.getText()));
		creature.setName(tfName.getText());
		creature.setSubname(tfSubname.getText());
		creature.setModelid1(Integer.parseInt(tfModelID1.getText()));
		creature.setModelid2(Integer.parseInt(tfModelID2.getText()));
		creature.setModelid3(Integer.parseInt(tfModelID3.getText()));
		creature.setModelid4(Integer.parseInt(tfModelID4.getText()));
		creature.setMinlevel(Integer.parseInt(tfMinLevel.getText()));
		creature.setMaxlevel(Integer.parseInt(tfMaxLevel.getText()));
		creature.setMingold(MoneyUtil.gscToTotal(tfMinGold.getText(), tfMinSilver.getText(), tfMinCopper.getText()));
		creature.setMaxgold(MoneyUtil.gscToTotal(tfMaxGold.getText(), tfMaxSilver.getText(), tfMaxCopper.getText()));
		creature.setFaction(Integer.parseInt(tfFaction.getText()));
		creature.setNpcflag(Identifier.calculateValue(flags, ccbFlags.getCheckModel().getCheckedItems()));
		creature.setFlagsExtra(Identifier.calculateValue(extraFlags, ccbFlagsExtra.getCheckModel().getCheckedItems()));
		creature.setDynamicflags(Identifier.calculateValue(dynamicFlags, ccbDynamicFlags.getCheckModel().getCheckedItems()));
		creature.setMechanicImmuneMask(Identifier.calculateValue(mechanicFlags, ccbMechanicImmune.getCheckModel().getCheckedItems()));
		creature.setSpellSchoolImmuneMask(Long.parseLong(tfSpellImmune.getText()));
		creature.setRegenHealth(cbRegenHealth.isSelected() ? 1 : 0);
		creature.setDifficultyEntry1(Integer.parseInt(tfDifficultyEntry1.getText()));
		creature.setDifficultyEntry2(Integer.parseInt(tfDifficultyEntry2.getText()));
		creature.setDifficultyEntry3(Integer.parseInt(tfDifficultyEntry3.getText()));
		creature.setKillCredit1(Long.parseLong(tfKillCredit1.getText()));
		creature.setKillCredit2(Long.parseLong(tfKillCredit2.getText()));
		creature.setExp(cbExpansion.getSelectionModel().getSelectedItem().getId());
		creature.setSpeedWalk(Float.parseFloat(tfSpeedWalk.getText()));
		creature.setSpeedRun(Float.parseFloat(tfSpeedRun.getText()));
		creature.setScale(Float.parseFloat(tfScale.getText()));
		creature.setRank(cbRank.getSelectionModel().getSelectedItem().getId());
		creature.setDmgschool(cbDamageSchool.getSelectionModel().getSelectedItem().getId());
		creature.setBaseAttackTime(Long.parseLong(tfBaseAttackTime.getText()));
		creature.setRangeAttackTime(Long.parseLong(tfRangeAttackTime.getText()));
		creature.setBaseVariance(Float.parseFloat(tfBaseVariance.getText()));
		creature.setRangeVariance(Float.parseFloat(tfRangeVariance.getText()));
		creature.setUnitClass((int) cbUnitClass.getSelectionModel().getSelectedItem().getValue());
		creature.setUnitFlags(Identifier.calculateValue(unitFlags, ccbUnitFlags1.getCheckModel().getCheckedItems()));
		creature.setUnitFlags2(Identifier.calculateValue(unitFlags2, ccbUnitFlags2.getCheckModel().getCheckedItems()));
		creature.setLootid(Integer.parseInt(tfLootID.getText()));
		creature.setPickpocketloot(Integer.parseInt(tfPickpocketID.getText()));
		creature.setSkinloot(Integer.parseInt(tfSkinID.getText()));
		creature.setFamily(cbFamily.getSelectionModel().getSelectedItem().getId());
		creature.setType(cbType.getSelectionModel().getSelectedItem().getId());
		creature.setTypeFlags(Identifier.calculateValue(typeFlags, ccbTypeFlags.getCheckModel().getCheckedItems()));
		creature.setMovementId(Long.parseLong(tfMovementID.getText()));
		creature.setMovementType(cbMovementType.getSelectionModel().getSelectedItem().getId());
		creature.setHoverHeight(Float.parseFloat(tfHoverHeight.getText()));
		creature.setHealthModifier(Float.parseFloat(tfModifierHealth.getText()));
		creature.setManaModifier(Float.parseFloat(tfModifierMana.getText()));
		creature.setArmorModifier(Float.parseFloat(tfModifierArmor.getText()));
		creature.setDamageModifier(Float.parseFloat(tfModifierDamage.getText()));
		creature.setExperienceModifier(Float.parseFloat(tfModifierExperience.getText()));
		creature.setRacialLeader(Integer.parseInt(cbRacialLeader.getText()));
		creature.setPetSpellDataId(Integer.parseInt(tfPetSpellDataID.getText()));
		creature.setVehicleId(Integer.parseInt(tfVehicleID.getText()));
		creature.setResistance1(Integer.parseInt(tfHolyResist.getText()));
		creature.setResistance2(Integer.parseInt(tfFireResist.getText()));
		creature.setResistance3(Integer.parseInt(tfNatureResist.getText()));
		creature.setResistance4(Integer.parseInt(tfFrostResist.getText()));
		creature.setResistance5(Integer.parseInt(tfShadowResist.getText()));
		creature.setResistance6(Integer.parseInt(tfArcaneResist.getText()));
		creature.setSpell1(Integer.parseInt(tfSpell1.getText()));
		creature.setSpell2(Integer.parseInt(tfSpell2.getText()));
		creature.setSpell3(Integer.parseInt(tfSpell3.getText()));
		creature.setSpell4(Integer.parseInt(tfSpell4.getText()));
		creature.setSpell5(Integer.parseInt(tfSpell5.getText()));
		creature.setSpell6(Integer.parseInt(tfSpell6.getText()));
		creature.setSpell7(Integer.parseInt(tfSpell7.getText()));
		creature.setSpell8(Integer.parseInt(tfSpell8.getText()));
		creature.setAIName(tfAIName.getText());
		creature.setIconName(tfIconName.getText());
		creature.setGossipMenuId(Integer.parseInt(tfGossipMenu.getText()));
		creature.setScriptName(tfScriptName.getText());
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
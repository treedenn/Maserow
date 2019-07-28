package me.heitx.maserow.item.editor.build;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import me.heitx.maserow.common.io.DelimiterReader;
import me.heitx.maserow.common.io.ICSV;
import me.heitx.maserow.common.io.Identifier;
import me.heitx.maserow.common.lookup.LookupManager;
import me.heitx.maserow.common.model.Item;
import me.heitx.maserow.common.utils.JavafxUtil;
import me.heitx.maserow.common.utils.MoneyUtil;
import me.heitx.maserow.item.editor.preview.ItemPreviewController;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ItemBuildController implements Initializable {
    @FXML private TitledPane tpGeneral;
    @FXML private TextField tfEntry;
    @FXML private TextField tfDisplayID;
    @FXML private TextField tfQuality;
    @FXML private Button btnQualityLookup;
    @FXML private TextField tfName;
    @FXML private TextField tfDescription;

    @FXML private TextField tfClass;
    @FXML private Button btnClassLookup;
    @FXML private TextField tfSheath;
    @FXML private Button btnSheathLookup;
    @FXML private TextField tfItemLevel;
    @FXML private TextField tfBonding;
    @FXML private Button btnBondingLookup;
    @FXML private TextField tfSubclass;
    @FXML private Button btnSubclassLookup;
    @FXML private TextField tfInventory;
    @FXML private Button btnInventoryLookup;
    @FXML private TextField tfItemSet;

    @FXML private TextField tfSellGold;
    @FXML private TextField tfSellSilver;
    @FXML private TextField tfSellCopper;
    @FXML private TextField tfBuyGold;
    @FXML private TextField tfBuySilver;
    @FXML private TextField tfBuyCopper;
    @FXML private TextField tfMaxCount;
    @FXML private TextField tfMaxStacks;

    @FXML private TextField tfReqLevel;
    @FXML private TextField tfReqSkill;
    @FXML private TextField tfReqHonorRank;
    @FXML private TextField tfReqSpell;
    @FXML private TextField tfReqClasses;
    @FXML private TextField tfReqRepRank;
    @FXML private TextField tfReqSkillRank;
    @FXML private TextField tfReqCityRank;
    @FXML private TextField tfReqRaces;
    @FXML private TextField tfReqRepFaction;

    @FXML private TextField tfSpellEntry1;
    @FXML private ChoiceBox<?> cbSpellTrigger1;
    @FXML private TextField tfSpellCharges1;
    @FXML private TextField tfSpellPPMRate1;
    @FXML private TextField tfSpellCooldown1;
    @FXML private TextField tfSpellCategory1;
    @FXML private TextField tfSpellCooldownCategory1;
    @FXML private TextField tfSpellEntry2;
    @FXML private ChoiceBox<?> cbSpellTrigger2;
    @FXML private TextField tfSpellCharges2;
    @FXML private TextField tfSpellPPMRate2;
    @FXML private TextField tfSpellCooldown2;
    @FXML private TextField tfSpellCategory2;
    @FXML private TextField tfSpellCooldownCategory2;
    @FXML private TextField tfSpellEntry3;
    @FXML private ChoiceBox<?> cbSpellTrigger3;
    @FXML private TextField tfSpellCharges3;
    @FXML private TextField tfSpellPPMRate3;
    @FXML private TextField tfSpellCooldown3;
    @FXML private TextField tfSpellCategory3;
    @FXML private TextField tfSpellCooldownCategory3;
    @FXML private TextField tfSpellEntry4;
    @FXML private ChoiceBox<?> cbSpellTrigger4;
    @FXML private TextField tfSpellCharges4;
    @FXML private TextField tfSpellPPMRate4;
    @FXML private TextField tfSpellCooldown4;
    @FXML private TextField tfSpellCategory4;
    @FXML private TextField tfSpellCooldownCategory4;
    @FXML private TextField tfSpellEntry5;
    @FXML private ChoiceBox<?> cbSpellTrigger5;
    @FXML private TextField tfSpellCharges5;
    @FXML private TextField tfSpellPPMRate5;
    @FXML private TextField tfSpellCooldown5;
    @FXML private TextField tfSpellCategory5;
    @FXML private TextField tfSpellCooldownCategory5;

    @FXML private TextField tfStartQuest;
    @FXML private TextField tfMaterial;
    @FXML private TextField tfDELevel;
    @FXML private TextField tfPageMaterial;
    @FXML private TextField tfDEID;
    @FXML private TextField tfDuration;
    @FXML private TextField tfItemLimitCategory;
    @FXML private TextField tfScriptName;
    @FXML private TextField tfTotemCategory;
    @FXML private TextField tfFoodType;
    @FXML private TextField tfReqDELevel;
    @FXML private TextField tfPageTextID;
    @FXML private TextField tfBagFamily;
    @FXML private TextField tfHolidayID;

    @FXML private TextField tfDamageMin1;
    @FXML private TextField tfDamageMax1;
    @FXML private ChoiceBox<?> cbDamageType1;
    @FXML private ChoiceBox<?> cbDamageType2;
    @FXML private TextField tfDamageMin2;
    @FXML private TextField tfDamageMax2;
    @FXML private TextField tfDelay;
    @FXML private TextField tfModRange;
    @FXML private ChoiceBox<?> cbAmmoType;

    @FXML private ChoiceBox<?> cbStatType1;
    @FXML private ChoiceBox<?> cbStatType2;
    @FXML private ChoiceBox<?> cbStatType3;
    @FXML private ChoiceBox<?> cbStatType4;
    @FXML private ChoiceBox<?> cbStatType5;
    @FXML private ChoiceBox<?> cbStatType6;
    @FXML private ChoiceBox<?> cbStatType7;
    @FXML private ChoiceBox<?> cbStatType8;
    @FXML private ChoiceBox<?> cbStatType9;
    @FXML private ChoiceBox<?> cbStatType10;
    @FXML private TextField tfStatValue1;
    @FXML private TextField tfStatValue2;
    @FXML private TextField tfStatValue3;
    @FXML private TextField tfStatValue4;
    @FXML private TextField tfStatValue5;
    @FXML private TextField tfStatValue6;
    @FXML private TextField tfStatValue7;
    @FXML private TextField tfStatValue8;
    @FXML private TextField tfStatValue9;
    @FXML private TextField tfStatValue10;
    @FXML private TextField tfScalingStatDist;
    @FXML private TextField tfScalingStatValue;

    @FXML private TextField tfArmor;
    @FXML private TextField tfBlock;
    @FXML private TextField tfDurability;
    @FXML private TextField tfArmorDamageMod;
    @FXML private TextField tfResHoly;
    @FXML private TextField tfResNature;
    @FXML private TextField tfResShadow;
    @FXML private TextField tfResFire;
    @FXML private TextField tfResFrost;
    @FXML private TextField tfResArcane;

    @FXML private RadioButton rbSocketNone1;
    @FXML private ToggleGroup tgSocket1;
    @FXML private RadioButton rbSocketMeta1;
    @FXML private RadioButton rbSocketRed1;
    @FXML private RadioButton rbSocketYellow1;
    @FXML private RadioButton rbSocketBlue1;
    @FXML private RadioButton rbSocketPrismatic1;
    @FXML private RadioButton rbSocketNone2;
    @FXML private ToggleGroup tgSocket2;
    @FXML private RadioButton rbSocketMeta2;
    @FXML private RadioButton rbSocketRed2;
    @FXML private RadioButton rbSocketYellow2;
    @FXML private RadioButton rbSocketBlue2;
    @FXML private RadioButton rbSocketPrismatic2;
    @FXML private RadioButton rbSocketNone3;
    @FXML private ToggleGroup tgSocket3;
    @FXML private RadioButton rbSocketMeta3;
    @FXML private RadioButton rbSocketRed3;
    @FXML private RadioButton rbSocketYellow3;
    @FXML private RadioButton rbSocketBlue3;
    @FXML private RadioButton rbSocketPrismatic3;
    @FXML private ChoiceBox<?> cbSocketBonus;

    private final String csvPath = ICSV.CSV_FOLDER_NAME + File.separator + "item" + File.separator;

    private ItemPreviewController previewController;
    private Item item;

    private Map<RadioButton, Integer> socketValues;

    public ItemBuildController() {
        item = new Item();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socketValues = new HashMap<>();

        RadioButton[] noneSockets = { rbSocketNone1, rbSocketNone2, rbSocketNone3 };
        RadioButton[] metaSockets = { rbSocketMeta1, rbSocketMeta2, rbSocketMeta3 };
        RadioButton[] redSockets = { rbSocketRed1, rbSocketRed2, rbSocketRed3 };
        RadioButton[] yelSockets = { rbSocketYellow1, rbSocketYellow2, rbSocketYellow3 };
        RadioButton[] blueSockets = { rbSocketBlue1, rbSocketBlue2, rbSocketBlue3 };
        RadioButton[] prisSockets = { rbSocketPrismatic1, rbSocketPrismatic2, rbSocketPrismatic3 };

        for (RadioButton socket : noneSockets) { socketValues.put(socket, 0); }
        for (RadioButton socket : metaSockets) { socketValues.put(socket, 1); }
        for (RadioButton socket : redSockets) { socketValues.put(socket, 2); }
        for (RadioButton socket : yelSockets) { socketValues.put(socket, 4); }
        for (RadioButton socket : blueSockets) { socketValues.put(socket, 8); }
        for (RadioButton socket : prisSockets) { socketValues.put(socket, 14); }

        initialiseEvents();
    }

    public void setPreviewController(ItemPreviewController previewController) {
        this.previewController = previewController;
    }

    public void setItem(Item item) {
        this.item = item;

        updateGeneralLayout();
        updateEquipmentLayout();
        updateVendorAndStacksLayout();
        updateRequirementsLayout();
        updateSpellsLayout();
        updateMiscellaneousLayout();

        updateWeaponLayout();
        updateStatsLayout();
        updateDefenceLayout();
        updateSocketsLayout();
    }

    private void initialiseEvents() {
        Set<TextField> general = new HashSet<>(Arrays.asList(tfEntry, tfDisplayID, tfQuality, tfName, tfDescription));

        for(TextField tf : general) {
            tf.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
                if(aBoolean && !t1) {
                    if(!tf.getText().isEmpty()) {
                        updateGeneralAttributes();
                        previewController.update();
                    }
                }
            });
        }


        btnQualityLookup.setOnAction(this::handleQualityLookupAction);
        btnClassLookup.setOnAction(this::handleClassLookupAction);
        btnSubclassLookup.setOnAction(this::handleSubclassLookupAction);
        btnSheathLookup.setOnAction(this::handleSheathLookupAction);
        btnBondingLookup.setOnAction(this::handleBondingLookupAction);
        btnInventoryLookup.setOnAction(this::handleInventoryLookupAction);
    }

    // ********************
    // GENERAL
    //*********************

    private void updateGeneralLayout() {
        tfEntry.setText(String.valueOf(item.getEntry()));
        tfDisplayID.setText(String.valueOf(item.getDisplayId()));
        tfQuality.setText(String.valueOf(item.getQuality()));
        tfName.setText(item.getName());
        tfDescription.setText(item.getDescription());
    }

    private void updateGeneralAttributes() {
        item.setEntry(Integer.parseInt(tfEntry.getText()));
        item.setDisplayId(Integer.parseInt(tfDisplayID.getText()));
        item.setQuality(Integer.parseInt(tfQuality.getText()));
        item.setName(tfName.getText());
        item.setDescription(tfDescription.getText());
    }

    // ********************
    // EQUIPMENT
    //*********************

    private void updateEquipmentLayout() {
        tfClass.setText(String.valueOf(item.getClazz()));
        tfSheath.setText(String.valueOf(item.getSheath()));
        tfItemLevel.setText(String.valueOf(item.getItemLevel()));
        tfBonding.setText(String.valueOf(item.getBonding()));
        tfSubclass.setText(String.valueOf(item.getSubclass()));
        tfInventory.setText(String.valueOf(item.getInventoryType()));
        tfItemSet.setText(String.valueOf(item.getItemSet()));
        tfMaxCount.setText(String.valueOf(item.getMaxCount()));
    }

    private void updateEquipmenAttributes() {
        item.setClazz(Integer.parseInt(tfClass.getText()));
        item.setSheath(Integer.parseInt(tfSheath.getText()));
        item.setItemLevel(Integer.parseInt(tfItemLevel.getText()));
        item.setBonding(Integer.parseInt(tfBonding.getText()));
        item.setSubclass(Integer.parseInt(tfSubclass.getText()));
        item.setInventoryType(Integer.parseInt(tfInventory.getText()));
        item.setItemSet(Integer.parseInt(tfItemSet.getText()));
        item.setMaxCount(Integer.parseInt(tfMaxCount.getText()));
    }

    // ********************
    // VENDOR & STACKS
    //*********************

    private void updateVendorAndStacksLayout() {
        List<TextField> sell = Arrays.asList(tfSellGold, tfSellSilver, tfSellCopper);
        List<TextField> buy = Arrays.asList(tfBuyGold, tfBuySilver, tfBuyCopper);

        JavafxUtil.assignValuesToTextfields(sell, MoneyUtil.totalToGSC(item.getSellPrice()));
        JavafxUtil.assignValuesToTextfields(buy, MoneyUtil.totalToGSC(item.getBuyPrice()));

        tfMaxCount.setText(String.valueOf(item.getMaxCount()));
        tfMaxStacks.setText(String.valueOf(item.getStackable()));
    }

    private void updateVendorAndStacksAttributes() {
        int sellTotal = MoneyUtil.gscToTotal(tfSellGold.getText(), tfSellSilver.getText(), tfSellCopper.getText());
        int buyTotal = MoneyUtil.gscToTotal(tfBuyGold.getText(), tfBuySilver.getText(), tfBuyCopper.getText());

        item.setSellPrice(sellTotal);
        item.setBuyPrice(buyTotal);
        item.setMaxCount(Integer.parseInt(tfMaxCount.getText()));
        item.setStackable(Integer.parseInt(tfMaxStacks.getText()));
    }

    // ********************
    // REQUIREMENTS
    //*********************

    private void updateRequirementsLayout() {
        tfReqLevel.setText(String.valueOf(item.getRequiredLevel()));
        tfReqSkill.setText(String.valueOf(item.getRequiredSkill()));
        tfReqHonorRank.setText(String.valueOf(item.getRequiredHonorRank()));
        tfReqSpell.setText(String.valueOf(item.getRequiredSpell()));
        tfReqClasses.setText(String.valueOf(item.getAllowableClass()));
        tfReqRepRank.setText(String.valueOf(item.getRequiredReputationRank()));
        tfReqSkillRank.setText(String.valueOf(item.getRequiredSkillRank()));
        tfReqCityRank.setText(String.valueOf(item.getRequiredCityRank()));
        tfReqRaces.setText(String.valueOf(item.getAllowableRace()));
        tfReqRepFaction.setText(String.valueOf(item.getRequiredReputationFaction()));
    }

    private void updateRequirementsAttributes() {
        item.setRequiredLevel(Integer.parseInt(tfReqLevel.getText()));
        item.setRequiredSkill(Integer.parseInt(tfReqSkill.getText()));
        item.setRequiredHonorRank(Integer.parseInt(tfReqHonorRank.getText()));
        item.setRequiredSpell(Integer.parseInt(tfReqSpell.getText()));
        item.setAllowableClass(Integer.parseInt(tfReqClasses.getText()));
        item.setRequiredReputationRank(Integer.parseInt(tfReqRepRank.getText()));
        item.setRequiredSkillRank(Integer.parseInt(tfReqSkillRank.getText()));
        item.setRequiredCityRank(Integer.parseInt(tfReqCityRank.getText()));
        item.setAllowableRace(Integer.parseInt(tfReqRaces.getText()));
        item.setRequiredReputationFaction(Integer.parseInt(tfReqRepFaction.getText()));
    }

    // ********************
    // SPELLS
    //*********************

    private void updateSpellsLayout() {
        Set<TextField> entries = new HashSet<>(Arrays.asList(tfSpellEntry1, tfSpellEntry2, tfSpellEntry3,
                tfSpellEntry4, tfSpellEntry5));
        Set<Integer> entryValues = new HashSet<>(Arrays.asList(item.getSpellId1(), item.getSpellId2(),
                item.getSpellId3(), item.getSpellId4(), item.getSpellId5()));

        Set<ChoiceBox> triggers = new HashSet<>(Arrays.asList(cbSpellTrigger1, cbSpellTrigger2, cbSpellTrigger3,
                cbSpellTrigger4, cbSpellTrigger5));
        Set<Integer> triggerValues = new HashSet<>(Arrays.asList(item.getSpellTrigger1(), item.getSpellTrigger2(),
                item.getSpellTrigger3(), item.getSpellTrigger4(), item.getSpellTrigger5()));

        Set<TextField> charges = new HashSet<>(Arrays.asList(tfSpellCharges1, tfSpellCharges2, tfSpellCharges3,
                tfSpellCharges4, tfSpellCharges5));
        Set<Integer> chargeValues = new HashSet<>(Arrays.asList(item.getSpellCharges1(), item.getSpellCharges2(),
                item.getSpellCharges3(), item.getSpellCharges4(), item.getSpellCharges5()));

        Set<TextField> ppmrates = new HashSet<>(Arrays.asList(tfSpellPPMRate1, tfSpellPPMRate2, tfSpellPPMRate3,
                tfSpellPPMRate4, tfSpellPPMRate5));
        Set<Float> ppmValues = new HashSet<>(Arrays.asList(item.getSpellPpmRate1(), item.getSpellPpmRate2(),
                item.getSpellPpmRate3(), item.getSpellPpmRate4(), item.getSpellPpmRate5()));

        Set<TextField> cooldowns = new HashSet<>(Arrays.asList(tfSpellCooldown1, tfSpellCooldown2, tfSpellCooldown3,
                tfSpellCooldown4, tfSpellCooldown5));
        Set<Integer> cdValues = new HashSet<>(Arrays.asList(item.getSpellCooldown1(), item.getSpellCooldown2(),
                item.getSpellCooldown3(), item.getSpellCooldown4(), item.getSpellCooldown5()));

        Set<TextField> categories = new HashSet<>(Arrays.asList(tfSpellCategory1, tfSpellCategory2, tfSpellCategory3,
                tfSpellCategory4, tfSpellCategory5));
        Set<Integer> catValues = new HashSet<>(Arrays.asList(item.getSpellCategory1(), item.getSpellCategory2(),
                item.getSpellCategory3(), item.getSpellCategory4(), item.getSpellCategory5()));

        Set<TextField> cdCategories = new HashSet<>(Arrays.asList(tfSpellCooldownCategory1, tfSpellCooldownCategory2,
                tfSpellCooldownCategory3, tfSpellCooldownCategory4, tfSpellCooldownCategory5));
        Set<Integer> cdcatValues = new HashSet<>(Arrays.asList(item.getSpellCategoryCooldown1(),
                item.getSpellCategoryCooldown2(), item.getSpellCategoryCooldown3(),
                item.getSpellCategoryCooldown4(), item.getSpellCategoryCooldown5()));

        JavafxUtil.assignValuesToTextfields(entries, entryValues);
        JavafxUtil.assignValuesToTextfields(charges, chargeValues);
        JavafxUtil.assignValuesToTextfields(ppmrates, ppmValues);
        JavafxUtil.assignValuesToTextfields(cooldowns, cdValues);
        JavafxUtil.assignValuesToTextfields(categories, catValues);
        JavafxUtil.assignValuesToTextfields(cdCategories, cdcatValues);

        //        JavafxUtil.assignValuesToTextfields(triggers, triggerValues);
    }

    private void updateSpellsAttributes() {
        item.setSpellId1(Integer.parseInt(tfSpellEntry1.getText()));
        item.setSpellId2(Integer.parseInt(tfSpellEntry2.getText()));
        item.setSpellId3(Integer.parseInt(tfSpellEntry3.getText()));
        item.setSpellId4(Integer.parseInt(tfSpellEntry4.getText()));
        item.setSpellId5(Integer.parseInt(tfSpellEntry5.getText()));
//        item.setSpellTrigger1(cbSpellTrigger1);
//        item.setSpellTrigger2(cbSpellTrigger2);
//        item.setSpellTrigger3(cbSpellTrigger3);
//        item.setSpellTrigger4(cbSpellTrigger4);
//        item.setSpellTrigger5(cbSpellTrigger5);
        item.setSpellCharges1(Integer.parseInt(tfSpellCharges1.getText()));
        item.setSpellCharges2(Integer.parseInt(tfSpellCharges2.getText()));
        item.setSpellCharges3(Integer.parseInt(tfSpellCharges3.getText()));
        item.setSpellCharges4(Integer.parseInt(tfSpellCharges4.getText()));
        item.setSpellCharges5(Integer.parseInt(tfSpellCharges5.getText()));
        item.setSpellPpmRate1(Float.parseFloat(tfSpellPPMRate1.getText()));
        item.setSpellPpmRate2(Float.parseFloat(tfSpellPPMRate2.getText()));
        item.setSpellPpmRate3(Float.parseFloat(tfSpellPPMRate3.getText()));
        item.setSpellPpmRate4(Float.parseFloat(tfSpellPPMRate4.getText()));
        item.setSpellPpmRate5(Float.parseFloat(tfSpellPPMRate5.getText()));
        item.setSpellCooldown1(Integer.parseInt(tfSpellCooldown1.getText()));
        item.setSpellCooldown2(Integer.parseInt(tfSpellCooldown2.getText()));
        item.setSpellCooldown3(Integer.parseInt(tfSpellCooldown3.getText()));
        item.setSpellCooldown4(Integer.parseInt(tfSpellCooldown4.getText()));
        item.setSpellCooldown5(Integer.parseInt(tfSpellCooldown5.getText()));
        item.setSpellCategory1(Integer.parseInt(tfSpellCategory1.getText()));
        item.setSpellCategory2(Integer.parseInt(tfSpellCategory2.getText()));
        item.setSpellCategory3(Integer.parseInt(tfSpellCategory3.getText()));
        item.setSpellCategory4(Integer.parseInt(tfSpellCategory4.getText()));
        item.setSpellCategory5(Integer.parseInt(tfSpellCategory5.getText()));
        item.setSpellCategoryCooldown1(Integer.parseInt(tfSpellCooldownCategory1.getText()));
        item.setSpellCategoryCooldown2(Integer.parseInt(tfSpellCooldownCategory2.getText()));
        item.setSpellCategoryCooldown3(Integer.parseInt(tfSpellCooldownCategory3.getText()));
        item.setSpellCategoryCooldown4(Integer.parseInt(tfSpellCooldownCategory4.getText()));
        item.setSpellCategoryCooldown5(Integer.parseInt(tfSpellCooldownCategory5.getText()));
    }

    // ********************
    // MISCELLANEOUS
    //*********************

    private void updateMiscellaneousLayout() {
        tfStartQuest.setText(String.valueOf(item.getStartQuest()));
        tfMaterial.setText(String.valueOf(item.getMaterial()));
        tfPageMaterial.setText(String.valueOf(item.getPageMaterial()));
        tfDEID.setText(String.valueOf(item.getDisenchantID()));
        tfDuration.setText(String.valueOf(item.getDuration()));
        tfItemLimitCategory.setText(String.valueOf(item.getItemLimitCategory()));
        tfScriptName.setText(String.valueOf(item.getScriptName()));
        tfTotemCategory.setText(String.valueOf(item.getTotemCategory()));
        tfFoodType.setText(String.valueOf(item.getFoodType()));
        tfReqDELevel.setText(String.valueOf(item.getRequiredDisenchantSkill()));
        tfPageTextID.setText(String.valueOf(item.getPage()));
        tfBagFamily.setText(String.valueOf(item.getBagFamily()));
        tfHolidayID.setText(String.valueOf(item.getHolidayId()));
    }

    private void updateMiscellaneousAttributes() {
        item.setStartQuest(Integer.parseInt(tfStartQuest.getText()));
        item.setMaterial(Integer.parseInt(tfMaterial.getText()));
        item.setPageMaterial(Integer.parseInt(tfPageMaterial.getText()));
        item.setDisenchantID(Integer.parseInt(tfDEID.getText()));
        item.setDuration(Long.parseLong(tfDuration.getText()));
        item.setItemLimitCategory(Integer.parseInt(tfItemLimitCategory.getText()));
        item.setScriptName(tfScriptName.getText());
        item.setTotemCategory(Integer.parseInt(tfTotemCategory.getText()));
        item.setFoodType(Integer.parseInt(tfFoodType.getText()));
        item.setRequiredDisenchantSkill(Integer.parseInt(tfReqDELevel.getText()));
        item.setPage(Integer.parseInt(tfPageTextID.getText()));
        item.setBagFamily(Integer.parseInt(tfBagFamily.getText()));
        item.setHolidayId(Long.parseLong(tfHolidayID.getText()));

    }

    // ********************
    // WEAPON
    //*********************

    private void updateWeaponLayout() {
        tfDamageMin1.setText(String.valueOf(item.getDamageMinimum1()));
        tfDamageMin2.setText(String.valueOf(item.getDamageMinimum2()));
        tfDamageMax1.setText(String.valueOf(item.getDamageMaximum1()));
        tfDamageMax2.setText(String.valueOf(item.getDamageMaximum2()));
        cbDamageType1.getSelectionModel().select(item.getDamageType1());
        cbDamageType2.getSelectionModel().select(item.getDamageType2());

        tfDelay.setText(String.valueOf(item.getDelay()));
        tfModRange.setText(String.valueOf(item.getRangedModRange()));
        cbAmmoType.getSelectionModel().select(item.getAmmoType());
    }

    private void updateWeaponAttributes() {
        item.setDamageMinimum1(Float.parseFloat(tfDamageMin1.getText()));
        item.setDamageMinimum2(Float.parseFloat(tfDamageMin2.getText()));
        item.setDamageMaximum1(Float.parseFloat(tfDamageMax1.getText()));
        item.setDamageMaximum2(Float.parseFloat(tfDamageMax2.getText()));
//        item.setDamageType1(cbDamageType1.getText());
//        item.setDamageType2(cbDamageType2.getText());

        item.setDelay(Integer.parseInt(tfDelay.getText()));
        item.setRangedModRange(Float.parseFloat(tfModRange.getText()));
//        item.setAmmoType(cbAmmoType.getText());
    }

    // ********************
    // STATS
    //*********************

    private void updateStatsLayout() {
        // TODO (19/07-19): Arrays to lists
        ChoiceBox<?>[] types = {
                cbStatType1, cbStatType2, cbStatType3, cbStatType4, cbStatType5,
                cbStatType6, cbStatType7, cbStatType8, cbStatType9, cbStatType10
        };

        TextField[] values = {
                tfStatValue1, tfStatValue2, tfStatValue3, tfStatValue4, tfStatValue5,
                tfStatValue6, tfStatValue7, tfStatValue8, tfStatValue9, tfStatValue10
        };

        int[] typeValues = {
                item.getStatType1(), item.getStatType2(), item.getStatType3(), item.getStatType4(),
                item.getStatType5(), item.getStatType6(), item.getStatType7(), item.getStatType8(),
                item.getStatType9(), item.getStatType10()
        };

        int[] valueValues = {
                item.getStatValue1(), item.getStatValue2(), item.getStatValue3(), item.getStatValue4(),
                item.getStatValue5(), item.getStatValue6(), item.getStatValue7(), item.getStatValue8(),
                item.getStatValue9(), item.getStatValue10()
        };

        for (int i = 0; i < types.length; i++) {
            types[i].getSelectionModel().select(typeValues[i]);
        }

        for (int i = 0; i < values.length; i++) {
            values[i].setText(String.valueOf(valueValues[i]));
        }

        tfScalingStatDist.setText(String.valueOf(item.getScalingStatDistribution()));
        tfScalingStatValue.setText(String.valueOf(item.getScalingStatValue()));
    }

    private void updateStatsAttributes() {
//        item.setStatType1(cbStatType1);
//        item.setStatType2(cbStatType2);
//        item.setStatType3(cbStatType3);
//        item.setStatType4(cbStatType4);
//        item.setStatType5(cbStatType5);
//        item.setStatType6(cbStatType6);
//        item.setStatType7(cbStatType7);
//        item.setStatType8(cbStatType8);
//        item.setStatType9(cbStatType9);
//        item.setStatType10(cbStatType10);
        item.setStatValue1(Integer.parseInt(tfStatValue1.getText()));
        item.setStatValue2(Integer.parseInt(tfStatValue2.getText()));
        item.setStatValue3(Integer.parseInt(tfStatValue3.getText()));
        item.setStatValue4(Integer.parseInt(tfStatValue4.getText()));
        item.setStatValue5(Integer.parseInt(tfStatValue5.getText()));
        item.setStatValue6(Integer.parseInt(tfStatValue6.getText()));
        item.setStatValue7(Integer.parseInt(tfStatValue7.getText()));
        item.setStatValue8(Integer.parseInt(tfStatValue8.getText()));
        item.setStatValue9(Integer.parseInt(tfStatValue9.getText()));
        item.setStatValue10(Integer.parseInt(tfStatValue10.getText()));

        int[] values = {
                item.getStatValue1(), item.getStatValue2(), item.getStatValue3(), item.getStatValue4(),
                item.getStatValue5(), item.getStatValue6(), item.getStatValue7(), item.getStatValue8(),
                item.getStatValue9(), item.getStatValue10()
        };

        item.setStatsCount((int) Arrays.stream(values).filter(i -> i != 0).count());
        item.setScalingStatDistribution(Integer.parseInt(tfScalingStatDist.getText()));
        item.setScalingStatValue(Long.parseLong(tfScalingStatValue.getText()));
    }

    // ********************
    // DEFENCE
    //*********************

    private void updateDefenceLayout() {
        tfArmor.setText(String.valueOf(item.getArmor()));
        tfBlock.setText(String.valueOf(item.getBlock()));
        tfDurability.setText(String.valueOf(item.getMaxDurability()));
        tfArmorDamageMod.setText(String.valueOf(item.getArmorDamageModifier()));
        tfResHoly.setText(String.valueOf(item.getHolyRes()));
        tfResNature.setText(String.valueOf(item.getNatureRes()));
        tfResShadow.setText(String.valueOf(item.getShadowRes()));
        tfResFire.setText(String.valueOf(item.getFireRes()));
        tfResFrost.setText(String.valueOf(item.getFrostRes()));
        tfResArcane.setText(String.valueOf(item.getArcaneRes()));
    }

    private void updateDefenceAttributes() {
        item.setArmor(Integer.parseInt(tfArmor.getText()));
        item.setBlock(Integer.parseInt(tfBlock.getText()));
        item.setMaxDurability(Integer.parseInt(tfDurability.getText()));
        item.setArmorDamageModifier(Float.parseFloat(tfArmorDamageMod.getText()));
        item.setHolyRes(Integer.parseInt(tfResHoly.getText()));
        item.setNatureRes(Integer.parseInt(tfResNature.getText()));
        item.setShadowRes(Integer.parseInt(tfResShadow.getText()));
        item.setFireRes(Integer.parseInt(tfResFire.getText()));
        item.setFrostRes(Integer.parseInt(tfResFrost.getText()));
        item.setArcaneRes(Integer.parseInt(tfResArcane.getText()));
    }

    // ********************
    // SOCKETS
    //*********************

    private void updateSocketsLayout() {
        // TODO (19/07-19) - Make it work

//        int socketColor1 = item.getSocketColor_1();
//
//        Map<RadioButton, Integer> collect = socketValues.entrySet().stream().filter(entry -> entry.getValue() == socketColor1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        for (RadioButton radioButton : collect.keySet()) {
//            System.out.println(radioButton);
//        }

//        @FXML private RadioButton rbSocketNone1;
//        @FXML private ToggleGroup tgSocket1;
//        @FXML private RadioButton rbSocketMeta1;
//        @FXML private RadioButton rbSocketRed1;
//        @FXML private RadioButton rbSocketYellow1;
//        @FXML private RadioButton rbSocketBlue1;
//        @FXML private RadioButton rbSocketPrismatic1;
    }

    private void updateSocketsAttributes() {
//        item.setSocketColor_1();
//        item.setSocketColor_2();
//        item.setSocketColor_3();
//        item.setSocketBonus();
    }

    // ********************
    // LOOKUPS
    //*********************

    private void handleQualityLookupAction(ActionEvent event) {
        List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "item_quality", true, false);
        Integer selected = Integer.valueOf(tfQuality.getText());

        LookupManager lm = LookupManager.getInstance();
        lm.showSingleLookup("Item Quality", "Item Building - Quality", true,
                identifiers, selected, aLong -> {
                    item.setQuality(Math.toIntExact(aLong));
                    tfQuality.setText(String.valueOf(aLong));
                    previewController.update();
                });
    }

    private void handleClassLookupAction(ActionEvent event) {
        List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "item_classes", true, false);
        Integer selected = Integer.valueOf(tfClass.getText());

        LookupManager lm = LookupManager.getInstance();
        lm.showSingleLookup("Item Class", "Item Building - Class", true,
                identifiers, selected, aLong -> {
                    item.setClazz(Math.toIntExact(aLong));
                    tfClass.setText(String.valueOf(aLong));
                    // reset subclass
                    tfSubclass.setText("0");
                    item.setSubclass(0);
                    previewController.update();
                });
    }

    private void handleSubclassLookupAction(ActionEvent event) {
        List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "item_subclasses" + File.separator +
                "item_subclass_" + String.format("%02d", Integer.valueOf(tfClass.getText())), true, true);
        Integer selected = Integer.valueOf(tfSubclass.getText());

        LookupManager lm = LookupManager.getInstance();
        lm.showSingleLookup("Item Subclass", "Item Building - Subclass", false,
                identifiers, selected, aLong -> {
                    item.setSubclass(Math.toIntExact(aLong));
                    tfSubclass.setText(String.valueOf(aLong));
                    previewController.update();
                });
    }

    private void handleSheathLookupAction(ActionEvent event) {
        List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "item_sheath", true, false);
        Integer selected = Integer.valueOf(tfSheath.getText());

        LookupManager lm = LookupManager.getInstance();
        lm.showSingleLookup("Item Sheath", "Item Building - Sheath", true,
                identifiers, selected, aLong -> {
                    item.setSheath(Math.toIntExact(aLong));
                    tfSheath.setText(String.valueOf(aLong));
                    previewController.update();
                });
    }

    private void handleBondingLookupAction(ActionEvent event) {
        List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "item_bonding", true, false);
        Integer selected = Integer.valueOf(tfBonding.getText());

        LookupManager lm = LookupManager.getInstance();
        lm.showSingleLookup("Item Bonding", "Item Building - Bonding", true,
                identifiers, selected, aLong -> {
                    item.setBonding(Math.toIntExact(aLong));
                    tfBonding.setText(String.valueOf(aLong));
                    previewController.update();
                });
    }

    private void handleInventoryLookupAction(ActionEvent event) {
        List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "item_inventory_type", true, false);
        Integer selected = Integer.valueOf(tfInventory.getText());

        LookupManager lm = LookupManager.getInstance();
        lm.showSingleLookup("Item Subclass", "Item Building - Subclass", true,
                identifiers, selected, aLong -> {
                    item.setInventoryType(Math.toIntExact(aLong));
                    tfInventory.setText(String.valueOf(aLong));
                    previewController.update();
                });
    }

}


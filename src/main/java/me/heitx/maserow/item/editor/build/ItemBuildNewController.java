package me.heitx.maserow.item.editor.build;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Pair;
import me.heitx.maserow.common.model.Item;
import me.heitx.maserow.common.utils.JavafxUtil;
import me.heitx.maserow.common.utils.MoneyUtil;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemBuildNewController implements Initializable {
    @FXML private TextField tfEntry;
    @FXML private TextField tfDisplayID;
    @FXML private TextField tfQuality;
    @FXML private TextField tfName;
    @FXML private TextField tfDescription;

    @FXML private TextField tfClass;
    @FXML private TextField tfSheath;
    @FXML private TextField tfItemLevel;
    @FXML private TextField tfBonding;
    @FXML private TextField tfSubclass;
    @FXML private TextField tfInventory;
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
    @FXML private ChoiceBox<?> tfSpellTrigger1;
    @FXML private TextField tfSpellCharges1;
    @FXML private TextField tfSpellPPMRate1;
    @FXML private TextField tfSpellCooldown1;
    @FXML private TextField tfSpellCategory1;
    @FXML private TextField tfSpellCooldownCategory1;
    @FXML private TextField tfSpellEntry2;
    @FXML private ChoiceBox<?> tfSpellTrigger2;
    @FXML private TextField tfSpellCharges2;
    @FXML private TextField tfSpellPPMRate2;
    @FXML private TextField tfSpellCooldown2;
    @FXML private TextField tfSpellCategory2;
    @FXML private TextField tfSpellCooldownCategory2;
    @FXML private TextField tfSpellEntry3;
    @FXML private ChoiceBox<?> tfSpellTrigger3;
    @FXML private TextField tfSpellCharges3;
    @FXML private TextField tfSpellPPMRate3;
    @FXML private TextField tfSpellCooldown3;
    @FXML private TextField tfSpellCategory3;
    @FXML private TextField tfSpellCooldownCategory3;
    @FXML private TextField tfSpellEntry4;
    @FXML private ChoiceBox<?> tfSpellTrigger4;
    @FXML private TextField tfSpellCharges4;
    @FXML private TextField tfSpellPPMRate4;
    @FXML private TextField tfSpellCooldown4;
    @FXML private TextField tfSpellCategory4;
    @FXML private TextField tfSpellCooldownCategory4;
    @FXML private TextField tfSpellEntry5;
    @FXML private ChoiceBox<?> tfSpellTrigger5;
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
    @FXML private ChoiceBox<?> tfDamageType1;
    @FXML private ChoiceBox<?> tfDamageType2;
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

    private Item item;

    private Map<RadioButton, Integer> socketValues;

    public ItemBuildNewController() {
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
    }

    public void setItem(Item item) {
        this.item = item;

        updateGeneral();
        updateEquipment();
        updateVendorAndStacks();
        updateRequirements();
        updateSpells();updateMiscellaneous();

        updateWeapon();
        updateStats();
        updateDefence();
        updateSockets();
    }

    private void updateGeneral() {
        tfEntry.setText(String.valueOf(item.getEntry()));
        tfDisplayID.setText(String.valueOf(item.getDisplayId()));
        tfQuality.setText(String.valueOf(item.getQuality()));
        tfName.setText(item.getName());
        tfDescription.setText(item.getDescription());
    }

    private void updateEquipment() {
        tfClass.setText(String.valueOf(item.getClazz()));
        tfSheath.setText(String.valueOf(item.getSheath()));
        tfItemLevel.setText(String.valueOf(item.getItemLevel()));
        tfBonding.setText(String.valueOf(item.getBonding()));
        tfSubclass.setText(String.valueOf(item.getSubclass()));
        tfInventory.setText(String.valueOf(item.getInventoryType()));
        tfItemSet.setText(String.valueOf(item.getItemSet()));
        tfMaxCount.setText(String.valueOf(item.getMaxCount()));
    }

    private void updateVendorAndStacks() {
        List<TextField> sell = Arrays.asList(tfSellGold, tfSellSilver, tfSellCopper);
        List<TextField> buy = Arrays.asList(tfBuyGold, tfBuySilver, tfBuyCopper);

        JavafxUtil.assignValuesToTextfields(sell, MoneyUtil.totalToGSC(item.getSellPrice()));
        JavafxUtil.assignValuesToTextfields(buy, MoneyUtil.totalToGSC(item.getBuyPrice()));

        tfMaxCount.setText(String.valueOf(item.getMaxCount()));
        tfMaxStacks.setText(String.valueOf(item.getStackable()));
    }

    private void updateRequirements() {
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

    private void updateSpells() {
        Set<TextField> entries = new HashSet<>(Arrays.asList(tfSpellEntry1, tfSpellEntry2, tfSpellEntry3,
                tfSpellEntry4, tfSpellEntry5));
        Set<Integer> entryValues = new HashSet<>(Arrays.asList(item.getSpellId1(), item.getSpellId2(),
                item.getSpellId3(), item.getSpellId4(), item.getSpellId5()));

        Set<ChoiceBox> triggers = new HashSet<>(Arrays.asList(tfSpellTrigger1, tfSpellTrigger2, tfSpellTrigger3,
                tfSpellTrigger4, tfSpellTrigger5));
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

    private void updateMiscellaneous() {
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

    private void updateWeapon() {
        tfDamageMin1.setText(String.valueOf(item.getDamageMinimum1()));
        tfDamageMax1.setText(String.valueOf(item.getDamageMaximum1()));
        tfDamageType1.getSelectionModel().select(item.getDamageType1());
        tfDamageMin2.setText(String.valueOf(item.getDamageMinimum2()));
        tfDamageMax2.setText(String.valueOf(item.getDamageMaximum2()));
        tfDamageType2.getSelectionModel().select(item.getDamageType2());

        tfDelay.setText(String.valueOf(item.getDelay()));
        tfModRange.setText(String.valueOf(item.getRangedModRange()));
        cbAmmoType.getSelectionModel().select(item.getAmmoType());
    }

    private void updateStats() {
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

    private void updateDefence() {
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

    private void updateSockets() {
        // TODO (19/07-19) - Make it work

        int socketColor1 = item.getSocketColor_1();

        Map<RadioButton, Integer> collect = socketValues.entrySet().stream().filter(entry -> entry.getValue() == socketColor1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (RadioButton radioButton : collect.keySet()) {
            System.out.println(radioButton);
        }

//        @FXML private RadioButton rbSocketNone1;
//        @FXML private ToggleGroup tgSocket1;
//        @FXML private RadioButton rbSocketMeta1;
//        @FXML private RadioButton rbSocketRed1;
//        @FXML private RadioButton rbSocketYellow1;
//        @FXML private RadioButton rbSocketBlue1;
//        @FXML private RadioButton rbSocketPrismatic1;
    }
}


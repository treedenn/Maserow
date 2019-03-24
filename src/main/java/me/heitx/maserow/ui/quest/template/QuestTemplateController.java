package me.heitx.maserow.ui.quest.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.repository.IQuestRepository;
import me.heitx.maserow.io.CommonCSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Quest;
import me.heitx.maserow.ui.LayoutUtil;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.utils.MoneyUtil;
import me.heitx.maserow.utils.Queries;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuestTemplateController implements Initializable, Updateable {
	// Buttons
	@FXML private Button btnExecute;
	@FXML private MenuItem miInsert;
	@FXML private MenuItem miUpdate;
	@FXML private MenuItem miDelete;

	// Right side of the layout
	@FXML private TextField tfEntry;
	@FXML private ComboBox<Identifier> cbQuestType;
	@FXML private TextField tfQuestLevel;
	@FXML private TextField tfMinLevel;
	@FXML private TextField tfSortId;
	@FXML private ComboBox<Identifier> cbInfoID;
	@FXML private CheckComboBox<Identifier> ccbFlags;
	@FXML private CheckComboBox<Identifier> ccbRaces;
	@FXML private TextField tfSuggestedGroup;
	@FXML private TextField tfStartItem;
	@FXML private TextField tfReqPlayerKills;

	@FXML private TextField tfCompletionLog;
	@FXML private TextField tfObjectiveText1;
	@FXML private TextField tfObjectiveText2;
	@FXML private TextField tfObjectiveText3;
	@FXML private TextField tfObjectiveText4;

	@FXML private TextField tfCreatureOrGameObjectId1;
	@FXML private TextField tfCreatureOrGameObjectId2;
	@FXML private TextField tfCreatureOrGameObjectId3;
	@FXML private TextField tfCreatureOrGameObjectId4;
	@FXML private TextField tfCreatureOrGameObjectCount1;
	@FXML private TextField tfCreatureOrGameObjectCount2;
	@FXML private TextField tfCreatureOrGameObjectCount3;
	@FXML private TextField tfCreatureOrGameObjectCount4;

	@FXML private TextField tfRequiredItemId1;
	@FXML private TextField tfRequiredItemId2;
	@FXML private TextField tfRequiredItemId3;
	@FXML private TextField tfRequiredItemId4;
	@FXML private TextField tfRequiredItemId5;
	@FXML private TextField tfRequiredItemId6;
	@FXML private TextField tfRequiredItemCount1;
	@FXML private TextField tfRequiredItemCount2;
	@FXML private TextField tfRequiredItemCount3;
	@FXML private TextField tfRequiredItemCount4;
	@FXML private TextField tfRequiredItemCount5;
	@FXML private TextField tfRequiredItemCount6;

	// Left side of the layout
	@FXML private TextField tfLogTitle;
	@FXML private TextArea taQuestDescription;
	@FXML private TextArea taLogDescription;
	@FXML private TextArea taAreaDescription;

	@FXML private HBox hboxChoice1;
	@FXML private Label labelChoiceQuantity1;
	@FXML private Label labelChoiceId1;
	@FXML private HBox hboxChoice2;
	@FXML private Label labelChoiceQuantity2;
	@FXML private Label labelChoiceId2;
	@FXML private HBox hboxChoice3;
	@FXML private Label labelChoiceQuantity3;
	@FXML private Label labelChoiceId3;
	@FXML private HBox hboxChoice4;
	@FXML private Label labelChoiceQuantity4;
	@FXML private Label labelChoiceId4;
	@FXML private HBox hboxChoice5;
	@FXML private Label labelChoiceQuantity5;
	@FXML private Label labelChoiceId5;
	@FXML private HBox hboxChoice6;
	@FXML private Label labelChoiceQuantity6;
	@FXML private Label labelChoiceId6;

	@FXML private TextField tfRewardGold;
	@FXML private TextField tfRewardSilver;
	@FXML private TextField tfRewardCopper;
	@FXML private TextField tfRewardMaxGold;
	@FXML private TextField tfRewardMaxSilver;
	@FXML private TextField tfRewardMaxCopper;
	@FXML private TextField tfExperienceDifficulty;
	@FXML private HBox hboxReward1;
	@FXML private Label labelRewardQuantity1;
	@FXML private Label labelRewardId1;
	@FXML private HBox hboxReward2;
	@FXML private Label labelRewardQuantity2;
	@FXML private Label labelRewardId2;
	@FXML private HBox hboxReward3;
	@FXML private Label labelRewardQuantity3;
	@FXML private Label labelRewardId3;
	@FXML private HBox hboxReward4;
	@FXML private Label labelRewardQuantity4;
	@FXML private Label labelRewardId4;
	@FXML private TextField tfNextQuest;
	@FXML private TextField tfRewardTalents;
	@FXML private TextField tfRewardHonorPoints;
	@FXML private TextField tfRewardHonorKills;
	@FXML private TextField tfRewardArenaPoints;
	@FXML private TextField tfRewardTitle;
	@FXML private TextField tfRewardDisplaySpell;

	private Quest quest;

	// the reward popup controller (one at the same)
	private PopupControl currentPopup;

	List<Identifier> questType;
	List<Identifier> questInfo;
	List<Identifier> flags;
	List<Identifier> races;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loadTextFormatters();

		quest = new Quest();

		btnExecute.setOnAction(this::onExecuteButtonAction);
		miInsert.setOnAction(this::onSaveSqlButtonAction);
		miUpdate.setOnAction(this::onSaveSqlButtonAction);
		miDelete.setOnAction(this::onSaveSqlButtonAction);

		currentPopup = new PopupControl();
		currentPopup.setAutoHide(true);

		final String csvPath = "csv" + File.separator + "quest" + File.separator;

		questType = DelimiterReader.readColumns(csvPath + "questtype", true, false);
		cbQuestType.setItems(FXCollections.observableList(questType));
		cbQuestType.getSelectionModel().select(0);
		LayoutUtil.showOnlyNameOnCombobox(cbQuestType);

		questInfo = DelimiterReader.readColumns(csvPath + "questinfo", true, false);
		cbInfoID.setItems(FXCollections.observableList(questInfo));
		cbInfoID.getSelectionModel().select(0);
		LayoutUtil.showOnlyNameOnCombobox(cbInfoID);

		flags = DelimiterReader.readColumns(csvPath + "flags", false, true);
		ccbFlags.getItems().setAll(flags);
		ccbFlags.getCheckModel().check(0);
		LayoutUtil.showOnlyNameOnCombobox(ccbFlags);

		races = DelimiterReader.readColumns(CommonCSV.RACES);
		ccbRaces.getItems().setAll(races);
		ccbRaces.getCheckModel().checkAll();
		LayoutUtil.showOnlyNameOnCombobox(ccbRaces);

		HBox[] hboxes = new HBox[] {
				hboxChoice1, hboxChoice2, hboxChoice3,
				hboxChoice4, hboxChoice5, hboxChoice6,
				hboxReward1, hboxReward2,
				hboxReward3, hboxReward4,
		};

		Label[] ids = new Label[] {
				labelChoiceId1, labelChoiceId2, labelChoiceId3,
				labelChoiceId4, labelChoiceId5, labelChoiceId6,
				labelRewardId1, labelRewardId2,
				labelRewardId3, labelRewardId4,
		};

		Label[] quantities = new Label[] {
				labelChoiceQuantity1, labelChoiceQuantity2, labelChoiceQuantity3,
				labelChoiceQuantity4, labelChoiceQuantity5, labelChoiceQuantity6,
				labelRewardQuantity1, labelRewardQuantity2,
				labelRewardQuantity3, labelRewardQuantity4,
		};

		for(int i = 0; i < hboxes.length; i++) {
			HBox hbox = hboxes[i];
			Label id = ids[i];
			Label quantity = quantities[i];

			setMouseClickOnHBoxShowPopup(hbox, id, quantity);
		}
	}

	@Override
	public void update() {
		btnExecute.setDisable(!Database.hasAccess(Database.Selection.WORLD));
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
		updateLayout();
	}

	public void loadTextFormatters() {
		TextField[] integerTextFields = new TextField[] {
				tfEntry, tfQuestLevel, tfMinLevel, tfSortId, tfSuggestedGroup, tfStartItem, tfReqPlayerKills,
				tfCreatureOrGameObjectId1, tfCreatureOrGameObjectId2, tfCreatureOrGameObjectId3, tfCreatureOrGameObjectId4,
				tfCreatureOrGameObjectCount1, tfCreatureOrGameObjectCount2, tfCreatureOrGameObjectCount3, tfCreatureOrGameObjectCount4,
				tfRequiredItemId1, tfRequiredItemId2, tfRequiredItemId3, tfRequiredItemId4, tfRequiredItemId5, tfRequiredItemId6,
				tfRequiredItemCount1, tfRequiredItemCount2, tfRequiredItemCount3, tfRequiredItemCount4, tfRequiredItemCount5, tfRequiredItemCount6,
				tfRewardGold, tfRewardSilver, tfRewardCopper, tfRewardMaxGold, tfRewardMaxSilver, tfRewardMaxCopper, tfExperienceDifficulty,
				tfNextQuest, tfRewardTalents, tfRewardHonorPoints, tfRewardHonorKills, tfRewardArenaPoints, tfRewardTitle, tfRewardDisplaySpell
		};

		for(TextField tf : integerTextFields) {
			tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), Integer.valueOf(tf.getText())));
		}
	}

	private void onExecuteButtonAction(ActionEvent event) {
		if(quest != null) {
			updateModel();
			IQuestRepository dao = Database.getInstance().getQuestDAO();

			if(dao.exists(quest.getId())) {
				Optional<ButtonType> alert = LayoutUtil.showAlert(Alert.AlertType.CONFIRMATION, "Conflict", "Identifier already exists..", "There exists already a quest with given identifier! " +
						"Do you want to overwrite the old quest with the new one?", ButtonType.NO, ButtonType.YES);
				if(alert.isPresent() && alert.get() == ButtonType.YES) {
					dao.update(quest);
				}
			} else {
				dao.insert(quest);
			}
		}
	}

	private void onSaveSqlButtonAction(ActionEvent event) {
		if(quest != null) {
			updateModel();

			Map<String, Object> attributes = ConverterUtil.toAttributes(quest);
			final String initialFileName = "quest:" + quest.getLogTitle().replaceAll(" ", "-") + ":" + quest.getId();
			final Window window = btnExecute.getScene().getWindow();

			if(event.getSource() == miInsert) {
				LayoutUtil.showSaveSqlWindow(window, "Save Insert Query", initialFileName, Queries.Quest.insert(false, attributes));
			} else if(event.getSource() == miUpdate) {
				LayoutUtil.showSaveSqlWindow(window, "Save Update Query", initialFileName, Queries.Quest.update(false, attributes));
			} else if(event.getSource() == miDelete) {
				LayoutUtil.showSaveSqlWindow(window, "Save Delete Query", initialFileName, Queries.Quest.delete(false, (Integer) attributes.get("guid")));
			}
		}
	}

	private void updateLayout() {
		if(quest != null) {
			tfEntry.setText(String.valueOf(quest.getId()));
			cbQuestType.getSelectionModel().select(Identifier.findsById(questType, quest.getQuestType()));
			tfQuestLevel.setText(String.valueOf(quest.getQuestLevel()));
			tfMinLevel.setText(String.valueOf(quest.getMinLevel()));
			tfSortId.setText(String.valueOf(quest.getQuestSortID()));
			cbInfoID.getSelectionModel().select(Identifier.findsById(questInfo, quest.getQuestInfoID()));

			ccbFlags.getCheckModel().clearChecks();
			List<Integer> flagsIndexes = Identifier.findIndicesByValue(flags, quest.getFlags());
			for(Integer index : flagsIndexes) {
				ccbFlags.getCheckModel().check(index);
			}

			ccbRaces.getCheckModel().clearChecks();
			List<Integer> racesIndexes = Identifier.findIndicesByValue(races, quest.getAllowableRaces(), true);
			for(Integer index : racesIndexes) {
				ccbRaces.getCheckModel().check(index);
			}

			tfSuggestedGroup.setText(String.valueOf(quest.getSuggestedGroupNum()));
			tfStartItem.setText(String.valueOf(quest.getStartItem()));
			tfReqPlayerKills.setText(String.valueOf(quest.getRequiredPlayerKills()));
			tfCompletionLog.setText(quest.getQuestCompletionLog());
			tfObjectiveText1.setText(quest.getObjectiveText1());
			tfObjectiveText2.setText(quest.getObjectiveText2());
			tfObjectiveText3.setText(quest.getObjectiveText3());
			tfObjectiveText4.setText(quest.getObjectiveText4());
			tfCreatureOrGameObjectId1.setText(String.valueOf(quest.getRequiredNpcOrGo1()));
			tfCreatureOrGameObjectId2.setText(String.valueOf(quest.getRequiredNpcOrGo2()));
			tfCreatureOrGameObjectId3.setText(String.valueOf(quest.getRequiredNpcOrGo3()));
			tfCreatureOrGameObjectId4.setText(String.valueOf(quest.getRequiredNpcOrGo4()));
			tfCreatureOrGameObjectCount1.setText(String.valueOf(quest.getRequiredNpcOrGoCount1()));
			tfCreatureOrGameObjectCount2.setText(String.valueOf(quest.getRequiredNpcOrGoCount2()));
			tfCreatureOrGameObjectCount3.setText(String.valueOf(quest.getRequiredNpcOrGoCount3()));
			tfCreatureOrGameObjectCount4.setText(String.valueOf(quest.getRequiredNpcOrGoCount4()));
			tfRequiredItemId1.setText(String.valueOf(quest.getRequiredItemId1()));
			tfRequiredItemId2.setText(String.valueOf(quest.getRequiredItemId2()));
			tfRequiredItemId3.setText(String.valueOf(quest.getRequiredItemId3()));
			tfRequiredItemId4.setText(String.valueOf(quest.getRequiredItemId4()));
			tfRequiredItemId5.setText(String.valueOf(quest.getRequiredItemId5()));
			tfRequiredItemId6.setText(String.valueOf(quest.getRequiredItemId6()));
			tfRequiredItemCount1.setText(String.valueOf(quest.getRequiredItemCount1()));
			tfRequiredItemCount2.setText(String.valueOf(quest.getRequiredItemCount2()));
			tfRequiredItemCount3.setText(String.valueOf(quest.getRequiredItemCount3()));
			tfRequiredItemCount4.setText(String.valueOf(quest.getRequiredItemCount4()));
			tfRequiredItemCount5.setText(String.valueOf(quest.getRequiredItemCount5()));
			tfRequiredItemCount6.setText(String.valueOf(quest.getRequiredItemCount6()));
			tfLogTitle.setText(quest.getLogTitle());
			taQuestDescription.setText(quest.getQuestDescription());
			taLogDescription.setText(quest.getLogDescription());
			taAreaDescription.setText(quest.getAreaDescription());
			labelChoiceQuantity1.setText(String.valueOf(quest.getRewardChoiceItemQuantity1()));
			labelChoiceId1.setText(String.valueOf(quest.getRewardChoiceItemId1()));
			labelChoiceQuantity2.setText(String.valueOf(quest.getRewardChoiceItemQuantity2()));
			labelChoiceId2.setText(String.valueOf(quest.getRewardChoiceItemId2()));
			labelChoiceQuantity3.setText(String.valueOf(quest.getRewardChoiceItemQuantity3()));
			labelChoiceId3.setText(String.valueOf(quest.getRewardChoiceItemId3()));
			labelChoiceQuantity4.setText(String.valueOf(quest.getRewardChoiceItemQuantity4()));
			labelChoiceId4.setText(String.valueOf(quest.getRewardChoiceItemId4()));
			labelChoiceQuantity5.setText(String.valueOf(quest.getRewardChoiceItemQuantity5()));
			labelChoiceId5.setText(String.valueOf(quest.getRewardChoiceItemId5()));
			labelChoiceQuantity6.setText(String.valueOf(quest.getRewardChoiceItemQuantity6()));
			labelChoiceId6.setText(String.valueOf(quest.getRewardChoiceItemId6()));
			setMoneyTextfields(MoneyUtil.totalToGSC(quest.getRewardMoney()), tfRewardGold, tfRewardSilver, tfRewardCopper);
			setMoneyTextfields(MoneyUtil.totalToGSC(quest.getRewardBonusMoney()), tfRewardMaxGold, tfRewardMaxSilver, tfRewardMaxCopper);
			tfExperienceDifficulty.setText(String.valueOf(quest.getRewardXPDifficulty()));
			labelRewardQuantity1.setText(String.valueOf(quest.getRewardAmount1()));
			labelRewardId1.setText(String.valueOf(quest.getRewardItem1()));
			labelRewardQuantity2.setText(String.valueOf(quest.getRewardAmount2()));
			labelRewardId2.setText(String.valueOf(quest.getRewardItem2()));
			labelRewardQuantity3.setText(String.valueOf(quest.getRewardAmount3()));
			labelRewardId3.setText(String.valueOf(quest.getRewardItem3()));
			labelRewardQuantity4.setText(String.valueOf(quest.getRewardAmount4()));
			labelRewardId4.setText(String.valueOf(quest.getRewardItem4()));
			tfNextQuest.setText(String.valueOf(quest.getRewardNextQuest()));
			tfRewardTalents.setText(String.valueOf(quest.getRewardTalents()));
			tfRewardHonorPoints.setText(String.valueOf(quest.getRewardHonor()));
			tfRewardHonorKills.setText(String.valueOf(quest.getRewardKillHonor()));
			tfRewardArenaPoints.setText(String.valueOf(quest.getRewardArenaPoints()));
			tfRewardTitle.setText(String.valueOf(quest.getRewardTitle()));
			tfRewardDisplaySpell.setText(String.valueOf(quest.getRewardDisplaySpell()));
		}
	}

	private void updateModel() {
		if(quest != null) {
			quest.setId(Integer.parseInt(tfEntry.getText()));
			quest.setQuestType(cbQuestType.getSelectionModel().getSelectedItem().getId());
			quest.setQuestLevel(Integer.parseInt(tfQuestLevel.getText()));
			quest.setMinLevel(Integer.parseInt(tfMinLevel.getText()));
			quest.setQuestSortID(Integer.parseInt(tfSortId.getText()));
			quest.setQuestInfoID(cbInfoID.getSelectionModel().getSelectedItem().getId());
			quest.setSuggestedGroupNum(Integer.parseInt(tfSuggestedGroup.getText()));
			quest.setRewardNextQuest(Integer.parseInt(tfNextQuest.getText()));
			quest.setRewardXPDifficulty(Integer.parseInt(tfExperienceDifficulty.getText()));
			quest.setRewardMoney(MoneyUtil.gscToTotal(tfRewardGold.getText(), tfRewardSilver.getText(), tfRewardCopper.getText()));
			quest.setRewardBonusMoney(MoneyUtil.gscToTotal(tfRewardMaxGold.getText(), tfRewardMaxSilver.getText(), tfRewardMaxCopper.getText()));
			quest.setRewardDisplaySpell(Integer.parseInt(tfRewardDisplaySpell.getText()));
			quest.setRewardSpell(Integer.parseInt(tfRewardDisplaySpell.getText()));
			quest.setRewardHonor(Integer.parseInt(tfRewardHonorPoints.getText()));
			quest.setRewardKillHonor(Float.parseFloat(tfRewardHonorKills.getText()));
			quest.setStartItem(Integer.parseInt(tfStartItem.getText()));
			quest.setFlags(Identifier.calculateValue(ccbFlags.getCheckModel().getCheckedItems(), races, 0L));
			quest.setRequiredPlayerKills(Integer.parseInt(tfReqPlayerKills.getText()));
			quest.setRewardItem1(Integer.parseInt(labelRewardId1.getText()));
			quest.setRewardAmount1(Integer.parseInt(labelRewardQuantity1.getText()));
			quest.setRewardItem2(Integer.parseInt(labelRewardId2.getText()));
			quest.setRewardAmount2(Integer.parseInt(labelRewardQuantity2.getText()));
			quest.setRewardItem3(Integer.parseInt(labelRewardId3.getText()));
			quest.setRewardAmount3(Integer.parseInt(labelRewardQuantity3.getText()));
			quest.setRewardItem4(Integer.parseInt(labelRewardId4.getText()));
			quest.setRewardAmount4(Integer.parseInt(labelRewardQuantity4.getText()));
			quest.setRewardChoiceItemId1(Integer.parseInt(labelChoiceId1.getText()));
			quest.setRewardChoiceItemQuantity1(Integer.parseInt(labelChoiceQuantity1.getText()));
			quest.setRewardChoiceItemId2(Integer.parseInt(labelChoiceId2.getText()));
			quest.setRewardChoiceItemQuantity2(Integer.parseInt(labelChoiceQuantity2.getText()));
			quest.setRewardChoiceItemId3(Integer.parseInt(labelChoiceId3.getText()));
			quest.setRewardChoiceItemQuantity3(Integer.parseInt(labelChoiceQuantity3.getText()));
			quest.setRewardChoiceItemId4(Integer.parseInt(labelChoiceId4.getText()));
			quest.setRewardChoiceItemQuantity4(Integer.parseInt(labelChoiceQuantity4.getText()));
			quest.setRewardChoiceItemId5(Integer.parseInt(labelChoiceId5.getText()));
			quest.setRewardChoiceItemQuantity5(Integer.parseInt(labelChoiceQuantity5.getText()));
			quest.setRewardChoiceItemId6(Integer.parseInt(labelChoiceId6.getText()));
			quest.setRewardChoiceItemQuantity6(Integer.parseInt(labelChoiceQuantity6.getText()));
			quest.setRewardTitle(Integer.parseInt(tfRewardTitle.getText()));
			quest.setRewardTalents(Integer.parseInt(tfRewardTalents.getText()));
			quest.setRewardArenaPoints(Integer.parseInt(tfRewardArenaPoints.getText()));
			quest.setAllowableRaces((int) (Identifier.calculateValue(races, ccbRaces.getCheckModel().getCheckedItems(), 0L)));
			quest.setLogTitle(tfLogTitle.getText());
			quest.setLogDescription(taLogDescription.getText());
			quest.setQuestDescription(taQuestDescription.getText());
			quest.setAreaDescription(taAreaDescription.getText());
			quest.setQuestCompletionLog(tfCompletionLog.getText());
			quest.setRequiredNpcOrGo1(Integer.parseInt(tfCreatureOrGameObjectId1.getText()));
			quest.setRequiredNpcOrGoCount1(Integer.parseInt(tfCreatureOrGameObjectCount1.getText()));
			quest.setRequiredNpcOrGo2(Integer.parseInt(tfCreatureOrGameObjectId2.getText()));
			quest.setRequiredNpcOrGoCount2(Integer.parseInt(tfCreatureOrGameObjectCount2.getText()));
			quest.setRequiredNpcOrGo3(Integer.parseInt(tfCreatureOrGameObjectId3.getText()));
			quest.setRequiredNpcOrGoCount3(Integer.parseInt(tfCreatureOrGameObjectCount3.getText()));
			quest.setRequiredNpcOrGo4(Integer.parseInt(tfCreatureOrGameObjectId4.getText()));
			quest.setRequiredNpcOrGoCount4(Integer.parseInt(tfCreatureOrGameObjectCount4.getText()));
			quest.setRequiredItemId1(Integer.parseInt(tfRequiredItemId1.getText()));
			quest.setRequiredItemCount1(Integer.parseInt(tfRequiredItemCount1.getText()));
			quest.setRequiredItemId2(Integer.parseInt(tfRequiredItemId2.getText()));
			quest.setRequiredItemCount2(Integer.parseInt(tfRequiredItemCount2.getText()));
			quest.setRequiredItemId3(Integer.parseInt(tfRequiredItemId3.getText()));
			quest.setRequiredItemCount3(Integer.parseInt(tfRequiredItemCount3.getText()));
			quest.setRequiredItemId4(Integer.parseInt(tfRequiredItemId4.getText()));
			quest.setRequiredItemCount4(Integer.parseInt(tfRequiredItemCount4.getText()));
			quest.setRequiredItemId5(Integer.parseInt(tfRequiredItemId5.getText()));
			quest.setRequiredItemCount5(Integer.parseInt(tfRequiredItemCount5.getText()));
			quest.setRequiredItemId6(Integer.parseInt(tfRequiredItemId6.getText()));
			quest.setRequiredItemCount6(Integer.parseInt(tfRequiredItemCount6.getText()));
			quest.setObjectiveText1(tfObjectiveText1.getText());
			quest.setObjectiveText2(tfObjectiveText2.getText());
			quest.setObjectiveText3(tfObjectiveText3.getText());
			quest.setObjectiveText4(tfObjectiveText4.getText());
		}
	}

	private void setMoneyTextfields(long[] money, TextField gold, TextField silver, TextField copper) {
		gold.setText(String.valueOf(money[0]));
		silver.setText(String.valueOf(money[1]));
		copper.setText(String.valueOf(money[2]));
	}

	private void setMouseClickOnHBoxShowPopup(HBox owner, Label currId, Label currQuantity) {
		owner.setOnMouseClicked(event -> {
			TextField tfId = new TextField(currId.getText());
			TextField tfQuantity = new TextField(currQuantity.getText());

			tfId.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), Integer.valueOf(tfId.getText())));
			tfQuantity.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), Integer.valueOf(tfQuantity.getText())));

			VBox vboxId = new VBox(new Label("Item ID"), tfId);
			VBox vboxAmount = new VBox(new Label("Item Quantity"), tfQuantity);

			HBox hbox = new HBox(5, vboxId, vboxAmount);
			hbox.setPadding(new Insets(5));

			hbox.setOnKeyPressed(event1 -> {
				if(event1.getCode() == KeyCode.ENTER) {
					currentPopup.hide();
				} else if(event1.getCode() == KeyCode.ESCAPE) {
					currentPopup.setOnHidden(null);
					currentPopup.hide();
				}
			});

			currentPopup.setOnHidden(event1 -> {
				currId.setText(tfId.getText());
				currQuantity.setText(tfQuantity.getText());
			});
			currentPopup.getScene().setRoot(hbox);

			currentPopup.show(owner, event.getScreenX(), event.getScreenY());
		});
	}
}
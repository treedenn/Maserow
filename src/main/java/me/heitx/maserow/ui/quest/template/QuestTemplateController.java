package me.heitx.maserow.ui.quest.template;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import me.heitx.maserow.io.CommonCSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Quest;
import me.heitx.maserow.model.Stack;
import me.heitx.maserow.ui.NodeUtil;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.utils.MoneyUtil;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.net.URL;
import java.util.List;
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
		currentPopup = new PopupControl();
		currentPopup.setAutoHide(true);

		final String csvPath = "csv" + File.separator + "quest" + File.separator;

		questType = DelimiterReader.readColumns(csvPath + "questtype.csv", true, false);
		cbQuestType.setItems(FXCollections.observableList(questType));
		cbQuestType.getSelectionModel().select(0);
		NodeUtil.showOnlyNameOnCombobox(cbQuestType);

		questInfo = DelimiterReader.readColumns(csvPath + "questinfo.csv", true, false);
		cbInfoID.setItems(FXCollections.observableList(questInfo));
		cbInfoID.getSelectionModel().select(0);
		NodeUtil.showOnlyNameOnCombobox(cbInfoID);

		flags = DelimiterReader.readColumns(csvPath + "flags.csv", false, true);
		ccbFlags.getItems().setAll(flags);
		ccbFlags.getCheckModel().check(0);
		NodeUtil.showOnlyNameOnCombobox(ccbFlags);

		races = DelimiterReader.readColumns(CommonCSV.RACES);
		ccbRaces.getItems().setAll(races);
		ccbRaces.getCheckModel().checkAll();
		NodeUtil.showOnlyNameOnCombobox(ccbRaces);

		hboxChoice1.setOnMouseClicked(event -> {
			showIdQuantityPopup(event, hboxChoice1, labelChoiceId1.getText(), labelChoiceQuantity1.getText(), stack -> {
				labelChoiceId1.setText(String.valueOf(stack.getId()));
				labelChoiceQuantity1.setText(String.valueOf(stack.getQuantity()));
				return null;
			});
		});
	}

	@Override
	public void update() {

	}

	public void setQuest(Quest quest) {
		this.quest = quest;

		updateLayout();
	}

	private void updateLayout() {

	}

	private void updateModel() {
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
		quest.setFlags(Identifier.calculateValue(ccbFlags.getCheckModel().getCheckedItems()));
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
		quest.setAllowableRaces((int) (Identifier.calculateValue(races, ccbRaces.getCheckModel().getCheckedItems(), 0)));
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

	private void showIdQuantityPopup(MouseEvent event, HBox owner, String currId, String currQuantity, Callback<Stack, Void> onHiddenCallback) {
		TextField tfId = new TextField(currId);
		TextField tfQuantity = new TextField(currQuantity);

		VBox vboxId = new VBox(new Label("Item Id"), tfId);
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
			Stack stack = new Stack(Integer.parseInt(tfId.getText()), Integer.parseInt(tfQuantity.getText()));
			onHiddenCallback.call(stack);
		});
		currentPopup.getScene().setRoot(hbox);
		currentPopup.show(owner, event.getScreenX(), event.getScreenY());
	}
}
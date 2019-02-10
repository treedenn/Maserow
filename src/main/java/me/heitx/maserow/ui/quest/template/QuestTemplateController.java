package me.heitx.maserow.ui.quest.template;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import me.heitx.maserow.model.Stack;
import me.heitx.maserow.ui.Updateable;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestTemplateController implements Initializable, Updateable {
	@FXML private Button btnExecute;
	@FXML private MenuItem miInsert;
	@FXML private MenuItem miUpdate;
	@FXML private MenuItem miDelete;
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
	@FXML private HBox hboxReward5;
	@FXML private Label labelRewardQuantity5;
	@FXML private Label labelRewardId5;
	@FXML private HBox hboxReward6;
	@FXML private Label labelRewardQuantity6;
	@FXML private Label labelRewardId6;

	@FXML private TextField tfRewardTitle;
	@FXML private TextField tfRewardHonorPoints;
	@FXML private TextField tfRewardHonorKills;
	@FXML private TextField tfRewardArenaPoints;
	@FXML private TextField tfRewardDisplaySpell;

	private PopupControl currentPopup;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		currentPopup = new PopupControl();
		currentPopup.setAutoHide(true);

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
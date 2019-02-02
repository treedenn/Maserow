package me.heitx.maserow.ui.quest.template;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import me.heitx.maserow.ui.Updateable;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestTemplateController implements Initializable, Updateable {
	@FXML private HBox hboxChoice1;
	@FXML private Label labelChoiceAmount1;
	@FXML private ImageView ivChoice1;
	@FXML private Label labelChoiceId1;

	private PopupControl currentPopup;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ivChoice1.setImage(new Image(getClass().getClassLoader().getResource("question-mark.png").toExternalForm()));

		currentPopup = new PopupControl();
		currentPopup.setAutoHide(true);

		hboxChoice1.setOnMouseClicked(event -> {
			if(currentPopup != null && !currentPopup.isShowing()) {
				TextField tfId = new TextField(labelChoiceId1.getText());
				TextField tfAmount = new TextField(labelChoiceAmount1.getText());

				VBox vboxId = new VBox(new Label("Item Id"), tfId);
				VBox vboxAmount = new VBox(new Label("Item Quantity"), tfAmount);

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
					labelChoiceId1.setText(tfId.getText());
					labelChoiceAmount1.setText(tfAmount.getText());
				});
				currentPopup.getScene().setRoot(hbox);
				currentPopup.show(hboxChoice1, event.getScreenX(), event.getScreenY());
			}
		});
	}

	@Override
	public void update() {

	}
}
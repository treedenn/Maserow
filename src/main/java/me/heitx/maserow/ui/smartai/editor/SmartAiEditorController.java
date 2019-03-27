package me.heitx.maserow.ui.smartai.editor;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.SmartScript;
import me.heitx.maserow.ui.LayoutUtil;
import org.controlsfx.control.CheckComboBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SmartAiEditorController implements Initializable {
	@FXML private Label labelTitle;

	@FXML private TableView<SmartScript> tvOverview;
	@FXML private TableColumn<SmartScript, Integer> tcID;
	@FXML private TableColumn<SmartScript, Integer> tcLink;
	@FXML private TableColumn<SmartScript, Integer> tcPhase;
	@FXML private TableColumn<SmartScript, Integer> tcChance;
	@FXML private TableColumn<SmartScript, Integer> tcAction;
	@FXML private TableColumn<SmartScript, Integer> tcTarget;
	@FXML private TableColumn<SmartScript, Long> tcFlags;
	@FXML private TableColumn<SmartScript, String> tcComment;

	@FXML private Button btnAdd;
	@FXML private Button btnDelete;
	@FXML private Button btnDuplicate;
	@FXML private Button btnExecute;
	@FXML private MenuItem miEvents;
	@FXML private MenuItem miActions;
	@FXML private MenuItem miTargets;

	@FXML private TextField tfComment;
	@FXML private ComboBox<Identifier> cbEvent;
	@FXML private ComboBox<Identifier> cbAction;
	@FXML private ComboBox<Identifier> cbTarget;
	@FXML private TextField tfID;
	@FXML private TextField tfLink;
	@FXML private TextField tfFlags;
	@FXML private CheckComboBox<Identifier> ccbPhase;
	@FXML private TextField tfChance;

	@FXML private TextField tfEventParam1;
	@FXML private TextField tfEventParam2;
	@FXML private TextField tfEventParam3;
	@FXML private TextField tfEventParam4;
	@FXML private TextField tfEventParam5;

	@FXML private TextField tfActionParam1;
	@FXML private TextField tfActionParam2;
	@FXML private TextField tfActionParam3;
	@FXML private TextField tfActionParam4;
	@FXML private TextField tfActionParam5;
	@FXML private TextField tfActionParam6;

	@FXML private TextField tfTargetParam1;
	@FXML private TextField tfTargetParam2;
	@FXML private TextField tfTargetParam3;
	@FXML private TextField tfTargetParam4;
	@FXML private TextField tfTargetX;
	@FXML private TextField tfTargetY;
	@FXML private TextField tfTargetZ;
	@FXML private TextField tfTargetO;

	private ObservableList<SmartScript> smartScripts;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		smartScripts = FXCollections.emptyObservableList();

		tvOverview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		btnAdd.setOnAction(this::onButtonAddAction);
		btnDelete.setOnAction(this::onButtonDeleteAction);
		btnDuplicate.setOnAction(this::onButtonDuplicateAction);

		miEvents.setOnAction(this::onMenuButtonLinksAction);
		miActions.setOnAction(this::onMenuButtonLinksAction);
		miTargets.setOnAction(this::onMenuButtonLinksAction);

		final String csvPath = "csv" + File.separator + "smartscript" + File.separator;
		List<Identifier> events = DelimiterReader.readColumns(csvPath + "event_type", true, false);
		List<Identifier> actions = DelimiterReader.readColumns(csvPath + "action_type", true, false);
		List<Identifier> targets = DelimiterReader.readColumns(csvPath + "target_type", true, false);
		List<Identifier> phases = DelimiterReader.readColumns(csvPath + "event_phase_mask", true, false);

		cbEvent.getItems().addAll(events);
		cbAction.getItems().addAll(actions);
		cbTarget.getItems().addAll(targets);
		ccbPhase.getItems().addAll(phases);

		cbEvent.getSelectionModel().selectFirst();
		cbAction.getSelectionModel().selectFirst();
		cbTarget.getSelectionModel().selectFirst();

		LayoutUtil.showOnlyNameOnCombobox(cbEvent, cbAction, cbTarget);
	}

	private void onButtonAddAction(ActionEvent event) {
		SmartScript ss = new SmartScript();
		ss.setLink(Integer.parseInt(tfLink.getText()));
		ss.setEventType(cbEvent.getSelectionModel().getSelectedItem().getId());
		// TODO: Fix this..
		ss.setEventPhaseMask(0);
		ss.setEventChance(Integer.parseInt(tfEventParam1.getText()));
		ss.setEventParam1(Long.parseLong(tfEventParam1.getText()));
		ss.setEventParam2(Long.parseLong(tfEventParam2.getText()));
		ss.setEventParam3(Long.parseLong(tfEventParam3.getText()));
		ss.setEventParam4(Long.parseLong(tfEventParam4.getText()));
		ss.setEventParam5(Long.parseLong(tfEventParam5.getText()));
		ss.setActionType(cbAction.getSelectionModel().getSelectedItem().getId());
		ss.setActionParam1(Long.parseLong(tfActionParam1.getText()));
		ss.setActionParam2(Long.parseLong(tfActionParam2.getText()));
		ss.setActionParam3(Long.parseLong(tfActionParam3.getText()));
		ss.setActionParam4(Long.parseLong(tfActionParam4.getText()));
		ss.setActionParam5(Long.parseLong(tfActionParam5.getText()));
		ss.setActionParam6(Long.parseLong(tfActionParam6.getText()));
		ss.setTargetType(cbTarget.getSelectionModel().getSelectedItem().getId());
		ss.setTargetParam1(Long.parseLong(tfTargetParam1.getText()));
		ss.setTargetParam2(Long.parseLong(tfTargetParam2.getText()));
		ss.setTargetParam3(Long.parseLong(tfTargetParam3.getText()));
		ss.setTargetParam4(Long.parseLong(tfTargetParam4.getText()));
		ss.setTargetX(Long.parseLong(tfTargetX.getText()));
		ss.setTargetY(Long.parseLong(tfTargetY.getText()));
		ss.setTargetZ(Long.parseLong(tfTargetZ.getText()));
		ss.setTargetO(Long.parseLong(tfTargetO.getText()));
		ss.setComment(tfComment.getText());

		smartScripts.add(ss);
	}

	private void onButtonDeleteAction(ActionEvent event) {
		final ObservableList<Integer> selectedIndices = tvOverview.getSelectionModel().getSelectedIndices();

		if(selectedIndices.size() > 0) {
			for(int i = 0; i < selectedIndices.size(); i++) {
				tvOverview.getItems().remove(selectedIndices.get(i) - i);
			}
		}
	}

	private void onButtonDuplicateAction(ActionEvent event) {

	}

	private void onMenuButtonLinksAction(ActionEvent event) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				if(event.getSource() == miEvents) {
					Desktop.getDesktop().browse(new URI("https://trinitycore.atlassian.net/wiki/spaces/tc/pages/2130108/smart+scripts#smart_scripts-event_type"));
				} else if(event.getSource() == miActions) {
					Desktop.getDesktop().browse(new URI("https://trinitycore.atlassian.net/wiki/spaces/tc/pages/2130108/smart+scripts#smart_scripts-action_type"));
				} else if(event.getSource() == miTargets) {
					Desktop.getDesktop().browse(new URI("https://trinitycore.atlassian.net/wiki/spaces/tc/pages/2130108/smart+scripts#smart_scripts-target_type"));
				}
			} catch(IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}
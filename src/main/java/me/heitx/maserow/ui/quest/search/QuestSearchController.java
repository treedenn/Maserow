package me.heitx.maserow.ui.quest.search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import me.heitx.maserow.model.Quest;
import me.heitx.maserow.ui.common.SearchController;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestSearchController extends SearchController<Quest> {
	@FXML private TextField tfTitle;
	@FXML private TableColumn<Quest, String> tcTitle;
	@FXML private TableColumn<Quest, String> tcDescription;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);


	}

	@Override
	public void update() {
		super.update();


	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {

	}
}
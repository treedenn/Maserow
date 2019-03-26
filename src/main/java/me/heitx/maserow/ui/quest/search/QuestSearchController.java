package me.heitx.maserow.ui.quest.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.model.Quest;
import me.heitx.maserow.ui.common.SearchController;

import java.net.URL;
import java.util.List;
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
		btnSearch.setDisable(!Database.hasAccess(Database.Selection.WORLD));

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>((long) data.getValue().getId()));
		tcTitle.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getLogTitle()));
		tcDescription.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getQuestDescription()));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<Quest> quests;

		if(tfEntry.getText().isEmpty() && tfTitle.getText().isEmpty()) {
			quests = Database.getInstance().getQuestRepository().getAll(100);
		} else {
			quests = Database.getInstance().getQuestRepository().search(Integer.parseInt(tfEntry.getText()), tfTitle.getText(), 100);
		}

		tvSearch.setItems(FXCollections.observableList(quests));
	}
}
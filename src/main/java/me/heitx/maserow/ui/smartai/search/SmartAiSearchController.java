package me.heitx.maserow.ui.smartai.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.repository.ISmartScriptRepository;
import me.heitx.maserow.model.Creature;
import me.heitx.maserow.model.SimpleSearchModel;
import me.heitx.maserow.ui.common.SearchController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SmartAiSearchController extends SearchController<SimpleSearchModel> {
	@FXML private TextField tfName;
	@FXML private TableColumn<SimpleSearchModel, String> tcName;

	@FXML private ToggleGroup tgSourceType;
	@FXML private RadioButton rbCreature;
	@FXML private RadioButton rbGameObject;
	@FXML private CheckBox cbExisting;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
	}

	@Override
	public void update() {
		btnSearch.setDisable(!Database.hasAccess(Database.Selection.WORLD));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<SimpleSearchModel> models;

		int entry = Integer.parseInt(tfEntry.getText());
		String name = tfName.getText();

		if(entry != 0 || !name.isEmpty()) {
			ISmartScriptRepository repository = Database.getInstance().getSmartScriptRepository();

			if(tgSourceType.getSelectedToggle() == rbCreature) {
				models = repository.search(0, entry, name, cbExisting.isSelected());
			} else {
				models = repository.search(1, entry, name, cbExisting.isSelected());
			}

			tvSearch.setItems(FXCollections.observableList(models));
			sortByEntry();
		}
	}
}
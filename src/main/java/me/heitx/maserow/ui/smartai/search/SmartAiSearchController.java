package me.heitx.maserow.ui.smartai.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import me.heitx.maserow.database.Database;
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
	}

	@Override
	public void update() {
		super.update();

		btnSearch.setDisable(!Database.hasAccess(Database.Selection.WORLD));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<SimpleSearchModel> models;

		int entry = Integer.parseInt(tfEntry.getText());
		String name = tfName.getText();

		if(tgSourceType.getSelectedToggle() == rbCreature) {
			models = searchCreatures(entry, name);
		} else {
			models = searchGameObjects(entry, name);
		}

		tvSearch.setItems(FXCollections.observableList(models));
	}

	private List<SimpleSearchModel> searchCreatures(int entry, String name) {
		List<SimpleSearchModel> creatures = new ArrayList<>();

//		List<Creature> search;
//		if(entry <= 0 && name.isEmpty()) {
//			search = Database.getInstance().getCreatureDAO().getAll(100);
//		} else {
//			search = Database.getInstance().getCreatureDAO().search(entry, name, 100);
//		}
//
//		for(Creature creature : search) {
//			creatures.add(new SimpleSearchModel(creature.getEntry(), creature.getName()));
//		}

		return creatures;
	}

	private List<SimpleSearchModel> searchGameObjects(int entry, String name) {
		List<SimpleSearchModel> creatures = new ArrayList<>();

//		List<Creature> search;
//		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
//			search = Database.getInstance().getCreatureDAO().getAll(100);
//		} else {
//			search = Database.getInstance().getCreatureDAO().search(entry, name, 100);
//		}
//
//		for(Creature creature : search) {
//			creatures.add(new SimpleSearchModel(creature.getEntry(), creature.getName()));
//		}

		return creatures;
	}
}
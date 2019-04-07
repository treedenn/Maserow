package me.heitx.maserow.creature.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.database.repository.ICreatureRepository;
import me.heitx.maserow.common.model.Creature;
import me.heitx.maserow.common.ui.SearchController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreatureSearchController extends SearchController<Creature> {
	@FXML private TextField tfName;
	@FXML private TableColumn<Creature, String> tcName;
	@FXML private TableColumn<Creature, String> tcSubname;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);
	}

	@Override
	public void update() {
		btnSearch.setDisable(!Database.hasAccess(Database.Selection.WORLD));

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>((long) data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
		tcSubname.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getSubname()));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<Creature> creatures;
		ICreatureRepository repo = Database.getInstance().getCreatureRepository();

		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
			creatures = repo.getAll(100);
		} else {
			creatures = repo.search(Integer.parseInt(tfEntry.getText()), tfName.getText(), 100);
		}

		tvSearch.setItems(FXCollections.observableList(creatures));
	}
}
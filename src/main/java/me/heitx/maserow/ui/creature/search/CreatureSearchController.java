package me.heitx.maserow.ui.creature.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.model.Creature;
import me.heitx.maserow.model.Quest;
import me.heitx.maserow.ui.common.SearchController;
import me.heitx.maserow.utils.ConverterUtil;

import java.net.URL;
import java.util.List;
import java.util.Map;
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
		super.update();

		btnSearch.setDisable(!Database.isIsLoggedIn());

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
		tcSubname.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getSubname()));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<Map<String, Object>> search;

		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
			search = Database.getInstance().getCreatureDAO().getAll(100);
		} else {
			search = Database.getInstance().getCreatureDAO().search(Integer.parseInt(tfEntry.getText()), tfName.getText(), 100);
		}

		List<Creature> creatures = ConverterUtil.toObjects(Creature.class, search);

		tvSearch.setItems(FXCollections.observableList(creatures));
	}
}
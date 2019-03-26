package me.heitx.maserow.ui.item.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.common.SearchController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemSearchController extends SearchController<Item> {
	@FXML private TextField tfName;
	@FXML private TableColumn<Item, String> tcName;
	@FXML private TableColumn<Item, String> tcDescription;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>((long) data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
		tcDescription.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDescription()));
	}

	@Override
	public void update() {
		btnSearch.setDisable(!Database.hasAccess(Database.Selection.WORLD));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<Item> items;

		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
			items = Database.getInstance().getItemRepository().getAll(100);
		} else {
			items = Database.getInstance().getItemRepository().search(Integer.parseInt(tfEntry.getText()), tfName.getText(), 100);
		}

		tvSearch.setItems(FXCollections.observableList(items));
	}
}

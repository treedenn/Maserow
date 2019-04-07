package me.heitx.maserow.item.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import me.heitx.maserow.common.database.Database;
import me.heitx.maserow.common.database.repository.IItemRepository;
import me.heitx.maserow.common.model.Item;
import me.heitx.maserow.common.ui.SearchController;

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
		IItemRepository repo = Database.getInstance().getItemRepository();

		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
			items = repo.getAll(100);
		} else {
			items = repo.search(Integer.parseInt(tfEntry.getText()), tfName.getText(), 100);
		}

		tvSearch.setItems(FXCollections.observableList(items));
	}
}

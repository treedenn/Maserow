package me.heitx.maserow.ui.item.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import me.heitx.maserow.converter.Converter;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.Updateable;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ItemSearchController implements Initializable, Updateable {
	@FXML private TextField tfEntry;
	@FXML private TextField tfName;
	@FXML private Button btnSearch;
	@FXML private TableView<Item> tvSearch;
	@FXML private TableColumn<Item, Integer> tcEntry;
	@FXML private TableColumn<Item, String> tcName;
	@FXML private TableColumn<Item, String> tcDescription;

	private Callback<Item, Void> rowSelectionCallback;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		update();

		btnSearch.setOnAction(this::onButtonSearchAction);
		tvSearch.setRowFactory(this::onTableViewRowSelection);

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
		tcDescription.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDescription()));
	}

	@Override
	public void update() {
		btnSearch.setDisable(!Database.isIsLoggedIn());
	}

	public void setRowSelectionCallback(Callback<Item, Void> rowSelectionCallback) {
		this.rowSelectionCallback = rowSelectionCallback;
	}

	private void onButtonSearchAction(ActionEvent event) {
		List<Item> items;
		List<Map<String, Object>> search;

		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
			search = Database.getInstance().getItemDAO().getAll(100);
		} else {
			search = Database.getInstance().getItemDAO().search(Integer.parseInt(tfEntry.getText()), tfName.getText(), 100);
		}

		items = Converter.getInstance().toItems(search);
		tvSearch.setItems(FXCollections.observableList(items));
	}

	private TableRow<Item> onTableViewRowSelection(TableView<Item> itemTableView) {
		TableRow<Item> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				Item selectedItem = tvSearch.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					// TODO: Use same reference or get a new one? Perhaps a setting?
					// Uses reference for now.
					if(rowSelectionCallback != null) rowSelectionCallback.call(selectedItem);
				}
			}
		});
		return row;
	}
}

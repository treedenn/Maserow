package me.heitx.maserow.ui.item.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.common.SearchController;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ItemSearchController extends SearchController<Item> {
	@FXML private TextField tfName;
	@FXML private TableColumn<Item, String> tcName;
	@FXML private TableColumn<Item, String> tcDescription;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);

		tcEntry.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getEntry()));
		tcName.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
		tcDescription.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getDescription()));
	}

	@Override
	protected void onButtonSearchAction(ActionEvent event) {
		List<Item> items;
		List<Map<String, Object>> search;

		if(tfEntry.getText().isEmpty() && tfName.getText().isEmpty()) {
			search = Database.getInstance().getItemDAO().getAll(100);
		} else {
			search = Database.getInstance().getItemDAO().search(Integer.parseInt(tfEntry.getText()), tfName.getText(), 100);
		}

		items = ConverterUtil.toObjects(Item.class, search);
		tvSearch.setItems(FXCollections.observableList(items));
	}
}

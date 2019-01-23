package me.heitx.maserow.ui.item.search;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import me.heitx.maserow.model.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemSearchController implements Initializable {
	@FXML private TextField tfEntry;
	@FXML private TextField tfName;
	@FXML private Button btnSearch;
	@FXML private TableView<Item> tvSearch;
	@FXML private TableColumn<Item, Integer> tcEntry;
	@FXML private TableColumn<Item, String> tcName;
	@FXML private TableColumn<Item, String> tcDescription;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Item item = new Item();
		item.setEntry(3000);
		item.setName("Name!");
		item.setDescription("DESCRIPTION");

		tcEntry.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getEntry()));
		tcName.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getName()));
		tcDescription.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getDescription()));

		tvSearch.getItems().add(item);
		tvSearch.getItems().add(item);
		tvSearch.getItems().add(item);
		tvSearch.getItems().add(item);
		tvSearch.getItems().add(item);
	}
}

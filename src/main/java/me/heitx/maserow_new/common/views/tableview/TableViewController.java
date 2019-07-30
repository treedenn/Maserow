package me.heitx.maserow_new.common.views.tableview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import me.heitx.maserow_new.common.io.Identifier;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {
	@FXML private TableView<Identifier> tvTable;

	@FXML private TableColumn<Identifier, Integer> tcId;
	@FXML private TableColumn<Identifier, Long> tcValue;
	@FXML private TableColumn<Identifier, String> tcName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	public void setTableContent(Collection<Identifier> identifiers) {
		tvTable.getItems().clear();
		tvTable.getItems().addAll(identifiers);
	}

	public void hideColumnId() {
		tcId.setVisible(false);
	}

	public void hideColumnValue() {
		tcValue.setVisible(false);
	}
}

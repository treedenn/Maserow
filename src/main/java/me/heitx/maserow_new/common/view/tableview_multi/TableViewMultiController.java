package me.heitx.maserow_new.common.view.tableview_multi;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import me.heitx.maserow_new.common.io.Identifier;

import java.net.URL;
import java.util.*;

public class TableViewMultiController implements Initializable {
	@FXML private TableView<Identifier> tvTable;
	@FXML private TableColumn<Identifier, Integer> tcId;
	@FXML private TableColumn<Identifier, Long> tcValue;
	@FXML private TableColumn<Identifier, String> tcName;
	@FXML private TableColumn<Identifier, Boolean> tcSelected;

	@FXML private TextField tfBitmask;
	@FXML private Button btnConvert;

	@FXML private Label labelStatus;

	private ObservableMap<Identifier, Boolean> selected;

	// {31 Jul 2019 02:21} Heitx - TODO: Create a new object for the data..
	// {31 Jul 2019 02:42} Heitx - TODO: Maybe rename Identifier to CSVData
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selected = FXCollections.observableHashMap();

		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcSelected.setCellFactory(CheckBoxTableCell.forTableColumn(tcSelected));
		tcSelected.setCellValueFactory(param -> {
			SimpleBooleanProperty property = new SimpleBooleanProperty(selected.get(param.getValue()));
			property.addListener((observable, oldValue, newValue) -> {
				selected.put(param.getValue(), newValue);
				tfBitmask.setText(String.valueOf(calculateBitmask()));
			});

			return property;
		});

		btnConvert.setOnAction(this::handleConvertAction);

		tvTable.setRowFactory(this::handleRowMouseClick);
	}

	private void handleConvertAction(ActionEvent event) {
		// reset
		for(Identifier identifier : selected.keySet()) {
			selected.put(identifier, false);
		}

		refreshSelections();

		convertBitmask();
	}

	public void setTableContent(Collection<Identifier> identifiers) {
		selected.clear();

		// insert
		for(Identifier identifier : identifiers) {
			selected.put(identifier, false);
		}

		tvTable.getItems().clear();
		tvTable.getItems().addAll(identifiers);
	}

	// {31 Jul 2019 03:57} Heitx - TODO: Return a list of bitmasks not used.
	private void convertBitmask() {
		long l = Long.valueOf(tfBitmask.getText());

		List<Identifier> items = new ArrayList<>(tvTable.getItems());
		Collections.reverse(items);

		for(Identifier item : items) {
			if(item.getValue() <= l) {
				selected.put(item, true);
				l -= item.getValue();
			}

			if(l == 0) break;
		}
	}
	
	private long calculateBitmask() {
		long l = 0;

		for(Map.Entry<Identifier, Boolean> entry : selected.entrySet()) {
			if(entry.getValue()) {
				l += entry.getKey().getValue();
			}
		}
		
		return l;
	}

	public void hideColumnId() {
		tcId.setVisible(false);
	}

	public void hideColumnValue() {
		tcValue.setVisible(false);
	}

	private TableRow<Identifier> handleRowMouseClick(TableView<Identifier> tv) {
		TableRow<Identifier> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				Identifier item = row.getTableView().getSelectionModel().getSelectedItem();
				selected.put(item, !selected.get(item));

				refreshSelections();

				tfBitmask.setText(String.valueOf(calculateBitmask()));
			}
		});

		return row;
	}

	private void refreshSelections() {
		tcSelected.setVisible(false);
		tcSelected.setVisible(true);
	}
}

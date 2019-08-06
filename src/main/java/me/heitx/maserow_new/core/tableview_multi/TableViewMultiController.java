package me.heitx.maserow_new.core.tableview_multi;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import me.heitx.maserow_new.common.io.Identifier;
import me.heitx.maserow_new.reader.CSVData;
import me.heitx.maserow_new.reader.CSVFile;

import java.net.URL;
import java.util.*;

public class TableViewMultiController implements Initializable {
	@FXML private TableView<CSVData> tvTable;
	@FXML private TableColumn<CSVData, Integer> tcId;
	@FXML private TableColumn<CSVData, Long> tcBitmask;
	@FXML private TableColumn<CSVData, String> tcName;
	@FXML private TableColumn<CSVData, Boolean> tcSelected;
	@FXML private TableColumn<CSVData, String> tcDescription;

	@FXML private HBox hboxBitmask;
	@FXML private TextField tfBitmask;
	@FXML private Button btnConvert;

	@FXML private Label labelStatus;

	private ObservableMap<CSVData, Boolean> selected;

	// {31 Jul 2019 02:21} Heitx - TODO: Create a new object for the selective data.
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selected = FXCollections.observableHashMap();

		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcBitmask.setCellValueFactory(new PropertyValueFactory<>("bitmask"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		tcSelected.setCellFactory(CheckBoxTableCell.forTableColumn(tcSelected));
		tcSelected.setCellValueFactory(param -> {
			SimpleBooleanProperty property = new SimpleBooleanProperty(selected.get(param.getValue()));
			property.addListener((observable, oldValue, newValue) -> {
				selected.put(param.getValue(), newValue);
				tfBitmask.setText(String.valueOf(calculateBitmask()));
			});

			return property;
		});

		// simple validation
		tfBitmask.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue) { // when focus lost
				if(!tfBitmask.getText().matches("^\\d+$")) {
					tfBitmask.setText("0");
				}
			}
		});

		btnConvert.setOnAction(this::handleConvertAction);

		tvTable.setRowFactory(this::handleRowMouseClick);
	}

	private void handleConvertAction(ActionEvent event) {
		// reset
		for(CSVData identifier : selected.keySet()) {
			selected.put(identifier, false);
		}

		refreshSelections();

		convertBitmask();
	}

	public void setTableContent(Collection<CSVData> csvData) {
		selected.clear();

		// insert
		for(CSVData data : csvData) {
			selected.put(data, false);
		}

		tvTable.getItems().clear();
		tvTable.getItems().addAll(csvData);
	}

	// {31 Jul 2019 03:57} Heitx - TODO: Return a list of bitmasks not used.
	private void convertBitmask() {
		long l = Long.valueOf(tfBitmask.getText());

		List<CSVData> items = new ArrayList<>(tvTable.getItems());
		Collections.reverse(items);

		for(CSVData item : items) {
			if(item.getBitmask() <= l) {
				selected.put(item, true);
				l -= item.getBitmask();
			}

			if(l == 0) break;
		}
	}
	
	private long calculateBitmask() {
		long l = 0;

		for(Map.Entry<CSVData, Boolean> entry : selected.entrySet()) {
			if(entry.getValue()) {
				l += entry.getKey().getBitmask();
			}
		}
		
		return l;
	}

	public void hideIdColumn() {
		tcId.setVisible(false);
	}

	public void hideBitmaskColumns() {
		tcBitmask.setVisible(false);
		tcSelected.setVisible(false);
	}

	public void hideBitmaskElements() {
		hboxBitmask.setVisible(false);
		hboxBitmask.setManaged(false);
		labelStatus.setVisible(false);
		labelStatus.setManaged(false);
	}

	public void hideDescriptionColumn() {
		tcDescription.setVisible(false);
	}

	private TableRow<CSVData> handleRowMouseClick(TableView<CSVData> tv) {
		TableRow<CSVData> row = new TableRow<>();

		if(tcSelected.isVisible()) {
			row.setOnMouseClicked(event -> {
				if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					CSVData item = row.getTableView().getSelectionModel().getSelectedItem();
					selected.put(item, !selected.get(item));

					refreshSelections();

					tfBitmask.setText(String.valueOf(calculateBitmask()));
				}
			});
		}

		return row;
	}

	private void refreshSelections() {
		tcSelected.setVisible(false);
		tcSelected.setVisible(true);
	}
}

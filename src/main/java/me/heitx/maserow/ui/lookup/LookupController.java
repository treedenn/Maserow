package me.heitx.maserow.ui.lookup;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import me.heitx.maserow.io.Identifier;

import java.net.URL;
import java.util.*;

public abstract class LookupController<T> implements Initializable {
	@FXML protected VBox vbRoot;
	@FXML protected Label labelTitle;
	@FXML protected Button btnEscape;
	@FXML protected TextField tfFilter;
	@FXML protected Button btnFilter;
	@FXML protected TableView<LookupData> tvTable;
	@FXML protected TableColumn<LookupData, Integer> tcEntry;
	@FXML protected TableColumn<LookupData, Long> tcValue;
	@FXML protected TableColumn<LookupData, String> tcName;
	@FXML protected CheckBox cbEntryColumn;
	@FXML protected CheckBox cbValueColumn;

	protected List<Identifier> identifiers;
	protected ObservableList<LookupData> lookupData;
	protected Pane parent;
	protected Callback<T, Void> onSuccess;
	protected Callback<Void, Void> onClose;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		tvTable.setRowFactory(this::onTableRowMouseClick);
		tvTable.getColumns().remove(tcEntry);
		tvTable.getColumns().remove(tcValue);

		tcEntry.setCellValueFactory(new PropertyValueFactory<>("entry"));
		tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

		cbEntryColumn.selectedProperty().addListener(this::onCheckboxEntryChangeListener);
		cbValueColumn.selectedProperty().addListener(this::onCheckboxValueChangeListener);

		btnEscape.setOnAction(this::onButtonEscapeAction);
		btnFilter.setOnAction(this::onButtonFilterAction);
	}

	protected void setParent(Pane parent) {
		this.parent = parent;
	}

	protected void clean() {
		identifiers = null;
		lookupData = null;
	}

	protected void close() {
		clean();

		if(vbRoot.getScene() != parent.getScene()) {
			Stage stage = (Stage) vbRoot.getScene().getWindow();
			stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			stage.close();
		} else {
			parent.getChildren().remove(vbRoot);
		}
	}

	public void setIdentifiers(List<Identifier> identifierList) {
		tvTable.getItems().clear();

		lookupData = FXCollections.observableArrayList();
		for(Identifier identifier : identifierList) {
			LookupData td = new LookupData(identifier);
			lookupData.add(td);
		}

		tvTable.setItems(lookupData);
	}

	protected abstract TableRow<LookupData> onTableRowMouseClick(TableView<LookupData> table);

	private void onButtonEscapeAction(ActionEvent event) {
		clean();
		close();
	}

	private void onButtonFilterAction(ActionEvent event) {
		tvTable.getItems().clear();

		String filter = tfFilter.getText();
		if(filter.isEmpty()) {
			tvTable.setItems(lookupData);
		} else {
			for(LookupData td : lookupData) {
				if(td.getName().contains(filter)) {
					tvTable.getItems().add(td);
				}
			}
		}
	}

	private void onCheckboxEntryChangeListener(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
		if(t1) {
			tvTable.getColumns().add(0, tcEntry);
		} else {
			tvTable.getColumns().remove(tcEntry);
		}
	}

	private void onCheckboxValueChangeListener(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
		ObservableList<TableColumn<LookupData, ?>> cols = tvTable.getColumns();

		if(t1) {
			int i = cols.indexOf(tcEntry);
			cols.add(i == -1 ? 0 : ++i, tcValue);
		} else {
			cols.remove(tcValue);
		}
	}
}
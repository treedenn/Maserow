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
import javafx.stage.WindowEvent;
import me.heitx.maserow.io.Identifier;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

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

	// Same as the sidebarContainer in LookupManager
	protected Pane parent;

	protected boolean returnEntry;
	// Returns a List of Identifiers and provides a string (name)
	protected Function<String, List<Identifier>> filterFunction;

	// Used for the identifier lookup and filter
	protected List<Identifier> identifiers;
	protected ObservableList<LookupData> lookupData;

	protected Consumer<T> onSuccess;

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

	public void setReturnEntry(boolean returnEntry) {
		this.returnEntry = returnEntry;
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

	private void onButtonFilterAction(ActionEvent event) {
		tvTable.getItems().clear();

		if(filterFunction != null) {
			setIdentifiers(filterFunction.apply(tfFilter.getText()));
		} else {
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

	private void onButtonEscapeAction(ActionEvent event) {
		clean();
		close();
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
}
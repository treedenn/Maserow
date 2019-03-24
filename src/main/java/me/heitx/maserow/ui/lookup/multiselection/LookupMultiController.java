package me.heitx.maserow.ui.lookup.multiselection;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import me.heitx.maserow.ui.lookup.LookupController;
import me.heitx.maserow.ui.lookup.LookupData;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LookupMultiController extends LookupController<List<Long>> {
	@FXML private TableColumn<LookupData, Boolean> tcSelected;
	@FXML private Button btnSelect;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);

		tcSelected.setCellValueFactory(new PropertyValueFactory<>("selected"));
		tcSelected.setCellFactory(tc -> new CheckBoxTableCell<>());

		btnSelect.setOnAction(this::onButtonSelectAction);
	}

	public void select(List<Integer> indices) {
		ObservableList<LookupData> items = tvTable.getItems();
		if(items.size() >= indices.size()) {
			for(Integer index : indices) {
				items.get(index).selectedProperty().set(true);
			}
		}
	}

	private void onButtonSelectAction(ActionEvent event) {
		if(onSuccess != null) {
			List<Long> selected = new ArrayList<>();
			for(LookupData item : tvTable.getItems()) {
				if(item.isSelected()) selected.add(returnEntry ? item.getEntry() : item.getValue());
			}

			onSuccess.accept(selected);
			clean();
			close();
		}
	}

	@Override
	protected TableRow<LookupData> onTableRowMouseClick(TableView<LookupData> tableDataTableView) {
		TableRow<LookupData> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				LookupData td = row.getTableView().getSelectionModel().getSelectedItem();
				td.selectedProperty().set(!td.selectedProperty().get());
			}
		});
		return row;
	}
}
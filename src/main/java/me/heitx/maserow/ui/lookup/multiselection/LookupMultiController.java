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
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.ui.lookup.LookupController;
import me.heitx.maserow.ui.lookup.LookupData;

import java.net.URL;
import java.util.*;

public class LookupMultiController extends LookupController<Long> {
	@FXML private TableColumn<LookupData, Boolean> tcSelected;
	@FXML private Button btnSelect;

	private CalculateValueMethod method;
	private Long methodValue;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		super.initialize(url, resourceBundle);

		tcSelected.setCellValueFactory(new PropertyValueFactory<>("selected"));
		tcSelected.setCellFactory(tc -> new CheckBoxTableCell<>());

		btnSelect.setOnAction(this::onButtonSelectAction);
	}

	public void setMethod(CalculateValueMethod method, Long methodValue) {
		this.method = method;
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
			onSuccess.call(calculateValue());
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

	private Long calculateValue() {
		Long value = 0L;

		List<Identifier> selected = new ArrayList<>();
		for(LookupData item : tvTable.getItems()) {
			if(item.isSelected()) selected.add(new Identifier(item.getEntry(), item.getValue(), item.getName()));
		}

		switch(method) {
			case IDENTIFIERS_ONLY:
				value = Identifier.calculateValue(selected);
				break;
			case IDENTIFIERS_PLUS_MATCH:
				value = Identifier.calculateValue(selected, identifiers, methodValue);
				break;
		}

		return value;
	}

	public enum CalculateValueMethod {
		IDENTIFIERS_ONLY,
		IDENTIFIERS_PLUS_MATCH,
	}
}
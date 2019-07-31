package me.heitx.maserow_new.common.view.tableview_textarea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import me.heitx.maserow_new.common.io.Identifier;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TableViewTextAreaController implements Initializable {
	@FXML private StackPane spRoot;
	@FXML private TableView<Identifier> tvTable;

	@FXML private TableColumn<Identifier, Integer> tcId;
	@FXML private TableColumn<Identifier, Long> tcValue;
	@FXML private TableColumn<Identifier, String> tcName;

	@FXML private TextArea taDescription;

	// null is a single description, true is id description, false is value description
	private Boolean columnDescription;
	private Map<Long, String> columnDescriptions;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnDescription = null;
		columnDescriptions = new HashMap<>();

		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

		tvTable.setRowFactory(param -> {
			TableRow<Identifier> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					Identifier item = param.getSelectionModel().getSelectedItem();
					if(item != null && columnDescription != null) {
						Long l = columnDescription ? item.getId() : item.getValue();
						String s = columnDescriptions.get(l);
						taDescription.setText(s);
					}
				}
			});

			return row;
		});
	}

	public void setTableContent(Collection<Identifier> identifiers) {
		tvTable.getItems().clear();
		tvTable.getItems().addAll(identifiers);
	}

	public void setDescription(String ... strings) {
		for(String string : strings) {
			taDescription.appendText(string);
			taDescription.appendText(System.lineSeparator());
		}
	}

	public void setDescription(Long i, String s) {
		columnDescriptions.put(i, s);
	}

	public void setColumnDescription(Boolean columnDescription) {
		this.columnDescription = columnDescription;
	}

	public void hideColumnId() {
		tcId.setVisible(false);
	}

	public void hideColumnValue() {
		tcValue.setVisible(false);
	}

	public void hideDescription() {
		spRoot.getChildren().clear();
		spRoot.getChildren().add(tvTable);

		taDescription.setVisible(false);
		taDescription.setManaged(false);
	}
}
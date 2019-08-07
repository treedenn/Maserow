package me.heitx.maserow.views.tableview_dual;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import me.heitx.maserow.reader.CSVData;

import java.net.URL;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TableviewDualController implements Initializable {
	@FXML private Label labelFirst;
	@FXML private TableView<CSVData> tvFirst;
	@FXML private TableColumn<CSVData, Integer> tcFirstId;
	@FXML private TableColumn<CSVData, Long> tcFirstBitmask;
	@FXML private TableColumn<CSVData, String> tcFirstName;
	@FXML private TableColumn<CSVData, String> tcFirstDescription;

	@FXML private Label labelSecond;
	@FXML private TableView<CSVData> tvSecond;
	@FXML private TableColumn<CSVData, Integer> tcSecondId;
	@FXML private TableColumn<CSVData, Long> tcSecondBitmask;
	@FXML private TableColumn<CSVData, String> tcSecondName;
	@FXML private TableColumn<CSVData, String> tcSecondDescription;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindTableColumnsToCSVData(tcFirstId, tcFirstBitmask, tcFirstName, tcFirstDescription);
		bindTableColumnsToCSVData(tcSecondId, tcSecondBitmask, tcSecondName, tcSecondDescription);
	}

	public static void bindTableColumnsToCSVData(TableColumn<CSVData, Integer> tcId, TableColumn<CSVData, Long> tcBitmask,
	                                             TableColumn<CSVData, String> tcName, TableColumn<CSVData, String> tcDescription) {
		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcBitmask.setCellValueFactory(new PropertyValueFactory<>("bitmask"));
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
	}

	public Label getLabelFirst() {
		return labelFirst;
	}

	public TableView<CSVData> getTableViewFirst() {
		return tvFirst;
	}

	public TableColumn<CSVData, Integer> getFirstId() {
		return tcFirstId;
	}

	public TableColumn<CSVData, Long> getFirstBitmask() {
		return tcFirstBitmask;
	}

	public TableColumn<CSVData, String> getFirstName() {
		return tcFirstName;
	}

	public TableColumn<CSVData, String> getFirstDescription() {
		return tcFirstDescription;
	}

	public Label getLabelSecond() {
		return labelSecond;
	}

	public TableView<CSVData> getTableViewSecond() {
		return tvSecond;
	}

	public TableColumn<CSVData, Integer> getSecondId() {
		return tcSecondId;
	}

	public TableColumn<CSVData, Long> getSecondBitmask() {
		return tcSecondBitmask;
	}

	public TableColumn<CSVData, String> getSecondName() {
		return tcSecondName;
	}

	public TableColumn<CSVData, String> getSecondDescription() {
		return tcSecondDescription;
	}

	public void setFirstTableViewRowConsumer(Consumer<MouseEvent> consumer) {
		setRowFactory(tvFirst, consumer);
	}

	public void setSecondTableViewRowConsumer(Consumer<MouseEvent> consumer) {
		setRowFactory(tvSecond, consumer);
	}

	private void setRowFactory(TableView<CSVData> tv, Consumer<MouseEvent> consumer) {
		tv.setRowFactory(param -> {
			TableRow<CSVData> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if(consumer != null) consumer.accept(event);
			});

			return row;
		});
	}
}
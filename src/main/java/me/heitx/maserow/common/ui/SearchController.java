package me.heitx.maserow.common.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import javafx.util.converter.LongStringConverter;
import me.heitx.maserow.common.Updateable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public abstract class SearchController<T> implements Initializable, Updateable {
	@FXML protected TextField tfEntry;
	@FXML protected TableView<T> tvSearch;
	@FXML protected TableColumn<T, Long> tcEntry;
	@FXML protected Button btnSearch;

	private Consumer<T> doubleClickRowCallback;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		update();

		tfEntry.setTextFormatter(new TextFormatter<>(new LongStringConverter(), 0L));
		btnSearch.setOnAction(this::onButtonSearchAction);
		tvSearch.setRowFactory(this::onTableViewRowSelection);
	}

	@Override
	public abstract void update();

	public SearchController<T> setDoubleClickRowCallback(Consumer<T> callback) {
		this.doubleClickRowCallback = callback;
		return this;
	}

	protected abstract void onButtonSearchAction(ActionEvent event);

	protected void sortByEntry() {
		tvSearch.getSortOrder().add(tcEntry);
		tcEntry.setSortType(TableColumn.SortType.DESCENDING);
	}

	private TableRow<T> onTableViewRowSelection(TableView<T> itemTableView) {
		TableRow<T> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				T selectedItem = tvSearch.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					// {08 Apr 2019 00:08} Heitx - TODO: Use same reference or get a new one? Perhaps a setting?
					// Uses reference for now.
					if(doubleClickRowCallback != null) doubleClickRowCallback.accept(selectedItem);
				}
			}
		});
		return row;
	}
}
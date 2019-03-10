package me.heitx.maserow.ui.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.ui.Updateable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class SearchController<T> implements Initializable, Updateable {
	@FXML protected TextField tfEntry;
	@FXML protected TableView<T> tvSearch;
	@FXML protected TableColumn<T, Integer> tcEntry;
	@FXML protected Button btnSearch;

	protected Callback<T, Void> doubleClickRowCallback;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		update();

		btnSearch.setOnAction(this::onButtonSearchAction);
		tvSearch.setRowFactory(this::onTableViewRowSelection);
	}

	@Override
	public void update() {
		btnSearch.setDisable(!Database.isLoggedIn());
	}

	public SearchController<T> setDoubleClickRowCallback(Callback<T, Void> doubleClickRowCallback) {
		this.doubleClickRowCallback = doubleClickRowCallback;
		return this;
	}

	protected abstract void onButtonSearchAction(ActionEvent event);

	private TableRow<T> onTableViewRowSelection(TableView<T> itemTableView) {
		TableRow<T> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				T selectedItem = tvSearch.getSelectionModel().getSelectedItem();
				if(selectedItem != null) {
					// TODO: Use same reference or get a new one? Perhaps a setting?
					// Uses reference for now.
					if(doubleClickRowCallback != null) doubleClickRowCallback.call(selectedItem);
				}
			}
		});
		return row;
	}


}
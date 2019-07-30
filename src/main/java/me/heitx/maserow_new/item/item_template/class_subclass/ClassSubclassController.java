package me.heitx.maserow_new.item.item_template.class_subclass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import me.heitx.maserow.common.io.ICSV;
import me.heitx.maserow_new.common.io.DelimiterReader;
import me.heitx.maserow_new.common.io.Identifier;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ClassSubclassController implements Initializable {
	@FXML private TableView<Identifier> tvClass;

	@FXML private TableColumn<Identifier, Integer> tcClassId;
	@FXML private TableColumn<Identifier, Long> tcClassName;

	@FXML private TableView<Identifier> tvSubclass;

	@FXML private TableColumn<Identifier, Integer> tcSubclassId;
	@FXML private TableColumn<Identifier, Long> tcSubclassName;

	private Map<Integer, Set<Identifier>> subclasses;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		subclasses = new HashMap<>();

		for(int i = 0; i < 17; i++) {
			subclasses.put(i, new HashSet<>());
		}

		tvClass.setRowFactory(this::onRowFactoryClasses);

		tcClassId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcClassName.setCellValueFactory(new PropertyValueFactory<>("name"));

		tcSubclassId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcSubclassName.setCellValueFactory(new PropertyValueFactory<>("name"));

		List<Identifier> classes = DelimiterReader.readColumns(ICSV.CSV_FOLDER_NAME + File.separator + "item" + File.separator + "item_classes",
				true, false);

		List<Identifier> subclasses = DelimiterReader.readColumns(ICSV.CSV_FOLDER_NAME + File.separator + "item" + File.separator + "item_subclasses",
				true, true);

		orderSubclasses(subclasses);

		tvClass.getItems().addAll(classes);
		tvSubclass.getItems().addAll(this.subclasses.get(0));
	}

	private void orderSubclasses(List<Identifier> subclasses) {
		for(Identifier subclass : subclasses) {
			Integer i = subclass.getId();
			this.subclasses.get(i).add(subclass);
		}
	}

	private TableRow<Identifier> onRowFactoryClasses(TableView<Identifier> param) {
		TableRow<Identifier> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
				Identifier item = param.getSelectionModel().getSelectedItem();
				if(item != null) {
					tvSubclass.getItems().clear();
					tvSubclass.getItems().addAll(this.subclasses.get(item.getId()));
				}
			}
		});

		return row;
	}
}
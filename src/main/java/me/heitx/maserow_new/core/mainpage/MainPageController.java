package me.heitx.maserow_new.core.mainpage;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import me.heitx.maserow_new.core.tableview_multi.TableViewMultiController;
import me.heitx.maserow_new.reader.CSVFile;
import me.heitx.maserow_new.reader.CSVReader;

import java.io.*;
import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {
	@FXML private BorderPane bpRoot;
	@FXML private ScrollPane spCenter;
	@FXML private ListView<CSVFile> lvOpenedFiles;

	private Map<CSVFile, Node> views;
	private CSVFile openedFile;

	// TEST PURPOSES
	private final File startDirectory = new File("C:\\Users\\Denni\\IdeaProjects\\Maserow\\csv\\item_template");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		views = new HashMap<>();

		spCenter.setOnMouseClicked(event -> {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				addFile();
			}
		});

		lvOpenedFiles.setOnMouseClicked(event -> {
			if(lvOpenedFiles.getItems().size() == 0 && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				addFile();
			}
		});

		lvOpenedFiles.setCellFactory(param -> {
			ListCell<CSVFile> cell = new ListCell<CSVFile>() {
				@Override
				protected void updateItem(CSVFile item, boolean empty) {
					super.updateItem(item, empty);

					if(item != null && !empty) {
						setText(item.getFile().getPath());
					} else {
						setText("");
					}
				}
			};

			cell.setOnMouseClicked(event -> onListCellMouseClick(event, cell));

			return cell;
		});
	}

	private void onListCellMouseClick(MouseEvent event, Cell<CSVFile> cell) {
		// {06 Aug 2019 14:04} Heitx - TODO: Place within the if/else to avoid double reference set.
		CSVFile selectedItem = lvOpenedFiles.getSelectionModel().getSelectedItem();

		if(!cell.isEmpty()) {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				refreshFile(selectedItem);
			} else if(event.getButton() == MouseButton.SECONDARY) {
				List<MenuItem> menuItems = new ArrayList<>();

				MenuItem reload = new MenuItem("Reload");
				MenuItem remove = new MenuItem("Remove");
				MenuItem _new = new MenuItem("New");

				if(!cell.isEmpty()) {
					menuItems.add(reload);
					menuItems.add(remove);
				}

				menuItems.add(_new);

				reload.setOnAction(action -> {
					refreshFile(selectedItem);
				});

				remove.setOnAction(action -> {
					ObservableList<CSVFile> items = lvOpenedFiles.getItems();

					int index = items.indexOf(selectedItem);
					items.remove(selectedItem);

					if(items.size() > 0) {
						index = index < items.size() ? index : index - 1;
						lvOpenedFiles.getSelectionModel().select(index);
						refreshFile(items.get(index));
					} else {
						spCenter.setContent(null);
					}

					views.remove(selectedItem);
				});

				_new.setOnAction(action -> {
					addFile();
				});

				ContextMenu cm = new ContextMenu(menuItems.toArray(new MenuItem[0]));
				cell.setContextMenu(cm);
			}
		} else if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
			addFile();
		}
	}

	private void addFile() {
		FileChooser fc = new FileChooser();

		fc.setInitialDirectory(startDirectory);
		List<File> files = fc.showOpenMultipleDialog(spCenter.getScene().getWindow());

		if(files != null && files.size() > 0) {
			for(File file : files) {
				CSVFile csv = CSVReader.read(file);
				lvOpenedFiles.getItems().add(csv);
			}

			loadFile(lvOpenedFiles.getItems().get(lvOpenedFiles.getItems().size() - 1));
		}
	}

	private void refreshFile(CSVFile file) {
		openedFile = file;
		spCenter.setContent(views.get(file));

		System.out.println("Refrehsing " + file.getFile().getPath());
	}

	private void loadFile(CSVFile file) {
		Node node = null;

		try {
			FXMLLoader loader = new FXMLLoader(TableViewMultiController.class.getResource("tableview_multi.fxml"));
			node = loader.load();
			TableViewMultiController controller = loader.getController();

			controller.setTableContent(file.getData());

			if(file.containsId() && file.containsBitmask()) {
				// .. no idea..
			} else if(file.containsId()) {
				controller.hideBitmaskColumns();
				controller.hideBitmaskElements();
			} else if(file.containsBitmask()) {
				controller.hideIdColumn();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		if(node != null) {
			openedFile = file;
			views.put(file, node);
			spCenter.setContent(node);
		}
	}
}

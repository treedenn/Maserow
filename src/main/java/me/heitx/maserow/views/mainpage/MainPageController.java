package me.heitx.maserow.views.mainpage;

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
import me.heitx.maserow.reader.CSVFile;
import me.heitx.maserow.reader.CSVReader;
import me.heitx.maserow.views.tableview_multi.TableViewMultiController;

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
	private final File startDirectory = new File("C:\\Users\\Denni\\IdeaProjects\\Maserow\\csv\\world\\item_template");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		views = new HashMap<>();

		spCenter.setOnMouseClicked(this::onScrollPaneMouseClick);
		lvOpenedFiles.setOnMouseClicked(this::onListViewMouseClick);
		lvOpenedFiles.setCellFactory(this::setListViewCelLFactory);
	}

	private void onScrollPaneMouseClick(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
			// double-clicking on the scroll pane
			newFilesToListView();
		}
	}

	private void onListViewMouseClick(MouseEvent event) {
		if(lvOpenedFiles.getItems().size() == 0 && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
			// double-clicking the listview when it is empty
			newFilesToListView();
		}
	}

	private ListCell<CSVFile> setListViewCelLFactory(ListView<CSVFile> lv) {
		ListCell<CSVFile> cell = new ListCell<CSVFile>() {
			@Override
			protected void updateItem(CSVFile item, boolean empty) {
				super.updateItem(item, empty);

				if(item != null && !empty) {
					setText(item.getFile().getParentFile().getName() + " > " + item.getFile().getName());
				} else {
					setText("");
				}
			}
		};

		cell.setOnMouseClicked(event -> onListViewCellMouseClick(event, cell));

		return cell;
	}

	private void onListViewCellMouseClick(MouseEvent event, Cell<CSVFile> cell) {
		// {06 Aug 2019 14:04} Heitx - TODO: Place within the if/else to avoid double reference set.
		CSVFile selectedItem = lvOpenedFiles.getSelectionModel().getSelectedItem();

		if(!cell.isEmpty()) {
			if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				// double-clicking on a not empty cell
				reloadLayout(selectedItem);
			} else if(event.getButton() == MouseButton.SECONDARY) {
				// right-clicking on a not empty cell
				MenuItem reload = new MenuItem("Reload");
				MenuItem remove = new MenuItem("Remove");
				MenuItem newFile = new MenuItem("New File");

				List<MenuItem> menuItems = new ArrayList<>();
				if(!cell.isEmpty()) {
					menuItems.add(reload);
					menuItems.add(remove);
				}

				menuItems.add(newFile);

				reload.setOnAction(action -> {
					reloadLayout(selectedItem);
				});

				remove.setOnAction(action -> {
					removeFromListView(selectedItem);
				});

				newFile.setOnAction(action -> {
					newFilesToListView();
				});

				ContextMenu cm = new ContextMenu(menuItems.toArray(new MenuItem[0]));
				cell.setContextMenu(cm);
			}
		} else if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
			// double-clicking on an empty cell
			newFilesToListView();
		}
	}

	private void reloadLayout(CSVFile file) {
		if(openedFile != file) {
			openedFile = file;
			spCenter.setContent(views.get(file));
		}
	}

	private void removeFromListView(CSVFile csvFile) {
		ObservableList<CSVFile> items = lvOpenedFiles.getItems();

		int index = items.indexOf(csvFile);
		items.remove(csvFile);

		// reload content by loading at same index, loading the previous added layout, or
		// setting the content to null (no files left)
		if(items.size() > 0) {
			index = index < items.size() ? index : index - 1;
			lvOpenedFiles.getSelectionModel().select(index);

			reloadLayout(items.get(index));
		} else {
			// empty listview
			spCenter.setContent(null);
		}

		views.remove(csvFile);
	}

	private void newFilesToListView() {
		List<File> files = getFilesFromDialog();
		List<CSVFile> csvFiles = readFiles(files);

		for(CSVFile csvFile : csvFiles) {
			Node node = loadLayout(csvFile);
			views.put(csvFile, node);
			lvOpenedFiles.getItems().add(csvFile);
		}

		ObservableList<CSVFile> items = lvOpenedFiles.getItems();
		Node node = views.get(items.get(items.size() - 1)); // last added node

		spCenter.setContent(node);
	}

	private List<File> getFilesFromDialog() {
		FileChooser fc = new FileChooser();

		fc.setInitialDirectory(startDirectory);
		return fc.showOpenMultipleDialog(spCenter.getScene().getWindow());
	}

	private List<CSVFile> readFiles(List<File> files) {
		List<CSVFile> csvFiles = new ArrayList<>();

		if(files != null && files.size() > 0) {
			for(File file : files) {
				CSVFile csvFile = CSVReader.read(file);
				csvFiles.add(csvFile);
			}
		}

		return csvFiles;
	}

	private Node loadLayout(CSVFile csvFile) {
		Node node = null;

		try {
			if(csvFile.getFile().getName().equalsIgnoreCase("item_classes")) {

			} else {
				node = useStandardLayout(csvFile);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		return node;
	}

	private Node useStandardLayout(CSVFile file) throws IOException {
		FXMLLoader loader = new FXMLLoader(TableViewMultiController.class.getResource("tableview_multi.fxml"));
		Node node = loader.load();
		TableViewMultiController controller = loader.getController();

		controller.setTableContent(file.getData());

		if(!file.containsId()) {
			controller.hideIdColumn();
		}

		if(!file.containsBitmask()) {
			controller.hideBitmaskColumns();
			controller.hideBitmaskElements();
		}

		return node;
	}
}

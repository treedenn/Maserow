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
import javafx.util.Pair;
import me.heitx.maserow.reader.CSVData;
import me.heitx.maserow.reader.CSVFile;
import me.heitx.maserow.reader.CSVReader;
import me.heitx.maserow.views.standard_layout.StandardController;
import me.heitx.maserow.views.tableview_dual.TableviewDualController;

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
		List<CSVFile> csvFiles = CSVReader.read(files);

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

	private Node loadLayout(CSVFile csvFile) {
		Node node = null;

		try {
			if(csvFile.getFile().getName().equalsIgnoreCase("item_classes.csv")) {
				File subclassFile = new File(csvFile.getFile().getParent(), "item_subclasses.csv");

				if(subclassFile.exists() && subclassFile.isFile()) {
					CSVFile csvSubclasFile = CSVReader.read(subclassFile);
					node = useTableViewDualForClassSubclass(csvFile, csvSubclasFile);
				} else {
					node = useStandardLayout(csvFile);
				}

			} else {
				node = useStandardLayout(csvFile);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		return node;
	}

	private Node useStandardLayout(CSVFile csvFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(StandardController.class.getResource("standard_layout.fxml"));
		Node node = loader.load();
		StandardController controller = loader.getController();

		controller.setTableContent(csvFile.getData());

		if(!csvFile.containsId()) {
			controller.hideIdColumn();
		}

		if(!csvFile.containsBitmask()) {
			controller.hideBitmaskColumns();
			controller.hideBitmaskElements();
		}

		return node;
	}

	private Pair<Node, TableviewDualController> useTableViewDual() throws IOException {
		FXMLLoader loader = new FXMLLoader(TableviewDualController.class.getResource("tableview_dual.fxml"));
		Node node = loader.load();
		TableviewDualController controller = loader.getController();

		return new Pair<>(node, controller);
	}

	private Node useTableViewDualForClassSubclass(CSVFile classFile, CSVFile subclassFile) throws IOException {
		Pair<Node, TableviewDualController> pair = useTableViewDual();

		// creates 16 new lists since 16 subclasses exist
		Map<Integer, List<CSVData>> subclasses = new HashMap<>();
		for(int i = 0; i < 17; i++) {
			subclasses.put(i, new ArrayList<>());
		}

		// sorts the subclasses by their ids
		for(CSVData data : subclassFile.getData()) {
			subclasses.get(data.getId()).add(data);
		}

		TableviewDualController controller = pair.getValue();

		// when clicking on a row, update the second tableview
		// by adding the subclasses based on the class' id
		controller.setFirstTableViewRowConsumer(mouseEvent -> {
			Integer id = controller.getTableViewFirst().getSelectionModel().getSelectedItem().getId();

			ObservableList<CSVData> items = controller.getTableViewSecond().getItems();
			items.clear();
			items.addAll(subclasses.get(id));
		});


		controller.getLabelFirst().setText("Class");
		controller.getLabelSecond().setText("Subclass");

		controller.getSecondBitmask().setText("id");

		controller.getFirstBitmask().setVisible(false);
		controller.getSecondId().setVisible(false);

		controller.getTableViewFirst().getItems().addAll(classFile.getData());
		controller.getTableViewSecond().getItems().addAll(subclasses.get(0));

		return pair.getKey();
	}
}

package me.heitx.maserow.common.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Pair;
import javafx.util.StringConverter;
import me.heitx.maserow.common.io.Identifier;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class LayoutUtil {
	public static void show(Node node) {
		toggle(node, true);
	}

	public static void hide(Node node) {
		toggle(node, false);
	}

	public static void toggle(Node node, boolean show) {
		node.setVisible(show);
		node.setManaged(show);
	}

	public static void selectFirstOnComboboxes(ComboBox<Identifier> ... comboBoxes) {
		for(ComboBox<Identifier> comboBox : comboBoxes) {
			comboBox.getSelectionModel().selectFirst();
		}
	}

	public static void showOnlyNameOnCombobox(ComboBox<Identifier> ... comboBoxes) {
		for(ComboBox<Identifier> comboBox : comboBoxes) {
			comboBox.setCellFactory(LayoutUtil::call);
			comboBox.setButtonCell(call(null));
		}
	}

	public static void showOnlyNameOnCombobox(CheckComboBox<Identifier> ... comboBoxes) {
		for(CheckComboBox<Identifier> comboBox : comboBoxes) {
			comboBox.setConverter(new StringConverter<Identifier>() {
				@Override
				public String toString(Identifier identifier) {
					return identifier.getName();
				}

				@Override
				public Identifier fromString(String s) {
					return null;
				}
			});
		}
	}

	private static ListCell<Identifier> call(ListView<Identifier> param) {
		return new ListCell<Identifier>() {
			@Override
			protected void updateItem(Identifier item, boolean empty) {
				super.updateItem(item, empty);
				if(item == null || empty) {
					setGraphic(null);
				} else {
					setText(item.getName());
				}
			}
		};
	}

	public static void onAltPrimaryButton(TextField tf, Runnable callback) {
		tf.setOnMouseClicked(event -> {
			if(event.isAltDown() && event.getButton() == MouseButton.PRIMARY) {
				callback.run();
				event.consume();
			}
		});
	}

	public static Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content, ButtonType ... buttons) {
		Alert alert = new Alert(type, content, buttons);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.initModality(Modality.APPLICATION_MODAL);

		return alert.showAndWait();
	}

	public static void showSaveSqlWindow(Window owner, String title, String initialFileName, String query) {
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		fc.setInitialFileName(initialFileName);
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Structured Query Language", "*.sql"));

		File selectedFile = fc.showSaveDialog(owner);
		if(selectedFile != null) {
			if(!selectedFile.getName().endsWith(".sql")) {
				selectedFile = new File(selectedFile + ".sql");
			}

			try {
				FileOutputStream fos = new FileOutputStream(selectedFile);
				fos.write(query.getBytes());
				fos.flush();
				fos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> Pair<Parent, T> loadLayout(Class<T> clazz, String fxmlFile) {
		Pair<Parent, T> pair = null;

		FXMLLoader fxml = new FXMLLoader(clazz.getResource(fxmlFile));
		try {
			pair = new Pair<>(fxml.load(), fxml.getController());
		} catch(IOException e) {
			e.printStackTrace();
		}

		return pair;
	}
}

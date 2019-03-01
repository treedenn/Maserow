package me.heitx.maserow.ui;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.StringConverter;
import me.heitx.maserow.io.Identifier;
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

	public static void showOnlyNameOnCombobox(ComboBox<Identifier> comboBox) {
		comboBox.setCellFactory(LayoutUtil::call);
		comboBox.setButtonCell(call(null));
	}

	public static void showOnlyNameOnCombobox(CheckComboBox<Identifier> comboBox) {
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
}

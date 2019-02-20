package me.heitx.maserow.ui;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;
import me.heitx.maserow.io.Identifier;
import org.controlsfx.control.CheckComboBox;

public class NodeUtil {
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
		comboBox.setCellFactory(NodeUtil::call);
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
}

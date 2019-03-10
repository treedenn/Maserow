package me.heitx.maserow.ui.item.template.build;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.io.ItemCSV;
import me.heitx.maserow.ui.LayoutUtil;

import java.util.LinkedList;
import java.util.List;

public class StatsContainer extends VBox {

	private final List<Identifier> statTypes;
	private LinkedList<HBoxStat> linkedChildren;
	private final int MAX_STATS = 10;

	private int[] types;
	private int[] values;

	public StatsContainer(int[] types, int[] values) {
		this.statTypes = DelimiterReader.readColumns(ItemCSV.ITEM_STAT_TYPES);
		this.types = types;
		this.values = values;
		this.linkedChildren = new LinkedList<>();

		for(int i = 0; i < types.length; i++) {
			if(values[i] != 0) {
				addHBoxStat(types[i], values[i]);
			}
		}

		if(linkedChildren.size() == 0) {
			addHBoxStat(0, 0);
		}
	}

	public int[] getTypes() {
		for(int i = 0; i < MAX_STATS; i++) {
			if(i < linkedChildren.size()) {
				types[i] = linkedChildren.get(i).getStatType();
			} else {
				types[i] = 0;
			}
		}

		return types;
	}

	public int[] getValues() {
		for(int i = 0; i < MAX_STATS; i++) {
			if(i < linkedChildren.size()) {
				values[i] = linkedChildren.get(i).getStatValue();
			} else {
				values[i] = 0;
			}
		}

		return values;
	}

	public int getStatsCount() {
		int count = 0;
		for(HBoxStat linkedChild : linkedChildren) {
			if(linkedChild.getStatValue() != 0) {
				count++;
			}
		}

		return count;
	}

	private void addHBoxStat(int type, int value) {
		if(linkedChildren.size() < MAX_STATS) {
			// if a child already exists, then hide the add icon on the previous hbox stat before adding
			if(linkedChildren.size() > 0) {
				linkedChildren.getLast().hideAddIcon();
			}
			// before adding, if there only exist one element, show the remove icon again
			if(linkedChildren.size() == 1) {
				linkedChildren.getLast().showRemoveIcon();
			}

			HBoxStat hbox = new HBoxStat(type, value);
			this.getChildren().add(hbox);
			this.linkedChildren.add(hbox);

			// after adding hide the add icon on the new element if max children is reached
			if(linkedChildren.size() == MAX_STATS) {
				this.linkedChildren.getLast().hideAddIcon();
			}
		}
	}

	private void removeHBoxStat(HBoxStat hbox) {
		this.getChildren().remove(hbox);
		this.linkedChildren.remove(hbox);

		// hide the remove icon when the container reaches the minimum elements of one
		if(linkedChildren.size() == 1) {
			this.linkedChildren.getLast().hideRemoveIcon();
		}
		// show the add icon on the last element when removing
		if(linkedChildren.size() > 0) {
			linkedChildren.getLast().showAddIcon();
		}
	}

	private class HBoxStat extends HBox {
		FontAwesomeIconView iconRemove;
		FontAwesomeIconView iconAdd;
		ComboBox<Identifier> cbStatType;
		TextField tfStatValue;

		public HBoxStat(int type, int value) {
			super(5);

			iconRemove = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
			iconAdd = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
			cbStatType = new ComboBox<>(FXCollections.observableArrayList(statTypes));
			tfStatValue = new TextField();

			iconRemove.setGlyphSize(20);
			iconAdd.setGlyphSize(20);

			iconRemove.setOnMouseClicked(this::onMouseClickIconRemove);
			iconAdd.setOnMouseClicked(this::onMouseClickIconAdd);

			LayoutUtil.showOnlyNameOnCombobox(cbStatType);

			cbStatType.getSelectionModel().select(Identifier.findsById(statTypes, type));
			tfStatValue.setText(String.valueOf(value));

			super.getChildren().addAll(new StackPane(iconRemove), new StackPane(iconAdd), cbStatType, tfStatValue);
		}

		public int getStatType() {
			return cbStatType.getSelectionModel().getSelectedItem().getId();
		}

		public int getStatValue() {
			return Integer.parseInt(tfStatValue.getText());
		}

		private void showAddIcon() {
			iconAdd.setVisible(true);
		}

		private void showRemoveIcon() {
			iconRemove.setVisible(true);
		}

		private void hideAddIcon() {
			iconAdd.setVisible(false);
		}

		private void hideRemoveIcon() {
			iconRemove.setVisible(false);
		}

		private void onMouseClickIconAdd(MouseEvent event) {
			StatsContainer.this.addHBoxStat(0, 0);
		}

		private void onMouseClickIconRemove(MouseEvent event) {
			FontAwesomeIconView iconRemove = (FontAwesomeIconView) event.getSource();
			HBoxStat hbox = (HBoxStat) iconRemove.getParent().getParent(); // first parent is the stackpane

			StatsContainer.this.removeHBoxStat(hbox);
		}
	}
}
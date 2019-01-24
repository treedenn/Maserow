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
import me.heitx.maserow.io.CSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.ItemStat;
import me.heitx.maserow.ui.NodeUtil;

import java.util.LinkedList;
import java.util.List;

public class StatsContainer extends VBox {

	private final List<Identifier> statTypes;
	private LinkedList<HBoxStat> linkedChildren;
	private final int MAX_STATS = 10;

	private List<ItemStat> stats;

	public StatsContainer(List<ItemStat> stats) {
		this.statTypes = DelimiterReader.readColumns(CSV.ITEM_STAT_TYPES);
		this.stats = stats;
		this.linkedChildren = new LinkedList<>();

		for(ItemStat stat : stats) {
			if(stat.getValue() != 0) {
				addHBoxStat(stat);
			}
		}

		if(linkedChildren.size() == 0) {
			addHBoxStat(null);
		}
	}

	public List<ItemStat> getItemStats() {
		for(int i = 0; i < linkedChildren.size(); i++) {
			HBoxStat child = linkedChildren.get(i);
			stats.set(i, new ItemStat(child.getStatType(), child.getStatValue()));
		}

		for(int i = linkedChildren.size(); i < stats.size(); i++) {
			stats.set(i, new ItemStat());
		}

		return stats;
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

	private void addHBoxStat(ItemStat stat) {
		if(linkedChildren.size() < MAX_STATS) {
			// if a child already exists, then hide the add icon on the previous hbox stat before adding
			if(linkedChildren.size() > 0) {
				linkedChildren.getLast().hideAddIcon();
			}
			// before adding, if there only exist one element, show the remove icon again
			if(linkedChildren.size() == 1) {
				linkedChildren.getLast().showRemoveIcon();
			}

			HBoxStat hbox = new HBoxStat(stat);
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

		public HBoxStat(ItemStat stat) {
			super(5);

			iconRemove = new FontAwesomeIconView(FontAwesomeIcon.MINUS);
			iconAdd = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
			cbStatType = new ComboBox<>(FXCollections.observableArrayList(statTypes));
			tfStatValue = new TextField();

			iconRemove.setGlyphSize(20);
			iconAdd.setGlyphSize(20);

			iconRemove.setOnMouseClicked(this::onMouseClickIconRemove);
			iconAdd.setOnMouseClicked(this::onMouseClickIconAdd);

			NodeUtil.showOnlyNameOnCombobox(cbStatType);

			if(stat != null) {
				cbStatType.getSelectionModel().select(Identifier.findById(statTypes, stat.getType()));
				tfStatValue.setText(String.valueOf(stat.getValue()));
			} else {
				cbStatType.getSelectionModel().select(0);
				tfStatValue.setText("0");
			}

			super.getChildren().addAll(new StackPane(iconRemove), new StackPane(iconAdd), cbStatType, tfStatValue);
		}

		public int getStatType() {
			return  cbStatType.getSelectionModel().getSelectedItem().getId();
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
			StatsContainer.this.addHBoxStat(null);
		}

		private void onMouseClickIconRemove(MouseEvent event) {
			FontAwesomeIconView iconRemove = (FontAwesomeIconView) event.getSource();
			HBoxStat hbox = (HBoxStat) iconRemove.getParent().getParent(); // first parent is the stackpane

			StatsContainer.this.removeHBoxStat(hbox);
		}
	}
}
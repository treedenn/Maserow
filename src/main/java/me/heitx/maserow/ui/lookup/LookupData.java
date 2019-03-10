package me.heitx.maserow.ui.lookup;

import javafx.beans.property.*;
import me.heitx.maserow.io.Identifier;

public class LookupData {
	private IntegerProperty entry;
	private LongProperty value;
	private StringProperty name;
	private BooleanProperty selected;

	public LookupData(Identifier identifier) {
		this(identifier, false);
	}

	public LookupData(Identifier identifier, boolean isSelected) {
		entry = new SimpleIntegerProperty(identifier.getId());
		value = new SimpleLongProperty(identifier.getValue());
		name = new SimpleStringProperty(identifier.getName());
		selected = new SimpleBooleanProperty(isSelected);
	}

	public int getEntry() {
		return entry.get();
	}

	public IntegerProperty entryProperty() {
		return entry;
	}

	public long getValue() {
		return value.get();
	}

	public LongProperty valueProperty() {
		return value;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public boolean isSelected() {
		return selected.get();
	}

	public BooleanProperty selectedProperty() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = false;

		if(o instanceof LookupData) {
			LookupData other = (LookupData) o;
			equals = entry.equals(other.entry) && value.equals(other.value) && name.equals(other.name) && selected.equals(other.selected);
		}

		return equals;
	}
}

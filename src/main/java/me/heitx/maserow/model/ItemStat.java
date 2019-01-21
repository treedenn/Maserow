package me.heitx.maserow.model;

public class ItemStat {
	private int type;
	private int value;

	public ItemStat() {
		this(0, 0);
	}

	public ItemStat(int type, int value) {
		this.type = type;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public ItemStat setType(int type) {
		this.type = type;
		return this;
	}

	public int getValue() {
		return value;
	}

	public ItemStat setValue(int value) {
		this.value = value;
		return this;
	}

	public void reset() {
		this.type = 0;
		this.value = 0;
	}

	public String getDisplayText(String typeName) {
		String s;

		if(type <= 7) {
			s = "+" + value + " " + typeName;
		} else {
			String word = type < 37 ? "Improves " : "Increases ";
			s = "Equip: " + word + typeName.toLowerCase() + " by " + value + ".";
		}

		return s;
	}

	@Override
	public String toString() {
		return "Type: " + type + " : Value: " + value;
	}
}
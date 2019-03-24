package me.heitx.maserow.ui.character.maildelivery;

public class SelectorData {
	private long value;
	private String text;
	private long quantity;

	public SelectorData(long value, String text, long quantity) {
		this.value = value;
		this.text = text;
		this.quantity = quantity;
	}

	public SelectorData(long value, String text) {
		this(value, text, 1);
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}
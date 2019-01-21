package me.heitx.maserow.model;

public class Resistance {
	public static final String[] NAMES = {
			"Holy", "Shadow", "Fire", "Frost", "Nature", "Arcane"
	};

	private int holy;
	private int shadow;
	private int fire;
	private int frost;
	private int nature;
	private int arcane;

	public Resistance() {
		this(0, 0, 0, 0, 0, 0);
	}

	public Resistance(int holy, int shadow, int fire, int frost, int nature, int arcane) {
		this.holy = holy;
		this.shadow = shadow;
		this.fire = fire;
		this.frost = frost;
		this.nature = nature;
		this.arcane = arcane;
	}

	public int getHoly() {
		return holy;
	}

	public Resistance setHoly(int holy) {
		this.holy = holy;
		return this;
	}

	public int getShadow() {
		return shadow;
	}

	public Resistance setShadow(int shadow) {
		this.shadow = shadow;
		return this;
	}

	public int getFire() {
		return fire;
	}

	public Resistance setFire(int fire) {
		this.fire = fire;
		return this;
	}

	public int getFrost() {
		return frost;
	}

	public Resistance setFrost(int frost) {
		this.frost = frost;
		return this;
	}

	public int getNature() {
		return nature;
	}

	public Resistance setNature(int nature) {
		this.nature = nature;
		return this;
	}

	public int getArcane() {
		return arcane;
	}

	public Resistance setArcane(int arcane) {
		this.arcane = arcane;
		return this;
	}

	public boolean allZero() {
		return holy == 0 && shadow == 0 && fire == 0 &&
				frost == 0 && nature == 0 && arcane == 0;
	}

	public boolean allSame() {
		return holy == shadow && shadow == fire && fire == frost &&
				frost == nature && nature == arcane;
	}
}
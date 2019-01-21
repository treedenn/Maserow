package me.heitx.maserow.model;

public class ItemSpell {
	private int id;
	private int trigger;
	private int charges;
	private float ppmRate;
	private int cooldown;
	private int category;
	private int categoryCooldown;

	public ItemSpell() {
		this(0, 0, 0, 0, -1, 0, -1);
	}

	public ItemSpell(int id, int trigger, int charges, float ppmRate, int cooldown, int category, int categoryCooldown) {
		this.id = id;
		this.trigger = trigger;
		this.charges = charges;
		this.ppmRate = ppmRate;
		this.cooldown = cooldown;
		this.category = category;
		this.categoryCooldown = categoryCooldown;
	}

	public int getId() {
		return id;
	}

	public ItemSpell setId(int id) {
		this.id = id;
		return this;
	}

	public int getTrigger() {
		return trigger;
	}

	public ItemSpell setTrigger(int trigger) {
		this.trigger = trigger;
		return this;
	}

	public int getCharges() {
		return charges;
	}

	public ItemSpell setCharges(int charges) {
		this.charges = charges;
		return this;
	}

	public float getPpmRate() {
		return ppmRate;
	}

	public ItemSpell setPpmRate(float ppmRate) {
		this.ppmRate = ppmRate;
		return this;
	}

	public int getCooldown() {
		return cooldown;
	}

	public ItemSpell setCooldown(int cooldown) {
		this.cooldown = cooldown;
		return this;
	}

	public int getCategory() {
		return category;
	}

	public ItemSpell setCategory(int category) {
		this.category = category;
		return this;
	}

	public int getCategoryCooldown() {
		return categoryCooldown;
	}

	public ItemSpell setCategoryCooldown(int categoryCooldown) {
		this.categoryCooldown = categoryCooldown;
		return this;
	}

	public void reset() {
		id = 0;
		trigger = 0;
		charges = 0;
		ppmRate = 0;
		cooldown = -1;
		category = 0;
		categoryCooldown = -1;
	}
}
package me.heitx.maserow.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Loot {
	@Column("Entry") private int entry;
	@Column("Item") private int item;
	@Column("Reference") private int reference;
	@Column("Chance") private float chance;
	@Column("QuestRequired") private int questRequired;
	@Column("LootMode") private int lootMode;
	@Column("GroupId") private int groupId;
	@Column("MinCount") private int minCount;
	@Column("MaxCount") private int maxCount;
	@Column("Comment") private String comment;

	public Loot() {
		reset();
	}

	public void reset() {
		this.entry = 0;
		this.item = 0;
		this.reference = 0;
		this.chance = 100;
		this.questRequired = 0;
		this.lootMode = 1;
		this.groupId = 0;
		this.minCount = 1;
		this.maxCount = 1;
		this.comment = null;
	}
}
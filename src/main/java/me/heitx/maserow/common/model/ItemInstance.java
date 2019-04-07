package me.heitx.maserow.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemInstance {
	@Column("guid") private long guid;
	@Column("itemEntry") private int itemEntry;
	@Column("owner_guid") private long ownerGuid;
	@Column("creatorGuid") private long creatorGuid;
	@Column("giftCreatorGuid") private long giftCreatorGuid;
	@Column("count") private long count;
	@Column("duration") private int duration;
	@Column("charges") private String charges;
	@Column("flags") private int flags;
	@Column("enchantments") private String enchantments;
	@Column("randomPropertyId") private int randomPropertyId;
	@Column("durability") private int durability;
	@Column("playedTime") private long playedTime;
	@Column("text") private String text;

	public ItemInstance() {
		guid = 0;
		itemEntry = 0;
		ownerGuid = 0;
		creatorGuid = 0;
		giftCreatorGuid = 0;
		count = 1;
		duration = 0;
		charges = "0 0 0 0 0";
		flags = 0;
		enchantments = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
		randomPropertyId = 0;
		durability = 0;
		playedTime = 0;
		text = "";
	}
}
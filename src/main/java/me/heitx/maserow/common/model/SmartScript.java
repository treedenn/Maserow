package me.heitx.maserow.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SmartScript {
	@Column("entryorguid") private int entryOrGuid;
	@Column("source_type") private int sourceType;
	@Column("id") private int id;
	@Column("link") private int link;
	@Column("event_type") private int eventType;
	@Column("event_phase_mask") private int eventPhaseMask;
	@Column("event_chance") private int eventChance;
	@Column("event_flags") private int eventFlags;
	@Column("event_param1") private long eventParam1;
	@Column("event_param2") private long eventParam2;
	@Column("event_param3") private long eventParam3;
	@Column("event_param4") private long eventParam4;
	@Column("event_param5") private long eventParam5;
	@Column("action_type") private int actionType;
	@Column("action_param1") private long actionParam1;
	@Column("action_param2") private long actionParam2;
	@Column("action_param3") private long actionParam3;
	@Column("action_param4") private long actionParam4;
	@Column("action_param5") private long actionParam5;
	@Column("action_param6") private long actionParam6;
	@Column("target_type") private int targetType;
	@Column("target_param1") private long targetParam1;
	@Column("target_param2") private long targetParam2;
	@Column("target_param3") private long targetParam3;
	@Column("target_param4") private long targetParam4;
	@Column("target_x") private float targetX;
	@Column("target_y") private float targetY;
	@Column("target_z") private float targetZ;
	@Column("target_o") private float targetO;
	@Column("comment") private String comment;
}
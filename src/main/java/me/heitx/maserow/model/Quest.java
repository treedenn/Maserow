package me.heitx.maserow.model;

import java.util.List;

public class Quest {
	private int id;
	private int questType;
	private int questLevel;
	private int minLevel;
	private int questSortID;
	private int questInfoID;
	private int suggestedGroupNum;
	private int requiredFactionId1;
	private int requiredFactionId2;
	private int requiredFactionValue1;
	private int requiredFactionValue2;
	private int rewardNextQuest;
	private int rewardXPDifficulty;
	private int rewardMoney;
	private long rewardBonusMoney;
	private int rewardDisplaySpell;
	private int rewardSpell;
	private int rewardHonor;
	private float rewardKillHonor;
	private int startItem;
	private long flags;
	private int requiredPlayerKills;
	private List<Stack> rewardItem; // 4 of these
	private List<Stack> itemDrop; // 4 of these
	private List<Stack> rewardChoiceItem; // 6 of these
	private int pOIContinent;
	private float pOIx;
	private float pOIy;
	private int pOIPriority;
	private int rewardTitle;
	private int rewardTalents;
	private int rewardArenaPoints;
	private int rewardFactionID1;
	private int rewardFactionValue1;
	private int rewardFactionOverride1;
	private int rewardFactionID2;
	private int rewardFactionValue2;
	private int rewardFactionOverride2;
	private int rewardFactionID3;
	private int rewardFactionValue3;
	private int rewardFactionOverride3;
	private int rewardFactionID4;
	private int rewardFactionValue4;
	private int rewardFactionOverride4;
	private int rewardFactionID5;
	private int rewardFactionValue5;
	private int rewardFactionOverride5;
	private long timeAllowed;
	private int allowableRaces;
	private String logTitle;
	private String logDescription;
	private String questDescription;
	private String areaDescription;
	private String questCompletionLog;
	private List<Stack> requiredNpcOrGo; // 4 of these
	private List<Stack> requiredItem; // 6 of these
	private String objectiveText1;
	private String objectiveText2;
	private String objectiveText3;
	private String objectiveText4;
}
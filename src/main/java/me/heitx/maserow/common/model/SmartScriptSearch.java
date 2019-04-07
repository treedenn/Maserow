package me.heitx.maserow.common.model;

import lombok.Getter;

@Getter
public class SmartScriptSearch extends SimpleSearchModel {
	private int sourceType;

	public SmartScriptSearch(long entry, String name, int sourceType) {
		super(entry, name);
		this.sourceType = sourceType;
	}
}
package me.heitx.maserow.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SimpleSearchModel {
	private long entry;
	private String name;

	public SimpleSearchModel(long entry, String name) {
		this.entry = entry;
		this.name = name;
	}
}
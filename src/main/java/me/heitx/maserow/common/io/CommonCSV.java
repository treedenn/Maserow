package me.heitx.maserow.common.io;

import java.io.File;

public enum CommonCSV implements ICSV {
	EXPANSIONS("expansions", true, false),
	RACES("races", true, true),
	CLASSES("classes", true, true);

	private String fileName;
	private boolean hasId;
	private boolean hasValue;

	CommonCSV(String fileName, boolean hasId, boolean hasValue) {
		this.fileName = fileName;
		this.hasId = hasId;
		this.hasValue = hasValue;
	}

	@Override
	public boolean hasId() {
		return hasId;
	}

	@Override
	public boolean hasValue() {
		return hasValue;
	}

	@Override
	public String getFile() {
		return ICSV.CSV_FOLDER_NAME + File.separator + "common" + File.separator + fileName;
	}
}
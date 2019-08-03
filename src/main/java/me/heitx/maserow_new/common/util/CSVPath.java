package me.heitx.maserow_new.common.util;

import java.io.File;

public interface CSVPath {
	String CSV_FOLDER_NAME = "csv";

	String COMMON = getStartCSVFolder("common");
	String ITEM = getStartCSVFolder("item");
	String CREATURE = getStartCSVFolder("creature");
	String QUEST = getStartCSVFolder("quest");

	static String getStartCSVFolder(String startFolder) {
		return CSV_FOLDER_NAME + File.separator + startFolder + File.separator;
	}
}

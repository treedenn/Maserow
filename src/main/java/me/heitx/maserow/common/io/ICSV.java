package me.heitx.maserow.common.io;

public interface ICSV {
	String CSV_FOLDER_NAME = "csv";

	boolean hasId();
	boolean hasValue();
	String getFile();
}
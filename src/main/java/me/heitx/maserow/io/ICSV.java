package me.heitx.maserow.io;

public interface ICSV {
	String CSV_FOLDER_NAME = "csv";

	boolean hasId();
	boolean hasValue();
	String getFile();
	String getFileName();
}
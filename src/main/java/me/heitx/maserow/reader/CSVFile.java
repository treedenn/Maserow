package me.heitx.maserow.reader;

import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
public class CSVFile {
	private File file;
	private List<CSVData> data;

	public CSVFile(File file, List<CSVData> data) {
		this.file = file;
		this.data = data;
	}

	public boolean containsId() {
		return data.get(0).getId() != null;
	}

	public boolean containsBitmask() {
		return data.get(0).getBitmask() != null;
	}

	public boolean containsName() {
		return data.get(0).getName() != null;
	}

	public boolean containsDescription() {
		return data.get(0).getDescription() != null;
	}
}

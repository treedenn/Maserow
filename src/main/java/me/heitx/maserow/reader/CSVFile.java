package me.heitx.maserow.reader;

import lombok.Getter;

import java.nio.file.Path;
import java.util.List;

@Getter
public class CSVFile {
	private Path path;
	private List<CSVData> data;

	public CSVFile(Path path, List<CSVData> data) {
		this.path = path;
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

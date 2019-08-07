package me.heitx.maserow.reader;

import lombok.Getter;

@Getter
public class CSVData {
	private Integer id;
	private Long bitmask; // use for bitmasks (this can also be an id e.g. subclasses in item_template)
	private String name;
	private String description; // comment/description

	public CSVData(Integer id, Long bitmask, String name, String description) {
		this.id = id;
		this.bitmask = bitmask;
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return String.join(";", String.valueOf(id), String.valueOf(bitmask), name, description);
	}
}

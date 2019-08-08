package me.heitx.maserow.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// {06 Aug 2019 17:16} Heitx - TODO: Asynchronous the read file process
public class CSVReader {
//	private static final List<String> availableHeaders = Arrays.asList("ID", "BITMASK", "NAME", "DESCRIPTION");

	public static List<CSVFile> read(List<Path> paths) {
		List<CSVFile> csvFiles = new ArrayList<>();

		for(Path path : paths) {
			CSVFile read = read(path);
			csvFiles.add(read);
		}

		return csvFiles;
	}

	public static CSVFile read(Path path) {
		if(path == null || Files.notExists(path)) return null;

		List<CSVData> data = new ArrayList<>();

		try {
			BufferedReader reader = Files.newBufferedReader(path);

			if(reader.ready()) {
				// loops until it reaches the end or a not empty line e.g. headers
				String headerLine;
				while((headerLine = reader.readLine()) != null && headerLine.isEmpty()) {
				}

				List<String> headers = Arrays.asList(headerLine.split(";"));
				Map<String, Integer> map = new HashMap<>();
				for(int i = 0; i < headers.size(); i++) {
					map.put(headers.get(i), i);
				}

				String line;
				while((line = reader.readLine()) != null) {
					String[] lineSplit = line.split(";", -1);

					Integer id = map.containsKey("ID") ? Integer.valueOf(lineSplit[map.get("ID")]) : null;
					Long bitmask = map.containsKey("BITMASK") ? Long.valueOf(lineSplit[map.get("BITMASK")]) : null;
					String name = map.containsKey("NAME") ? lineSplit[map.get("NAME")] : null;
					String description = map.containsKey("DESCRIPTION") ? lineSplit[map.get("DESCRIPTION")] : null;

					data.add(new CSVData(id, bitmask, name, description));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		return new CSVFile(path, data);
	}
}

package me.heitx.maserow.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// {06 Aug 2019 17:16} Heitx - TODO: Asynchronous the read file process
public class CSVReader {
//	private static final List<String> availableHeaders = Arrays.asList("ID", "BITMASK", "NAME", "DESCRIPTION");

	public static CSVFile read(File file) {
		List<CSVData> data = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

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

		return new CSVFile(file, data);
	}
}

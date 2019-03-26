package me.heitx.maserow.database.implementation;

import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.repository.ISmartScriptRepository;
import me.heitx.maserow.model.Creature;
import me.heitx.maserow.model.SimpleSearchModel;
import me.heitx.maserow.model.SmartScript;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.utils.Queries;
import me.heitx.maserow.utils.QueryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SmartScriptRepository extends MySqlDatabase implements ISmartScriptRepository {
	public SmartScriptRepository(IClient client) {
		super(client);
	}

	@Override
	public List<SimpleSearchModel> search(long sourceType, long entryOrGuid, String name, boolean withExisting) {
		AtomicReference<List<SimpleSearchModel>> atomic = new AtomicReference<>(new ArrayList<>(0));

		execute(Database.Selection.WORLD, conn -> {
			List<SimpleSearchModel> searchModels = new ArrayList<>();
			List<String> blocks;

			if(sourceType == 0) {
				if(withExisting) {
					blocks = getExistingQuery("creature_template", "creature", entryOrGuid, name);
				} else {
					blocks = getTemplateQuery("creature_template", entryOrGuid, name);
				}
			} else { // sourceType == 1
				if(withExisting) {
					blocks = getExistingQuery("gameobject_template", "gameobject", entryOrGuid, name);
				} else {
					blocks = getTemplateQuery("gameobject_template", entryOrGuid, name);
				}
			}

			blocks.add(QueryUtil.limit(100));

			PreparedStatement ps = conn.prepareStatement(QueryUtil.buildNewLineFormat(false, blocks));
			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();

			if(withExisting) {
				// Entry is template, which is why only one should be added.
				Set<Long> addedEntries = new HashSet<>();
				while(rs.next()) {
					long entry = rs.getLong("entry");

					searchModels.add(new SimpleSearchModel(rs.getLong("guid"), rs.getString("name")));
					if(!addedEntries.contains(entry)) {
						searchModels.add(new SimpleSearchModel(rs.getLong("entry"), rs.getString("name")));
						addedEntries.add(entry);
					}
				}
			} else {
				while(rs.next()) {
					searchModels.add(new SimpleSearchModel(rs.getLong("entry"), rs.getString("name")));
				}
			}


			atomic.set(searchModels);
			return new Statement[] { ps };
		});

		return atomic.get();
	}

	@Override
	public List<SmartScript> getAll(long entryOrGuid, int sourceType, int id) {
		return null;
	}

	@Override
	public boolean replace(List<SmartScript> smartScripts) {
		AtomicBoolean atomic = new AtomicBoolean(false);

		if(smartScripts.size() > 0) {
			SmartScript smartScript = smartScripts.get(0);
			final int entryOrGuid = smartScript.getEntryOrGuid();
			final int sourceType = smartScript.getSourceType();

			execute(Database.Selection.WORLD, conn -> {
				Statement ps = conn.createStatement();

				List<String> orBlocks = new ArrayList<>();
				orBlocks.add("entryorguid = " + entryOrGuid);
				orBlocks.add("source_type = " + sourceType);

				System.out.println(QueryUtil.delete("smart_scripts", QueryUtil.and(orBlocks)));
				for(SmartScript script : smartScripts) {
					System.out.println(QueryUtil.insert("smart_scripts", ConverterUtil.toAttributes(script)));
				}

				return new Statement[] { ps };
			});
		}

		return atomic.get();
	}

	private List<String> getExistingQuery(String table, String joinTable, long entryOrGuid, String name) {
//		-- with
//		SELECT -ABS(guid) guid, entry, name FROM creature_template
//		JOIN creature ON id = entry WHERE entry = 37965 OR guid = 37965 OR name LIKE '%Argent Commander%';

		List<String> columns = new ArrayList<>();
		columns.add("-ABS(guid) guid"); columns.add("entry"); columns.add("name");

		List<String> blocks = new ArrayList<>(QueryUtil.select(columns, table, null));

		List<String> whereBlocks = new ArrayList<>();
		if(entryOrGuid != 0) whereBlocks.add("guid = " + entryOrGuid);
		if(entryOrGuid != 0) whereBlocks.add("entry = " + entryOrGuid);
		if(name != null && !name.isEmpty()) whereBlocks.add("name LIKE '%" + name + "%'");

		blocks.addAll(QueryUtil.join(joinTable, "id = entry", QueryUtil.or(whereBlocks)));

		return blocks;
	}

	private List<String> getTemplateQuery(String table, long entry, String name) {
//		-- without
//		SELECT entry, name FROM creature_template WHERE entry = 37965 OR name LIKE '%Argent Commander%';

		List<String> columns = new ArrayList<>();
		columns.add("entry"); columns.add("name");

		List<String> orBlocks = new ArrayList<>(2);
		if(entry != 0) orBlocks.add("entry = " + entry);
		if(name != null && !name.isEmpty()) orBlocks.add("name LIKE '%" + name + "%'");

		return QueryUtil.select(columns, table, QueryUtil.or(orBlocks));
	}
}
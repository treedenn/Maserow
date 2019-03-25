package me.heitx.maserow.database.implementation;

import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.repository.ISmartScriptRepository;
import me.heitx.maserow.model.Creature;
import me.heitx.maserow.model.SimpleSearchModel;
import me.heitx.maserow.model.SmartScript;
import me.heitx.maserow.utils.Queries;
import me.heitx.maserow.utils.QueryUtil;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SmartScriptRepository extends MySqlDatabase implements ISmartScriptRepository {


	public SmartScriptRepository(IClient client) {
		super(client);
	}

	@Override
	public List<SimpleSearchModel> search(long entryOrGuid, long sourceType, String name, boolean withExisting, boolean withTemplate) {
		AtomicReference<List<SimpleSearchModel>> atomic = new AtomicReference<>(new ArrayList<>());

		if(withExisting || withTemplate) {
			execute(Database.Selection.WORLD, conn -> {
				List<Statement> statements = new ArrayList<>();
				List<SimpleSearchModel> searchModels = new ArrayList<>();
				String query, existingQuery, templateQuery;

				if(sourceType == 0) {
					/*  SELECT * FROM smart_scripts
						WHERE entryorguid IN(SELECT -ABS(guid) FROM gameobject WHERE guid = @entryOrGuid OR id = @entryOrGuid) OR
                        entryorguid IN(SELECT entry FROM gameobject_template WHERE entry = @entryOrGuid OR name LIKE CONCAT('%', @name, '%')); */

					List<String> whereBlocks = new ArrayList<>();
					if(withExisting) whereBlocks.add(getExistingQuery("creature", entryOrGuid));
					if(withTemplate) whereBlocks.add(getTemplateQuery("creature_template", entryOrGuid, name));

					QueryUtil.select(null, "smart_scripts", )
				} else if(sourceType == 1) {
					query = "";
				} else {
					query = null;
				}

				if(query != null) {
					PreparedStatement ps = conn.prepareStatement(query);



					statements.add(ps);
				}

				atomic.set(searchModels);
				return statements.toArray(new Statement[0]);
			});
		}

		return atomic.get();
	}

	@Override
	public List<SmartScript> getAll(long entryOrGuid, int sourceType, int id) {
		return null;
	}

	@Override
	public boolean replace(List<SmartScript> smartScripts) {
		return false;
	}

	private String getExistingQuery(String table, long guid) {
		List<String> orBlocks = new ArrayList<>(2);
		orBlocks.add("guid = " + guid);
		orBlocks.add("id = " + guid);

		List<String> query = QueryUtil.select(Collections.singleton("-ABS(guid)"), table, QueryUtil.or(orBlocks));
		return QueryUtil.buildNewLineFormat(false, query);
	}

	private String getTemplateQuery(String table, long entry, String name) {
		List<String> orBlocks = new ArrayList<>(2);
		orBlocks.add("entry = " + entry);
		orBlocks.add("id LIKE '%" + name + "%'");

		List<String> query = QueryUtil.select(Collections.singleton("entry"), table, QueryUtil.or(orBlocks));
		return QueryUtil.buildNewLineFormat(false, query);
	}
}
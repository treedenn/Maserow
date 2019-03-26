package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.SimpleSearchModel;
import me.heitx.maserow.model.SmartScript;

import java.util.List;

// Uncommon type of Repository than the rest
public interface ISmartScriptRepository {
	List<SimpleSearchModel> search(long sourceType, long entryOrGuid, String name, boolean withExisting);
	List<SmartScript> getAll(long entryOrGuid, int sourceType, int id);

	/**
	 * Replaces the smart scripts.
	 * @param smartScripts any list, not null
	 * @return boolean whether it was success
	 */
	boolean replace(List<SmartScript> smartScripts);
}
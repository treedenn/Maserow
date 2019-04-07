package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.SimpleSearchModel;
import me.heitx.maserow.common.model.SmartScript;

import java.util.List;

// Uncommon type of Repository than the rest
public interface ISmartScriptRepository extends Repository {
	List<SimpleSearchModel> search(long sourceType, long entryOrGuid, String name, boolean withExisting);
	List<SmartScript> getAll(long entryOrGuid, int sourceType);

	/**
	 * Replaces the smart scripts.
	 * @param smartScripts any list, not null
	 * @return boolean whether it was success
	 */
	boolean replace(List<SmartScript> smartScripts);
}
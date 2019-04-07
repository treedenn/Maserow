package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.Quest;

import java.util.List;

public interface IQuestRepository extends ExtendedRepository<Quest> {
	List<Quest> search(int entry, String logTitle, int limit);
}
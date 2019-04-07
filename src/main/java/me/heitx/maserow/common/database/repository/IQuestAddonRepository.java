package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.QuestAddon;

import java.util.List;

public interface IQuestAddonRepository extends BaseRepository<QuestAddon> {
	List<QuestAddon> search(long entry, int limit);
}
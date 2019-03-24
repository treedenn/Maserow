package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.QuestAddon;

import java.util.List;

public interface IQuestAddonRepository extends BaseRepository<QuestAddon> {
	List<QuestAddon> search(long entry, int limit);
}
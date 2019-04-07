package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.Character;

import java.util.List;

public interface ICharacterRepository extends BaseRepository<Character> {
	List<Character> search(int entry, String name, int[] raceIds, int[] classIds, int limit);
}

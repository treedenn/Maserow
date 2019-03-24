package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.Character;

import java.util.List;

public interface ICharacterRepository extends BaseRepository<Character> {
	List<Character> search(int entry, String name, int[] raceIds, int[] classIds, int limit);
}

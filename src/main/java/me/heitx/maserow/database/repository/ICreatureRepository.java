package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.Creature;

import java.util.List;
import java.util.Map;

public interface ICreatureRepository extends ExtendedRepository<Creature> {
	List<Creature> search(int entry, String name, int limit);
}
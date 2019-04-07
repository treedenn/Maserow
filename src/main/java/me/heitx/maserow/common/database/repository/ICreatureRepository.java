package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.Creature;

import java.util.List;

public interface ICreatureRepository extends ExtendedRepository<Creature> {
	List<Creature> search(int entry, String name, int limit);
}
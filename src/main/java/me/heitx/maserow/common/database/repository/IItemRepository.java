package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.Item;

import java.util.List;

public interface IItemRepository extends ExtendedRepository<Item> {
	List<Item> search(int entry, String name, int limit);
}
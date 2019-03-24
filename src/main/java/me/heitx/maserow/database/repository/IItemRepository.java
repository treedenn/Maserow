package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.Item;

import java.util.List;

public interface IItemRepository extends ExtendedRepository<Item> {
	List<Item> search(int entry, String name, int limit);
}
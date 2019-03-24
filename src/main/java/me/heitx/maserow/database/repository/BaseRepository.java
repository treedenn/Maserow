package me.heitx.maserow.database.repository;

import java.util.List;

public interface BaseRepository<T> {
	boolean insert(T type);

	boolean update(T type);

	boolean delete(int entry);

	T get(int entry);

	List<T> getAll(int limit);

	boolean exists(int entry);
}
package me.heitx.maserow.common.database.repository;

import java.util.List;

public interface BaseRepository<T> extends Repository {
	boolean insert(T type);

	boolean update(T type);

	boolean delete(int entry);

	T get(int entry);

	List<T> getAll(int limit);

	boolean exists(int entry);
}
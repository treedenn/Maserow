package me.heitx.maserow.database.repository;

public interface ExtendedRepository<T> extends BaseRepository<T> {
	long getMaxEntry();
}
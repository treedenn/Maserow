package me.heitx.maserow.common.database.repository;

public interface ExtendedRepository<T> extends BaseRepository<T> {
	long getMaxEntry();
}
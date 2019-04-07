package me.heitx.maserow.common.database.function;

import java.sql.SQLException;

@FunctionalInterface
public interface Function<A, R> {
	R apply(A t) throws SQLException;
}

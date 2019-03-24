package me.heitx.maserow.database.function;

import java.sql.SQLException;

@FunctionalInterface
public interface BiFunction<A, B, R> {
	R apply(A a, B b) throws SQLException;
}

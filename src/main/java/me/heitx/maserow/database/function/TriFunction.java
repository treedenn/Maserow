package me.heitx.maserow.database.function;

import java.sql.SQLException;

// Received from Stackoverflow.
@FunctionalInterface
public interface TriFunction<A, B, C, R> {
	R apply(A a, B b, C c) throws SQLException;
}
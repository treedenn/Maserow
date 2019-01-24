package me.heitx.maserow;

import javafx.application.Application;
import me.heitx.maserow.converter.Converter;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.io.config.Config;
import me.heitx.maserow.ui.App;

public class Main {
	public static void main(String[] args) {
		Config.getInstance().load();
		Converter.select(Converter.Type.TRINITY);
		Database.select(Database.Type.TRINITY);

		Application.launch(App.class, args);
	}
}
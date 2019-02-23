package me.heitx.maserow;

import javafx.application.Application;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.io.config.Config;
import me.heitx.maserow.ui.App;

import java.io.File;

public class Main {
	public static File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

	public static void main(String[] args) {
		Config.getInstance().load();
		Database.select(Database.Type.TRINITY);

		Application.launch(App.class, args);
	}
}
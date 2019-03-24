package me.heitx.maserow;

import javafx.application.Application;
import me.heitx.maserow.io.config.Config;
import me.heitx.maserow.ui.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Main {
	public static File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

	private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) {
		LOGGER.info("--------- New Session ---------");
		Config.getInstance().load();

		LOGGER.info("Initialized config, default database selected and application started.");
		Application.launch(App.class, args);
	}
}
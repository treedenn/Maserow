package me.heitx.maserow;

import javafx.application.Application;
import me.heitx.maserow.common.io.config.Config;
import me.heitx.maserow.core.JavaFX;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@ComponentScan
public class Main {
	public static File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

	private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) {
		LOGGER.info("--------- New Session ---------");
		Config.getInstance().load();

		LOGGER.info("Initialized config and starting application.");

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(SpringConfig.class);

		Application.launch(JavaFX.class, args);
	}
}
package me.heitx.maserow.core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.heitx.maserow.common.io.config.Config;
import me.heitx.maserow.core.mainpage.MainPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaFX extends Application {
	private static final Logger LOGGER = LogManager.getLogger(JavaFX.class.getName());

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(MainPageController.class.getResource("mainpage.fxml"));
		Pane root = loader.load();

		Scene scene = new Scene(root, root.getWidth(), root.getHeight());
		scene.getStylesheets().add(getClass().getClassLoader().getResource("darktheme.css").toExternalForm());

		//stage.setFullScreen(true);
		stage.setResizable(true);
		stage.setTitle("MaseroW v" + getClass().getPackage().getImplementationVersion() + " - Created by Heitx");
		stage.setScene(scene);

		stage.setOnCloseRequest(windowEvent -> {
			Config.getInstance().save();
			LOGGER.info("Config saved!");

			Platform.exit();
			System.exit(0);
		});

		stage.show();
	}
}
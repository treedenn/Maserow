package me.heitx.maserow.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.heitx.maserow.io.config.Config;
import me.heitx.maserow.ui.app.AppController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {
	private static final Logger LOGGER = LogManager.getLogger(App.class.getName());

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(AppController.class.getResource("app.fxml"));
		Pane root = loader.load();

		Scene scene = new Scene(root, root.getWidth(), root.getHeight());
		scene.getStylesheets().add(getClass().getResource("darktheme.css").toExternalForm());

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
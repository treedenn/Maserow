package me.heitx.maserow.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.heitx.maserow.ui.app.AppController;

public class App extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(AppController.class.getResource("app.fxml"));
		Pane root = loader.load();

		stage.setTitle("MaseroW v0.0.1 - Created by Heitx");
		stage.setScene(new Scene(root, root.getWidth(), root.getHeight()));
		stage.show();
	}
}
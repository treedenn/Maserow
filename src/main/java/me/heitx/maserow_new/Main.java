package me.heitx.maserow_new;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import me.heitx.maserow_new.core.mainpage.MainPageController;


public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(MainPageController.class.getResource("main.fxml"));
		Pane root = loader.load();

		Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
//		scene.getStylesheets().add(getClass().getClassLoader().getResource("darktheme.css").toExternalForm());

		//stage.setFullScreen(true);
		stage.setResizable(true);
		stage.setTitle("MaseroW v" + getClass().getPackage().getImplementationVersion());
		stage.setScene(scene);

		stage.show();
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		TilePane flow = new TilePane();
//		flow.setStyle("-fx-border-color: red");
//		addPanes(flow, 4);
//
//		ScrollPane scroll = new ScrollPane(flow);
//		scroll.setStyle("-fx-border-color: green");
//		scroll.setFitToHeight(true);
//		scroll.setFitToWidth(true);
//
//		Scene scene = new Scene(scroll, 450, 450);
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}
//
//	public void addPanes(TilePane root, int panes) {
//		for(int i = 0; i < panes; i++) {
//			StackPane filler = new StackPane();
//			filler.setStyle("-fx-border-color: black");
//			filler.setPrefSize(100, 50);
//			root.getChildren().add(filler);
//		}
//	}
}
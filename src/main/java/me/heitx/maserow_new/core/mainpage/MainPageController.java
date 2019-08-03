package me.heitx.maserow_new.core.mainpage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import me.heitx.maserow_new.common.service.ITablePlugin;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.kordamp.ikonli.material.Material;

import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {
	@FXML private BorderPane bpRoot;
	@FXML private ScrollPane spCenter;
	@FXML private TilePane tpMenu;

	private Stack<Region> breadcrumbs;

	private Map<String, Set<Button>> tables;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		breadcrumbs = new Stack<>();
		tables = new HashMap<>();

		loadPlugins();
	}

	public void addMenu(Button button) {
		addBackButtonHandler(button);
		button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		tpMenu.getChildren().add(button);
	}

	public void addToBreadcrumbs(Region previous) {
		breadcrumbs.add(previous);
	}

	public void setContent(Region region) {
		bpRoot.setCenter(region);
	}

	public void setContent(Region current, Region next) {
		breadcrumbs.add(current);
		bpRoot.setCenter(next);
	}

	private void loadPlugins() {
		ServiceLoader<ITablePlugin> plugins = ServiceLoader.load(ITablePlugin.class);

		for(ITablePlugin plugin : plugins) {
			plugin.onStart(this, tables);
		}
	}

	private void addBackButtonHandler(Button button) {
		button.addEventHandler(ActionEvent.ANY, event -> {
			StackedFontIcon icon = new StackedFontIcon();
			icon.setIconSize(24);
			icon.setIconCodeLiterals(Material.ARROW_BACK.getDescription());

			Button backButton = new Button();
//			backButton.setPrefWidth();
			backButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			backButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			backButton.setGraphic(icon);

			// if breadcrumbs is empty, set default otherwise pop.
			backButton.setOnAction(event1 -> {
				if(breadcrumbs.empty()) {
					bpRoot.setCenter(tpMenu);
					bpRoot.setLeft(null);
				} else {
					Region pop = breadcrumbs.pop();
					bpRoot.setCenter(pop);
				}
			});

			bpRoot.setLeft(backButton);
		});
	}
}

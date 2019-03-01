package me.heitx.maserow.ui.creature.template;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import me.heitx.maserow.ui.Updateable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatureTemplateController implements Initializable, Updateable {
	@FXML TilePane tpLayout;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		tpLayout.widthProperty().addListener((observableValue, number, t1) -> {
			recursiveToFitTilePane(0);
		});
	}

	@Override
	public void update() {

	}

	private void recursiveToFitTilePane(int index) {
		if(index == tpLayout.getChildren().size()) return;

		double width = tpLayout.widthProperty().get() / (tpLayout.getChildren().size() - index) - tpLayout.getHgap();

		if(width >= 300) {
			fitTilePane(width);
		} else {
			recursiveToFitTilePane(++index);
		}
	}

	private void fitTilePane(double width) {
		tpLayout.setPrefTileWidth(width);

		for(Node child : tpLayout.getChildren()) {
			if(child instanceof GridPane) {
				GridPane gp = (GridPane) child;
				gp.setPrefWidth(width);
			}
		}
	}
}
package me.heitx.maserow.common.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class ButtonedTextField extends HBox {
	private TextField textField;
	private Button button;

	public ButtonedTextField() {
		setAlignment(Pos.CENTER);
		textField = new TextField();
		button = new Button();
		button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

		StackedFontIcon icon = new StackedFontIcon();
		icon.setIconCodeLiterals(MaterialDesign.MDI_CHEVRON_DOUBLE_RIGHT.getDescription());
		button.setGraphic(icon);

		getChildren().addAll(textField, button);
	}

	public TextField getTextField() {
		return textField;
	}

	public Button getButton() {
		return button;
	}
}
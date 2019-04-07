package me.heitx.maserow.common.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.StackedFontIcon;

import java.util.List;

public class SideElement extends VBox {
	private Button button;
	private List<SideElement> elements;
	private VBox container;
	private SideElement owner;

	private EventHandler<ActionEvent> handler;

	public SideElement(StackedFontIcon icon, String title) {
		this.button = new Button(title, icon);
		this.elements = FXCollections.observableArrayList();
		this.container = new VBox();

		this.button.setOnAction(this::onButtonAction);
		this.button.setAlignment(Pos.CENTER_LEFT);
		this.button.setMaxWidth(Double.MAX_VALUE);

		this.container.setMaxHeight(Double.MAX_VALUE);
		super.setMaxHeight(Double.MAX_VALUE);
		super.getChildren().add(button);
		super.getChildren().add(container);
	}

	public Button getButton() {
		return button;
	}

	public void setButtonAction(EventHandler<ActionEvent> handler) {
		this.handler = handler;
	}

	public List<SideElement> getElements() {
		return elements;
	}

	public VBox getContainer() {
		return container;
	}

	public SideElement getOwner() {
		return owner;
	}

	private void setOwner(SideElement owner) {
		this.owner = owner;
	}

	public void addElement(SideElement element) {
		element.setOwner(this);
		elements.add(element);
		container.getChildren().add(element);
	}

	private void onButtonAction(ActionEvent event) {
		if(handler != null) {
			handler.handle(event);
		}
	}
}
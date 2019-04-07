package me.heitx.maserow.core.sidebar;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.ui.SideElement;
import me.heitx.maserow.common.services.ISidebar;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.StackedFontIcon;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SidebarController implements Initializable, ISidebar {
	private final PseudoClass SELECTED_PSEUDO;
	private final PseudoClass SHRINKED_PSEUDO;

	@FXML private VBox vboxSidemenu;
	@FXML private Label labelTitle;
	@FXML private VBox vboxTop;
	@FXML private VBox vboxBottom;
	@FXML private Button btnToggle;

	private LinkedList<SideElement> activeElements;

	private boolean isShrinked;
	private StackedFontIcon toggleIcon;

	public SidebarController() {
		SELECTED_PSEUDO = PseudoClass.getPseudoClass("selected");
		SHRINKED_PSEUDO = PseudoClass.getPseudoClass("shrinked");

		isShrinked = false;
	}

	// There is an invisible character on toggle button (to push the graphic to the left).
	// https://stackoverflow.com/questions/17978720/invisible-characters-ascii

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		toggleIcon = new StackedFontIcon();
		toggleIcon.setIconCodeLiterals(FontAwesomeSolid.ARROW_CIRCLE_LEFT.getDescription());

		btnToggle.setGraphic(toggleIcon);
		btnToggle.setOnAction(this::onToggleAction);

		vboxTop.addEventFilter(ActionEvent.ACTION, event -> {
			SideElement selected = (SideElement) ((Button) event.getTarget()).getParent();
			select(selected);
		});
	}

	@Override
	public void addElement(boolean top, SideElement element) {
		if(top) {
			vboxTop.getChildren().add(element);
		} else {
			vboxBottom.getChildren().add(element);
		}
	}

	@Override
	public void setSelected(SideElement element) {
		select(element);
	}

	private void select(SideElement selected) {
		LinkedList<SideElement> selectedElements = (LinkedList<SideElement>) getRoots(selected);

		if(activeElements == null) activeElements = selectedElements;

		// removes all the previous active elements if selected elements are not a match
		for(int i = 0; i < Math.min(selectedElements.size(), activeElements.size()); i++) {
			SideElement selectedElement = selectedElements.get(i);
			SideElement activeElement = activeElements.get(i);

			if(selectedElement != activeElement) {
				// hides all elements from the first element
				// where selected and active are not equal.
				deactivateSelected(i);
				break;
			}
		}

		// toggles the sub elements
		if(selected.getElements().size() > 0) {
			VBox container = selected.getContainer();
			container.setManaged(true);
			container.setVisible(true);
		}

		for(SideElement selectedElement : selectedElements) {
			selectedElement.pseudoClassStateChanged(SELECTED_PSEUDO, true);
		}

		activeElements = selectedElements;
	}

	private void deactivateSelected(int i) {
		for(; i < activeElements.size(); i++) {
			SideElement element = activeElements.get(i);
			element.pseudoClassStateChanged(SELECTED_PSEUDO, false);
			element.getContainer().setManaged(false);
			element.getContainer().setVisible(false);
		}
	}

	/*
			RECURSIVE STUFF FOR TOGGLE ICONS,
			AND HIDE SUBCATEGORIES AND MORE.
	 */

	private void onToggleAction(ActionEvent event) {
		for(Node child : vboxTop.getChildren()) {
			if(child instanceof SideElement) {
				recursiveToggleIcon((SideElement) child);
			}
		}

		for(Node child : vboxBottom.getChildren()) {
			if(child instanceof SideElement) {
				recursiveToggleIcon((SideElement) child);
			}
		}

		isShrinked = !isShrinked;

		LayoutUtil.toggle(labelTitle, !isShrinked);
		vboxSidemenu.pseudoClassStateChanged(SHRINKED_PSEUDO, isShrinked);
		toggleIcon.setIconCodeLiterals(isShrinked ? FontAwesomeSolid.ARROW_CIRCLE_RIGHT.getDescription()
				: FontAwesomeSolid.ARROW_CIRCLE_LEFT.getDescription() );
	}

	private void recursiveToggleIcon(SideElement initElement) {
		for(SideElement element : initElement.getElements()) {
			recursiveToggleIcon(element);
		}

		initElement.getButton().setContentDisplay(isShrinked ? ContentDisplay.LEFT : ContentDisplay.GRAPHIC_ONLY);
	}

	public void hideAllElements() {
		for(Node child : vboxTop.getChildren()) {
			if(child instanceof SideElement) {
				recursiveHide((SideElement) child);
			}
		}
	}

	private void recursiveHide(SideElement element) {
		for(SideElement elementElement : element.getElements()) {
			if(elementElement.getElements().size() > 0) {
				recursiveHide(elementElement);
			}
		}

		element.getContainer().setVisible(false);
		element.getContainer().setManaged(false);
	}

	private List<SideElement> getRoots(SideElement element) {
		LinkedList<SideElement> roots = new LinkedList<>();

		roots.add(element);
		recursiveRoot(element, roots);
		Collections.reverse(roots);

		return roots;
	}

	private void recursiveRoot(SideElement element, List<SideElement> elements) {
		SideElement owner = element.getOwner();

		if(owner != null) {
			elements.add(owner);
			recursiveRoot(owner, elements);
		}
	}
}
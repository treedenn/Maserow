package me.heitx.maserow.ui.sidemenu;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.heitx.maserow.ui.ICallback;
import me.heitx.maserow.ui.NodeUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class SidemenuController implements Initializable {
	private final int MENU_ICON_SIZE = 30;
	private final int SUBMENU_ICON_SIZE = 24;
	private final double EXPAND_WIDTH = 150;
	private final double SHRINK_WIDTH = 45;
	private final PseudoClass SHRINKED_PSEUDO;

	@FXML public VBox vboxSidemenu;
	@FXML private Label labelTitle;
	@FXML private Button btnLogin;
	@FXML private Button btnItem;
	@FXML private Button btnSettings;
	@FXML private Button btnToggle;

	@FXML private VBox vboxItem;
	@FXML private Button btnItemSearch;
	@FXML private Button btnItemTemplate;
	@FXML private Button btnItemSQL;

	private boolean isShrinked;
	private VBox selectedVBox;

	private final MaterialDesignIconView toggleShrink;
	private final MaterialDesignIconView toggleExpand;
	private Labeled[] labeledsTogglable;

	public SidemenuController() {
		SHRINKED_PSEUDO = PseudoClass.getPseudoClass("shrinked");

		isShrinked = false;
		toggleShrink = new MaterialDesignIconView(MaterialDesignIcon.FORMAT_HORIZONTAL_ALIGN_RIGHT);
		toggleExpand = new MaterialDesignIconView(MaterialDesignIcon.FORMAT_HORIZONTAL_ALIGN_LEFT);
	}

	// There is an invisible character on toggle button (to push the graphic to the left).
	// https://stackoverflow.com/questions/17978720/invisible-characters-ascii

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		labeledsTogglable = new Labeled[] {
				btnLogin,
				btnItem,
				btnItemSearch,
				btnItemTemplate,
				btnItemSQL,
				btnSettings,
				btnToggle
		};

		NodeUtil.hide(vboxItem);

		// new MaterialIconView(MaterialIcon.MENU)
		setIcon(btnLogin, new MaterialDesignIconView(MaterialDesignIcon.DATABASE), MENU_ICON_SIZE);
		setIcon(btnItem, new MaterialDesignIconView(MaterialDesignIcon.SWORD), MENU_ICON_SIZE);
		setIcon(btnItemSearch, new MaterialIconView(MaterialIcon.SEARCH), SUBMENU_ICON_SIZE);
		setIcon(btnItemTemplate, new MaterialIconView(MaterialIcon.EDIT), SUBMENU_ICON_SIZE);
		setIcon(btnItemSQL, new MaterialDesignIconView(MaterialDesignIcon.DATABASE_PLUS), SUBMENU_ICON_SIZE);

		setIcon(btnSettings, new MaterialIconView(MaterialIcon.SETTINGS), MENU_ICON_SIZE);
		setIcon(btnToggle, toggleExpand, MENU_ICON_SIZE);

		setCategoryAction(btnItem, vboxItem, null);

		btnToggle.setOnAction(this::onButtonToggleAction);
	}

	private void onButtonToggleAction(ActionEvent event) {
		if(isShrinked) {
			// Expand
			for(Labeled labeled : labeledsTogglable) {
				labeled.setContentDisplay(ContentDisplay.LEFT);
				labeled.pseudoClassStateChanged(SHRINKED_PSEUDO, false);
			}

			NodeUtil.show(labelTitle);
			setIcon(btnToggle, toggleExpand, MENU_ICON_SIZE);
			vboxSidemenu.setPrefWidth(EXPAND_WIDTH);
		} else {
			// Shrink
			for(Labeled labeled : labeledsTogglable) {
				labeled.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				labeled.pseudoClassStateChanged(SHRINKED_PSEUDO, true);
			}

			NodeUtil.hide(labelTitle);
			setIcon(btnToggle, toggleShrink, MENU_ICON_SIZE);
			vboxSidemenu.setPrefWidth(SHRINK_WIDTH);
		}

		isShrinked = !isShrinked;
	}

	// SETS

	private void setButtonAction(Button btn, ICallback callback) {
		btn.setOnAction(actionEvent -> {
			if(selectedVBox != null) {
				NodeUtil.hide(selectedVBox);
				selectedVBox = null;
			}

			if(callback != null) callback.call();
		});
	}

	private void setCategoryAction(Button btn, VBox vbox, ICallback callback) {
		btn.setOnAction(actionEvent -> {
			if(selectedVBox != null && selectedVBox != vbox) {
				NodeUtil.hide(selectedVBox);
				NodeUtil.show(vbox);
			} else {
				NodeUtil.toggle(vbox, !vbox.isVisible());
			}

			selectedVBox = vbox;
			if(callback != null) callback.call();
		});
	}

	private void setIcon(Labeled label, GlyphIcon icon, int size) {
		StackPane stackPane = new StackPane(icon);
		stackPane.setPrefWidth(size);
		stackPane.setPrefHeight(size);
		icon.setSize(String.valueOf(size));
		label.setGraphic(stackPane);
	}

	// SETS CALLBACKS

	public void setLoginCallback(ICallback callback) {
		setButtonAction(btnLogin, callback);
	}
	public void setItemCallback(ICallback callback) {
		setCategoryAction(btnItem, vboxItem, callback);
	}
	public void setSettingsCallback(ICallback callback) {
		setButtonAction(btnSettings, callback);
	}

	public void setItemSearchCallback(ICallback callback) {
		setButtonAction(btnItemSearch, callback);
	}
	public void setItemTemplateCallback(ICallback callback) {
		setButtonAction(btnItemTemplate, callback);
	}
	public void setItemSQLCallback(ICallback callback) {
		setButtonAction(btnItemSQL, callback);
	}
}

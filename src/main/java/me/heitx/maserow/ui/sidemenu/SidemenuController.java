package me.heitx.maserow.ui.sidemenu;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.heitx.maserow.ui.Callback;
import me.heitx.maserow.ui.LayoutUtil;

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
	@FXML private Button btnQuest;
	@FXML private Button btnCreature;
	@FXML private Button btnSQL;
	@FXML private Button btnSettings;
	@FXML private Button btnToggle;

	@FXML private VBox vboxItem;
	@FXML private Button btnItemSearch;
	@FXML private Button btnItemTemplate;

	@FXML private VBox vboxQuest;
	@FXML private Button btnQuestSearch;
	@FXML private Button btnQuestTemplate;

	@FXML private VBox vboxCreature;
	@FXML private Button btnCreatureSearch;
	@FXML private Button btnCreatureTemplate;

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
				btnItem, btnItemSearch, btnItemTemplate,
				btnQuest, btnQuestSearch, btnQuestTemplate,
				btnCreature, btnCreatureSearch, btnCreatureTemplate,
				btnSQL,
				btnSettings,
				btnToggle
		};

		btnSettings.setDisable(true);
		btnSQL.setDisable(true);

		LayoutUtil.hide(vboxItem);
		LayoutUtil.hide(vboxQuest);
		LayoutUtil.hide(vboxCreature);

		setIcon(btnLogin, new MaterialDesignIconView(MaterialDesignIcon.DATABASE), MENU_ICON_SIZE);
		setIcon(btnItem, new MaterialDesignIconView(MaterialDesignIcon.SWORD), MENU_ICON_SIZE);
		setIcon(btnItemSearch, new MaterialIconView(MaterialIcon.SEARCH), SUBMENU_ICON_SIZE);
		setIcon(btnItemTemplate, new MaterialIconView(MaterialIcon.EDIT), SUBMENU_ICON_SIZE);
		setIcon(btnQuest, new MaterialIconView(MaterialIcon.PRIORITY_HIGH), MENU_ICON_SIZE);
		setIcon(btnQuestSearch, new MaterialIconView(MaterialIcon.SEARCH), SUBMENU_ICON_SIZE);
		setIcon(btnQuestTemplate, new MaterialIconView(MaterialIcon.EDIT), SUBMENU_ICON_SIZE);
		setIcon(btnCreature, new FontAwesomeIconView(FontAwesomeIcon.BUG), MENU_ICON_SIZE);
		setIcon(btnCreatureSearch, new MaterialIconView(MaterialIcon.SEARCH), SUBMENU_ICON_SIZE);
		setIcon(btnCreatureTemplate, new MaterialIconView(MaterialIcon.EDIT), SUBMENU_ICON_SIZE);
		setIcon(btnSQL, new MaterialDesignIconView(MaterialDesignIcon.DATABASE_PLUS), MENU_ICON_SIZE);
		setIcon(btnSettings, new MaterialIconView(MaterialIcon.SETTINGS), MENU_ICON_SIZE);
		setIcon(btnToggle, toggleExpand, MENU_ICON_SIZE);

		setCategoryAction(btnItem, vboxItem, null);
		setCategoryAction(btnQuest, vboxQuest, null);
		setCategoryAction(btnCreature, vboxCreature, null);

		btnToggle.setOnAction(this::onButtonToggleAction);
	}

	private void onButtonToggleAction(ActionEvent event) {
		if(isShrinked) {
			// Expand
			for(Labeled labeled : labeledsTogglable) {
				labeled.setContentDisplay(ContentDisplay.LEFT);
				labeled.pseudoClassStateChanged(SHRINKED_PSEUDO, false);
			}

			LayoutUtil.show(labelTitle);
			setIcon(btnToggle, toggleExpand, MENU_ICON_SIZE);
			vboxSidemenu.setPrefWidth(EXPAND_WIDTH);
		} else {
			// Shrink
			for(Labeled labeled : labeledsTogglable) {
				labeled.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				labeled.pseudoClassStateChanged(SHRINKED_PSEUDO, true);
			}

			LayoutUtil.hide(labelTitle);
			setIcon(btnToggle, toggleShrink, MENU_ICON_SIZE);
			vboxSidemenu.setPrefWidth(SHRINK_WIDTH);
		}

		isShrinked = !isShrinked;
	}

	// SETS

	private void setButtonAction(Button btn, Callback callback) {
		btn.setOnAction(actionEvent -> {
			if(selectedVBox != null) {
				LayoutUtil.hide(selectedVBox);
				selectedVBox = null;
			}

			if(callback != null) callback.call();
		});
	}

	private void setCategoryAction(Button btn, VBox vbox, Callback callback) {
		btn.setOnAction(actionEvent -> {
			if(selectedVBox != null && selectedVBox != vbox) {
				LayoutUtil.hide(selectedVBox);
				LayoutUtil.show(vbox);
			} else {
				LayoutUtil.toggle(vbox, !vbox.isVisible());
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

	public void setLoginCallback(Callback callback) {
		setButtonAction(btnLogin, callback);
	}
	// ITEM
	public void setItemCallback(Callback callback) {
		setCategoryAction(btnItem, vboxItem, callback);
	}
	public void setItemSearchCallback(Callback callback) {
		setButtonAction(btnItemSearch, callback);
	}
	public void setItemTemplateCallback(Callback callback) {
		setButtonAction(btnItemTemplate, callback);
	}
	// QUEST
	public void setQuestCallback(Callback callback) {
		setCategoryAction(btnQuest, vboxQuest, callback);
	}
	public void setQuestSearchCallback(Callback callback) {
		setButtonAction(btnQuestSearch, callback);
	}
	public void setQuestTemplateCallback(Callback callback) {
		setButtonAction(btnQuestTemplate, callback);
	}
	// CREATURE
	public void setCreatureCallback(Callback callback) {
		setCategoryAction(btnCreature, vboxCreature, callback);
	}
	public void setCreatureSearchCallback(Callback callback) {
		setButtonAction(btnCreatureSearch, callback);
	}
	public void setCreatureTemplateCallback(Callback callback) {
		setButtonAction(btnCreatureTemplate, callback);
	}
	// ...
	public void setSQLCallback(Callback callback) {
		setButtonAction(btnSQL, callback);
	}
	public void setSettingsCallback(Callback callback) {
		setButtonAction(btnSettings, callback);
	}
}

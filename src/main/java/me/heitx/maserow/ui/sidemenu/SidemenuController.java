package me.heitx.maserow.ui.sidemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import me.heitx.maserow.ui.ICallback;

import java.net.URL;
import java.util.ResourceBundle;

public class SidemenuController implements Initializable {
	@FXML private Button btnLogin;
	@FXML private Button btnItem;
	@FXML private Button btnSettings;
	@FXML private Button btnToggle;

	private ICallback loginCallback;
	private ICallback itemCallback;
	private ICallback settingsCallback;
	private ICallback toggleCallback;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		btnLogin.setOnAction(this::onButtonLoginAction);
		btnItem.setOnAction(this::onButtonItemAction);
		btnSettings.setOnAction(this::onButtonSettingsAction);
		btnToggle.setOnAction(this::onButtonToggleAction);
	}

	public void setLoginCallback(ICallback loginCallback) {
		this.loginCallback = loginCallback;
	}

	public void setItemCallback(ICallback itemCallback) {
		this.itemCallback = itemCallback;
	}

	public void setSettingsCallback(ICallback settingsCallback) {
		this.settingsCallback = settingsCallback;
	}

	public void setToggleCallback(ICallback toggleCallback) {
		this.toggleCallback = toggleCallback;
	}

	// ACTIONS

	private void onButtonLoginAction(ActionEvent event) {
		if(loginCallback != null) loginCallback.call();
	}

	private void onButtonItemAction(ActionEvent event) {
		if(itemCallback != null) itemCallback.call();
	}

	private void onButtonSettingsAction(ActionEvent event) {
		if(settingsCallback != null) settingsCallback.call();
	}

	private void onButtonToggleAction(ActionEvent event) {
		if(toggleCallback != null) toggleCallback.call();
	}
}

package me.heitx.maserow.ui.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.IDatabase;
import me.heitx.maserow.database.MySqlClient;
import me.heitx.maserow.io.config.Config;
import me.heitx.maserow.io.config.ConfigKey;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
	@FXML private TextField tfHostname;
	@FXML private TextField tfUsername;
	@FXML private PasswordField pfPassword;
	@FXML private TextField tfPort;
	@FXML private TextField tfAuth;
	@FXML private TextField tfCharacters;
	@FXML private TextField tfWorld;
	@FXML private CheckBox cbSaveInfo;
	@FXML private CheckBox cbSavePass;
	@FXML private Button btnExit;
	@FXML private Button btnDefault;
	@FXML private Button btnLogin;
	@FXML private Label labelResponse;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		load(Config.getInstance());

		btnExit.setOnAction(this::onButtonExitAction);
		btnDefault.setOnAction(this::onButtonDefaultAction);
		btnLogin.setOnAction(this::onButtonLoginAction);
	}

	private void onButtonExitAction(ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	private void onButtonDefaultAction(ActionEvent event) {
		loadDefault(Config.getInstance());
	}

	private void onButtonLoginAction(ActionEvent event) {
		String hostname = tfHostname.getText();
		String username = tfUsername.getText();
		String password = pfPassword.getText();
		int port = Integer.parseInt(tfPort.getText());
		String auth = tfAuth.getText();
		String characters = tfCharacters.getText();
		String world = tfWorld.getText();

		Database.select(Database.Type.TRINITY);

		IClient client = new MySqlClient(hostname, username, password, port, auth, characters, world);
		IDatabase db = Database.getInstance();

		try {
			client.getConnection().close();
			db.setClient(client);
			Database.setIsLoggedIn(true);
			save(Config.getInstance());
			labelResponse.setText("Login Successful!");
		} catch(SQLException e) {
			Database.setIsLoggedIn(false);
			labelResponse.setText(e.getLocalizedMessage());
		}
	}

	// SAVE/LOAD

	private void save(Config config) {
		if(cbSaveInfo.isSelected()) {
			config.update(ConfigKey.HOSTNAME, tfHostname.getText());
			config.update(ConfigKey.USERNAME, tfUsername.getText());
			config.update(ConfigKey.PORT, tfPort.getText());
			config.update(ConfigKey.AUTH, tfAuth.getText());
			config.update(ConfigKey.CHARACTERS, tfCharacters.getText());
			config.update(ConfigKey.WORLD, tfWorld.getText());
		}

		if(cbSavePass.isSelected()) {
			config.update(ConfigKey.PASSWORD, pfPassword.getText());
		}

		config.update(ConfigKey.SAVE_INFO, String.valueOf(cbSaveInfo.isSelected()));
		config.update(ConfigKey.SAVE_PASS, String.valueOf(cbSavePass.isSelected()));
	}

	private void load(Config config) {
		tfHostname.setText(config.get(ConfigKey.HOSTNAME));
		tfUsername.setText(config.get(ConfigKey.USERNAME));
		pfPassword.setText(config.get(ConfigKey.PASSWORD));
		tfPort.setText(config.get(ConfigKey.PORT));
		tfAuth.setText(config.get(ConfigKey.AUTH));
		tfCharacters.setText(config.get(ConfigKey.CHARACTERS));
		tfWorld.setText(config.get(ConfigKey.WORLD));
		cbSaveInfo.setSelected(Boolean.parseBoolean(config.get(ConfigKey.SAVE_INFO)));
		cbSavePass.setSelected(Boolean.parseBoolean(config.get(ConfigKey.SAVE_PASS)));
	}

	private void loadDefault(Config config) {
		tfHostname.setText(config.getDefault(ConfigKey.HOSTNAME));
		tfUsername.setText(config.getDefault(ConfigKey.USERNAME));
		pfPassword.setText(config.getDefault(ConfigKey.PASSWORD));
		tfPort.setText(config.getDefault(ConfigKey.PORT));
		tfAuth.setText(config.getDefault(ConfigKey.AUTH));
		tfCharacters.setText(config.getDefault(ConfigKey.CHARACTERS));
		tfWorld.setText(config.getDefault(ConfigKey.WORLD));
	}
}
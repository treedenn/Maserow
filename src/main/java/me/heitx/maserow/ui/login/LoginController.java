package me.heitx.maserow.ui.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.IDatabase;
import me.heitx.maserow.database.MySqlClient;
import me.heitx.maserow.io.config.Config;
import me.heitx.maserow.io.config.ConfigKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

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

		tfPort.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 3306));
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

		IClient client = new MySqlClient(hostname, username, password, port, auth, characters, world);
		IDatabase db = Database.getInstance();

		db.setClient(client);

		StringBuilder sb = new StringBuilder();

		attempt(client, auth, Database.Selection.AUTH, sb);
		sb.append(System.lineSeparator());
		attempt(client, characters, Database.Selection.CHARACTERS, sb);
		sb.append(System.lineSeparator());
		attempt(client, world, Database.Selection.WORLD, sb);

		if(Database.hasAccess(Database.Selection.AUTH)
				&& Database.hasAccess(Database.Selection.CHARACTERS)
				&& Database.hasAccess(Database.Selection.WORLD)) {
			sb.delete(0, sb.length());
			sb.append("You have access to all databases!");
		}

		labelResponse.setText(sb.toString());
	}

	private void attempt(IClient client, String database, Database.Selection selection, StringBuilder sb) {
		if(database.isEmpty()) {
			onAccessDenied(selection, sb);
		} else {
			try {
				client.getConnection(selection).close();
				onAccessGranted(selection, sb);
			} catch(SQLException e) {
				onAccessDenied(": " + e.getLocalizedMessage(), selection, sb);
				LOGGER.info(e.getLocalizedMessage());
			}
		}
	}

	private void onAccessGranted(Database.Selection selection, StringBuilder sb) {
		String capitalised = selection.name().substring(0, 1).toUpperCase() + selection.name().substring(1).toLowerCase();
		Database.setAccess(selection, true);
		sb.append(capitalised);
		sb.append(" [x]");
	}

	private void onAccessDenied(String text, Database.Selection selection, StringBuilder sb) {
		String capitalised = selection.name().substring(0, 1).toUpperCase() + selection.name().substring(1).toLowerCase();
		Database.setAccess(selection, false);
		sb.append(capitalised);
		sb.append(text);
	}

	private void onAccessDenied(Database.Selection selection, StringBuilder sb) {
		onAccessDenied(" [  ]", selection, sb);
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
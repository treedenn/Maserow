package me.heitx.maserow_new.common.service;

import javafx.scene.control.Button;
import me.heitx.maserow_new.core.mainpage.MainPageController;

import java.util.Map;
import java.util.Set;

public interface ITablePlugin {
	/**
	 * Called on main page. Variable is to save buttons to a specific group to categorised all tables.
	 */
	void onStart(MainPageController mainPageController, Map<String, Set<Button>> tables);
}

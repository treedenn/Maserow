package me.heitx.maserow.common.services;

import me.heitx.maserow.core.mainpage.MainPageController;

public interface ISidebarPlugin {
	void onStart(MainPageController mainPageController, ISidebar sidebar);
	//void onChange(Consumer<ISidebar> sidebar);
}
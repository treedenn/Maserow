package me.heitx.maserow.common.services;

import me.heitx.maserow.common.ui.SideElement;

public interface ISidebar {
	void addElement(boolean top, SideElement element);
	void setSelected(SideElement element);
}
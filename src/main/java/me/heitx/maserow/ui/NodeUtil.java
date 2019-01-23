package me.heitx.maserow.ui;

import javafx.scene.Node;

public class NodeUtil {
	public static void show(Node node) {
		toggle(node, true);
	}

	public static void hide(Node node) {
		toggle(node, false);
	}

	public static void toggle(Node node, boolean show) {
		node.setVisible(show);
		node.setManaged(show);
	}
}

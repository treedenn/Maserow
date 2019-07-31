package me.heitx.maserow_new.common.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.function.BiConsumer;

public final class JavaFXUtil {
	public static <T> void loadView(Class<T> clazz, String fxml, BiConsumer<Region, T> consumer) {
		FXMLLoader loader = new FXMLLoader(clazz.getResource(fxml));
		try {
			consumer.accept(loader.load(), loader.getController());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}

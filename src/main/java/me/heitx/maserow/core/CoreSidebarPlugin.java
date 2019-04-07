package me.heitx.maserow.core;

import javafx.scene.Parent;
import javafx.util.Pair;
import me.heitx.maserow.common.services.ISidebar;
import me.heitx.maserow.common.services.ISidebarPlugin;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.ui.SideElement;
import me.heitx.maserow.core.login.LoginController;
import me.heitx.maserow.core.mainpage.MainPageController;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.StackedFontIcon;

public class CoreSidebarPlugin implements ISidebarPlugin {
	private Parent loginParent;
	private LoginController loginController;

	@Override
	public void onStart(MainPageController mainPageController, ISidebar sidebar) {
		StackedFontIcon icon = new StackedFontIcon();
		icon.setIconCodeLiterals(FontAwesomeSolid.DATABASE.getDescription());

		SideElement category = new SideElement(icon, "Login");
		category.getButton().setOnAction(actionEvent -> onLoginButtonAction(mainPageController));

		sidebar.addElement(true, category);
	}

	private void onLoginButtonAction(MainPageController controller) {
		if(loginParent == null && loginController == null) {
			Pair<Parent, LoginController> layout = LayoutUtil.loadLayout(LoginController.class, "login.fxml");
			loginParent = layout.getKey();
			loginController = layout.getValue();
		}

		controller.setContent(loginParent);
	}
}
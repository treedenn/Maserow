package me.heitx.maserow.character;

import javafx.scene.Parent;
import javafx.util.Pair;
import me.heitx.maserow.character.maildelivery.MailDeliveryController;
import me.heitx.maserow.common.services.ISidebar;
import me.heitx.maserow.common.services.ISidebarPlugin;
import me.heitx.maserow.common.ui.LayoutUtil;
import me.heitx.maserow.common.ui.SideElement;
import me.heitx.maserow.core.mainpage.MainPageController;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import org.kordamp.ikonli.material.Material;

public class CharacterSidebarPlugin implements ISidebarPlugin {
	private Parent mailParent;
	private MailDeliveryController mailController;

	@Override
	public void onStart(MainPageController mainPageController, ISidebar sidebar) {
		StackedFontIcon cIcon = new StackedFontIcon();
		StackedFontIcon mIcon = new StackedFontIcon();

		cIcon.setIconCodeLiterals(Material.ACCOUNT_CIRCLE.getDescription());
		mIcon.setIconCodeLiterals(Material.MAIL.getDescription());

		SideElement category = new SideElement(cIcon, "Character");
		SideElement mailDelivery = new SideElement(mIcon, "Mail Delivery");

		category.addElement(mailDelivery);

		sidebar.addElement(true, category);

		mailDelivery.getButton().setOnAction(actionEvent -> onMailDeliveryAction(mainPageController));
	}

	private void onMailDeliveryAction(MainPageController controller) {
		if(mailParent == null && mailController == null) {
			Pair<Parent, MailDeliveryController> layout = LayoutUtil.loadLayout(MailDeliveryController.class, "maildelivery.fxml");
			mailParent = layout.getKey();
			mailController = layout.getValue();
		}
//		else {
//			mailController.update();
//		}

		controller.setContent(mailParent);
	}
}
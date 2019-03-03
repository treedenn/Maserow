package me.heitx.maserow.ui.item.template;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.ui.LayoutUtil;
import me.heitx.maserow.ui.item.template.build.ItemBuildController;
import me.heitx.maserow.ui.item.template.preview.ItemPreviewController;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.utils.query.TrinityItemQuery;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemTemplateController implements Initializable, Updateable {
	@FXML private VBox vboxTemplate;
	@FXML private Button btnExecute;
	@FXML private Button btnScreenshot;
	@FXML private MenuButton mbSQL;
	@FXML private MenuItem miInsert;
	@FXML private MenuItem miUpdate;
	@FXML private MenuItem miDelete;
	@FXML private CheckBox cbPreview;

	@FXML private Parent build;
	@FXML private ItemBuildController buildController;
	@FXML private Parent preview;
	@FXML private ItemPreviewController previewController;

	private Item item;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		LayoutUtil.hide(preview);

		update();

		btnExecute.setOnAction(this::onButtonExecuteAction);
		btnScreenshot.setOnAction(this::onButtonScreenshotAction);
		miInsert.setOnAction(this::onMenuButtonAction);
		miUpdate.setOnAction(this::onMenuButtonAction);
		miDelete.setOnAction(this::onMenuButtonAction);
		cbPreview.setOnAction(this::onCheckBoxPreviewAction);

		item = new Item();
		buildController.setItem(item);
		previewController.setItem(item);
	}

	@Override
	public void update() {
		btnExecute.setDisable(!Database.isLoggedIn());
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;

		buildController.setItem(item);
		previewController.setItem(item);

		previewController.update();
	}

	private void onButtonExecuteAction(ActionEvent event) {
		if(item != null) {
			IItemDAO dao = Database.getInstance().getItemDAO();
			Map<String, Object> attributes = ConverterUtil.toAttributes(item);

			if(dao.exists(item.getEntry())) {
				Optional<ButtonType> alert = LayoutUtil.showAlert(Alert.AlertType.CONFIRMATION, "Conflict", "Identifier already exists..", "There exists already an item with given identifier! " +
						"Do you want to overwrite the old item with the new one?", ButtonType.NO, ButtonType.YES);
				if(alert.isPresent() && alert.get() == ButtonType.YES) {
					dao.update(attributes);
				}
			} else {
				dao.insert(attributes);
			}
		}
	}

	private void onMenuButtonAction(ActionEvent event) {
		Map<String, Object> attributes = ConverterUtil.toAttributes(item);
		final String initialFileName = "item:" + item.getName().replaceAll(" ", "-") + ":" + item.getEntry();
		final Window window = btnExecute.getScene().getWindow();

		if(event.getSource() == miInsert) {
			LayoutUtil.showSaveSqlWindow(window, "Save Insert Query", initialFileName, TrinityItemQuery.getInsertQuery(attributes, true));
		} else if(event.getSource() == miUpdate) {
			LayoutUtil.showSaveSqlWindow(window, "Save Update Query", initialFileName, TrinityItemQuery.getInsertQuery(attributes, true));
		} else if(event.getSource() == miDelete) {
			LayoutUtil.showSaveSqlWindow(window, "Save Delete Query", initialFileName, TrinityItemQuery.getInsertQuery(attributes, true));
		}
	}

	private void onCheckBoxPreviewAction(ActionEvent event) {
		if(cbPreview.isSelected()) {
			previewController.update();
			LayoutUtil.show(preview);
			LayoutUtil.hide(build);
		} else {
			LayoutUtil.show(build);
			LayoutUtil.hide(preview);
		}
	}

	// TODO: Place these inside IO folder
	private void onButtonScreenshotAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save Preview as Image");
		fc.setInitialFileName("image" + item.getName().replaceAll(" ", "-") + ":" + item.getEntry());
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Portable Network Graphics", "*.png"));

		File selectedFile = fc.showSaveDialog(vboxTemplate.getScene().getWindow());
		if(selectedFile != null) {
			if(!selectedFile.getName().endsWith(".png")) {
				selectedFile = new File(selectedFile + ".png");
			}

			try {
				WritableImage snapshot = preview.snapshot(new SnapshotParameters(), null);
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", selectedFile);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
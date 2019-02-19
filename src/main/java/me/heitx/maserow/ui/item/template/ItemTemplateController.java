package me.heitx.maserow.ui.item.template;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import me.heitx.maserow.converter.Converter;
import me.heitx.maserow.converter.IConverter;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.utils.query.TrinityItemQuery;
import me.heitx.maserow.ui.NodeUtil;
import me.heitx.maserow.ui.Updateable;
import me.heitx.maserow.ui.item.template.build.ItemBuildController;
import me.heitx.maserow.ui.item.template.preview.ItemPreviewController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
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
		NodeUtil.hide(preview);

		update();

		btnExecute.setOnAction(this::onButtonExecuteAction);
		btnScreenshot.setOnAction(this::onButtonScreenshotAction);
		miInsert.setOnAction(this::onMenuButtonAction);
		miUpdate.setOnAction(this::onMenuButtonAction);
		miDelete.setOnAction(this::onMenuButtonAction);
		cbPreview.setOnAction(this::onCheckBoxPreviewAction);
	}

	@Override
	public void update() {
		btnExecute.setDisable(!Database.isIsLoggedIn());
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
			Map<String, Object> attributes = Converter.toAttributes(item);

			if(dao.exists(item.getEntry())) {
				dao.update(attributes);
			} else {
				dao.insert(attributes);
			}
		}
	}

	private void onMenuButtonAction(ActionEvent event) {
		Map<String, Object> attributes = Converter.toAttributes(item);

		if(event.getSource() == miInsert) {
			saveSql("Save Insert Query", TrinityItemQuery.getInsertQuery(attributes, true));
		} else if(event.getSource() == miUpdate) {
			saveSql("Save Update Query", TrinityItemQuery.getInsertQuery(attributes, true));
		} else if(event.getSource() == miDelete) {
			saveSql("Save Delete Query", TrinityItemQuery.getInsertQuery(attributes, true));
		}
	}

	private void onCheckBoxPreviewAction(ActionEvent event) {
		if(cbPreview.isSelected()) {
			previewController.update();
			NodeUtil.show(preview);
			NodeUtil.hide(build);
		} else {
			NodeUtil.show(build);
			NodeUtil.hide(preview);
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

	private void saveSql(String title, String query) {
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		fc.setInitialFileName(item.getName().replaceAll(" ", "-") + ":" + item.getEntry());
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Structured Query Language", "*.sql"));

		File selectedFile = fc.showSaveDialog(vboxTemplate.getScene().getWindow());
		if(selectedFile != null) {
			if(!selectedFile.getName().endsWith(".sql")) {
				selectedFile = new File(selectedFile + ".sql");
			}

			try {
				FileOutputStream fos = new FileOutputStream(selectedFile);
				fos.write(query.getBytes());
				fos.flush();
				fos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
package me.heitx.maserow.ui.character.maildelivery;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.util.converter.IntegerStringConverter;
import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.repository.ICharacterRepository;
import me.heitx.maserow.database.repository.IItemRepository;
import me.heitx.maserow.database.repository.IMailRepository;
import me.heitx.maserow.io.CommonCSV;
import me.heitx.maserow.io.DelimiterReader;
import me.heitx.maserow.io.ICSV;
import me.heitx.maserow.io.Identifier;
import me.heitx.maserow.model.Character;
import me.heitx.maserow.model.Item;
import me.heitx.maserow.model.Mail;
import me.heitx.maserow.model.Stack;
import me.heitx.maserow.ui.LayoutUtil;
import me.heitx.maserow.ui.lookup.LookupManager;
import me.heitx.maserow.utils.MoneyUtil;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MailDeliveryController implements Initializable {
	@FXML private FlowPane pRoot;

	@FXML private Button btnSend;

	@FXML private TextField tfSubject;
	@FXML private TextArea taBody;
	@FXML private CheckBox cbCOD;

	@FXML private TextField tfGold;
	@FXML private TextField tfSilver;
	@FXML private TextField tfCopper;

	@FXML private ListView<SelectorData> lvAvailableItems;
	@FXML private ListView<SelectorData> lvSelectedItems;
	@FXML private Button btnItemsTransfer;
	@FXML private Button btnItemsRemove;
	@FXML private TextField tfItemQuantity;

	@FXML private TextField tfItemSearchEntry;
	@FXML private TextField tfItemSearchName;
	@FXML private Button btnItemSearch;

	@FXML private ListView<SelectorData> lvCharacters;
	@FXML private ListView<SelectorData> lvReceivers;
	@FXML private Button btnCharacterTransfer;
	@FXML private Button btnCharacterRemove;
	@FXML private TextField tfCharacterName;
	@FXML private Button btnCharacterSearch;
	@FXML private TextField tfClasses;
	@FXML private TextField tfRaces;

	@FXML private TextField tfSender;
	@FXML private TextField tfChecked;

	@FXML private DateTimePicker dpDelivery;
	@FXML private DateTimePicker dpExpire;
	@FXML private CheckBox cbInstant;
	@FXML private CheckBox cbDefault;

	private final String csvPath = ICSV.CSV_FOLDER_NAME + File.separator + "mail" + File.separator;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		lvAvailableItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvSelectedItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvCharacters.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lvReceivers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tfRaces.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfClasses.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfSender.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfChecked.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfGold.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfSilver.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfCopper.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0));
		tfItemQuantity.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1));

		btnItemSearch.setOnAction(this::buttonItemSearchAction);
		btnItemsTransfer.setOnAction(this::buttonItemsTransferRemoveAction);
		btnItemsRemove.setOnAction(this::buttonItemsTransferRemoveAction);

		btnCharacterSearch.setOnAction(this::buttonCharacterSearchAction);
		btnCharacterTransfer.setOnAction(this::buttonCharactersTransferRemoveAction);
		btnCharacterRemove.setOnAction(this::buttonCharactersTransferRemoveAction);

		btnSend.setOnAction(this::buttonSendAction);

		lvAvailableItems.setCellFactory(lv -> getListCell(lv, SelectorData::getText));
		lvSelectedItems.setCellFactory(lv -> getListCell(lv, item -> item.getText() + " x" + item.getQuantity()));

		lvCharacters.setCellFactory(lv -> getListCell(lv, SelectorData::getText));
		lvReceivers.setCellFactory(lv -> getListCell(lv, SelectorData::getText));

		lvCharacters.setOnMouseClicked(event -> {

			if(event.getButton() == MouseButton.SECONDARY) {
				SelectorData selectedItem = lvCharacters.getSelectionModel().getSelectedItem();

				if(selectedItem != null) {
					tfSender.setText(String.valueOf(selectedItem.getValue()));
				}
			}
		});

		dpDelivery.setDateTimeValue(LocalDateTime.now());
		dpExpire.setDateTimeValue(LocalDateTime.now().plusDays(30));

		LayoutUtil.onAltPrimaryButton(tfClasses, () -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(CommonCSV.CLASSES);
			List<Integer> selected = Identifier.findIndicesByValue(identifiers, Long.parseLong(tfClasses.getText()));

			LookupManager.getInstance().showMultiLookup("Specify Classes as Receivers", "Mail Delivery - Classes", false, identifiers, selected, entries -> {
				long totalValue = 0;
				for(Long entry : entries) totalValue += entry;

				tfClasses.setText(String.valueOf(totalValue));
			});
		});

		LayoutUtil.onAltPrimaryButton(tfRaces, () -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(CommonCSV.RACES);
			List<Integer> selected = Identifier.findIndicesByValue(identifiers, Long.parseLong(tfRaces.getText()));

			LookupManager.getInstance().showMultiLookup("Specify Races as Receivers", "Mail Delivery - Races", false, identifiers, selected, entries -> {
				long totalValue = 0;
				for(Long entry : entries) totalValue += entry;

				tfRaces.setText(String.valueOf(totalValue));
			});
		});

		LayoutUtil.onAltPrimaryButton(tfChecked, () -> {
			List<Identifier> identifiers = DelimiterReader.readColumns(csvPath + "checked", false, true);
			List<Integer> selected = Identifier.findIndicesByValue(identifiers, Long.parseLong(tfChecked.getText()));

			LookupManager.getInstance().showMultiLookup("Checked Flag", "Mail Delivery - Checked", false, identifiers, selected, entries -> {
				long totalValue = 0;
				for(Long entry : entries) totalValue += entry;

				tfChecked.setText(String.valueOf(totalValue));
			});
		});
	}

	private void buttonItemSearchAction(ActionEvent event) {
		if(Database.hasAccess(Database.Selection.WORLD)) {
			int entry = Integer.parseInt(tfItemSearchEntry.getText());

			IItemRepository dao = Database.getInstance().getItemDAO();
			List<Item> items = dao.search(entry, tfItemSearchName.getText(), 100);

			List<SelectorData> data = new ArrayList<>();
			for(Item item : items) {
				data.add(new SelectorData(item.getEntry(), item.getName()));
			}

			lvAvailableItems.getItems().clear();
			lvAvailableItems.getItems().addAll(data);
		}
	}

	private ListCell<SelectorData> getListCell(ListView<SelectorData> lv, Function<SelectorData, String> function) {
		return new ListCell<SelectorData>() {
			@Override
			protected void updateItem(SelectorData item, boolean empty) {
				super.updateItem(item, empty);
				if(!empty && item != null) setText(function.apply(item));
				else setText("");
			}
		};
	}

	private void buttonItemsTransferRemoveAction(ActionEvent event) {
		if(event.getSource() == btnItemsTransfer) {
			alternating(lvAvailableItems, lvSelectedItems, tfItemQuantity);
		} else {
			alternating(lvSelectedItems, lvAvailableItems, tfItemQuantity);
		}
	}

	private void buttonCharacterSearchAction(ActionEvent event) {
		if(Database.hasAccess(Database.Selection.CHARACTERS)) {


			ICharacterRepository dao = Database.getInstance().getCharacterDAO();
			List<Character> characters = dao.search(0, tfCharacterName.getText(), new int[0], new int[0], 100);

			List<SelectorData> data = new ArrayList<>();
			for(Character character : characters) {
				data.add(new SelectorData(character.getGuid(), character.getName()));
			}

			lvCharacters.getItems().clear();
			lvCharacters.getItems().addAll(data);
		}
	}

	private void buttonCharactersTransferRemoveAction(ActionEvent event) {
		if(event.getSource() == btnCharacterTransfer) {
			alternating(lvCharacters, lvReceivers, null);
		} else {
			alternating(lvReceivers, lvCharacters, null);
		}
	}

	private void alternating(ListView<SelectorData> lvFrom, ListView<SelectorData> lvTo, TextField forQuantity) {
		ObservableList<Integer> selectedIndices = lvFrom.getSelectionModel().getSelectedIndices();
		for(int i = 0; i < selectedIndices.size(); i++) {
			SelectorData removed = lvFrom.getItems().remove(selectedIndices.get(i) - i);
			if(forQuantity != null) removed.setQuantity(Long.parseLong(tfItemQuantity.getText()));
			lvTo.getItems().add(removed);
		}

		lvFrom.refresh();
		lvTo.refresh();
	}

	private void buttonSendAction(ActionEvent event) {
		if(Database.hasAccess(Database.Selection.WORLD) && Database.hasAccess(Database.Selection.CHARACTERS)) {
			List<Stack> items = new ArrayList<>();
			for(SelectorData item : lvSelectedItems.getItems()) {
				items.add(new Stack((int) item.getValue(), (int) item.getQuantity()));
			}

			List<Long> receivers = new ArrayList<>();
			for(SelectorData item : lvReceivers.getItems()) {
				receivers.add(item.getValue());
			}

			List<Identifier> raceIdentifiers = DelimiterReader.readColumns(CommonCSV.RACES);
			List<Integer> raceIndices = Identifier.findIndicesByValue(raceIdentifiers, Long.parseLong(tfRaces.getText()));

			List<Integer> races = new ArrayList<>();
			for(Integer index : raceIndices) {
				races.add(raceIdentifiers.get(index).getId());
			}

			List<Identifier> classIdentifiers = DelimiterReader.readColumns(CommonCSV.CLASSES);
			List<Integer> classIndices = Identifier.findIndicesByValue(classIdentifiers, Long.parseLong(tfClasses.getText()));

			List<Integer> classes = new ArrayList<>();
			for(Integer index : classIndices) {
				classes.add(classIdentifiers.get(index).getId());
			}

			IMailRepository mailDAO = Database.getInstance().getMailDAO();
			mailDAO.send(getMail(), items, receivers, races, classes);
		}
	}

	private Mail getMail() {
		Mail mail = new Mail();
		mail.setSubject(tfSubject.getText());
		mail.setBody(taBody.getText());
		mail.setCod(cbCOD.isSelected() ? 1 : 0);
		mail.setMoney(MoneyUtil.gscToTotal(tfGold.getText(), tfSilver.getText(), tfCopper.getText()));
		mail.setSender(Long.parseLong(tfSender.getText()));
		mail.setChecked(Integer.parseInt(tfChecked.getText()));

		if(cbInstant.isSelected()) {
			mail.setDeliverTime(Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond());
		} else {
			mail.setDeliverTime(dpDelivery.getDateTimeValue().toEpochSecond(ZoneOffset.UTC));
		}

		if(cbDefault.isSelected() || dpExpire.getDateTimeValue().compareTo(dpDelivery.getDateTimeValue()) < 1) {
			mail.setExpireTime(Instant.now().plus(30, ChronoUnit.DAYS).getEpochSecond());
		} else {
			mail.setExpireTime(dpExpire.getDateTimeValue().toEpochSecond(ZoneOffset.UTC));
		}

		return mail;
	}
}
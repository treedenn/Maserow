package me.heitx.maserow.ui.lookup.singleselection;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import me.heitx.maserow.ui.lookup.LookupController;
import me.heitx.maserow.ui.lookup.LookupData;

public class LookupSingleController extends LookupController<Long> {
	private boolean useEntry;

	public void setUseEntry(boolean useEntry) {
		this.useEntry = useEntry;
	}

	public void setSelected(Integer rowIndex) {
		if(rowIndex != null) tvTable.getSelectionModel().select(rowIndex);
	}

	@Override
	protected TableRow<LookupData> onTableRowMouseClick(TableView<LookupData> table) {
		TableRow<LookupData> row = new TableRow<>();
		row.setOnMouseClicked(event -> {
			if(!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				LookupData item = table.getSelectionModel().getSelectedItem();
				if(onSuccess != null && item != null) {
					onSuccess.call(useEntry ? item.getEntry() : item.getValue());
					clean();
					close();
				}
			}
		});
		return row;
	}
}
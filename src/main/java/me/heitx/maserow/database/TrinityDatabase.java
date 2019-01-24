package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.ItemDAO;
import me.heitx.maserow.database.trinitywotlk.TrinityWotlkItemDAO;

public class TrinityDatabase implements IDatabase {
	private IClient client;

	private ItemDAO itemDAO;

	TrinityDatabase() {
		this.itemDAO = null;
	}

	@Override
	public IClient getClient() {
		return client;
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;
	}

	@Override
	public ItemDAO getItemDAO() {
		if(Database.isIsLoggedIn() && itemDAO == null) itemDAO = new TrinityWotlkItemDAO(client);
		return itemDAO;
	}
}

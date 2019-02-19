package me.heitx.maserow.database;

import me.heitx.maserow.database.dao.IItemDAO;
import me.heitx.maserow.database.trinitywotlk.ItemDAO;

public class TrinityDatabase implements IDatabase {
	private IClient client;

	private IItemDAO IItemDAO;

	TrinityDatabase() {
		this.IItemDAO = null;
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
	public IItemDAO getItemDAO() {
		if(Database.isIsLoggedIn() && IItemDAO == null) IItemDAO = new ItemDAO(client);
		return IItemDAO;
	}
}

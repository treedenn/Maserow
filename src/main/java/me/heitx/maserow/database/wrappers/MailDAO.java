package me.heitx.maserow.database.wrappers;

import me.heitx.maserow.database.Database;
import me.heitx.maserow.database.IClient;
import me.heitx.maserow.database.MySqlDatabase;
import me.heitx.maserow.database.dao.IMailDAO;
import me.heitx.maserow.model.*;
import me.heitx.maserow.model.Stack;
import me.heitx.maserow.utils.ConverterUtil;
import me.heitx.maserow.utils.Queries;
import me.heitx.maserow.utils.QueryUtil;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static me.heitx.maserow.utils.QueryUtil.*;

public class MailDAO extends MySqlDatabase implements IMailDAO {
	public MailDAO(IClient client) {
		super(client);
	}

	@Override
	public boolean send(Mail mail, List<Stack> items, List<Long> receiverGuids, List<Integer> races, List<Integer> classes) {
		AtomicBoolean atomic = new AtomicBoolean(false);

		mail.setHasItems(items.size() > 0 ? 1 : 0);

		// Extracts all item_template from all the entries to obtain the max durability of each item
		// (in case it's a weapon or a armor piece)
		execute(Database.Selection.CHARACTERS, Database.Selection.WORLD, (cConn, wConn) -> {
			Statement recieversStatement = cConn.createStatement();
			receiverGuids.addAll(getRacesAndClassesReceivers(recieversStatement, races, classes));

			Statement itemStatement = wConn.createStatement();
			Map<Integer, Item> entryToItemMap = getItems(itemStatement, items);

			List<ItemInstance> itemInstances = new ArrayList<>();
			createItemInstances(items, entryToItemMap, itemInstances);

			Statement highestIIStatement = cConn.createStatement();
			Statement highestMailStatement = cConn.createStatement();

			long iiGuid = highestItemInstanceEntry(highestIIStatement) + 5; // +5 is gap between the max
			long mailGuid = highestMailEntry(highestMailStatement) + 5; // +5 is gap between the max

			Statement iiStatement = cConn.createStatement();
			Statement mailStatement = cConn.createStatement();
			Statement mailItemsStatement = cConn.createStatement();
			for(Long guid : receiverGuids) {
				mail.setId(mailGuid);
				mail.setReceiver(guid);

				Map<String, Object> mailAttributes = ConverterUtil.toAttributes(mail);
				mailStatement.execute(Queries.Mail.insert(false, mailAttributes));

				for(ItemInstance itemInstance : itemInstances) {
					itemInstance.setGuid(iiGuid);
					itemInstance.setOwnerGuid(guid);

					Map<String, Object> iiAttributes = ConverterUtil.toAttributes(itemInstance);
					iiStatement.addBatch(Queries.ItemInstance.insert(false, iiAttributes));

					MailItem mi = new MailItem(mailGuid, iiGuid, guid);
					Map<String, Object> miAttributes = ConverterUtil.toAttributes(mi);
					mailItemsStatement.addBatch(Queries.Mail.insertItem(false, miAttributes));

					iiGuid++;
				}

				mailGuid++;
			}

			mailStatement.executeLargeBatch();
			mailItemsStatement.executeLargeBatch();
			iiStatement.executeLargeBatch();

			atomic.set(true);
			return new Statement[] { };
		});

		return atomic.get();
	}

	private void createItemInstances(List<Stack> items, Map<Integer, Item> entryToItemMap, List<ItemInstance> itemInstances) {
		for(Stack item : items) {
			Item curr = entryToItemMap.get(item.getId());
			ItemInstance ii = new ItemInstance();
			ii.setItemEntry(curr.getEntry());
			ii.setCount(item.getQuantity());
			ii.setDurability(curr.getMaxDurability());

			itemInstances.add(ii);
		}
	}

	private long highestMailEntry(Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery(Queries.Mail.getMaxEntry(false));
		rs.next();
		return rs.getLong(1);
	}

	private long highestItemInstanceEntry(Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery(Queries.ItemInstance.getMaxEntry(false));
		rs.next();
		return rs.getLong(1);
	}

	private Map<Integer, Item> getItems(Statement statement, List<Stack> items) throws SQLException {
		// Gets all the unique entries
		Set<Integer> uniqueEntries = new HashSet<>();
		for(Stack item : items) uniqueEntries.add(item.getId());

		// Gets the item_template from each unique entry
		List<String> blocks = new ArrayList<>(QueryUtil.select(null, Queries.Item.TEMPLATE_TABLE, Queries.Item.identifier + " " + QueryUtil.in(uniqueEntries)));
		String sql = QueryUtil.buildNewLineFormat(false, blocks);

		ResultSet rs = statement.executeQuery(sql);

		// Links each item to an entry
		Map<Integer, Item> entryLink = new HashMap<>();
		while(rs.next()) {
			Map<String, Object> map = convertResultSet(rs);
			Item item = ConverterUtil.toObject(Item.class, map);
			entryLink.put(item.getEntry(), item);
		}

		return entryLink;
	}

	private Set<Long> getRacesAndClassesReceivers(Statement statement, List<Integer> races, List<Integer> classes) throws SQLException {
		Set<Long> receivers = new HashSet<>();

		List<String> orBlocks = new ArrayList<>();
		if(races.size() > 0) orBlocks.add(QueryUtil.in("race", races));
		if(classes.size() > 0) orBlocks.add(QueryUtil.in("class", classes));

		if(races.size() > 0 || classes.size() > 0) {
			List<String> blocks = QueryUtil.select(Collections.singleton("guid"), "characters", QueryUtil.or(orBlocks));
			String sql = QueryUtil.buildNewLineFormat(false, blocks);

			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()) receivers.add(rs.getLong(1));
		}

		return receivers;
	}
}
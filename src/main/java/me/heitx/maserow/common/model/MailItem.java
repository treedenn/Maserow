package me.heitx.maserow.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MailItem {
	@Column("mail_id") private long id;
	@Column("item_guid") private long itemGuid;
	@Column("receiver") private long receiver;

	public MailItem(long id, long itemGuid, long receiver) {
		this.id = id;
		this.itemGuid = itemGuid;
		this.receiver = receiver;
	}
}
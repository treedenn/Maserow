package me.heitx.maserow.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Mail {
	@Column("id") private long id;
	@Column("messageType") private int messageType;
	@Column("stationery") private int stationery;
	@Column("mailTemplateId") private int mailTemplateId;
	@Column("sender") private long sender;
	@Column("receiver") private long receiver;
	@Column("subject") private String subject;
	@Column("body") private String body;
	@Column("has_items") private int hasItems;
	@Column("expire_time") private long expireTime;
	@Column("deliver_time") private long deliverTime;
	@Column("money") private long money;
	@Column("cod") private long cod;
	@Column("checked") private int checked;
}

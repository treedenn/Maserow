package me.heitx.maserow.database.dao;

import me.heitx.maserow.model.Mail;
import me.heitx.maserow.model.Stack;

import java.util.List;
import java.util.Map;

public interface IMailDAO {
	boolean send(Mail mail, List<Stack> items, List<Long> receiverGuids, List<Integer> races, List<Integer> classes);
}
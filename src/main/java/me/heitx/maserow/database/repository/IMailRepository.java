package me.heitx.maserow.database.repository;

import me.heitx.maserow.model.Mail;
import me.heitx.maserow.model.Stack;

import java.util.List;

public interface IMailRepository {
	boolean send(Mail mail, List<Stack> items, List<Long> receiverGuids, List<Integer> races, List<Integer> classes);
}
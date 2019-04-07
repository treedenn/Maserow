package me.heitx.maserow.common.database.repository;

import me.heitx.maserow.common.model.Mail;
import me.heitx.maserow.common.Stack;

import java.util.List;

public interface IMailRepository extends Repository {
	boolean send(Mail mail, List<Stack> items, List<Long> receiverGuids, List<Integer> races, List<Integer> classes);
}
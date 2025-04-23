package com.example.project.service.impl;

import com.example.project.dao.TransactionsDao;
import com.example.project.model.Transactions;
import com.example.project.model.User;
import com.example.project.model.enums.RoleType;
import com.example.project.security.AuthenticatedUser;
import com.example.project.service.TransactionsService;
import com.example.project.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final TransactionsDao transactionsDao;

    public TransactionsServiceImpl(TransactionsDao transactionsDao) {
        this.transactionsDao = transactionsDao;
    }

    @Override
    public List<Transactions> findAll() {
        User user = AuthenticatedUser.get();
        if (user.getProfiles().getRole().equals(RoleType.ADMIN)) {
            return (List<Transactions>) transactionsDao.findAll();
        }
        return transactionsDao.findAllByUser_Id(user.getId());
    }

    @Override
    public Transactions findById(UUID uuid) {
        User user = AuthenticatedUser.get();
        Transactions transactions = transactionsDao.findById(uuid).orElse(null);
        if (transactions != null && (user.getProfiles().getRole().equals(RoleType.ADMIN) || transactions.getUser().equals(user))) {
            return transactions;
        }
        return null;
    }

    @Override
    public Transactions save(Transactions transactions) {
        User user = AuthenticatedUser.get();
        transactions.setDate(new Date());
        transactions.setUser(user);
        return transactionsDao.save(transactions);
    }

    @Override
    public Transactions deleteById(UUID id) {
        Transactions transactions = transactionsDao.findById(id).orElse(null);
        if (transactions != null && transactions.getUser().equals(AuthenticatedUser.get())) {
            transactionsDao.delete(transactions);
        }
        return transactions;
    }

    @Override
    public Transactions update(Transactions transactions, UUID uuid) {
        Transactions oldTransactions = transactionsDao.findById(uuid).orElse(null);
        if (oldTransactions == null) {
            return transactions;
        }
        Utils.copyNonNullProperties(oldTransactions, transactions);
        return transactionsDao.save(oldTransactions);
    }

    public List<Transactions> getTransactionsByDate(Date date) {
        User user = AuthenticatedUser.get();
        if (user.getProfiles().getRole().equals(RoleType.ADMIN)) {
            return transactionsDao.findAllByDate(date);
        }
        return transactionsDao.findAllByDateAndUser_Id(date, user.getId());
    }
}

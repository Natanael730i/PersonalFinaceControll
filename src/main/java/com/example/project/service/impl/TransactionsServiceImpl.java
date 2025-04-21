package com.example.project.service.impl;

import com.example.project.dao.TransactionsDao;
import com.example.project.model.Transactions;
import com.example.project.service.TransactionsService;
import com.example.project.utils.Utils;
import org.springframework.stereotype.Service;

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
        return (List<Transactions>) transactionsDao.findAll();
    }

    @Override
    public Transactions findById(UUID uuid) {
        return transactionsDao.findById(uuid).orElse(null);
    }

    @Override
    public Transactions save(Transactions transactions) {
        return transactionsDao.save(transactions);
    }

    @Override
    public Transactions deleteById(UUID id) {
        Transactions transactions = transactionsDao.findById(id).orElse(null);
        if (transactions != null) {
            transactionsDao.delete(transactions);
        }
        return transactions;
    }

    @Override
    public Transactions update(Transactions transactions, UUID uuid) {
        Transactions oldTransactions = transactionsDao.findById(uuid).orElse(null);
        if (oldTransactions == null) {
            return null;
        }
        Utils.copyNonNullProperties(oldTransactions, transactions);
        return transactionsDao.save(oldTransactions);
    }
}

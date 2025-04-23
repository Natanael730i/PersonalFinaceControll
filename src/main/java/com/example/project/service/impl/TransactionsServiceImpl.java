package com.example.project.service.impl;

import com.example.project.dao.TransactionsDao;
import com.example.project.model.Transactions;
import com.example.project.model.User;
import com.example.project.model.enums.RoleType;
import com.example.project.security.AuthenticatedUser;
import com.example.project.service.TransactionsService;
import com.example.project.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
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
        Transactions transactions = transactionsDao.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("transactions with id: " + uuid + " not found"));
        if ((user.getProfiles().getRole().equals(RoleType.ADMIN) || transactions.getUser().equals(user))) {
            return transactions;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Transactions save(Transactions transactions) {
        User user = AuthenticatedUser.get();
        transactions.setDate(new Date());
        transactions.setUser(user);
        return transactionsDao.save(transactions);
    }

    @Override
    public void deleteById(UUID id) {
        Transactions transactions = transactionsDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transactions with id " + id + " not found!"));
        if (!transactions.getUser().equals(AuthenticatedUser.get())) {
            throw new AccessDeniedException("Access denied!");
        }
        transactionsDao.delete(transactions);
    }

    @Override
    public Transactions update(Transactions transactions, UUID uuid) {
        Transactions oldTransactions = transactionsDao.findById(uuid)
                .orElseThrow(() ->new EntityNotFoundException("Transactions whit id " + uuid + " not found!"));
        if (!transactions.getUser().equals(oldTransactions.getUser())) {
            throw new  AccessDeniedException("Access denied!");
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

    @Override
    public List<Transactions> getDateByPeriod(Date initialDate, Date finalDate) {
        User user = AuthenticatedUser.get();
        if (user.getProfiles().getRole().equals(RoleType.ADMIN)) {
            return transactionsDao.findAllByDateBetween(initialDate, finalDate);
        }
        return transactionsDao.findAllByDateBetweenAndUser_Id(initialDate, finalDate, user.getId());
    }
}

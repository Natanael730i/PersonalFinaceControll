package com.example.project.service;

import com.example.project.model.Transactions;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionsService extends GenericService<Transactions, UUID>{
    List<Transactions> getTransactionsByDate(Date date);

    List<Transactions> getDateByPeriod(Date initialDate, Date finalDate);
}

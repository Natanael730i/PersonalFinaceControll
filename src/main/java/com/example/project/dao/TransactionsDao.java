package com.example.project.dao;

import com.example.project.model.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionsDao extends CrudRepository<Transactions, UUID> {
    List<Transactions> findAllByUser_Id(UUID userId);

    List<Transactions> findAllByDate(Date date);

    List<Transactions> findAllByDateAndUser_Id(Date date, UUID userId);
}

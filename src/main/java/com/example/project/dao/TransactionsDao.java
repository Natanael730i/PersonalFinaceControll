package com.example.project.dao;

import com.example.project.model.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransactionsDao extends CrudRepository<Transactions, UUID> {
}

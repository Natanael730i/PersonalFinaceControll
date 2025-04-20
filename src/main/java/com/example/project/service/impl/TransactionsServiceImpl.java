package com.example.project.service.impl;

import com.example.project.model.Transactions;
import com.example.project.service.TransactionsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Override
    public List<Transactions> findAll() {
        return List.of();
    }

    @Override
    public Transactions findById(UUID uuid) {
        return null;
    }

    @Override
    public Transactions save(Transactions transactions) {
        return null;
    }

    @Override
    public Transactions deleteById(UUID uuid) {
        return null;
    }

    @Override
    public Transactions update(Transactions transactions, UUID uuid) {
        return null;
    }
}

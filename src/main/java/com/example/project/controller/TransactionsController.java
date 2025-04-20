package com.example.project.controller;

import com.example.project.model.Transactions;
import com.example.project.service.TransactionsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionsController extends GenericController<Transactions, UUID> {

    public TransactionsController(TransactionsService service) {
        super(service);
    }
}

package com.example.project.controller;

import com.example.project.model.Transactions;
import com.example.project.service.TransactionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionsController extends GenericController<Transactions, UUID> {

    private final TransactionsService service;

    public TransactionsController(TransactionsService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/{dateLong}")
    public List<Transactions> getTransactionsByDate(@PathVariable Long dateLong){
        return service.getTransactionsByDate(new Date(dateLong));
    }

    @GetMapping("/{initialDate}/{finalDate}")
    public List<Transactions> getDateByPeriod(@PathVariable Long initialDate, @PathVariable Long finalDate){
        return service.getDateByPeriod(new Date(initialDate), new Date(finalDate));
    }
}

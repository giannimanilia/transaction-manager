package com.gmaniliapp.transactionmanager.ws.rest.controller.transactions;

import com.gmaniliapp.transactionmanager.model.Transaction;
import com.gmaniliapp.transactionmanager.service.transactions.TransactionsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TransactionsRestController {

    private static final Logger logger = LogManager.getLogger(TransactionsRestController.class);

    private final TransactionsService transactionsService;

    public TransactionsRestController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(
        @Validated @RequestBody Transaction transaction) {
        logger.info("createTransaction - START");

        transaction = transactionsService.registerTransaction(transaction);
        ResponseEntity<Transaction> responseEntity = ResponseEntity.ok(transaction);

        logger.info("createTransaction - END");
        return responseEntity;
    }

    @DeleteMapping("/transactions")
    public ResponseEntity<Void> deleteAllTransactions() {
        logger.info("deleteAllTransactions - START");

        transactionsService.deleteAllTransactions();
        ResponseEntity<Void> responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        logger.info("deleteAllTransactions - END");
        return responseEntity;
    }

}

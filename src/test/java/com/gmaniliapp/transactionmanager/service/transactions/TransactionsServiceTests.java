package com.gmaniliapp.transactionmanager.service.transactions;

import static com.gmaniliapp.transactionmanager.utility.ZoneDateTimeUtils.generateCurrentZoneDateTime;

import com.gmaniliapp.transactionmanager.model.Transaction;
import com.gmaniliapp.transactionmanager.utility.lock.ReadWriteArrayList;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionsServiceTests {

    private static final Logger logger = LogManager.getLogger(TransactionsServiceTests.class);

    private TransactionsService transactionsService;

    private static ReadWriteArrayList<Transaction> generateTransactions() {
        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(1.11), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(2.22), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(3.33), generateCurrentZoneDateTime()));
        return transactions;
    }

    @BeforeEach
    void init() {
        transactionsService = new TransactionsService();
    }

    private void registerTransactions(ReadWriteArrayList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transactionsService.registerTransaction(transaction);
        }
    }

    @Test
    void testTransactionRegistration() {
        logger.info("testTransactionRegistration - START");

        ReadWriteArrayList<Transaction> transactions = generateTransactions();
        registerTransactions(transactions);

        Assertions.assertEquals(transactions, transactionsService.getTransactions());

        logger.info("testTransactionRegistration - END");
    }

    @Test
    void testDeletionWithoutTransactions() {
        logger.info("testDeletionWithoutTransactions - START");

        transactionsService.deleteAllTransactions();

        Assertions.assertEquals(0, transactionsService.getTransactions().size());

        logger.info("testDeletionWithoutTransactions - END");
    }

    @Test
    void testDeletionWithTransactions() {
        logger.info("testDeletionWithTransactions - START");

        registerTransactions(generateTransactions());

        transactionsService.deleteAllTransactions();

        Assertions.assertEquals(0, transactionsService.getTransactions().size());

        logger.info("testDeletionWithTransactions - END");
    }

}

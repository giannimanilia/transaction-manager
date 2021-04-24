package com.gmaniliapp.transactionmanager.service.transactions;

import com.gmaniliapp.transactionmanager.model.Transaction;
import com.gmaniliapp.transactionmanager.utility.lock.ReadWriteArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

    private static final Logger logger = LogManager.getLogger(TransactionsService.class);

    private static final ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();

    public Transaction registerTransaction(Transaction transaction) {
        logger.info("registerTransaction - START");
        logger.debug("registerTransaction - transaction = {}", transaction);

        transactions.add(transaction);

        logger.info("registerTransaction - END");
        return transaction;
    }

    public void deleteAllTransactions() {
        logger.info("deleteAllTransactions - START");

        transactions.clear();

        logger.info("deleteAllTransactions - END");
    }

    public ReadWriteArrayList<Transaction> getTransactions() {
        return transactions;
    }

}

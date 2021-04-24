package com.gmaniliapp.transactionmanager.service.statistics;

import com.gmaniliapp.transactionmanager.model.Statistics;
import com.gmaniliapp.transactionmanager.model.Transaction;
import com.gmaniliapp.transactionmanager.service.transactions.TransactionsService;
import com.gmaniliapp.transactionmanager.utility.lock.ReadWriteArrayList;
import com.gmaniliapp.transactionmanager.utility.statistics.BigDecimalSummaryStatistics;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private static final Logger logger = LogManager.getLogger(StatisticsService.class);

    private final TransactionsService transactionsService;

    public StatisticsService(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    public Statistics calculateLastMinuteStatistics() {
        logger.info("calculateLastMinuteStatistics - START");

        ReadWriteArrayList<Transaction> transactions = transactionsService.getTransactions();

        Statistics statistics = calculateLastSecondsStatistics(60, transactions);

        logger.debug("calculateLastMinuteStatistics - return = {}", statistics);
        logger.info("calculateLastMinuteStatistics - END");
        return statistics;
    }

    protected Statistics calculateLastSecondsStatistics(int seconds,
        ReadWriteArrayList<Transaction> transactions) {
        logger.info("calculateLastSecondsStatistics - START");
        logger.debug("calculateLastSecondsStatistics - seconds = {} - transactions = {}", seconds,
            transactions);

        Statistics statistics;
        try {
            transactions.lockReading();
            statistics = new Statistics(
                transactions.stream().filter(transaction -> transaction.getTimestamp()
                    .isAfter(ZonedDateTime.now(ZoneId.of("UTC")).minusSeconds(seconds)))
                    .map(Transaction::getAmount)
                    .collect(BigDecimalSummaryStatistics.calculateStatistics()));
        } finally {
            transactions.unlockReading();
        }

        logger.info("calculateLastSecondsStatistics - END");
        return statistics;
    }
}

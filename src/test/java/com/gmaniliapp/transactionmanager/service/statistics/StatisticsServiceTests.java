package com.gmaniliapp.transactionmanager.service.statistics;

import static com.gmaniliapp.transactionmanager.utility.ZoneDateTimeUtils.generateCurrentZoneDateTime;

import com.gmaniliapp.transactionmanager.model.Statistics;
import com.gmaniliapp.transactionmanager.model.Transaction;
import com.gmaniliapp.transactionmanager.service.transactions.TransactionsService;
import com.gmaniliapp.transactionmanager.utility.StatisticsUtils;
import com.gmaniliapp.transactionmanager.utility.lock.ReadWriteArrayList;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTests {

    private static final Logger logger = LogManager.getLogger(StatisticsServiceTests.class);

    @Mock
    private TransactionsService transactionsService;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void testEmptyStatistics() {
        logger.info("testEmptyStatistics - START");

        Mockito.when(transactionsService.getTransactions()).thenReturn(new ReadWriteArrayList<>());

        Statistics emptyStatistics = StatisticsUtils.generateEmptyStatistics();

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(emptyStatistics, resultStatistics);

        logger.info("testEmptyStatistics - END");
    }

    @Test
    void testStatisticsWithPositiveTransactions() {
        logger.info("testStatisticsWithPositiveTransactions - START");

        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(1.11), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(2.22), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(3.33), generateCurrentZoneDateTime()));

        Mockito.when(transactionsService.getTransactions()).thenReturn(transactions);

        Statistics statistics = new Statistics(BigDecimal.valueOf(6.66), BigDecimal.valueOf(2.22),
            BigDecimal.valueOf(3.33), BigDecimal.valueOf(1.11), 3);

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testStatisticsWithPositiveTransactions - END");
    }

    @Test
    void testStatisticsWithNegativeTransactions() {
        logger.info("testStatisticsWithNegativeTransactions - START");

        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(-1.11), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(-2.22), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(-3.33), generateCurrentZoneDateTime()));

        Mockito.when(transactionsService.getTransactions()).thenReturn(transactions);

        Statistics statistics = new Statistics(BigDecimal.valueOf(-6.66), BigDecimal.valueOf(-2.22),
            BigDecimal.valueOf(-1.11), BigDecimal.valueOf(-3.33), 3);

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testStatisticsWithNegativeTransactions - END");
    }

    @Test
    void testStatisticsWithMixedTransactions() {
        logger.info("testStatisticsWithMixedTransactions - START");

        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(-1.11), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(2.22), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(-3.33), generateCurrentZoneDateTime()));

        Mockito.when(transactionsService.getTransactions()).thenReturn(transactions);

        Statistics statistics = new Statistics(BigDecimal.valueOf(-2.22), BigDecimal.valueOf(-0.74),
            BigDecimal.valueOf(2.22), BigDecimal.valueOf(-3.33), 3);

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testStatisticsWithMixedTransactions - END");
    }

    @Test
    void testRoundingDownStatistics() {
        logger.info("testRoundingDownStatistics - START");

        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(-1.111), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(2.222), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(-3.333), generateCurrentZoneDateTime()));

        Mockito.when(transactionsService.getTransactions()).thenReturn(transactions);

        Statistics statistics = new Statistics(BigDecimal.valueOf(-2.22), BigDecimal.valueOf(-0.74),
            BigDecimal.valueOf(2.22), BigDecimal.valueOf(-3.33), 3);

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testRoundingDownStatistics - END");
    }

    @Test
    void testRoundingUpStatistics() {
        logger.info("testRoundingUpStatistics - START");

        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(-1.115), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(2.226), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(-3.337), generateCurrentZoneDateTime()));

        Mockito.when(transactionsService.getTransactions()).thenReturn(transactions);

        Statistics statistics = new Statistics(BigDecimal.valueOf(-2.23), BigDecimal.valueOf(-0.74),
            BigDecimal.valueOf(2.23), BigDecimal.valueOf(-3.34), 3);

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testRoundingUpStatistics - END");
    }

    @Test
    void testDecimalScaleStatistics() {
        logger.info("testDecimalScaleStatistics - START");

        ReadWriteArrayList<Transaction> transactions = new ReadWriteArrayList<>();
        transactions.add(new Transaction(BigDecimal.valueOf(-1.1), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(2.2), generateCurrentZoneDateTime()));
        transactions.add(new Transaction(BigDecimal.valueOf(-3.3), generateCurrentZoneDateTime()));

        Mockito.when(transactionsService.getTransactions()).thenReturn(transactions);

        Statistics statistics = new Statistics(BigDecimal.valueOf(-2.20), BigDecimal.valueOf(-0.73),
            BigDecimal.valueOf(2.20), BigDecimal.valueOf(-3.30), 3);

        Statistics resultStatistics = statisticsService.calculateLastMinuteStatistics();

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testDecimalScaleStatistics - END");
    }

}

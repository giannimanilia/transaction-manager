package com.gmaniliapp.transactionmanager.ws.rest.controller.statistics;

import com.gmaniliapp.transactionmanager.model.Statistics;
import com.gmaniliapp.transactionmanager.service.statistics.StatisticsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class StatisticsRestController {

    private static final Logger logger = LogManager.getLogger(StatisticsRestController.class);

    private final StatisticsService statisticsService;

    public StatisticsRestController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics() {
        logger.info("getStatistics - START");

        Statistics statistics = statisticsService.calculateLastMinuteStatistics();
        ResponseEntity<Statistics> responseEntity = ResponseEntity.ok(statistics);

        logger.info("getStatistics - END");
        return responseEntity;
    }

}

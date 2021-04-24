package com.gmaniliapp.transactionmanager.ws.rest.controller.statistics;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmaniliapp.transactionmanager.model.Statistics;
import com.gmaniliapp.transactionmanager.service.statistics.StatisticsService;
import com.gmaniliapp.transactionmanager.utility.StatisticsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = StatisticsRestController.class)
class StatisticsRestControllerTests {

    private static final Logger logger = LogManager.getLogger(StatisticsRestControllerTests.class);

    private static final String STATISTICS_URL_TEMPLATE = "/statistics";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    void testStatisticsWithoutTransactions() throws Exception {
        logger.info("testStatisticsWithoutTransactions - START");

        Statistics statistics = StatisticsUtils.generateEmptyStatistics();

        Mockito.when(statisticsService.calculateLastMinuteStatistics()).thenReturn(statistics);

        MvcResult mvcResult = mockMvc.perform(get(STATISTICS_URL_TEMPLATE))
            .andExpect(status().isOk())
            .andReturn();

        Statistics resultStatistics =
            objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Statistics.class);

        Assertions.assertEquals(statistics, resultStatistics);

        logger.info("testStatisticsWithoutTransactions - END");
    }

}

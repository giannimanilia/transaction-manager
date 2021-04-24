package com.gmaniliapp.transactionmanager.ws.rest.controller.transactions;

import static com.gmaniliapp.transactionmanager.utility.ZoneDateTimeUtils.generateCurrentZoneDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmaniliapp.transactionmanager.model.Transaction;
import com.gmaniliapp.transactionmanager.service.transactions.TransactionsService;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = TransactionsRestController.class)
class TransactionsRestControllerTests {

    private static final Logger logger = LogManager.getLogger(TransactionsRestController.class);

    private static final String TRANSACTIONS_URL_TEMPLATE = "/transactions";

    private static final String APPLICATION_JSON_CONTENT_TYPE = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionsService transactionsService;

    private static void assertResponseIsEmpty(MockHttpServletResponse mockHttpServletResponse)
        throws UnsupportedEncodingException {
        Assertions.assertEquals("", mockHttpServletResponse.getContentAsString());
    }

    @Test
    void testEmptyAmountInTransactionCreationBody() throws Exception {
        logger.info("testEmptyAmountInTransactionCreationBody - START");

        Transaction transaction = new Transaction(null, generateCurrentZoneDateTime());

        MvcResult mvcResult = mockMvc
            .perform(post(TRANSACTIONS_URL_TEMPLATE).contentType(APPLICATION_JSON_CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(transaction)))
            .andExpect(status().isUnprocessableEntity()).andReturn();

        assertResponseIsEmpty(mvcResult.getResponse());

        logger.info("testEmptyAmountInTransactionCreationBody - END");
    }

    @Test
    void testEmptyTimestampInTransactionCreationBody() throws Exception {
        logger.info("testEmptyTimestampInTransactionCreationBody - START");

        Transaction transaction = new Transaction(BigDecimal.ONE, null);

        MvcResult mvcResult = mockMvc
            .perform(post(TRANSACTIONS_URL_TEMPLATE).contentType(APPLICATION_JSON_CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(transaction)))
            .andExpect(status().isUnprocessableEntity()).andReturn();

        assertResponseIsEmpty(mvcResult.getResponse());

        logger.info("testEmptyTimestampInTransactionCreationBody - END");
    }

    @Test
    void testFutureTimestampInTransactionCreationBody() throws Exception {
        logger.info("testFutureTimestampInTransactionCreationBody - START");

        Transaction transaction = new Transaction(BigDecimal.ONE,
            generateCurrentZoneDateTime().plusSeconds(1));

        MvcResult mvcResult = mockMvc
            .perform(post(TRANSACTIONS_URL_TEMPLATE).contentType(APPLICATION_JSON_CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(transaction)))
            .andExpect(status().isUnprocessableEntity()).andReturn();

        assertResponseIsEmpty(mvcResult.getResponse());

        logger.info("testFutureTimestampInTransactionCreationBody - END");
    }

    @Test
    void testTransactionCreationResponseAndStatus() throws Exception {
        logger.info("testTransactionCreationResponseAndStatus - START");

        Transaction transaction = new Transaction(BigDecimal.ONE, generateCurrentZoneDateTime());

        Mockito.when(transactionsService.registerTransaction(Mockito.any(Transaction.class)))
            .thenReturn(transaction);

        MvcResult mvcResult =
            mockMvc
                .perform(post(TRANSACTIONS_URL_TEMPLATE).contentType(APPLICATION_JSON_CONTENT_TYPE)
                    .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk()).andReturn();

        Transaction resultTransaction =
            objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Transaction.class);

        Assertions.assertEquals(transaction, resultTransaction);

        logger.info("testTransactionCreationResponseAndStatus - END");
    }

    @Test
    void testTransactionDeletionResponseAndStatus() throws Exception {
        logger.info("testTransactionDeletionResponseAndStatus - START");

        Mockito.doNothing().when(transactionsService).deleteAllTransactions();

        MvcResult mvcResult =
            mockMvc.perform(delete(TRANSACTIONS_URL_TEMPLATE)).andExpect(status().isNoContent())
                .andReturn();

        assertResponseIsEmpty(mvcResult.getResponse());

        logger.info("testTransactionDeletionResponseAndStatus - END");
    }

}

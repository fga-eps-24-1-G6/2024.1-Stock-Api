package com.stocksapi.controller;

import com.stocksapi.service.MockMvcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StockControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvcService mockMvcService;

    private final String TICKER = "petr4";

    @Test
    void shouldGetStocksByTicker() throws Exception {



        ;
        mockMvcService.get("/api/stocks/stock-summary/%s".replace("%s", TICKER))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ticker").value("petr4"))
                .andExpect(jsonPath("$.currentPrice").value(27.96))
                .andExpect(jsonPath("$.companyName").value("PETROLEO BRASILEIRO S.A. PETROBRAS"))
                .andExpect(jsonPath("$.freeFloat").value(63.33))
                .andExpect(jsonPath("$.tagAlong").value(100.00))
                .andExpect(jsonPath("$.avgDailyLiquidity").value(1617781000))
                .andExpect(jsonPath("$.categorie").value("SMALL"))
                .andExpect(jsonPath("$.variationOneDay").value(3.3000))
                .andExpect(jsonPath("$.variationOneMonth").value(11.0000))
                .andExpect(jsonPath("$.variationTwelveMonths").value(28.8300));
    }
}

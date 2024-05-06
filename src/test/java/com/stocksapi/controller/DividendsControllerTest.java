package com.stocksapi.controller;

import com.stocksapi.service.MockMvcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DividendsControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvcService mockMvcService;

    private final String TICKER = "petr4";

    private final String INVALID_TICKER = "4trep";

    @Test
    void shouldGetDividendsByTicker() throws Exception {
        mockMvcService.get("/api/dividends/%s".replace("%s", TICKER))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dividendYieldResponse.current").value(0.0196521538))
                .andExpect(jsonPath("$.dividendYieldResponse.avgFiveYears").value(0.0196521538))
                .andExpect(jsonPath("$.dividendYieldResponse.avgTenYears").value(0.0196521538))
                .andExpect(jsonPath("$.paymentMonthResponseList[0].month").value("May"))
                .andExpect(jsonPath("$.paymentMonthResponseList[0].frequency").value(0.54947422))
                .andExpect(jsonPath("$.yearlyPaymentResponseList[0].year").value(2024))
                .andExpect(jsonPath("$.yearlyPaymentResponseList[0].frequency").value(0.54947422))
                .andExpect(jsonPath("$.dividendsResponseList[0].type").value("Dividendos"))
                .andExpect(jsonPath("$.dividendsResponseList[0].payment").value(0.54947422))
                .andExpect(jsonPath("$.dividendsResponseList[0].ownershipDate").value("2024-04-25"))
                .andExpect(jsonPath("$.dividendsResponseList[0].paymentDate").value("2024-05-20"));
    }

    @Test
    void shouldNotGetDividendsByTicker() throws Exception {
        mockMvcService.get("/api/dividends/%s".replace("%s", INVALID_TICKER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find dividends with ticker: %s".replace("%s", INVALID_TICKER))));
    }

}

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

public class StockControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvcService mockMvcService;

    private final String TICKER = "petr4";

    private final String INVALID_TICKER = "4trep";

    @Test
    void shouldGetStocksByTicker() throws Exception {
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

    @Test
    void shouldNotGetStocksByTicker() throws Exception {
        mockMvcService.get("/api/stocks/stock-summary/%s".replace("%s", INVALID_TICKER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find stocks with ticker: %s".replace("%s", INVALID_TICKER))));
    }

    @Test
    void shouldGetIndicatorsByTicker() throws Exception {
        mockMvcService.get("/api/stocks/indicators/%s".replace("%s", TICKER))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.indicatorValueResponseList.[0].indicator").value("P/L"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[0].value").value(2.9139231753))
                .andExpect(jsonPath("$.indicatorValueResponseList.[1].indicator").value("P/VP"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[1].value").value(0.9539261081))
                .andExpect(jsonPath("$.indicatorValueResponseList.[2].indicator").value("DIV YIELD"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[2].value").value(0.0196521538))
                .andExpect(jsonPath("$.indicatorValueResponseList.[3].indicator").value("PAYOUT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[3].value").value(0E-10))
                .andExpect(jsonPath("$.indicatorValueResponseList.[4].indicator").value("MARGEM LÍQ"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[4].value").value(0.2444677086))
                .andExpect(jsonPath("$.indicatorValueResponseList.[5].indicator").value("MARGEM BRUTA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[5].value").value(0.5272190690))
                .andExpect(jsonPath("$.indicatorValueResponseList.[6].indicator").value("MARGEM EBIT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[6].value").value(0.3698129275))
                .andExpect(jsonPath("$.indicatorValueResponseList.[7].indicator").value("EV/EBIT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[7].value").value(1.9262715518))
                .andExpect(jsonPath("$.indicatorValueResponseList.[8].indicator").value("EV/EBITDA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[8].value").value(1.4272346590))
                .andExpect(jsonPath("$.indicatorValueResponseList.[9].indicator").value("VPA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[9].value").value(29.3104463369))
                .andExpect(jsonPath("$.indicatorValueResponseList.[10].indicator").value("LPA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[10].value").value(9.5953113098))
                .andExpect(jsonPath("$.indicatorValueResponseList.[11].indicator").value("ROE"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[11].value").value(0.3273683109))
                .andExpect(jsonPath("$.indicatorValueResponseList.[12].indicator").value("ROIC"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[12].value").value(0.1801733391))
                .andExpect(jsonPath("$.indicatorValueResponseList.[13].indicator").value("ROA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[13].value").value(0.1191049855))
                .andExpect(jsonPath("$.indicatorValueResponseList.[14].indicator").value("CAGR LUCRO"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[14].value").value(new BigDecimal("15666527556000000000000")))
                .andExpect(jsonPath("$.indicatorValueResponseList.[15].indicator").value("CAGR REC"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[15].value").value(new BigDecimal("262137856036000000000000")))
                .andExpect(jsonPath("$.indicatorValueResponseList.[16].indicator").value("DÍV LÍQ/PAT LÍQ"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[16].value").value("0.5958021656"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[17].indicator").value("DÍV LÍQ/EBIT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[17].value").value("1.2031086605"));
    }

    @Test
    void shouldNotGetIndicatorsByTicker() throws Exception {
        mockMvcService.get("/api/stocks/indicators/%s".replace("%s", INVALID_TICKER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find stocks with ticker: %s".replace("%s", INVALID_TICKER))));
    }
}

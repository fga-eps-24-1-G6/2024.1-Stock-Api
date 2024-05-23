package com.stocksapi.controller;

import com.stocksapi.service.MockMvcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
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
                .andExpect(jsonPath("$.dividends[0].stocks.id").value(4))
                .andExpect(jsonPath("$.dividends[0].stocks.ticker").value("petr4"))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.id").value(1))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.name").value("PETROLEO BRASILEIRO S.A. PETROBRAS"))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.cnpj").value("33000167000101"))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.ipo").value(1977))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.foundationYear").value(1953))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.firmValue").value(new BigDecimal("754871726000")))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.numberOfPapers").value(new BigDecimal("13044496000")))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.marketSegment").value("Nível 2"))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.sector").value("Empresas do Setor Petróleo, Gás e Biocombustíveis"))
                .andExpect(jsonPath("$.dividends[0].stocks.companies.segment").value("Empresas do Segmento Exploração  Refino e Distribuição"))
                .andExpect(jsonPath("$.dividends[0].stocks.freeFloat").value(63.33))
                .andExpect(jsonPath("$.dividends[0].stocks.tagAlong").value(100))
                .andExpect(jsonPath("$.dividends[0].type").value("Dividendos"))
                .andExpect(jsonPath("$.dividends[0].value").value(0.54947422))
                .andExpect(jsonPath("$.dividends[0].ownershipDate").value("2024-04-25"))
                .andExpect(jsonPath("$.dividends[0].paymentDate").value("2024-05-23"))
                .andExpect(jsonPath("$.dividends[0].id").value(1))
                .andExpect(jsonPath("$.yearlyPayments.2024").value(0.54947422))
                .andExpect(jsonPath("$.paymentMonths.MAY").value(100.0))
                .andExpect(jsonPath("$.dividendYield.dividendYieldLastTenYears").value(0.00))
                .andExpect(jsonPath("$.dividendYield.dividendYieldCurrent").value(0.02))
                .andExpect(jsonPath("$.dividendYield.dividendYieldLastFiveYears").value(0.00));
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

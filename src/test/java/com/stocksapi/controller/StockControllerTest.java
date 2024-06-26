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
                .andExpect(jsonPath("$.currentPrice").isNumber())
                .andExpect(jsonPath("$.companyName").value("PETROLEO BRASILEIRO S.A. PETROBRAS"))
                .andExpect(jsonPath("$.freeFloat").isNumber())
                .andExpect(jsonPath("$.tagAlong").isNumber())
                .andExpect(jsonPath("$.avgDailyLiquidity").isNumber())
                .andExpect(jsonPath("$.categorie").value("SMALL"))
                .andExpect(jsonPath("$.variationOneDay").isNumber())
                .andExpect(jsonPath("$.variationOneMonth").isNumber())
                .andExpect(jsonPath("$.variationTwelveMonths").isNumber());
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
                .andExpect(jsonPath("$.indicatorValueResponseList.[0].indicator").value("LPA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[0].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[1].indicator").value("P/L"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[1].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[2].indicator").value("VPA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[2].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[3].indicator").value("P/VP"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[3].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[4].indicator").value("DIV YIELD"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[4].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[5].indicator").value("PAYOUT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[5].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[6].indicator").value("MARGEM LÍQ"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[6].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[7].indicator").value("MARGEM BRUTA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[7].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[8].indicator").value("MARGEM EBIT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[8].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[9].indicator").value("EV/EBIT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[9].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[10].indicator").value("EV/EBITDA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[10].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[11].indicator").value("ROE"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[11].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[12].indicator").value("ROIC"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[12].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[13].indicator").value("ROA"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[13].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[14].indicator").value("DÍV LÍQ/PAT LÍQ"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[14].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[15].indicator").value("DÍV LÍQ/EBIT"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[15].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[16].indicator").value("CAGR LUCRO"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[16].value").isNumber())

                .andExpect(jsonPath("$.indicatorValueResponseList.[17].indicator").value("CAGR REC"))
                .andExpect(jsonPath("$.indicatorValueResponseList.[17].value").isNumber());
    }

    @Test
    void shouldNotGetIndicatorsByTicker() throws Exception {
        mockMvcService.get("/api/stocks/indicators/%s".replace("%s", INVALID_TICKER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find stocks with ticker: %s".replace("%s", INVALID_TICKER))));
    }

    @Test
    void shouldGetValuationByTicker() throws Exception {
        mockMvcService.get("/api/stocks/valuation/%s".replace("%s", TICKER))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.targetPriceResponse.method").value("PREÇO ALVO"))
                .andExpect(jsonPath("$.targetPriceResponse.value").isNumber())
                .andExpect(jsonPath("$.ceilingPriceResponse.method").value("PREÇO TETO"))
                .andExpect(jsonPath("$.ceilingPriceResponse.value").isNumber());
    }

    @Test
    void shouldNotGetValuationByTicker() throws Exception {
        mockMvcService.get("/api/stocks/valuation/%s".replace("%s", INVALID_TICKER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find stocks with ticker: %s".replace("%s", INVALID_TICKER))));
    }

    @Test
    void shouldSearchStocks() throws Exception {
        mockMvcService.get("/api/stocks/search?ticker=%s".replace("%s", TICKER))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].ticker").value("petr4"))
                .andExpect(jsonPath("$[0].companyId").value(1))
                .andExpect(jsonPath("$[0].companyName").value("PETROLEO BRASILEIRO S.A. PETROBRAS"))
                .andExpect(jsonPath("$[0].lastPrice").isNumber())
                .andExpect(jsonPath("$[0].variationOneDay").isNumber())
                .andExpect(jsonPath("$[0].sector").value("Empresas do Setor Petróleo, Gás e Biocombustíveis"));
    }

    @Test
    void shouldNotSearchStocks() throws Exception {
        mockMvcService.get("/api/stocks/search?ticker=%s".replace("%s", INVALID_TICKER))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find stocks with ticker: %s".replace("%s", INVALID_TICKER))));
    }

}

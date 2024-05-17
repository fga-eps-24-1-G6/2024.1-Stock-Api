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

public class BalanceSheetControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvcService mockMvcService;

    private final String COMPANY_ID = "1";

    private final String INVALID_COMPANY_ID = "0";

    @Test
    void shouldGetBalanceSheetByCompanyId() throws Exception {
        mockMvcService.get("/api/balance-sheet/%s".replace("%s", COMPANY_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].item").value("Margem Líquida"))
                .andExpect(jsonPath("$[0].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[0].yearlyValues.[0].value").value(new BigDecimal("0.2444677086")))
                .andExpect(jsonPath("$[0].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[0].yearlyValues.[1].value").value(new BigDecimal("0.2444677086")))

                .andExpect(jsonPath("$[1].item").value("Receita Líquida"))
                .andExpect(jsonPath("$[1].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[1].yearlyValues.[0].value").value(new BigDecimal("511994000000")))
                .andExpect(jsonPath("$[1].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[1].yearlyValues.[1].value").value(new BigDecimal("511994000000")))

                .andExpect(jsonPath("$[2].item").value("Lucro Líquido"))
                .andExpect(jsonPath("$[2].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[2].yearlyValues.[0].value").value(new BigDecimal("125166000000")))
                .andExpect(jsonPath("$[2].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[2].yearlyValues.[1].value").value(new BigDecimal("125166000000")))

                .andExpect(jsonPath("$[3].item").value("Dívida Líquida"))
                .andExpect(jsonPath("$[3].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[3].yearlyValues.[0].value").value(new BigDecimal("227799000000")))
                .andExpect(jsonPath("$[3].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[3].yearlyValues.[1].value").value(new BigDecimal("227799000000")))

                .andExpect(jsonPath("$[4].item").value("Impostos"))
                .andExpect(jsonPath("$[4].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[4].yearlyValues.[0].value").value(new BigDecimal("-52315000000")))
                .andExpect(jsonPath("$[4].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[4].yearlyValues.[1].value").value(new BigDecimal("-52315000000")))

                .andExpect(jsonPath("$[5].item").value("EBITDA"))
                .andExpect(jsonPath("$[5].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[5].yearlyValues.[0].value").value(new BigDecimal("255546000000")))
                .andExpect(jsonPath("$[5].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[5].yearlyValues.[1].value").value(new BigDecimal("255546000000")))

                .andExpect(jsonPath("$[6].item").value("Margem Bruta"))
                .andExpect(jsonPath("$[6].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[6].yearlyValues.[0].value").value(0.5272190690))
                .andExpect(jsonPath("$[6].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[6].yearlyValues.[1].value").value(0.5272190690))

                .andExpect(jsonPath("$[7].item").value("EBIT"))
                .andExpect(jsonPath("$[7].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[7].yearlyValues.[0].value").value(new BigDecimal("189342000000")))
                .andExpect(jsonPath("$[7].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[7].yearlyValues.[1].value").value(new BigDecimal("189342000000")))

                .andExpect(jsonPath("$[8].item").value("Custos"))
                .andExpect(jsonPath("$[8].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[8].yearlyValues.[0].value").value(new BigDecimal("-242061000000")))
                .andExpect(jsonPath("$[8].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[8].yearlyValues.[1].value").value(new BigDecimal("-242061000000")))

                .andExpect(jsonPath("$[9].item").value("Lucro Bruto"))
                .andExpect(jsonPath("$[9].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[9].yearlyValues.[0].value").value(new BigDecimal("269933000000")))
                .andExpect(jsonPath("$[9].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[9].yearlyValues.[1].value").value(new BigDecimal("269933000000")))

                .andExpect(jsonPath("$[10].item").value("Dívida Bruta"))
                .andExpect(jsonPath("$[10].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[10].yearlyValues.[0].value").value(new BigDecimal("303062000000")))
                .andExpect(jsonPath("$[10].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[10].yearlyValues.[1].value").value(new BigDecimal("303062000000")))

                .andExpect(jsonPath("$[11].item").value("Ativos"))
                .andExpect(jsonPath("$[11].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[11].yearlyValues.[0].value").value(new BigDecimal("1050888000000")))
                .andExpect(jsonPath("$[11].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[11].yearlyValues.[1].value").value(new BigDecimal("1050888000000")))

                .andExpect(jsonPath("$[12].item").value("Patrimônio Líquido"))
                .andExpect(jsonPath("$[12].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[12].yearlyValues.[0].value").value(new BigDecimal("382340000000")))
                .andExpect(jsonPath("$[12].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[12].yearlyValues.[1].value").value(new BigDecimal("382340000000")))

                .andExpect(jsonPath("$[13].item").value("Passivos"))
                .andExpect(jsonPath("$[13].yearlyValues.[0].year").value(2023))
                .andExpect(jsonPath("$[13].yearlyValues.[0].value").value(new BigDecimal("1050888000000")))
                .andExpect(jsonPath("$[13].yearlyValues.[1].year").value(2023))
                .andExpect(jsonPath("$[13].yearlyValues.[1].value").value(new BigDecimal("1050888000000")));
    }

    @Test
    void shouldNotGetBalanceSheetByCompanyId() throws Exception {
        mockMvcService.get("/api/balance-sheet/%s".replace("%s", INVALID_COMPANY_ID))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not Balance Sheets with company id: %s".replace("%s", INVALID_COMPANY_ID))));
    }


}

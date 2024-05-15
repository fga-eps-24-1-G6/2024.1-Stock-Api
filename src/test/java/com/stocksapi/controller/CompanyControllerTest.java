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

public class CompanyControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvcService mockMvcService;

    private final String COMPANY_ID = "1";

    private final String INVALID_COMPANY_ID = "0";

    @Test
    void shouldGetCompanyById() throws Exception {
        mockMvcService.get("/api/company/company-info/%s".replace("%s", COMPANY_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ipo").value(1977))
                .andExpect(jsonPath("$.sector").value("Empresas do Setor Petróleo, Gás e Biocombustíveis"))
                .andExpect(jsonPath("$.segment").value("Empresas do Segmento Exploração  Refino e Distribuição"))
                .andExpect(jsonPath("$.marketValue").value(364724108160.00))
                .andExpect(jsonPath("$.equity").value(new BigDecimal("382340000000")))
                .andExpect(jsonPath("$.numberOfPapers").value(new BigDecimal("13044496000")));
    }

    @Test
    void shouldNotGetCompanyById() throws Exception {
        mockMvcService.get("/api/company/company-info/%s".replace("%s", INVALID_COMPANY_ID))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(404))
                .andExpect(jsonPath("$.message", is("Could not find company with id: %s".replace("%s", INVALID_COMPANY_ID))));
    }
}

package com.stocksapi.service;

import com.stocksapi.dto.BalanceSheetsResponse;
import com.stocksapi.dto.YearlyValuesResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.repository.BalanceSheetsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceSheetsService {
    private final BalanceSheetsRepository balanceSheetsRepository;

    public BalanceSheetsService(BalanceSheetsRepository balanceSheetsRepository) {
        this.balanceSheetsRepository = balanceSheetsRepository;
    }

    public List<BalanceSheetsResponse> getByCompanyId(Integer companyId) {
        List<BalanceSheet> allByCompaniesId = balanceSheetsRepository.findAllByCompaniesId(companyId);
        if (!allByCompaniesId.isEmpty()){
            List<BalanceSheetsResponse> balanceSheetsResponseList = new ArrayList<BalanceSheetsResponse>();
            List<YearlyValuesResponse> netRevenueList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> costsList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> grossProfitList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> netProfitList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> ebitdaList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> ebitList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> taxesList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> grossDebtList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> netDebtList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> grossMarginList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> netMarginList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> equityList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> assetsList = new ArrayList<YearlyValuesResponse>();
            List<YearlyValuesResponse> liabilitiesList = new ArrayList<YearlyValuesResponse>();

            for (BalanceSheet balanceSheet : allByCompaniesId){

                YearlyValuesResponse netRevenueResponse = new YearlyValuesResponse(balanceSheet.getNetRevenue(), balanceSheet.getId().getYear());
                netRevenueList.add(netRevenueResponse);
                BalanceSheetsResponse balanceSheetsNetRevenueResponse = new BalanceSheetsResponse("Receita Líquida", netRevenueList);
                balanceSheetsResponseList.add(balanceSheetsNetRevenueResponse);

                YearlyValuesResponse costsResponse = new YearlyValuesResponse(balanceSheet.getCosts(), balanceSheet.getId().getYear());
                costsList.add(costsResponse);
                BalanceSheetsResponse balanceSheetsCostsResponse = new BalanceSheetsResponse("Custos", costsList);
                balanceSheetsResponseList.add(balanceSheetsCostsResponse);

                YearlyValuesResponse grossProfitResponse = new YearlyValuesResponse(balanceSheet.getGrossProfit(), balanceSheet.getId().getYear());
                grossProfitList.add(grossProfitResponse);
                BalanceSheetsResponse balanceSheetsGrossProfitResponse = new BalanceSheetsResponse("Lucro Bruto", grossProfitList);
                balanceSheetsResponseList.add(balanceSheetsGrossProfitResponse);

                YearlyValuesResponse netProfitResponse = new YearlyValuesResponse(balanceSheet.getNetProfit(), balanceSheet.getId().getYear());
                netProfitList.add(netProfitResponse);
                BalanceSheetsResponse balanceSheetsNetProfitResponse = new BalanceSheetsResponse("Lucro Líquido", netProfitList);
                balanceSheetsResponseList.add(balanceSheetsNetProfitResponse);

                YearlyValuesResponse ebitdaResponse = new YearlyValuesResponse(balanceSheet.getEbitda(), balanceSheet.getId().getYear());
                ebitdaList.add(ebitdaResponse);
                BalanceSheetsResponse balanceSheetsEbitdaResponse = new BalanceSheetsResponse("EBITDA", ebitdaList);
                balanceSheetsResponseList.add(balanceSheetsEbitdaResponse);

                YearlyValuesResponse ebitResponse = new YearlyValuesResponse(balanceSheet.getEbit(), balanceSheet.getId().getYear());
                ebitList.add(ebitResponse);
                BalanceSheetsResponse balanceSheetsEbitResponse = new BalanceSheetsResponse("EBIT", ebitList);
                balanceSheetsResponseList.add(balanceSheetsEbitResponse);

                YearlyValuesResponse taxesResponse = new YearlyValuesResponse(balanceSheet.getTaxes(), balanceSheet.getId().getYear());
                taxesList.add(taxesResponse);
                BalanceSheetsResponse balanceSheetsTaxesResponse = new BalanceSheetsResponse("Impostos", taxesList);
                balanceSheetsResponseList.add(balanceSheetsTaxesResponse);

                YearlyValuesResponse grossDebtResponse = new YearlyValuesResponse(balanceSheet.getGrossDebt(), balanceSheet.getId().getYear());
                grossDebtList.add(grossDebtResponse);
                BalanceSheetsResponse balanceSheetsGrossDebtResponse = new BalanceSheetsResponse("Dívida Bruta", grossDebtList);
                balanceSheetsResponseList.add(balanceSheetsGrossDebtResponse);

                YearlyValuesResponse netDebtResponse = new YearlyValuesResponse(balanceSheet.getNetDebt(), balanceSheet.getId().getYear());
                netDebtList.add(netDebtResponse);
                BalanceSheetsResponse balanceSheetsNetDebtResponse = new BalanceSheetsResponse("Dívida Líquida", netDebtList);
                balanceSheetsResponseList.add(balanceSheetsNetDebtResponse);

                int scale = 10;
                RoundingMode roundingMode = RoundingMode.HALF_UP;
                BigDecimal grossMargin = balanceSheet.getGrossProfit().divide(balanceSheet.getNetRevenue(), scale, roundingMode);
                YearlyValuesResponse grossMarginResponse = new YearlyValuesResponse(grossMargin, balanceSheet.getId().getYear());
                grossMarginList.add(grossMarginResponse);
                BalanceSheetsResponse balanceSheetsGrossMarginResponse = new BalanceSheetsResponse("Margem Bruta", grossMarginList);
                balanceSheetsResponseList.add(balanceSheetsGrossMarginResponse);

                BigDecimal netMargin = balanceSheet.getNetProfit().divide(balanceSheet.getNetRevenue(), scale, roundingMode);
                YearlyValuesResponse netMarginResponse = new YearlyValuesResponse(netMargin, balanceSheet.getId().getYear());
                netMarginList.add(netMarginResponse);
                BalanceSheetsResponse balanceSheetsNetMarginResponse = new BalanceSheetsResponse("Margem Líquida", netMarginList);
                balanceSheetsResponseList.add(balanceSheetsNetMarginResponse);

                YearlyValuesResponse equityResponse = new YearlyValuesResponse(balanceSheet.getEquity(), balanceSheet.getId().getYear());
                equityList.add(equityResponse);
                BalanceSheetsResponse balanceSheetsEquityResponse = new BalanceSheetsResponse("Patrimônio Líquido", equityList);
                balanceSheetsResponseList.add(balanceSheetsEquityResponse);

                YearlyValuesResponse assetsResponse = new YearlyValuesResponse(balanceSheet.getAssets(), balanceSheet.getId().getYear());
                assetsList.add(assetsResponse);
                BalanceSheetsResponse balanceSheetsAssetsResponse = new BalanceSheetsResponse("Ativos", assetsList);
                balanceSheetsResponseList.add(balanceSheetsAssetsResponse);

                YearlyValuesResponse liabilitiesResponse = new YearlyValuesResponse(balanceSheet.getLiabilities(), balanceSheet.getId().getYear());
                liabilitiesList.add(liabilitiesResponse);
                BalanceSheetsResponse balanceSheetsLiabilitiesResponse = new BalanceSheetsResponse("Passivos", liabilitiesList);
                balanceSheetsResponseList.add(balanceSheetsLiabilitiesResponse);
            }

            return balanceSheetsResponseList;

        } else {
            throw new BadRequestNotFoundException(404, "Could not find Balanace Sheets from Company with id: " + companyId);
        }

    }
}

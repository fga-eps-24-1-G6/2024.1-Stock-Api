package com.stocksapi.dto;

import com.stocksapi.model.Dividends;

import java.util.List;

public class DividendsWithDividendYieldResponse {
    private DividendYieldResponse dividendYieldResponse;
    private List<PaymentMonthResponse> paymentMonthResponseList;
    private List<YearlyPaymentResponse> yearlyPaymentResponseList;
    private List<DividendsResponse> dividendsResponseList;

    public DividendsWithDividendYieldResponse(List<DividendsResponse> dividendsResponseList, DividendYieldResponse dividendYieldResponse, List<PaymentMonthResponse> paymentMonthResponseList, List<YearlyPaymentResponse> yearlyPaymentResponseList) {
        this.dividendsResponseList = dividendsResponseList;
        this.dividendYieldResponse = dividendYieldResponse;
        this.paymentMonthResponseList = paymentMonthResponseList;
        this.yearlyPaymentResponseList = yearlyPaymentResponseList;
    }

    public List<DividendsResponse> getDividendsResponseList() {
        return dividendsResponseList;
    }

    public DividendYieldResponse getDividendYieldResponse() {
        return dividendYieldResponse;
    }

    public List<PaymentMonthResponse> getPaymentMonthResponseList() {
        return paymentMonthResponseList;
    }

    public List<YearlyPaymentResponse> getYearlyPaymentResponseList() {
        return yearlyPaymentResponseList;
    }
}

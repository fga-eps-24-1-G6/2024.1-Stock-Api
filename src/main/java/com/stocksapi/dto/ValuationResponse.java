package com.stocksapi.dto;

import java.util.ArrayList;
import java.util.List;

public class ValuationResponse {
    List<ValuationValueResponse> valuationValueResponseList = new ArrayList<ValuationValueResponse>();

    public ValuationResponse(List<ValuationValueResponse> valuationValueResponseList) {
        this.valuationValueResponseList = valuationValueResponseList;
    }

    public List<ValuationValueResponse> getValuationValueResponseList() {
        return valuationValueResponseList;
    }

}

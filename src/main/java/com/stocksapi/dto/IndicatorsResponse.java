package com.stocksapi.dto;

import java.util.ArrayList;
import java.util.List;

public class IndicatorsResponse {
    List<IndicatorValueResponse> indicatorValueResponseList = new ArrayList<IndicatorValueResponse>();

    public IndicatorsResponse(List<IndicatorValueResponse> indicatorValueResponseList) {
        this.indicatorValueResponseList = indicatorValueResponseList;
    }

    public List<IndicatorValueResponse> getIndicatorValueResponseList() {
        return indicatorValueResponseList;
    }
}

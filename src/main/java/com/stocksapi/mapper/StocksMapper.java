package com.stocksapi.mapper;

import com.stocksapi.dto.StocksResponse;
import com.stocksapi.model.Stocks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StocksMapper {
    StocksResponse stocksToStocksResponse(Stocks stocks);

}

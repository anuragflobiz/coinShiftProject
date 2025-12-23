package com.coinShiftProject.coinShiftProject.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateResponse {
    private Map<String, Double> rates;
}

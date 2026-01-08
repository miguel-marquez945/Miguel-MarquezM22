package com.example.model;

import com.example.service.ExchangeRateService;

public class Api {

    private final ExchangeRateService service = new ExchangeRateService();

    public double obtenerEurUsd() {
        return service.eurUsd();
    }
}

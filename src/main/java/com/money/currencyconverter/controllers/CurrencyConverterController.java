package com.money.currencyconverter.controllers;

import com.money.currencyconverter.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/currencies") //открывается почему-то все равно в корне
public class CurrencyConverterController {

    @Autowired
    CurrencyService currencyService;

    //метод для возврата курса валют
    @GetMapping
    public String getCurrencies () {
        return currencyService.getCurrencyString();
    }
}

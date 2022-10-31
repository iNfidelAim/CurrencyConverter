package com.money.currencyconverter.models;
//import java.util.Currency;
import java.util.List;

public class DataAPI {
    private List<Currency> currencies;
    private List<CurrencyRate> currencyRates;

    public DataAPI(List<Currency> currencies, List<CurrencyRate> currencyRates) {
        this.currencies = currencies;
        this.currencyRates = currencyRates;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<CurrencyRate> getCurrencyRates() {

        return currencyRates;
    }
}






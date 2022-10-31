package com.money.currencyconverter.repositories;
import com.money.currencyconverter.models.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
    Currency findByCharCode(String charCode);
}
package com.money.currencyconverter.repositories;
import com.money.currencyconverter.controllers.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, String> {
    Currency findByCharCode(String charCode);
}
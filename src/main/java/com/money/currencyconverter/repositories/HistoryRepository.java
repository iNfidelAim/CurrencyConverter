package com.money.currencyconverter.repositories;
import com.money.currencyconverter.models.Conversion;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends CrudRepository<Conversion, Long> {
    List<Conversion> findByFirstCurrencyAndSecondCurrencyAndDate(String firstCurrency, String secondCurrency, LocalDate date);
}
package com.money.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CurrencyConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
	@Bean
	ApplicationRunner init(CurrencyRepository repository, CurrencyRateRepository rateRepository){
		//При запуске приложения необходимо получить список актуальных валют и их курсов с сайта ЦБРФ
		//и записать их в базу данных а также курсы (привязанные к валюте) на дату запроса.
		DataFromXML data = XMLService.parseRates();
		return args -> {
			repository.save(new Currency("1", "111", "RUB", 1, "Российский рубль"));
			for (Currency c:data.getCurrencies())
				repository.save(c);
			rateRepository.save(new CurrencyRate("1", null, "RUB", 1.0));
			for (CurrencyRate cr:data.getCurrencyRates())
				rateRepository.save(cr);
		};
	}

}
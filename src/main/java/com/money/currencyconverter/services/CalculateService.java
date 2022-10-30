package com.money.currencyconverter.services;
import com.money.currencyconverter.controllers.Conversion;
import com.money.currencyconverter.models.CurrencyRate;
import com.money.currencyconverter.models.DataAPI;
import com.money.currencyconverter.repositories.CurrencyRateRepository;
import com.money.currencyconverter.repositories.CurrencyRepository;
import com.money.currencyconverter.repositories.HistoryRepository;
import com.money.currencyconverter.controllers.Currency;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Сервис для вычисления конвертации
@Service("calculateService")
public class CalculateService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final HistoryRepository historyRepository;

    public CalculateService(CurrencyRepository currencyRepository, CurrencyRateRepository currencyRateRepository,
                            HistoryRepository historyRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyRateRepository = currencyRateRepository;
        this.historyRepository = historyRepository;
    }
    public double  calculateValue( String firstCurrency,  String secondCurrency, double amount) {
        //Получение информации о валютах из таблицы валют БД
        Currency c1 = currencyRepository.findByCharCode(firstCurrency);
        int n1 = c1.getNominal();
        Currency c2 = currencyRepository.findByCharCode(secondCurrency);
        int n2 = c2.getNominal();
        LocalDate currentDate = LocalDate.now();
        CurrencyRate cr1 = null, cr2 = null;
        DataAPI data;
        //Получение курса валют из БД на сегодняшний день
        Optional<CurrencyRate> ocr1 = currencyRateRepository.findByDateAndCharCode(currentDate, firstCurrency);
        if (ocr1.isPresent()) {
            cr1 = ocr1.get();
        }
        else if (firstCurrency.equals("RUB")) {
            cr1 = currencyRateRepository.findByCharCode("RUB");
        }
        Optional<CurrencyRate> ocr2 = currencyRateRepository.findByDateAndCharCode(currentDate, secondCurrency);
        if (ocr2.isPresent()) {
            cr2 = ocr2.get();
        }

        else if (secondCurrency.equals("RUB")) {
            cr2 = currencyRateRepository.findByCharCode("RUB");
        }
        //запрос в БД на получение актуального курса на текущую дату. Если данные на текущую
        //дату отсутствуют, то  получим курсы с сайта ЦБ и добавим новые записи в БД.
        if (cr1 == null || cr2 == null){
            //Берем актуальную дату с сайта ЦБ через APIService (парсинг)
            LocalDate actualDate = APIService.getActualDate();
            //Берем последнюю дату из БД
            LocalDate lastBaseDate = currencyRateRepository.findTopByOrderByIdDesc().getDate();
            System.out.println("Последняя дата в БД: " + lastBaseDate);
            //Если полученные две даты разные, то в таблицу с курсами БД добавятся актуальные курсы с сайта ЦБ
            if (!actualDate.equals(lastBaseDate)) {
                data = APIService.parseRates();
                for (CurrencyRate cr : data.getCurrencyRates()) {
                    if (cr.getCharCode().equals(firstCurrency)) {
                        cr1 = cr;
                    }
                    if (cr.getCharCode().equals(secondCurrency)) {
                        cr2 = cr;
                    }
                    currencyRateRepository.save(cr);
                }
            }
            //Если даты ==, то курсы валют возвращаем из БД
            else {
                Optional<CurrencyRate> opcr1 = currencyRateRepository.findByDateAndCharCode(actualDate, firstCurrency);
                if (opcr1.isPresent())
                    cr1 = opcr1.get();
                Optional<CurrencyRate> opcr2 = currencyRateRepository.findByDateAndCharCode(actualDate, secondCurrency);
                if (opcr2.isPresent())
                    cr2 = opcr2.get();
            }
        }
        System.out.println("cr1" + cr1 + "cr2" + cr2);
        //Считаем что получается после конвертации
        double result = amount*cr1.getRate()/n1/cr2.getRate()*n2;
        result = Math.round(result*100.0)/100.0;
        Conversion conversion = new Conversion(c1.getCharCode(), c2.getCharCode(), amount, result, currentDate);
        System.out.println(conversion);
        //Сохраняем конвертацию(conversion) в таблице истории конвертаций БД
        historyRepository.save(conversion);
        return result;

    }
    public List<Conversion> getHistory(String firstCurrency, String secondCurrency, LocalDate date) {
        return historyRepository.findByFirstCurrencyAndSecondCurrencyAndDate(firstCurrency, secondCurrency, date);
    }
    public Iterable <Currency> getAllCurrencies(){
        return currencyRepository.findAll();
    }
}
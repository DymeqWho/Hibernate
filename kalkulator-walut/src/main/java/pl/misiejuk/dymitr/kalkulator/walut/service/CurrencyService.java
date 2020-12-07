package pl.misiejuk.dymitr.kalkulator.walut.service;

import pl.misiejuk.dymitr.kalkulator.walut.database.DatabaseDao;
import pl.misiejuk.dymitr.kalkulator.walut.database.DatabaseDaoHibernateImpl;
import pl.misiejuk.dymitr.kalkulator.walut.externalapi.CalculatorHttpClient;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyService {
    private final CalculatorHttpClient calculatorHttpClient;
    private final DatabaseDao databaseDao;

    public CurrencyService() {
        this.calculatorHttpClient = new CalculatorHttpClient();
        this.databaseDao = new DatabaseDaoHibernateImpl(); //to się może zmienić w kodzie, bo to idzie po interfejsie jako nadrzednym typie danych
    }


    public BigDecimal exchange(String currencyFrom, String currencyTo, LocalDate day) {
        return exchange(currencyFrom, currencyTo, day, BigDecimal.ONE);


    }

    public BigDecimal exchange(String currencyFrom, String currencyTo, LocalDate day, BigDecimal amount) {
        Exchange exchangeInfo = getFromDatabaseOrExternalApi(currencyFrom, currencyTo, day, BigDecimal.ONE);
        return amount.multiply(BigDecimal.valueOf(exchangeInfo.getExchangeRate()));
    }

    private Exchange getFromDatabaseOrExternalApi(String currencyFrom, String currencyTo, LocalDate day, BigDecimal amount) {
        Exchange data = databaseDao.getByCriteria(currencyFrom, currencyTo, day);
        if (data == null) {
            data = calculatorHttpClient.getExchange(currencyFrom, currencyTo, day);
            databaseDao.save(data);
        }
        return data;
    }
}

package pl.misiejuk.dymitr.kalkulator.walut.database;

import pl.misiejuk.dymitr.kalkulator.walut.service.Exchange;

import java.time.LocalDate;

public interface DatabaseDao {

    Exchange getByCriteria(String currencyFrom, String currencyTo, LocalDate day);

    void save(Exchange data);
}

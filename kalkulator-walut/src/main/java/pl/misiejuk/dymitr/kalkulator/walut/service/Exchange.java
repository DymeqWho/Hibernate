package pl.misiejuk.dymitr.kalkulator.walut.service;

import java.time.LocalDate;

public class Exchange {
    private String currencyFrom;
    private String currencyTo;
    private double exchangeRate;
    private LocalDate excangeDate;

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public LocalDate getExchangeDate() {
        return excangeDate;
    }

    public void setExchangeDate(LocalDate excangeDate) {
        this.excangeDate = excangeDate;
    }
}

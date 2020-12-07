package pl.misiejuk.dymitr.kalkulator.walut.externalapi;

import java.util.Map;

public class CalculatorResponse {
    private Map<String, Double> rates;
    private String base;
    private String date;

    //gettery i settery można zrobić z adnotacją lomboka

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

/*
{
  "rates": {

    "SEK": 10.2813,
    "IDR": 17145.18,
    "JPY": 126.13,
    "THB": 36.5,
    "CHF": 1.0819,
    "SGD": 1.6176,
    "PLN": 4.4783,

  },
  "base": "EUR",
  "date": "2020-12-02"
}

 */

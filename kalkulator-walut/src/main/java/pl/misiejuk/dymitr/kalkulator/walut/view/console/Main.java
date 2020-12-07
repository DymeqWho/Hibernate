package pl.misiejuk.dymitr.kalkulator.walut.view.console;

import pl.misiejuk.dymitr.kalkulator.walut.service.CurrencyService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CurrencyService currencyService = new CurrencyService();
        currencyService.exchange("", "", LocalDate.now());
    }
}

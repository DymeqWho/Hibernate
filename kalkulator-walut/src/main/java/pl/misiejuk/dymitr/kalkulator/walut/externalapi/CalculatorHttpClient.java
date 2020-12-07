package pl.misiejuk.dymitr.kalkulator.walut.externalapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.misiejuk.dymitr.kalkulator.walut.service.Exchange;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class CalculatorHttpClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CalculatorHttpClient() {
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = new ObjectMapper();
    }

    //parametry: waluta od, walita do, data (jeśli nie podana, to dzisiejsza)
    public Exchange getExchange(String currencyFrom, String currencyTo, LocalDate day) {
        return getApiResponse(currencyFrom, currencyTo, day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .map(response -> {
                    Exchange exchange = new Exchange();
                    exchange.setCurrencyFrom(response.getBase());
                    exchange.setCurrencyTo(currencyTo);
                    exchange.setExchangeDate(LocalDate.parse(
                            response.getDate(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    );
                    exchange.setExchangeRate(response.getRates().get(currencyTo));
                    return exchange;
                }).orElseThrow(() -> new RuntimeException("problem podczas pobierania danych"));

    }

    private Optional<CalculatorResponse> getApiResponse(String currencyFrom, String currencyTo, String date) {
        String requestUrl = "https://api.exchangeratesapi.io/" + date
                + "?base=" + currencyFrom
                + "&symbols=" + currencyTo;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUrl))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            CalculatorResponse asObject = objectMapper.readValue(body, CalculatorResponse.class);
            return Optional.ofNullable(asObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
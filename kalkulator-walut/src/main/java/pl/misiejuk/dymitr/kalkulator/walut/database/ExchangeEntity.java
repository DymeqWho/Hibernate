package pl.misiejuk.dymitr.kalkulator.walut.database;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "rate", nullable = false)
    private BigDecimal rate;
    @Column(name = "fromCurrency", nullable = false)
    private String fromCurrency;
    @Column(name = "toCurrency", nullable = false)
    private String toCurrency;
    @Column(name = "exchange_date", nullable = false)
    private LocalDate exchangeDate;
}

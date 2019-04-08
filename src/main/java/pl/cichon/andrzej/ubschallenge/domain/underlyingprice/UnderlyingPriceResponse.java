package pl.cichon.andrzej.ubschallenge.domain.underlyingprice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@AllArgsConstructor
public class UnderlyingPriceResponse {
    private String symbol;
    private BigDecimal price;
    private Currency currency;
    private LocalDateTime priceDate;
}

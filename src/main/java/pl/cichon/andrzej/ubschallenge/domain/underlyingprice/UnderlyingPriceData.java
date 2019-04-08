package pl.cichon.andrzej.ubschallenge.domain.underlyingprice;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class UnderlyingPriceData {
    private String symbol;
    private Price price;
    private LocalDateTime priceDate;
}

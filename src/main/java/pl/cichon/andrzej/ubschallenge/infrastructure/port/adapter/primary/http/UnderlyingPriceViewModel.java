package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.primary.http;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
public class UnderlyingPriceViewModel {
    private String symbol;
    @JsonSerialize(using = PriceSerializer.class)
    private BigDecimal price;
    private String currency;
    private LocalDateTime priceDate;
}

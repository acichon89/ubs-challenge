package pl.cichon.andrzej.ubschallenge.domain.underlyingprice;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Currency;

@Value(staticConstructor = "of")
public class Price {
    private BigDecimal value;
    private Currency currency;
}

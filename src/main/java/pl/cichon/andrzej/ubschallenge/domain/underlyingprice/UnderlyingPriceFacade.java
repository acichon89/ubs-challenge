package pl.cichon.andrzej.ubschallenge.domain.underlyingprice;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UnderlyingPriceFacade {
    Optional<UnderlyingPriceData> getCurrentUnderlyingPrice(String symbol);
    Optional<UnderlyingPriceData> getLatestUnderlyingPriceOlderThan(String symbol, LocalDateTime date);

}

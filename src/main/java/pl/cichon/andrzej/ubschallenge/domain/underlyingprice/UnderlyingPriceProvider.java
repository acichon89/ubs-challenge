package pl.cichon.andrzej.ubschallenge.domain.underlyingprice;

import java.util.List;

public interface UnderlyingPriceProvider {
    UnderlyingPriceResponse getCurrent(UnderlyingPriceRequest request);
    List<UnderlyingPriceResponse> findAll(UnderlyingPriceRequest request);
}

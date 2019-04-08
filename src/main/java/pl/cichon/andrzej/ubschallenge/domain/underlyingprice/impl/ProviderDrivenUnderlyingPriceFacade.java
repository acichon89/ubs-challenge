package pl.cichon.andrzej.ubschallenge.domain.underlyingprice.impl;

import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class ProviderDrivenUnderlyingPriceFacade implements UnderlyingPriceFacade {

    private final UnderlyingPriceProvider provider;

    ProviderDrivenUnderlyingPriceFacade(UnderlyingPriceProvider provider) {
        this.provider = provider;
    }

    @Override
    public Optional<UnderlyingPriceData> getCurrentUnderlyingPrice(String symbol) {
        UnderlyingPriceRequest request = new UnderlyingPriceRequest(symbol);
        return Optional.ofNullable(this.provider.getCurrent(request)).map(this::map);
    }

    @Override
    public Optional<UnderlyingPriceData> getLatestUnderlyingPriceOlderThan(String symbol, LocalDateTime date) {
        UnderlyingPriceRequest request = new UnderlyingPriceRequest(symbol);
        return this.provider.findAll(request).stream()
                .filter(response -> response.getPriceDate().isBefore(date))
                .sorted(Comparator.comparing(UnderlyingPriceResponse::getPriceDate).reversed())
                .findFirst()
                .map(this::map);
    }

    private UnderlyingPriceData map(UnderlyingPriceResponse response) {
        Price price = Price.of(response.getPrice(), response.getCurrency());
        return UnderlyingPriceData.of(response.getSymbol(), price, response.getPriceDate());
    }
}

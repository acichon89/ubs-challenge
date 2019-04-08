package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.secondary.fake;

import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceProvider;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceRequest;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceResponse;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

class InMemoryUnderlyingPriceProvider implements UnderlyingPriceProvider {

    private Map<String, UnderlyingPriceResponse> fakeMap;

    @PostConstruct
    public void init() {
        fakeMap = new ConcurrentHashMap<>();
        fakeMap.put("GOOGL-1", new UnderlyingPriceResponse("GOOGL", BigDecimal.valueOf(1228.23), Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4,3,15,0,0)));
        fakeMap.put("GOOGL-2", new UnderlyingPriceResponse("GOOGL", BigDecimal.valueOf(1238.23), Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4,4,15,0,0)));
        fakeMap.put("GOOGL-3", new UnderlyingPriceResponse("GOOGL", BigDecimal.valueOf(1248.23), Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4,5,15,0,0)));
    }

    @Override
    public UnderlyingPriceResponse getCurrent(UnderlyingPriceRequest request) {
        return fakeMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(request.getSymbol()))
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(UnderlyingPriceResponse::getPriceDate).reversed()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    @Override
    public List<UnderlyingPriceResponse> findAll(UnderlyingPriceRequest request) {
        return fakeMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(request.getSymbol()))
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(UnderlyingPriceResponse::getPriceDate).reversed()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}

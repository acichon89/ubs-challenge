package pl.cichon.andrzej.ubschallenge.domain.underlyingprice.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceData;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceProvider;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceRequest;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("underlying-price-facade should")
class ProviderDrivenUnderlyingPriceFacadeTest {

    private UnderlyingPriceProvider provider;

    private ProviderDrivenUnderlyingPriceFacade facade;

    @BeforeEach
    void setUp() {
        provider = mock(UnderlyingPriceProvider.class);
        facade = new ProviderDrivenUnderlyingPriceFacade(provider);
    }

    @Nested
    @DisplayName("evaluating current underlying price")
    class CurrentCases {
        @Test
        @DisplayName("should map response from provider properly")
        void mapTest() {
            //given
            UnderlyingPriceRequest request = new UnderlyingPriceRequest("test");
            UnderlyingPriceResponse response = new UnderlyingPriceResponse("test", BigDecimal.TEN, Currency.getInstance(Locale.US), LocalDateTime.now());
            given(provider.getCurrent(request)).willReturn(response);
            //when
            Optional<UnderlyingPriceData> underlyingPriceValue = facade.getCurrentUnderlyingPrice("test");
            //then
            assertThat(underlyingPriceValue)
                    .isPresent();
            assertThat(underlyingPriceValue.get().getPrice().getCurrency())
                    .isEqualTo(Currency.getInstance(Locale.US));
            assertThat(underlyingPriceValue.get().getPrice().getValue())
                    .isEqualTo(BigDecimal.TEN);
        }

        @Test
        @DisplayName("should return empty value if provider returns null")
        void emptyTest() {
            //given
            given(provider.getCurrent(any(UnderlyingPriceRequest.class))).willReturn(null);
            UnderlyingPriceRequest request = new UnderlyingPriceRequest("test");
            //when
            Optional<UnderlyingPriceData> underlyingPriceValue = facade.getCurrentUnderlyingPrice("test");
            //then
            assertThat(underlyingPriceValue).isEmpty();
        }
    }

    @Nested
    @DisplayName("evaluating historical data")
    class OlderThanCases {

        @Test
        @DisplayName("should consider historical order: newer first")
        void orderTest() {
            //given
            UnderlyingPriceRequest request = new UnderlyingPriceRequest("test");
            UnderlyingPriceResponse response = new UnderlyingPriceResponse("old", BigDecimal.ONE, Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4, 4, 16, 0, 0));
            UnderlyingPriceResponse response2 = new UnderlyingPriceResponse("new", BigDecimal.ONE, Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4, 6, 16, 0, 0));
            given(provider.findAll(request)).willReturn(Arrays.asList(response, response2));
            //when
            Optional<UnderlyingPriceData> priceValue = facade.getLatestUnderlyingPriceOlderThan("test", LocalDateTime.of(2019, 4, 7, 16, 0, 0));
            //then
            assertThat(priceValue).isPresent();
            assertThat(priceValue.get().getSymbol()).isEqualTo("new");
        }

        @Test
        @DisplayName("should check if price date is older than requested")
        void filterTest() {
            //given
            UnderlyingPriceRequest request = new UnderlyingPriceRequest("test");
            UnderlyingPriceResponse response = new UnderlyingPriceResponse("old", BigDecimal.ONE, Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4, 4, 16, 0, 0));
            UnderlyingPriceResponse response2 = new UnderlyingPriceResponse("new", BigDecimal.ONE, Currency.getInstance(Locale.US), LocalDateTime.of(2019, 4, 6, 16, 0, 0));
            given(provider.findAll(request)).willReturn(Arrays.asList(response, response2));
            //when
            Optional<UnderlyingPriceData> priceValue = facade.getLatestUnderlyingPriceOlderThan("test", LocalDateTime.of(2019, 4, 3, 16, 0, 0));
            //then
            assertThat(priceValue).isEmpty();
        }

        @Test
        @DisplayName("should return empty value")
        void emptyTest() {
            //given
            given(provider.findAll(any(UnderlyingPriceRequest.class))).willReturn(Collections.emptyList());
            //when
            Optional<UnderlyingPriceData> priceValue = facade.getLatestUnderlyingPriceOlderThan("test", LocalDateTime.now());
            //then
            assertThat(priceValue).isEmpty();
        }
    }


}
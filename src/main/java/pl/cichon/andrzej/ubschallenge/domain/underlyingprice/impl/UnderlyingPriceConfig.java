package pl.cichon.andrzej.ubschallenge.domain.underlyingprice.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceFacade;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceProvider;

@Configuration
class UnderlyingPriceConfig {

    @Bean
    UnderlyingPriceFacade underlyingPriceFacade(UnderlyingPriceProvider underlyingPriceProvider) {
        return new ProviderDrivenUnderlyingPriceFacade(underlyingPriceProvider);
    }
}

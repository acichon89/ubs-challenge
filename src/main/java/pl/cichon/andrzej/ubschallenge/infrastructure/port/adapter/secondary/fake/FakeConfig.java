package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.secondary.fake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceProvider;

@Configuration
@Profile("localdev")
class FakeConfig {

    @Bean
    UnderlyingPriceProvider underlyingPriceProvider() {
        return new InMemoryUnderlyingPriceProvider();
    }
}

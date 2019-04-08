package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.secondary.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceProvider;

@Configuration
@Profile("qa")
class RestConfig {

    @Value("${underlying-price.internal-rest-calls.connection.url:www.example.com}")
    private String underlyingPriceApiUrl;

    @Bean
    RestTemplate restTemplate() {
        //oauth2, ribbon, load balancer config etc.
        return new RestTemplate();
    }

    @Bean
    UnderlyingPriceProvider underlyingPriceProvider(RestTemplate restTemplate) {
        return new RestUnderlyingPriceProvider(underlyingPriceApiUrl, restTemplate);
    }
}

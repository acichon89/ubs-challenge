package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.secondary.rest;

import org.springframework.web.client.RestTemplate;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceProvider;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceRequest;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceResponse;

import java.util.List;

class RestUnderlyingPriceProvider implements UnderlyingPriceProvider {

    private String apiUrl;
    private RestTemplate restTemplate;

    RestUnderlyingPriceProvider(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public UnderlyingPriceResponse getCurrent(UnderlyingPriceRequest request) {
        return new CurrentUnderlyingPriceRestCommand(request, restTemplate, apiUrl+"/current").execute();
    }

    @Override
    public List<UnderlyingPriceResponse> findAll(UnderlyingPriceRequest request) {
        return new ListUnderlyingPriceRestCommand(request, restTemplate, apiUrl+"/all").execute();
    }
}

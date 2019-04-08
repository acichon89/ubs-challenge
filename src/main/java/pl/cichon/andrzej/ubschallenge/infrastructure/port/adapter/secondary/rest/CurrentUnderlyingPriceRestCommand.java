package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.secondary.rest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceRequest;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceResponse;

@Slf4j
class CurrentUnderlyingPriceRestCommand extends HystrixCommand<UnderlyingPriceResponse> {

    private final UnderlyingPriceRequest requestData;
    private final RestTemplate restTemplate;
    private final String apiUrl;

    protected CurrentUnderlyingPriceRestCommand(UnderlyingPriceRequest requestData, RestTemplate restTemplate, String apiUrl) {
        super(HystrixCommandGroupKey.Factory.asKey("current-underlying-price"));
        this.requestData = requestData;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    protected UnderlyingPriceResponse run() throws Exception {
        return restTemplate.getForObject(String.format(apiUrl, requestData.getSymbol()), UnderlyingPriceResponse.class);
    }

    @Override
    protected UnderlyingPriceResponse getFallback() {
        log.warn("Failure when calling for current-underlying-price with data {}", requestData);
        return null;
    }
}

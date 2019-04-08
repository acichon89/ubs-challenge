package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.secondary.rest;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceRequest;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceResponse;

import java.util.Collections;
import java.util.List;

@Slf4j
class ListUnderlyingPriceRestCommand extends HystrixCommand<List<UnderlyingPriceResponse>> {

    private final UnderlyingPriceRequest requestData;
    private final RestTemplate restTemplate;
    private final String apiUrl;

    ListUnderlyingPriceRestCommand(UnderlyingPriceRequest requestData, RestTemplate restTemplate, String apiUrl) {
        super(HystrixCommandGroupKey.Factory.asKey("list-underlying-price"));
        this.requestData = requestData;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    protected List<UnderlyingPriceResponse> run() throws Exception {
        return restTemplate.getForObject(String.format(apiUrl, requestData.getSymbol()), List.class);
    }

    @Override
    protected List<UnderlyingPriceResponse> getFallback() {
        log.warn("Failure when calling for list-underlying-price with data {}", requestData);
        return Collections.EMPTY_LIST;
    }
}

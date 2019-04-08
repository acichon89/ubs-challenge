package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.primary.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceData;
import pl.cichon.andrzej.ubschallenge.domain.underlyingprice.UnderlyingPriceFacade;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@Slf4j
class UnderlyingPriceEndpoint {

    private final UnderlyingPriceFacade facade;

    UnderlyingPriceEndpoint(UnderlyingPriceFacade facade) {
        this.facade = facade;
    }

    @Secured("ROLE_PREMIUM")
    @GetMapping("/api/underlying-price/current")
    public ResponseEntity getCurrentPrice(Principal principal, @RequestParam("symbol") String symbol) {
        log.debug("user {} checks current underlying price", principal.getName());
        return facade.getCurrentUnderlyingPrice(symbol)
                .map(this::toViewModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured("ROLE_PREMIUM")
    @GetMapping("/api/underlying-price/latest")
    public ResponseEntity getLatestOlderThan(Principal principal, @RequestParam("symbol") String symbol,
                                             @RequestParam("olderThan") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime olderThan) {
        log.debug("user {} checks latest older than {} underlying price", principal.getName(), olderThan);
        return facade.getLatestUnderlyingPriceOlderThan(symbol, olderThan)
                .map(this::toViewModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private UnderlyingPriceViewModel toViewModel(UnderlyingPriceData data) {
        UnderlyingPriceViewModel viewModel = new UnderlyingPriceViewModel();
        viewModel.setCurrency(data.getPrice().getCurrency().getSymbol());
        viewModel.setPrice(data.getPrice().getValue());
        viewModel.setPriceDate(data.getPriceDate());
        viewModel.setSymbol(data.getSymbol());
        return viewModel;
    }
}

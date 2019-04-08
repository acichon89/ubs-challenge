package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.primary.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("localdev")
public class UnderlyingPriceEndpointTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldSucceedWith200() throws Exception {
        //given
        //when
        ResponseEntity<UnderlyingPriceViewModel> result = template.withBasicAuth("ubsuser", "ubspass")
                .getForEntity("/api/underlying-price/current?symbol=GOOGL", UnderlyingPriceViewModel.class);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldFailedWith401() throws Exception {
        //given
        //when
        ResponseEntity<UnderlyingPriceViewModel> result = template
                .getForEntity("/api/underlying-price/current?symbol=GOOGL", UnderlyingPriceViewModel.class);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void shouldFailedWith403() throws Exception {
        //given
        //when
        ResponseEntity<UnderlyingPriceViewModel> result = template.withBasicAuth("user", "pass")
                .getForEntity("/api/underlying-price/current?symbol=GOOGL", UnderlyingPriceViewModel.class);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void shouldFailedWith404() throws Exception {
        //given
        //when
        ResponseEntity<UnderlyingPriceViewModel> result = template.withBasicAuth("ubsuser", "ubspass")
                .getForEntity("/api/underlying-price/current?symbol=GOOGL2", UnderlyingPriceViewModel.class);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
package pl.cichon.andrzej.ubschallenge.infrastructure.port.adapter.primary.http;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebAuthorizationConfig extends GlobalMethodSecurityConfiguration {
}

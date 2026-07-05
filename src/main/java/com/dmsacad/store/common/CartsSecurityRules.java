package com.dmsacad.store.common;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CartsSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>
                                  .AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers("/carts/**").permitAll(); //TO enable only POST request on User
    }
}

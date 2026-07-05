package com.dmsacad.store.common;

import com.dmsacad.store.entities.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AdminSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>
                                  .AuthorizationManagerRequestMatcherRegistry registry) {
        registry.requestMatchers("/admin/**")
                .hasRole(Role.ADMIN.name());//To allow only ADMIN to access /admin endpoints
    }
}

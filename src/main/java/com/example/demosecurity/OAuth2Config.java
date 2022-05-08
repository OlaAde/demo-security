package com.example.demosecurity;

import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2Config {

    private static final String APPROLE_ROLE_1 = "APPROLE_ROLE_1";
    private static final String APPROLE_ROLE_2 = "APPROLE_ROLE_2";

    @Order(1)
    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends AADResourceServerWebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http.antMatcher("/api/**").authorizeRequests().anyRequest().hasAnyAuthority(APPROLE_ROLE_1, APPROLE_ROLE_2).and().oauth2Login();
        }
    }

    @Configuration
    public static class HtmlWebSecurityConfigurerAdapter extends AADWebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            super.configure(http);
            http
                    .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .requireCsrfProtectionMatcher(httpServletRequest -> false)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/actuator/health", "/public/*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login();
        }
    }

}


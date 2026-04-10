package com.ktmmobile.msf.external.websecurity.security.auth;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.DispatcherType;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ktmmobile.msf.external.websecurity.security.auth.converter.JwtMemberAuthenticationConverter;
import com.ktmmobile.msf.external.websecurity.security.auth.handler.DefaultAccessDeniedHandler;
import com.ktmmobile.msf.external.websecurity.security.auth.handler.DefaultAuthenticationEntryPoint;
import com.ktmmobile.msf.external.websecurity.security.auth.properties.JwtSecurityProperties;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class AuthConfig {

    @Bean
    public SecurityFilterChain filterChain(
        HttpSecurity http,
        AuthenticationEntryPoint authenticationEntryPoint,
        AccessDeniedHandler accessDeniedHandler,
        JwtMemberAuthenticationConverter jwtMemberAuthenticationConverter
    ) {
        http.httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .requestCache(AbstractHttpConfigurer::disable)
            .cors(configurer -> configurer.configurationSource(corsConfigurationSource()))

            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .anyRequest().permitAll())

            .exceptionHandling(configurer -> configurer
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader(HttpHeaders.LOCATION);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtDecoder jwtDecoder(JwtSecurityProperties jwtSecurityProperties) {
        Assert.hasText(jwtSecurityProperties.secretKey(), "spring.security.jwt.secret-key is required");

        SecretKey secretKey = new SecretKeySpec(jwtSecurityProperties.secretKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
            .macAlgorithm(MacAlgorithm.HS256)
            .build();

        OAuth2TokenValidator<Jwt> tokenValidator = createJwtOAuth2TokenValidator(jwtSecurityProperties.issuer());
        jwtDecoder.setJwtValidator(tokenValidator);
        return jwtDecoder;
    }

    @Bean
    public JwtEncoder jwtEncoder(JwtSecurityProperties jwtSecurityProperties) {
        Assert.hasText(jwtSecurityProperties.secretKey(), "spring.security.jwt.secret-key is required");
        SecretKey secretKey = new SecretKeySpec(jwtSecurityProperties.secretKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    private static OAuth2TokenValidator<Jwt> createJwtOAuth2TokenValidator(String issuer) {
        if (StringUtils.hasText(issuer)) {
            return new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefault(), JwtValidators.createDefaultWithIssuer(issuer));
        }
        return JwtValidators.createDefault();
    }

    @Bean
    public AuthenticationEntryPoint defaultAuthenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler defaultAccessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }
}

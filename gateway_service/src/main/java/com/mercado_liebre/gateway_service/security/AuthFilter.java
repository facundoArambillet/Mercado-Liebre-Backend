package com.mercado_liebre.gateway_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return(((exchange, chain) -> {
//        if(routeValidator.isSecured.test(exchange.getRequest())) {
//            //header contains token or not
//            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                throw new RuntimeException("Missing authorization header");
//            }
//            String authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            if(authHeaders != null && authHeaders.startsWith("Bearer ")) {
//                authHeaders = authHeaders.substring(7);
//                System.out.println(authHeaders);
//            }
//            try {
//                if(!jwtUtil.validateToken(authHeaders)) {
//                     throw new RuntimeException("");
//                }
//
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                throw new RuntimeException("Unauthorized access to application");
//            }
//        }
//            return chain.filter(exchange);
//        }));
//    }
//
//    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {

        log.info("============== Apply Method Start ==============");
        log.info("apply(Config) -> | Config : {}", config);

        GatewayFilter bearer = (exchange, chain) -> {

            log.info("apply(Config) -> | Exchange : {} | Chain : {}", exchange, chain);

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                log.info("apply(Config) -> | Route Validation isSecured : ");
                //header contains token or not
                log.info("apply(Config) -> | HttpHeader Check : {}", HttpHeaders.AUTHORIZATION);

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.error("apply(Config) -> | MissingAuthorizationHeaderException Throw :");
                }

                log.info("apply(Config) -> | authorization header present");
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                log.info("apply(Config) -> | AuthHeader : {}", authHeader);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    log.info("apply(Config) -> | After Delete AuthHeader : {}", authHeader);
                }

                try {

                    log.info("apply(Config) -> | Try to validateToken");
                    jwtUtil.validateToken(authHeader);

                } catch (Exception e) {
                    log.error("apply(Config) -> | Exception Message : {} | Exception Cause : {}", e.getMessage(), e.getCause());
                    log.error("apply(Config) -> | UnauthorizedAccessException Throw | invalid access...");
                }

            }

            Mono<Void> filter = chain.filter(exchange);
            return filter;
        };

        return bearer;
    }

    public static class Config {
        public Config() {
        }
    }
}

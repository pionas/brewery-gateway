package pl.excellentapp.brewery.gateway.infrastructure.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!local-discovery")
@Configuration(proxyBeanMethods = false)
class LocalHostRouteConfiguration {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/beers/**")
                        .uri("http://localhost:8080"))
                .route(r -> r.path("/api/v1/inventories/**")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/api/v1/orders/**")
                        .uri("http://localhost:8082"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}

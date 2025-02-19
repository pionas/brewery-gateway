package pl.excellentapp.brewery.gateway.infrastructure.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local")
@Configuration(proxyBeanMethods = false)
class LoadBalancedRoutesConfiguration {

    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/beers/**")
                        .uri("lb://brewery-beer-service"))
                .route(r -> r.path("/api/v1/inventories/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")
                        ))
                        .uri("lb://brewery-inventory-service"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://inventory-service"))
                .route(r -> r.path("/api/v1/orders/**")
                        .uri("lb://brewery-order-service"))
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://brewery-inventory-failover"))
                .build();
    }
}

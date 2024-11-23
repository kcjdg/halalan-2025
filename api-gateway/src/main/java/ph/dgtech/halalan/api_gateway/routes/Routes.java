package ph.dgtech.halalan.api_gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Value("${voter-profile.url}")
    private String voterProfileUrl;

    @Value("${voting-service.url}")
    private String votingServiceUrl;


    @Bean
    public RouterFunction<ServerResponse> voterProfile() {
        return GatewayRouterFunctions.route("voter-profile")
                .route(RequestPredicates.path("/voter-profile/**"), HandlerFunctions.http(voterProfileUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("voterProfileFallBack",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> voterProfileSwagger() {
        return GatewayRouterFunctions.route("voter-profile-swagger")
                .route(RequestPredicates.path("voter-profile/api-docs"), HandlerFunctions.http(voterProfileUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("voterProfileSwaggerFallback", URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> votingService() {
        return GatewayRouterFunctions.route("voting-service")
                .route(RequestPredicates.path("/voting-service/**"), HandlerFunctions.http(votingServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("voterProfileFallBack",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }
}

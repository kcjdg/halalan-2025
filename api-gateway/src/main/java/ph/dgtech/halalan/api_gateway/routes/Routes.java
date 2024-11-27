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

    @Value("${polling-service.url}")
    private String pollingServiceUrl;


    @Bean
    public RouterFunction<ServerResponse> votingFunction() {
        return GatewayRouterFunctions
                .route("voting")
                .route(RequestPredicates.path("/voter-profile/**"), HandlerFunctions.http(voterProfileUrl))
                .route(RequestPredicates.path("/voting-service/**"), HandlerFunctions.http(votingServiceUrl))
                .route(RequestPredicates.path("/polling-service/**"), HandlerFunctions.http(pollingServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("votingFallback",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> votingSwagger() {
        return GatewayRouterFunctions.route("votingSwagger")
                .route(RequestPredicates.path("voter-profile/api-docs"), HandlerFunctions.http(voterProfileUrl))
                .route(RequestPredicates.path("voter-service/api-docs"), HandlerFunctions.http(votingServiceUrl))
                .route(RequestPredicates.path("polling-service/v3/api-docs"), HandlerFunctions.http(pollingServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("votingSwagger",
                        URI.create("forward:/fallbackRoute")))
                .filter(setPath("/api-docs"))
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

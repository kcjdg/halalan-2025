package ph.dgtech.halalan.voting.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SecurityScheme(name = "oauth2_bearer", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "${springdoc.oauthflow.authorization-url}",
                        tokenUrl = "${springdoc.oauthflow.token-url}",
                        scopes = {@OAuthScope(name = "openid", description = "openid")
                        })))
public class OpenApiConfig {
    @Bean
    public OpenAPI productServiceAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Voting Service")
                        .description("REST API Documentation for Voting Service")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
                        .description("Voting Service Wiki Documentation")
                        .url("https://voting-service/api-docs"));
    }
}

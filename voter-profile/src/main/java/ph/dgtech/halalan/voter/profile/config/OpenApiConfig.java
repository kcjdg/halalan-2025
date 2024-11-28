package ph.dgtech.halalan.voter.profile.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


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
                        .title("Voter Profile Service")
                        .description("REST API Documentation for Voter Profile Service")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new io.swagger.v3.oas.models.ExternalDocumentation()
                        .description("Product Service Wiki Documentation")
                        .url("https://voter-profile-service/api-docs"));
    }
}

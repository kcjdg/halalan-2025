package ph.dgtech.halalan.polling.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;

@OpenAPIDefinition(
        info = @Info(
                title = "Polling Service API",
                description = "Polling Service API documentation",
                version = "1.0"
        ),
        security = @SecurityRequirement(name = "oauth2_bearer"))
public class OpenApiConfig {
}

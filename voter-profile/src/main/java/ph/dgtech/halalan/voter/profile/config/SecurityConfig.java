//package ph.dgtech.halalan.voter.registration.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.ws.rs.HttpMethod;
//import java.util.List;
//
//@Configuration
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//
//        return (web) -> {
//            web.ignoring().requestMatchers(
//                    HttpMethod.POST,
//                    "/ph/voter/**",
//                    "/users"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.GET,
//                    "/public/**"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.DELETE,
//                    "/public/**",
//                    "/users/{id}"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.PUT,
//                    "/public/**",
//                    "/users/{id}/send-verification-email",
//                    "/users/forgot-password"
//
//            );
//            web.ignoring().requestMatchers(
//                            HttpMethod.OPTIONS,
//                            "/**"
//                    )
//                    .requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
//                            "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**");
//
//        };
//    }
//
//}

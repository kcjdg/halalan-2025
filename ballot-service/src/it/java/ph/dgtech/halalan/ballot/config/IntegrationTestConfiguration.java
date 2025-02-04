package ph.dgtech.halalan.ballot.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@TestConfiguration
public class IntegrationTestConfiguration {
    @Container
    private static final MySQLContainer<?> primaryDb = new MySQLContainer<>("mysql:8.3.0")
            .withDatabaseName("testdb")
            .withUsername("testroot")
            .withPassword("testpwd")
            .withReuse(true)
            .withUrlParam("useSSL", "false")
            .withUrlParam("allowPublicKeyRetrieval", "true");

    @Container
    private static final MySQLContainer<?> secondaryDb = new MySQLContainer<>("mysql:8.3.0")
            .withDatabaseName("testkeycloak")
            .withUsername("testroot")
            .withPassword("testpwd")
            .withReuse(true)
            .withUrlParam("useSSL", "false")
            .withUrlParam("allowPublicKeyRetrieval", "true");

    static {
        primaryDb.start();
        secondaryDb.start();
    }



    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.first-datasource.url=" + primaryDb.getJdbcUrl(),
                    "spring.datasource.first-datasource.username=" + primaryDb.getUsername(),
                    "spring.datasource.first-datasource.password=" + primaryDb.getPassword(),
                    "spring.datasource.second-datasource.url=" + secondaryDb.getJdbcUrl(),
                    "spring.datasource.second-datasource.username=" + secondaryDb.getUsername(),
                    "spring.datasource.second-datasource.password=" + secondaryDb.getPassword()
            ).applyTo(context.getEnvironment());
        }
    }


    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.first-datasource.url", () -> primaryDb.getJdbcUrl());
        registry.add("spring.datasource.first-datasource.username", () -> primaryDb.getUsername());
        registry.add("spring.datasource.first-datasource.password", () -> primaryDb.getPassword());
        registry.add("spring.datasource.first-datasource.driver-class-name",
                () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.datasource.first-datasource.hikari.connection-timeout",
                () -> "20000");
        registry.add("spring.datasource.first-datasource.hikari.maximum-pool-size",
                () -> "5");
        registry.add("spring.jpa.properties.hibernate.dialect.first", () -> "org.hibernate.dialect.MySQL8Dialect"); // 🔥 Explicitly set dialect


        registry.add("spring.datasource.second-datasource.url", () -> secondaryDb.getJdbcUrl());
        registry.add("spring.datasource.second-datasource.username", () -> secondaryDb.getUsername());
        registry.add("spring.datasource.second-datasource.password", () -> secondaryDb.getPassword());
        registry.add("spring.datasource.second-datasource.driver-class-name",
                () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.datasource.second-datasource.hikari.connection-timeout",
                () -> "20000");
        registry.add("spring.datasource.second-datasource.hikari.maximum-pool-size",
                () -> "5");
        registry.add("spring.jpa.properties.hibernate.dialect.second", () -> "org.hibernate.dialect.MySQL8Dialect"); // 🔥 Explicitly set dialect
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect"); // 🔥 Explicitly set dialect

    }

}

package ph.dgtech.halalan.ballot.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class IntegrationTestConfiguration {

    @Container
    private static final MySQLContainer<?> primaryDb = new MySQLContainer<>("mysql:8.3.0")
            .withDatabaseName("testdb")
            .withUsername("testroot")
            .withPassword("testpwd")
            .withReuse(true)
            .withUrlParam("useSSL", "false");

    @Container
    private static final MySQLContainer<?> secondaryDb = new MySQLContainer<>("mysql:8.3.0")
            .withDatabaseName("testkeycloak")
            .withUsername("testroot")
            .withPassword("testpwd")
            .withReuse(true)
            .withUrlParam("useSSL", "false");

    static {

        primaryDb.start();
        secondaryDb.start();
    }


    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        System.out.println("DynamicPropertySource is being called!");
        registry.add("spring.first-datasource.url", primaryDb::getJdbcUrl);
        registry.add("spring.first-datasource.username", primaryDb::getUsername);
        registry.add("spring.first-datasource.password", primaryDb::getPassword);
        registry.add("spring.first-datasource.driver-class-name",
                () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.first-datasource.hikari.connection-timeout",
                () -> "20000");
        registry.add("spring.first-datasource.hikari.maximum-pool-size",
                () -> "5");
        registry.add("spring.flyway.enabled", () -> "true");


        registry.add("spring.second-datasource.url", secondaryDb::getJdbcUrl);
        registry.add("spring.second-datasource.username", secondaryDb::getUsername);
        registry.add("spring.second-datasource.password", secondaryDb::getPassword);
        registry.add("spring.second-datasource.driver-class-name",
                () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.second-datasource.hikari.connection-timeout",
                () -> "20000");
        registry.add("spring.second-datasource.hikari.maximum-pool-size",
                () -> "5");

    }



}

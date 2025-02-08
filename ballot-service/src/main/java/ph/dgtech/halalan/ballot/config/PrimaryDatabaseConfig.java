package ph.dgtech.halalan.ballot.config;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "ph.dgtech.halalan.ballot.repo.primary", // Repository location for primary DB
        entityManagerFactoryRef = "firstEntityManagerFactory",
        transactionManagerRef = "firstTransactionManager"
)
public class PrimaryDatabaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.first-datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource firstDataSource() {
        return firstDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
             @Qualifier("firstDataSource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        return builder
                .dataSource(dataSource)
                .packages("ph.dgtech.halalan.ballot.model.primary")
                .persistenceUnit("firstPU")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager firstTransactionManager(
            @Qualifier("firstEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Primary
    @Bean
    public Flyway firstFlyway(@Qualifier("firstDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/primary")
                .baselineOnMigrate(true)
                .load();
    }
}

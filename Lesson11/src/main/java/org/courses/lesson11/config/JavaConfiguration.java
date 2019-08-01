package org.courses.lesson11.config;

import org.courses.lesson11.repository.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@ComponentScan("org.courses.lesson11")
public class JavaConfiguration {

    private final Environment environment;

    public JavaConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ConnectionPool getConnectionPool() {
        return ConnectionPool.getInstance();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(createDataSource());
        initializer.setDatabasePopulator(createDatabasePopulator());
        return initializer;
    }

    private DatabasePopulator createDatabasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        populator.addScript(new ClassPathResource("data.sql"));
        return populator;
    }

    private SimpleDriverDataSource createDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(org.postgresql.Driver.class);
        simpleDriverDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        simpleDriverDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        simpleDriverDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return simpleDriverDataSource;
    }

}

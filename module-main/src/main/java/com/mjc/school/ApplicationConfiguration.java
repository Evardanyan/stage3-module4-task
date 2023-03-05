//package com.mjc.school;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.data.auditing.DateTimeProvider;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.Optional;
//import java.util.Properties;
//
//@Configuration
//@ComponentScan(basePackages = "com.mjc.school")
//@EnableAspectJAutoProxy
//public class ApplicationConfiguration {
//    @Bean(name = "iso8601DateTimeProvider")
//    public DateTimeProvider dateTimeProvider() {
//        return () -> Optional.of(OffsetDateTime.now(ZoneOffset.UTC));
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/epam_news");
//        dataSource.setUsername("epam_news");
//        dataSource.setPassword("password");
//
//        return dataSource;
//    }
//
//    @Bean
//    public Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.connection.pool_size", "3");
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.setProperty("hibernate.show_sql", "true");
//
//        return properties;
//    }
//}

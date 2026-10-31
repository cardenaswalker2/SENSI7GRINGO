package com.sensi7gringo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sensi7gringo.repositories")
public class MongoConfig {

    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${spring.data.mongodb.uri:mongodb://localhost:27017/sensi7gringo}")
    private String mongoUri;

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() {
        try {
            MongoClient mongoClient = MongoClients.create(mongoUri);
            log.info("Conectando cliente MongoDB con URI: {}", mongoUri);
            return new MongoTemplate(mongoClient, "sensi7gringo");
        } catch (Exception e) {
            log.warn("No se pudo conectar a MongoDB. La aplicación continuará usando InMemoryDataStore: {}", e.getMessage());
            return null;
        }
    }
}

package com.example.webapp.config;

import com.example.webapp.client.catalog.CatalogServiceClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@EnableConfigurationProperties(CatalogClientProperties.class)
public class CatalogClientConfig {

    @Bean
    RestClient catalogRestClient(CatalogClientProperties properties) {
        String baseUrl = properties.baseUrl() != null ? properties.baseUrl() : "http://localhost:8081";
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    CatalogServiceClient catalogServiceClient(RestClient catalogRestClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(catalogRestClient))
                .build();
        return factory.createClient(CatalogServiceClient.class);
    }
}

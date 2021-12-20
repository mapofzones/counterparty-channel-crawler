package com.mapofzones.counterpartychannelcrawler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapofzones.counterpartychannelcrawler.common.properties.CrawlerProperties;
import com.mapofzones.counterpartychannelcrawler.common.properties.EndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public EndpointProperties endpointProperties() {
        return new EndpointProperties();
    }

    @Bean
    public CrawlerProperties crawlerProperties() {
        return new CrawlerProperties();
    }
}

package com.mapofzones.counterpartychannelcrawler.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapofzones.counterpartychannelcrawler.common.properties.EndpointProperties;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.IbcChannelClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class IbcChannelClientConfig {

    @Bean
    public RestTemplate ibcChannelRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public ObjectMapper ibcChannelObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public IbcChannelClient ibcChannelClient(RestTemplate ibcChannelRestTemplate,
                                             EndpointProperties endpointProperties,
                                             ObjectMapper ibcChannelObjectMapper) {
        return new IbcChannelClient(ibcChannelRestTemplate, endpointProperties, ibcChannelObjectMapper);
    }

}

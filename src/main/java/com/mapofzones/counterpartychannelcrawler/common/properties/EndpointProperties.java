package com.mapofzones.counterpartychannelcrawler.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "endpoint")
public class EndpointProperties {

    private IBC ibc;

    @Getter
    @Setter
    public static class IBC {
        private String denomTrace;
        private String channels;
    }
}
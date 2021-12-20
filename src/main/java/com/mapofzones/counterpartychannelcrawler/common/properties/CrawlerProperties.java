package com.mapofzones.counterpartychannelcrawler.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "crawler")
public class CrawlerProperties {

    private Duration syncTime;

}

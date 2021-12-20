package com.mapofzones.counterpartychannelcrawler.schedulers;

import com.mapofzones.counterpartychannelcrawler.services.CrawlerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class CrawlerScheduler {

    private final CrawlerFacade crawlerFacade;

    public CrawlerScheduler(CrawlerFacade crawlerFacade) {
        this.crawlerFacade = crawlerFacade;
    }

    @Scheduled(fixedDelayString = "#{crawlerProperties.syncTime}")
    public void run() {
        crawlerFacade.findCounterpartyChannel();
    }

}

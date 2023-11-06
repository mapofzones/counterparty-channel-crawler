package com.mapofzones.counterpartychannelcrawler.services;

import com.mapofzones.counterpartychannelcrawler.domain.ZoneNode;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.IIbcChanelService;
import com.mapofzones.counterpartychannelcrawler.services.zonenode.IZoneNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CrawlerFacade {

    private final IIbcChanelService ibcChanelService;
    private final IZoneNodeService zoneNodeService;

    public CrawlerFacade(IIbcChanelService ibcChanelService,
                         IZoneNodeService zoneNodeService) {
        this.ibcChanelService = ibcChanelService;
        this.zoneNodeService = zoneNodeService;
    }

    @Transactional
    public void findCounterpartyChannel() {
        log.info("Start crawler");
        List<ZoneNode> zoneNodeList = zoneNodeService.findLcdAddresses();

        Map<String, List<ZoneNode>> lcdAddressMap = zoneNodeList.stream()
                .collect(Collectors.groupingBy(ZoneNode::getZone));

        Map<String, List<ZoneNode>> sortedLcdAddressMap = lcdAddressMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(String::toLowerCase)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        ibcChanelService.findByAddressList(sortedLcdAddressMap);
        log.info("Finish crawler");
    }

}

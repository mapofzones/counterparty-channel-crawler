package com.mapofzones.counterpartychannelcrawler.services.ibcchannel;

import com.mapofzones.counterpartychannelcrawler.domain.IbcChannel;
import com.mapofzones.counterpartychannelcrawler.domain.ZoneNode;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.IbcChannelClient;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto.ChannelsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IbcChanelService implements IIbcChanelService {

    private final IbcChannelRepository ibcChannelRepository;
    private final IbcChannelClient ibcChannelClient;

    public IbcChanelService(IbcChannelRepository ibcChannelRepository,
                            IbcChannelClient ibcChannelClient) {
        this.ibcChannelRepository = ibcChannelRepository;
        this.ibcChannelClient = ibcChannelClient;
    }

    @Override
    public void findByAddressList(Map<String, List<ZoneNode>> lcdAddressMap) {

        List<IbcChannel> ibcChannelList = findByCounterpartyIsNull();

        Map<String, List<IbcChannel>> ibcChannelMap = ibcChannelList.stream()
                .collect(Collectors.groupingBy(IbcChannel::getZone));

        ibcChannelMap.forEach((key, value) -> {

            List<ZoneNode> zoneNodeList = lcdAddressMap.get(key);
            if (zoneNodeList != null) {

                String workedUrl = "";
                ZoneNode workedZoneNode = new ZoneNode();
                for (ZoneNode zoneNode : zoneNodeList) {
                    String url = ibcChannelClient.check(zoneNode.getLcdAddress());
                    if (!url.isEmpty()) {
                        workedUrl = url;
                        workedZoneNode = zoneNode;
                        break;
                    }
                }

                List<ChannelsDto> dtoList;
                if (!workedUrl.isEmpty())
                    dtoList = ibcChannelClient.findChannels(workedUrl).getChannels();
                else
                    return;

                log.info("Start set counterparty in: " + workedZoneNode.getZone());
                ZoneNode finalWorkedZoneNode = workedZoneNode;
                value.forEach(ch -> dtoList.forEach(dto -> {
                    if (ch.getIbcChannelId().getChannelId().equals(dto.getChannelId()) && !dto.getCounterparty().getChannelId().isBlank()) {
                        ch.setCounterpartyChannelId(dto.getCounterparty().getChannelId());
                    } else if (dto.getCounterparty().getChannelId().isBlank())
                        log.info(finalWorkedZoneNode.getZone() + " has empty counterparty channel (channel_id:" + dto.getChannelId() + "). " + "Trying set...");
                }));
                log.info("Finish set counterparty in: " + workedZoneNode.getZone());
            }
        });
    }

    private List<IbcChannel> findByCounterpartyIsNull() {
        return ibcChannelRepository.findAllByCounterpartyChannelIdIsNull();
    }
}

package com.mapofzones.counterpartychannelcrawler.services.ibcchannel;

import com.mapofzones.counterpartychannelcrawler.domain.ZoneNode;

import java.util.List;
import java.util.Map;

public interface IIbcChanelService {

    void findByAddressList(Map<String, List<ZoneNode>> lcdAddressMap);

}

package com.mapofzones.counterpartychannelcrawler.services.zonenode;

import com.mapofzones.counterpartychannelcrawler.domain.ZoneNode;

import java.util.List;

public interface IZoneNodeService {

	List<ZoneNode> findLcdAddresses();
}

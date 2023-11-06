package com.mapofzones.counterpartychannelcrawler.services.zonenode;

import com.mapofzones.counterpartychannelcrawler.domain.ZoneNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ZoneNodeService implements IZoneNodeService {

	private final ZoneNodeRepository zoneNodeRepository;
		
	public ZoneNodeService(ZoneNodeRepository zoneNodeRepository) {
		this.zoneNodeRepository = zoneNodeRepository;
	}

	@Override
	public List<ZoneNode> findLcdAddresses() {
		return zoneNodeRepository.findAllByLcdAddressIsNotNull();
	}

	@Override
	public List<ZoneNode> findActiveLcdAddresses() {
		return zoneNodeRepository.findAllByLcdAddressIsNotNullAndIsLcdAddressActiveIsTrue();
	}
}

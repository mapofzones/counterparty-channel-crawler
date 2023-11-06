package com.mapofzones.counterpartychannelcrawler.services.zonenode;

import com.mapofzones.counterpartychannelcrawler.domain.ZoneNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneNodeRepository extends JpaRepository<ZoneNode, String> {

	Optional<ZoneNode> findFirstByZoneAndIsLcdAddressActiveIsTrue(String zone);
	List<ZoneNode> findAllByLcdAddressIsNotNull();
	List<ZoneNode> findAllByLcdAddressIsNotNullAndIsLcdAddressActiveIsTrue();
	
}

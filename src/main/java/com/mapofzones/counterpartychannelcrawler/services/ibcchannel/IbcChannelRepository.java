package com.mapofzones.counterpartychannelcrawler.services.ibcchannel;

import com.mapofzones.counterpartychannelcrawler.domain.IbcChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IbcChannelRepository extends JpaRepository<IbcChannel, IbcChannel.IbcChannelId> {

    List<IbcChannel> findAllByCounterpartyChannelIdIsNull();

}

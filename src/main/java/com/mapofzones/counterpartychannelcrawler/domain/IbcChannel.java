package com.mapofzones.counterpartychannelcrawler.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@Entity
@Table(name = "IBC_CHANNELS")
public class IbcChannel {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class IbcChannelId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "CHANNEL_ID")
        private String channelId;
    }

    @EmbeddedId
    private IbcChannelId ibcChannelId;

    @Column(name = "COUNTERPARTY_CHANNEL_ID")
    private String counterpartyChannelId;

    @Transient
    public String getZone() {
        return ibcChannelId.getZone();
    }

}

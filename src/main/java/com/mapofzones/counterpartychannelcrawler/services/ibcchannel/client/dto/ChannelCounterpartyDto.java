package com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelCounterpartyDto {

    public ChannelCounterpartyDto(boolean isSuccessReceived) {
        this.isSuccessReceived = isSuccessReceived;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName("counterparty")
    public static class Counterparty {
        @JsonProperty("channel_id")
        private String channelId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName("channel")
    public static class Channel {
        @JsonProperty("counterparty")
        private ChannelCounterpartyDto.Counterparty counterparty;
    }

    @JsonProperty("channel")
    private ChannelCounterpartyDto.Channel channel;

    @JsonIgnore
    private boolean isSuccessReceived;
}

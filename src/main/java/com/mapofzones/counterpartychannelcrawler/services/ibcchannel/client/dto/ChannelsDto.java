package com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelsDto {

    private ArrayList<Channel> channels;
    private Pagination pagination;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
	@JsonIgnoreProperties(ignoreUnknown = true)
    public static class Channel {
        private Counterparty counterparty;
		@JsonProperty("channel_id")
        private String channelId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
	@JsonRootName("counterparty")
    public static class Counterparty {
        private String port_id;
		@JsonProperty("channel_id")
        private String channelId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonRootName("pagination")
    public static class Pagination {
		@JsonProperty("next_key")
        private String nextKey;
    }

    @JsonIgnore
    private boolean isSuccessReceived;
}

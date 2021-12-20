package com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelsDto {

	public ChannelsDto(boolean isSuccessReceived) {
		this.isSuccessReceived = isSuccessReceived;
	}

	private List<ChannelsDto> channels;

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

	@JsonProperty("counterparty")
	private Counterparty counterparty;

	@JsonProperty("channel_id")
	private String channelId;

	@JsonIgnore
	private boolean isSuccessReceived;

}

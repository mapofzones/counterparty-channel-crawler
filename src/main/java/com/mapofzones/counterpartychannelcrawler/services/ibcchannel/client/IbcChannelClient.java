package com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapofzones.counterpartychannelcrawler.common.exceptons.JsonParseException;
import com.mapofzones.counterpartychannelcrawler.common.properties.EndpointProperties;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto.ChannelCounterpartyDto;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto.ChannelsDto;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto.ChannelsDto.Channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

@Slf4j
public class IbcChannelClient {

	private final RestTemplate denomTraceRestTemplate;
	private final EndpointProperties endpointProperties;
	private final ObjectMapper denomTraceObjectMapper;

	private static final String EMPTY_STRING = "";
		
	public IbcChannelClient(RestTemplate denomTraceRestTemplate,
							EndpointProperties endpointProperties,
							ObjectMapper denomTraceObjectMapper) {
		this.denomTraceRestTemplate = denomTraceRestTemplate;
		this.endpointProperties = endpointProperties;
		this.denomTraceObjectMapper = denomTraceObjectMapper;
	}

	public String check(String address) {
		if (!address.isEmpty()) {

			URI uri = URI.create(address + endpointProperties.getIbc().getChannels());
			String foundAddress = checkUrl(uri);

			if (foundAddress.isEmpty()) {
				uri = URI.create(address + endpointProperties.getIbc().getChannelsBeta());
				foundAddress = checkUrl(uri);
			}

			return foundAddress;
		} else return EMPTY_STRING;
	}

	public ArrayList<ChannelsDto.Channel> findChannels(String address, String pagination, ArrayList<Channel> channels) {

		URI uri = URI.create(address.trim() + pagination);
		log.info(String.valueOf((uri)));

		try {
			ResponseEntity<String> response = denomTraceRestTemplate.getForEntity(uri, String.class);
			ChannelsDto receivedChannelsDto = jsonToDto(response.getBody());

			channels.addAll(receivedChannelsDto.getChannels());

			String paginationKey = receivedChannelsDto.getPagination().getNextKey();

			if(paginationKey == null || paginationKey.isEmpty() || paginationKey.equals("null")) {
				return channels;
			}

			return findChannels(address, ("?pagination.key=" + paginationKey).trim(), channels);
		} catch (RestClientException e) {
			log.warn("Request cant be completed. " + uri);
			return null;
		}
	}

	public ChannelCounterpartyDto findCounterpartyByChannel(String address, String channel) {
		URI uri = URI.create(String.format(address + endpointProperties.getIbc().getChannelsByNum(), channel));

		ChannelCounterpartyDto dto = findCounterpartyByChannel(uri);

		if (!dto.isSuccessReceived()) {
			URI uriBeta = URI.create(String.format(address + endpointProperties.getIbc().getChannelsByNumBeta(), channel));
			dto = findCounterpartyByChannel(uriBeta);
		}
		return dto;

	}

	public ChannelCounterpartyDto findCounterpartyByChannel(URI uri) {
		try {
			ChannelCounterpartyDto receivedDto = denomTraceRestTemplate.getForEntity(uri, ChannelCounterpartyDto.class).getBody();
			if (receivedDto != null) {
				receivedDto.setSuccessReceived(true);
				return receivedDto;
			} else return new ChannelCounterpartyDto(false);
		} catch (RestClientException e) {
			log.warn("Request cant be completed. " + uri);
			return new ChannelCounterpartyDto(false);
		}
	}

	private String checkUrl(URI uri) {
		try {
			ResponseEntity<String> response = denomTraceRestTemplate.getForEntity(uri, String.class);

			if (channelsIsEmptyOrNull(response.getBody()))
				return EMPTY_STRING;

			return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError() ? EMPTY_STRING : uri.toString();
		} catch (RestClientException e) {
			log.warn("Request cant be completed. " + uri);
			return EMPTY_STRING;
		}
	}

	private boolean channelsIsEmptyOrNull(String json) {
		ChannelsDto dto = jsonToDto(json);
		return dto.getChannels() == null || dto.getChannels().isEmpty();
	}

	private ChannelsDto jsonToDto(String json) {
		try {
			return denomTraceObjectMapper.readValue(json, ChannelsDto.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.warn("Cant parse json. ");
			throw new JsonParseException("Cant parse json", e.getCause());
		}
	}
	
}

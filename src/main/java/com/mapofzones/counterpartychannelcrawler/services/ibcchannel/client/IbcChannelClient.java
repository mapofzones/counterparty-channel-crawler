package com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapofzones.counterpartychannelcrawler.common.exceptons.JsonParseException;
import com.mapofzones.counterpartychannelcrawler.common.properties.EndpointProperties;
import com.mapofzones.counterpartychannelcrawler.services.ibcchannel.client.dto.ChannelsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
public class IbcChannelClient {

	private final RestTemplate denomTraceRestTemplate;
	private final EndpointProperties endpointProperties;
	private final ObjectMapper denomTraceObjectMapper;
		
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

			try {
				ResponseEntity<String> response = denomTraceRestTemplate.getForEntity(uri, String.class);

				if (channelsIsEmptyOrNull(response.getBody()))
					return "";

				return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError() ? "" : uri.toString();
			} catch (RestClientException e) {
				try {
					log.warn("Request cant be completed. " + uri + " Try v1beta...");
					uri = URI.create(address + endpointProperties.getIbc().getChannelsBeta());
					ResponseEntity<String> response = denomTraceRestTemplate.exchange(uri, HttpMethod.GET, null, String.class);

					if (channelsIsEmptyOrNull(response.getBody()))
						return "";

					return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError() ? "" : uri.toString();
				} catch (RestClientException e1) {
					log.warn("Request cant be completed. " + uri);
					return "";
				}
			}
		} else return "";
	}

	// TODO: Need to refactoring when findRest wos implemented
	public ChannelsDto findChannels(String address) {

		URI uri = URI.create(address);
		log.info(String.valueOf((uri)));

		try {
			ResponseEntity<String> response = denomTraceRestTemplate.getForEntity(uri, String.class);
			ChannelsDto receivedChannelsDto = jsonToDto(response.getBody());
			receivedChannelsDto.setSuccessReceived(true);
			return receivedChannelsDto;
		} catch (RestClientException e) {
			log.warn("Request cant be completed. " + uri);
			return new ChannelsDto(false);
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

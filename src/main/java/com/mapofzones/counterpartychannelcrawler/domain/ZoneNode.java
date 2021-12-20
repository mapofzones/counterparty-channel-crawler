package com.mapofzones.counterpartychannelcrawler.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "ZONE_NODES")
public class ZoneNode {

	@Id
	@Column(name = "RPC_ADDR")
	private String rpcAddress;
	
	@Column(name = "ZONE")
	private String zone;
	
	@Column(name = "IS_ALIVE")
	private Boolean isAlive;

	@Column(name = "LCD_ADDR")
	private String lcdAddress;

	@Column(name = "IS_LCD_ADDR_ACTIVE")
	private Boolean isLcdAddressActive;
	
}

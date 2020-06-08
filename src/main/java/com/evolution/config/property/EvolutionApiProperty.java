package com.evolution.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("evolution")
public class EvolutionApiProperty {

	private boolean cors;
	private String[] origens;
	private boolean enableHttps;

	public boolean isCors() {
		return cors;
	}

	public void setCors(boolean cors) {
		this.cors = cors;
	}

	public String[] getOrigens() {
		return origens;
	}

	public void setOrigens(String[] origens) {
		this.origens = origens;
	}

	public boolean isEnableHttps() {
		return enableHttps;
	}

	public void setEnableHttps(boolean enableHttps) {
		this.enableHttps = enableHttps;
	}

}

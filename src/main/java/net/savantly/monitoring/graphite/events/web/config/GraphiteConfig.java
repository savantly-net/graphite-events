package net.savantly.monitoring.graphite.events.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("graphite")
public class GraphiteConfig {

	private String host;
	private int carbonPort;
	private int webPort;
	private String prefix;
	private boolean showRecentImage;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getCarbonPort() {
		return carbonPort;
	}
	public void setCarbonPort(int port) {
		this.carbonPort = port;
	}
	public int getWebPort() {
		return webPort;
	}
	public void setWebPort(int webPort) {
		this.webPort = webPort;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public boolean isShowRecentImage() {
		return showRecentImage;
	}
	public void setShowRecentImage(boolean showRecentImage) {
		this.showRecentImage = showRecentImage;
	}
	
}

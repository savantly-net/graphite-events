package net.savantly.monitoring.graphite.events.web.metrics;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.savantly.graphite.CarbonMetric;
import net.savantly.graphite.GraphiteClient;
import net.savantly.graphite.GraphiteClientFactory;
import net.savantly.graphite.impl.SimpleCarbonMetric;
import net.savantly.monitoring.graphite.events.web.config.GraphiteConfig;

@Service
public class MetricService {
	
	@Autowired
	GraphiteConfig graphiteConfig;
	GraphiteClient graphiteClient;
	@Value("${graphite.prefix}")
	String prefix;

	@PostConstruct
	public void post() throws UnknownHostException, SocketException{
		graphiteClient = GraphiteClientFactory.defaultGraphiteClient(graphiteConfig.getHost(), graphiteConfig.getCarbonPort());
	}

	/***
	 * Store a metric value at the bottom of every minute for the specified duration.
	 * 
	 * @param metricName
	 * @param value
	 * @param startDateTime
	 * @param durationInMinutes
	 */
	public void addGraphiteEvent(String metricName, Float value, DateTime startDateTime, int durationInMinutes) {
		int year = startDateTime.getYear();
		int monthOfYear = startDateTime.getMonthOfYear();
		int dayOfMonth = startDateTime.getDayOfMonth();
		int hourOfDay = startDateTime.getHourOfDay();
		int minuteOfHour = startDateTime.getMinuteOfHour();
		DateTime start = new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour);
		ArrayList<CarbonMetric> metrics = new ArrayList<CarbonMetric>(durationInMinutes);
		
		String name = ensurePrefix(metricName);	
		
		int i = 0;
		do {
			metrics.add(new SimpleCarbonMetric(name, value.toString(), start.plusMinutes(i).getMillis()/1000));
		}
		while ( ++i < durationInMinutes);
			
		graphiteClient.saveCarbonMetrics(metrics);
	}
	
	private String ensurePrefix(String name) {
		if(name.startsWith(prefix)) return name;
		else {
			return prefix + name;
		}
	}

}

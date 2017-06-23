package net.savantly.monitoring.graphite.events.web.rest;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.savantly.monitoring.graphite.events.web.domain.EventItem;
import net.savantly.monitoring.graphite.events.web.metrics.MetricService;

@RestController
@RequestMapping("/rest/health")
@ConfigurationProperties("health")
public class HealthAPI {
	
	@Autowired
	MetricService metricService;
	
	List<String> eventNames;
	
	@RequestMapping(value="/event/names", method=RequestMethod.GET)
	public List<String> apiGetEventNames(){
		return eventNames;
	}
	
	@RequestMapping(value="/event", method=RequestMethod.POST)
	public void setEvent(@RequestBody EventItem body){
		float value = body.getValue();
		if(0 > value || 1 < value){
			throw new RuntimeException("value must be between 0 and 1");
		}
			
		metricService.addGraphiteEvent(body.getName(), body.getValue(), new DateTime(body.getStartDateTime()), body.getDurationInMinutes());
	}

	public List<String> getEventNames() {
		return eventNames;
	}

	public void setEventNames(List<String> eventNames) {
		this.eventNames = eventNames;
	}

}

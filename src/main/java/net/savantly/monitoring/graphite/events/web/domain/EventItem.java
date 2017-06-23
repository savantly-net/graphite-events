package net.savantly.monitoring.graphite.events.web.domain;

public class EventItem {
	
	private String name;
	private float value;
	private int durationInMinutes;
	private long startDateTime;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public int getDurationInMinutes() {
		return durationInMinutes;
	}
	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
	public long getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(long startDateTime) {
		this.startDateTime = startDateTime;
	}
}

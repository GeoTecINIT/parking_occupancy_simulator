package simulation.model.support;

import javax.xml.bind.annotation.XmlAttribute;

public class Timing implements Comparable<Timing>{	
	@XmlAttribute(name="weekDay", required = true)
	private int weekDay;
	@XmlAttribute(name="hour", required = true)
	private int hour;
	@XmlAttribute(name="minute", required = true)
	private int minute;
	@XmlAttribute(name="second", required = true)
	private int second;
	
	public static final Timing NULL = new Timing();
	public static final Timing ZERO = new Timing(0, 0, 0, 0);
	
	public Timing(){}

	public Timing(int weekDay, int hour, int minute, int second) {
		super();
		this.weekDay = weekDay;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public int getWeekDay() {
		return weekDay;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}
	
	public Timing getAddedTime(Timing added){
		return this.getAddedTime(added.weekDay, added.hour, added.minute, added.second);
	}
	
	public Timing getAddedTime(int weekDay, int hour, int minute, int second){
		int resultSecond = (this.second + second) % 60;
		int excessSecond = (this.second + second) / 60;
		int resultMinute = (this.minute + minute + excessSecond) % 60;
		int excessMinute = (this.minute + minute + excessSecond) / 60;
		int resultHour = (this.hour + hour + excessMinute) % 24;
		int excessHour = (this.hour + hour + excessMinute) / 24;
		int resultWeekDay = (this.weekDay + weekDay + excessHour) % 7;
		return new Timing(resultWeekDay, resultHour, resultMinute, resultSecond);
	}

	public int compareTo(Timing o) {
		if ((this.weekDay - o.weekDay) != 0) return (this.weekDay - o.weekDay);
		if ((this.hour - o.hour) != 0) return (this.hour - o.hour);
		if ((this.minute - o.minute) != 0) return (this.minute - o.minute);
		return (this.second - o.second);
	}
	
	public static Timing fromBaseTime(double time){
		long remainder = (long)time;
		int seconds = (int)(remainder % 60);
		remainder = remainder / 60;
		int minutes = (int)(remainder % 60);
		remainder = remainder / 60;
		int hours = (int)(remainder % 24);
		int days = (int)(remainder / 24);
		days = days % 7;
		return new Timing(days, hours, minutes, seconds);
	}
	
	public static double toBaseTime(Timing time){
		long hours = time.weekDay * 24 + time.hour;
		long minuts = hours * 60 + time.minute;
		long seconds = minuts * 60 + time.second;
		return seconds;
	}
	
	public static Timing getBasicUnit(){
		return new Timing(0, 0, 0, 1);
	}
	
	@Override
	public Timing clone(){
		return new Timing(weekDay, hour, minute, second);
	}
	
	@Override
	public String toString() {
		return "D:" + weekDay + " H:" + hour + " M:" + minute + " S:" + second;
	}
}

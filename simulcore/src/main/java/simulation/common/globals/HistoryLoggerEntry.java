package simulation.common.globals;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HistoryLoggerEntry implements Comparable<HistoryLoggerEntry>{
	@XmlElement
	private String text;
	@XmlElement
	private long time;
	
	public HistoryLoggerEntry(){}
	
	public HistoryLoggerEntry(String text, long time) {
		super();
		this.text = text;
		this.time = time;
	}
	
	public String getText() {
		return text;
	}
	
	public long getTime() {
		return time;
	}

	@Override
	public int compareTo(HistoryLoggerEntry o) {
		return (int)(this.time - o.time);
	}
}

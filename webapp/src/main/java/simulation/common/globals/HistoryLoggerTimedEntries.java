package simulation.common.globals;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class HistoryLoggerTimedEntries {
	private List<HistoryLoggerEntry> entries;
	private long time;
	
	public HistoryLoggerTimedEntries(){}
	
	public HistoryLoggerTimedEntries(List<HistoryLoggerEntry> entries, long time) {
		super();
		this.entries = entries;
		this.time = time;
	}
	
	@XmlElement
	public List<HistoryLoggerEntry> getEntries() {
		return entries;
	}
	
	@XmlElement
	public long getTime() {
		return time;
	}
	
	@XmlElement
	public int getSize(){
		if (entries != null){
			return entries.size();
		}
		return 0;
	}
}

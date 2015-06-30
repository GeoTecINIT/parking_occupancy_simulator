package simulation.common.globals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HistoryLogger {
	private List<HistoryLoggerEntry> entries;
	
    private HistoryLogger() {
    	entries = new LinkedList<HistoryLoggerEntry>();
    }
    
    public synchronized void add(HistoryLoggerEntry entry){
    	entries.add(entry);
    }
    
    public synchronized HistoryLoggerTimedEntries getEntries(long time){
    	List<HistoryLoggerEntry> resultingList = new ArrayList<HistoryLoggerEntry>();
    	long maxTime = 0;
    	for (HistoryLoggerEntry historyLoggerEntry : entries) {
			if (historyLoggerEntry.getTime() > time){
				resultingList.add(historyLoggerEntry);
				if (maxTime < historyLoggerEntry.getTime()){
					maxTime = historyLoggerEntry.getTime();
				}
			}
		}
    	return new HistoryLoggerTimedEntries(resultingList, maxTime);
    }
    
	private static volatile HistoryLogger instance = null;
 
    public static HistoryLogger getInstance() {
        if (instance == null) {
            synchronized (HistoryLogger.class) {
                if (instance == null) {
                    instance = new HistoryLogger();
                }
            }
        }
        return instance;
    }
}

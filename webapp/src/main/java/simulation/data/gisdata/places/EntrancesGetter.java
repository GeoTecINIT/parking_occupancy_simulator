package simulation.data.gisdata.places;

import java.util.ArrayList;
import java.util.List;

import simulation.model.support.EntrancePoint;

public class EntrancesGetter {
	private static volatile EntrancesGetter instance = null;
	
	private EntrancesGetter() {}
	
	public static EntrancesGetter getInstance() {
        if (instance == null) {
            synchronized (EntrancesGetter.class) {
                if (instance == null) {
                    instance = new EntrancesGetter();
                }
            }
        }
        return instance;
    }
	
	public List<EntrancePoint> getEntries(){
		List<EntrancePoint> list = new ArrayList<EntrancePoint>();
		list.add(new EntrancePoint(1, "Norte", -7779.282, 4865573.718));
		list.add(new EntrancePoint(2, "Sur", -7347.893, 4864638.107));
		list.add(new EntrancePoint(5, "PrincipalNorte", -7182.620, 4864943.441));
		list.add(new EntrancePoint(7, "PrincipalSur", -7241.446, 4864851.001));
		list.add(new EntrancePoint(6, "Noreste", -7069.765, 4865159.601));
		return list;
	}
}

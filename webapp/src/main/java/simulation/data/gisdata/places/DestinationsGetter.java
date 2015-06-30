package simulation.data.gisdata.places;

import java.util.ArrayList;
import java.util.List;

import simulation.model.support.BuildingEntranceDestination;

public class DestinationsGetter {
	private static volatile DestinationsGetter instance = null;
	
	private DestinationsGetter() {}
	
	public static DestinationsGetter getInstance() {
        if (instance == null) {
            synchronized (DestinationsGetter.class) {
                if (instance == null) {
                    instance = new DestinationsGetter();
                }
            }
        }
        return instance;
    }
	
	public List<BuildingEntranceDestination> getEntries(){
		List<BuildingEntranceDestination> list = new ArrayList<BuildingEntranceDestination>();
		// Hacer que la decripcion coincida con la del agent.xml y quitarla de allí
		list.add(new BuildingEntranceDestination(1, 1, 1, "Raquetas", -7668.217900, 4865411.543100));
		list.add(new BuildingEntranceDestination(2, 1, 2, "Raquetas", -7789.794100, 4865479.938000));
		list.add(new BuildingEntranceDestination(3, 1, 3, "Raquetas", -7701.952300, 4865369.209700));
		list.add(new BuildingEntranceDestination(4, 2, 4, "EspaitecI-II", -8210.563900, 4865051.466800));
		list.add(new BuildingEntranceDestination(5, 2, 5, "EspaitecI-II", -8133.777500, 4864997.865000));
		list.add(new BuildingEntranceDestination(6, 3, 6, "Pabellon", -7859.232700, 4865096.717200));
		list.add(new BuildingEntranceDestination(7, 3, 7, "Pabellon", -7973.091500, 4865126.966000));
		list.add(new BuildingEntranceDestination(8, 4, 8, "ESTCE", -7646.146600, 4865016.843200));
		list.add(new BuildingEntranceDestination(9, 4, 9, "ESTCE", -7632.430700, 4864898.886400));
		list.add(new BuildingEntranceDestination(10, 4, 10, "ESTCE", -7587.168200, 4864983.239200));
		list.add(new BuildingEntranceDestination(11, 4, 11, "ESTCE", -7688.323000, 4864924.946600));
		list.add(new BuildingEntranceDestination(12, 5, 12, "FCJE", -7357.943900, 4865125.209300));
		list.add(new BuildingEntranceDestination(13, 6, 13, "Dep-Pisc", -7910.833500, 4865404.033000));
		list.add(new BuildingEntranceDestination(14, 6, 14, "Dep-Pisc", -8081.159400, 4865062.653700));
		list.add(new BuildingEntranceDestination(15, 6, 15, "Dep-Pisc", -8156.168900, 4865147.188200));
		list.add(new BuildingEntranceDestination(16, 2, 16, "EspaitecI-II", -8163.749300, 4865011.694800));
		list.add(new BuildingEntranceDestination(17, 2, 17, "EspaitecI-II", -8227.249400, 4865022.013600));
		list.add(new BuildingEntranceDestination(18, 7, 18, "FCHS", -7907.588000, 4865327.828500));
		list.add(new BuildingEntranceDestination(19, 7, 19, "FCHS", -7943.042200, 4865323.066000));
		list.add(new BuildingEntranceDestination(20, 7, 20, "FCHS", -7955.213100, 4865299.253400));
		list.add(new BuildingEntranceDestination(21, 7, 21, "FCHS", -7941.454700, 4865269.090800));
		list.add(new BuildingEntranceDestination(22, 7, 22, "FCHS", -7842.500300, 4865317.245200));
		list.add(new BuildingEntranceDestination(23, 7, 23, "FCHS", -7814.454500, 4865305.603400));
		list.add(new BuildingEntranceDestination(24, 7, 24, "FCHS", -7787.466900, 4865290.786700));
		list.add(new BuildingEntranceDestination(25, 7, 25, "FCHS", -7846.733700, 4865182.836500));
		list.add(new BuildingEntranceDestination(26, 7, 26, "FCHS", -7873.721200, 4865197.653200));
		list.add(new BuildingEntranceDestination(27, 7, 27, "FCHS", -7899.121300, 4865212.469900));
		list.add(new BuildingEntranceDestination(28, 7, 28, "FCHS", -7808.104500, 4865231.519900));
		list.add(new BuildingEntranceDestination(29, 9, 29, "Rect-Paran", -7632.949900, 4865244.220000));
		list.add(new BuildingEntranceDestination(30, 9, 30, "Rect-Paran", -7582.149800, 4865257.978300));
		list.add(new BuildingEntranceDestination(31, 9, 31, "Rect-Paran", -7652.000000, 4865296.607600));
		list.add(new BuildingEntranceDestination(32, 9, 32, "Rect-Paran", -7595.908200, 4865310.895100));
		list.add(new BuildingEntranceDestination(33, 9, 33, "Rect-Paran", -7629.245800, 4865346.349300));
		list.add(new BuildingEntranceDestination(34, 9, 34, "Rect-Paran", -7537.699700, 4865336.824300));
		list.add(new BuildingEntranceDestination(35, 5, 35, "FCJE", -7511.241400, 4865287.611700));
		list.add(new BuildingEntranceDestination(36, 5, 36, "FCJE", -7542.991400, 4865227.815800));
		list.add(new BuildingEntranceDestination(37, 5, 37, "FCJE", -7455.678700, 4865216.703200));
		list.add(new BuildingEntranceDestination(38, 5, 38, "FCJE", -7408.053700, 4865193.419900));
		list.add(new BuildingEntranceDestination(39, 5, 39, "FCJE", -7323.916000, 4865187.069800));
		list.add(new BuildingEntranceDestination(40, 5, 40, "FCJE", -7275.761700, 4865115.632200));
		list.add(new BuildingEntranceDestination(41, 5, 41, "FCJE", -7229.724100, 4865091.290500));
		list.add(new BuildingEntranceDestination(42, 5, 42, "FCJE", -7173.103200, 4865057.952900));
		list.add(new BuildingEntranceDestination(43, 8, 43, "Biblioteca", -7725.554300, 4865076.473800));
		list.add(new BuildingEntranceDestination(44, 8, 44, "Biblioteca", -7726.612600, 4865028.319500));
		list.add(new BuildingEntranceDestination(45, 4, 45, "ESTCE", -7573.815400, 4864919.575600));
		list.add(new BuildingEntranceDestination(46, 4, 46, "ESTCE", -7411.890100, 4864830.146200));
		list.add(new BuildingEntranceDestination(47, 4, 47, "ESTCE", -7475.919400, 4864912.167200));
		list.add(new BuildingEntranceDestination(48, 4, 48, "ESTCE", -7543.123700, 4864848.137900));
		list.add(new BuildingEntranceDestination(49, 4, 49, "ESTCE", -7494.440300, 4864820.621200));
		list.add(new BuildingEntranceDestination(50, 4, 50, "ESTCE", -7319.814900, 4864777.758600));
		list.add(new BuildingEntranceDestination(51, 4, 51, "ESTCE", -7384.373400, 4864757.121100));
		list.add(new BuildingEntranceDestination(52, 4, 52, "ESTCE", -7337.806600, 4864843.375400));
		list.add(new BuildingEntranceDestination(53, 11, 53, "Inv-Tall-EI1", -7425.119300, 4864599.429100));
		list.add(new BuildingEntranceDestination(54, 11, 54, "Inv-Tall-EI1", -7437.819300, 4864637.529200));
		list.add(new BuildingEntranceDestination(55, 11, 55, "Inv-Tall-EI1", -7462.161100, 4864593.079100));
		list.add(new BuildingEntranceDestination(56, 11, 56, "Inv-Tall-EI1", -7474.331900, 4864630.650000));
		list.add(new BuildingEntranceDestination(57, 11, 57, "Inv-Tall-EI1", -7589.690500, 4864710.554300));
		list.add(new BuildingEntranceDestination(58, 11, 58, "Inv-Tall-EI1", -7609.798800, 4864674.041700));
		list.add(new BuildingEntranceDestination(59, 11, 59, "Inv-Tall-EI1", -7717.219900, 4864797.867000));
		list.add(new BuildingEntranceDestination(60, 11, 60, "Inv-Tall-EI1", -7761.140800, 4864755.004400));
		list.add(new BuildingEntranceDestination(61, 10, 61, "Postgrado-ITC", -7263.180800, 4865311.093500));
		list.add(new BuildingEntranceDestination(62, 10, 62, "Postgrado-ITC", -7199.680600, 4865311.093500));
		list.add(new BuildingEntranceDestination(63, 10, 63, "Postgrado-ITC", -7249.422400, 4865359.777000));
		list.add(new BuildingEntranceDestination(64, 10, 64, "Postgrado-ITC", -7422.989400, 4865403.168700));
		list.add(new BuildingEntranceDestination(65, 10, 65, "Postgrado-ITC", -7365.839300, 4865394.702000));
		list.add(new BuildingEntranceDestination(66, 12, 66, "EI2-Guard-Resid", -8119.374100, 4864889.876000));
		list.add(new BuildingEntranceDestination(67, 12, 67, "EI2-Guard-Resid", -8162.765900, 4864893.051000));
		list.add(new BuildingEntranceDestination(68, 12, 68, "EI2-Guard-Resid", -7939.457100, 4864903.634400));
		list.add(new BuildingEntranceDestination(69, 13, 69, "InfoCampus", -7735.992100, 4865010.261700));
		list.add(new BuildingEntranceDestination(70, 13, 70, "InfoCampus", -7783.617200, 4864972.161600));
		return list;
	}
}

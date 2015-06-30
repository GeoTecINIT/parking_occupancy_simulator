package simulation.data.configs.profiles;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import org.junit.Assert;

import simulation.common.globals.RandomGenerator;

@XmlType
@XmlEnum(String.class)
public enum WeekRepetitionType {
	@XmlEnumValue ("Sunday") SUNDAY(0),
	@XmlEnumValue ("Monday") MONDAY(1),
	@XmlEnumValue ("Tuesday") TUESDAY(2),
	@XmlEnumValue ("Wednesday") WEDNESDAY(3),
	@XmlEnumValue ("Thursday") THURSDAY(4),
	@XmlEnumValue ("Friday") FRIDAY(5),
	@XmlEnumValue ("Saturday") SATURDAY(6),
	@XmlEnumValue ("AllWeek") ALL_WEEK(10),
	@XmlEnumValue ("WeekDays") WEEK_DAYS(11),
	@XmlEnumValue ("ThreeWeekDays") THREE_RANDOM(12),
	@XmlEnumValue ("One") ONE_RANDOM(13);
	
	private int value;
	private WeekRepetitionType(int value) {
	   this.value = value;
	}
	public int getValue() {
	   return value;
	}
	
	public static List<Integer> getDaysFor(WeekRepetitionType repetition){
		List<Integer> list = new ArrayList<Integer>();
		switch (repetition) {
		case ALL_WEEK:
			for (int i = 0; i < 7; i++){
				list.add(i);
			}
			break;
		case WEEK_DAYS:
			for (int i = 1; i < 6; i++){
				list.add(i);
			}
			break;
		case THREE_RANDOM:
			int i = 0;
			while(i < 3){
				int chosen = RandomGenerator.getInstance().nextInt(5);
				if (!list.contains(chosen)){
					list.add(chosen);
					i++;
				}
			}
			break;
		case ONE_RANDOM:
			int chosen = RandomGenerator.getInstance().nextInt(5);
			list.add(chosen);
			break;
		case SUNDAY:
		case MONDAY:
		case TUESDAY:
		case WEDNESDAY:
		case THURSDAY:
		case FRIDAY:
		case SATURDAY:
			list.add(repetition.getValue());
			break;
		default:
			Assert.fail("Problems");
			break;
		}
		return list;
	}
}

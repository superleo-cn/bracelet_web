package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyDateUtils {
	
	public static List<Date> getDateByDay() {
		List<Date> dates = new ArrayList<>();
		Calendar c = GregorianCalendar.getInstance();
		setTimeToBeginningOfDay(c);
		dates.add(c.getTime());
		setTimeToEndofDay(c);
		dates.add(c.getTime());
		return dates;
	}

	public static List<Date> getDateByWeek() {
		List<Date> dates = new ArrayList<>();
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		setTimeToBeginningOfDay(c);
		dates.add(c.getTime());
		c.add(Calendar.DATE, 6);
		setTimeToEndofDay(c);
		dates.add(c.getTime());
		return dates;
	}

	public static List<Date> getDateByMonth() {
		List<Date> dates = new ArrayList<>();
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		setTimeToBeginningOfDay(c);
		dates.add(c.getTime());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		setTimeToEndofDay(c);
		dates.add(c.getTime());
		return dates;
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}
}

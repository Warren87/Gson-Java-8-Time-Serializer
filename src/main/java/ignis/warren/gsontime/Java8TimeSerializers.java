package ignis.warren.gsontime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;

class Java8TimeSerializers {

	static Map<Class<? extends TemporalAccessor>, TemporalAccessorSerializer<?>> SERIALIZERS_MAP = new HashMap<>();

	static {
		SERIALIZERS_MAP.put(Instant.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_INSTANT, Instant::from));
		SERIALIZERS_MAP.put(LocalTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_LOCAL_TIME, LocalTime::from));
		SERIALIZERS_MAP.put(LocalDate.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_LOCAL_DATE, LocalDate::from));
		SERIALIZERS_MAP.put(LocalDateTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_LOCAL_DATE_TIME, LocalDateTime::from));
		SERIALIZERS_MAP.put(ZonedDateTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_ZONED_DATE_TIME, ZonedDateTime::from));
		SERIALIZERS_MAP.put(MonthDay.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ofPattern("MM-dd"), MonthDay::from));
		SERIALIZERS_MAP.put(OffsetDateTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_OFFSET_DATE_TIME, OffsetDateTime::from));
		SERIALIZERS_MAP.put(OffsetTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_OFFSET_TIME, OffsetTime::from));
		SERIALIZERS_MAP.put(YearMonth.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ofPattern("yyyy-MM"), YearMonth::from));
		SERIALIZERS_MAP.put(Year.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ofPattern("yyyy"), Year::from));
	}

}

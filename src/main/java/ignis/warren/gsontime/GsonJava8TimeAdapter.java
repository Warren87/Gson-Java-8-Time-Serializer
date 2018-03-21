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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonJava8TimeAdapter {

	private static final Map<Class<? extends TemporalAccessor>, TemporalAccessorSerializer<?>> JAVA_8_TIME_SERIALIZERS = new HashMap<>();

	private final Map<Class<? extends TemporalAccessor>, TemporalAccessorSerializer<?>> instanceMap = new HashMap<>();

	static {
		JAVA_8_TIME_SERIALIZERS.put(Instant.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_INSTANT, Instant::from));
		JAVA_8_TIME_SERIALIZERS.put(LocalTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_LOCAL_TIME, LocalTime::from));
		JAVA_8_TIME_SERIALIZERS.put(LocalDate.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_LOCAL_DATE, LocalDate::from));
		JAVA_8_TIME_SERIALIZERS.put(LocalDateTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_LOCAL_DATE_TIME, LocalDateTime::from));
		JAVA_8_TIME_SERIALIZERS.put(ZonedDateTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_ZONED_DATE_TIME, ZonedDateTime::from));
		JAVA_8_TIME_SERIALIZERS.put(MonthDay.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ofPattern("MM-dd"), MonthDay::from));
		JAVA_8_TIME_SERIALIZERS.put(OffsetDateTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_OFFSET_DATE_TIME, OffsetDateTime::from));
		JAVA_8_TIME_SERIALIZERS.put(OffsetTime.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ISO_OFFSET_TIME, OffsetTime::from));
		JAVA_8_TIME_SERIALIZERS.put(YearMonth.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ofPattern("yyyy-MM"), YearMonth::from));
		JAVA_8_TIME_SERIALIZERS.put(Year.class,
				new TemporalAccessorSerializer<>(DateTimeFormatter.ofPattern("yyyy"), Year::from));
	}

	private GsonJava8TimeAdapter() {
		instanceMap.putAll(JAVA_8_TIME_SERIALIZERS);
	}

	public static Gson create() {
		return register(new GsonBuilder()).create();
	}

	public static GsonBuilder register(GsonBuilder gsonBuilder) {
		return new GsonJava8TimeAdapter().registerAllSerializers(gsonBuilder, Collections.emptyMap());
	}

	public static Gson create(Map<Class<? extends TemporalAccessor>, DateTimeFormatter> formatMap) {
		Objects.nonNull(formatMap);
		return createBuilder(formatMap).create();
	}

	public static GsonBuilder createBuilder() {
		return createBuilder(Collections.emptyMap());
	}

	public static GsonBuilder createBuilder(Map<Class<? extends TemporalAccessor>, DateTimeFormatter> formatMap) {
		return new GsonJava8TimeAdapter().registerAllSerializers(new GsonBuilder(), formatMap);
	}

	private GsonBuilder registerAllSerializers(GsonBuilder gsonBuilder,
			Map<Class<? extends TemporalAccessor>, DateTimeFormatter> formatMap) {

		formatMap.forEach(this::overrideFormatter);

		instanceMap.forEach(gsonBuilder::registerTypeAdapter);
		return gsonBuilder;
	}

	private void overrideFormatter(Class<? extends TemporalAccessor> accessor, DateTimeFormatter formatter) {
		final TemporalAccessorSerializer<?> overrideFormatter = instanceMap.get(accessor)
				.overrideFormatter(formatter);
		instanceMap.put(accessor, overrideFormatter);
	}

}

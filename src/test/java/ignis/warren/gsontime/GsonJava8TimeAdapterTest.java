package ignis.warren.gsontime;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonJava8TimeAdapterTest {

	private Gson gson;

	@Before
	public void before() {
		gson = GsonJava8TimeAdapter.create();
	}

	@Test
	public void shouldGenerateGson() throws Exception {
		assertNotNull(gson);
	}

	@Test
	public void shouldDeserializeInstantAsFormated() throws Exception {
		final String instant = "2007-12-03T10:15:30.00Z";
		final String json = gson
				.toJson(Instant.parse(instant));
		assertEquals("\"2007-12-03T10:15:30Z\"", json);
	}

	@Test
	public void shouldSerializeInstantProperly() throws Exception {
		final Instant expected = Instant.parse("2007-12-03T10:15:30Z");
		final Instant instant = gson
				.fromJson("\"2007-12-03T10:15:30Z\"", Instant.class);
		assertEquals(expected, instant);
	}

	@Test
	public void shouldBeSymetricSerialization() throws Exception {
		final Instant expected = Instant.parse("2007-12-03T10:15:30Z");
		final Instant serializedInstant = gson.fromJson(gson.toJson(expected), Instant.class);
		assertEquals(expected, serializedInstant);
	}

	@Test
	public void shouldBePossibleToChangeFormat() throws Exception {
		final Gson gson = GsonJava8TimeAdapter
				.create(singletonMap(LocalDate.class, DateTimeFormatter.ofPattern("yyyyMMdd")));

		final LocalDate instant = gson.fromJson("20070101", LocalDate.class);

		assertEquals(LocalDate.parse("2007-01-01"), instant);

		final String json = gson.toJson(instant);
		assertEquals("\"20070101\"", json);
	}

	@Test
	public void displayAllNormalParsing() throws Exception {
		final Gson gsonPrinter = new GsonBuilder().create();

		final StringJoiner joiner = new StringJoiner("\n", "===\n", "\n===")
				.add("LocalDate: " + gsonPrinter.toJson(LocalDate.now()))
				.add("LocalDateTime: " + gsonPrinter.toJson(LocalDateTime.now()))
				.add("LocalTime: " + gsonPrinter.toJson(LocalTime.now()))
				.add("Instant: " + gsonPrinter.toJson(Instant.now()))
				.add("ZoneDateTime: " + gsonPrinter.toJson(ZonedDateTime.now()))
				.add("Month: " + gsonPrinter.toJson(Month.from(LocalDateTime.now())))
				.add("MonthDay: " + gsonPrinter.toJson(MonthDay.now()))
				.add("OffsetDateTime: " + gsonPrinter.toJson(OffsetDateTime.now()))
				.add("OffsetTime: " + gsonPrinter.toJson(OffsetTime.now()))
				.add("Year: " + gsonPrinter.toJson(Year.now()))
				.add("YearMonth: " + gsonPrinter.toJson(YearMonth.now()));

		System.out.println(joiner);
	}

	@Test
	public void displayAllNewParsing() throws Exception {
		final Gson gsonPrinter = GsonJava8TimeAdapter.create();

		final StringJoiner joiner = new StringJoiner("\n", "===\n", "\n===")
				.add("LocalDate: " + gsonPrinter.toJson(LocalDate.now()))
				.add("LocalDateTime: " + gsonPrinter.toJson(LocalDateTime.now()))
				.add("LocalTime: " + gsonPrinter.toJson(LocalTime.now()))
				.add("Instant: " + gsonPrinter.toJson(Instant.now()))
				.add("ZoneDateTime: " + gsonPrinter.toJson(ZonedDateTime.now()))
				.add("Month: " + gsonPrinter.toJson(Month.from(LocalDateTime.now())))
				.add("MonthDay: " + gsonPrinter.toJson(MonthDay.now()))
				.add("OffsetDateTime: " + gsonPrinter.toJson(OffsetDateTime.now()))
				.add("OffsetTime: " + gsonPrinter.toJson(OffsetTime.now()))
				.add("Year: " + gsonPrinter.toJson(Year.now()))
				.add("YearMonth: " + gsonPrinter.toJson(YearMonth.now()));

		System.out.println(joiner);
	}

}

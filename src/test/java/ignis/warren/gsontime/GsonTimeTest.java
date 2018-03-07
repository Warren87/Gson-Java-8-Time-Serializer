package ignis.warren.gsontime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;

import org.junit.Test;

import com.google.gson.Gson;

public class GsonTimeTest {

	@Test
	public void shouldGenerateGson() throws Exception {
		final Gson gson = GsonTime.create();
		assertNotNull(gson);
	}

	@Test
	public void shouldDeserializeInstantAsFormated() throws Exception {
		final String instant = "2007-12-03T10:15:30.00Z";
		final String json = GsonTime.create()
				.toJson(Instant.parse(instant));
		assertEquals(instant, json);
	}

}

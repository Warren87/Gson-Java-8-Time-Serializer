package ignis.warren.gsontime;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public enum InstantConverter implements JsonSerializer<Instant> {

	INSTANCE;

	final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSSZ");

	public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(FORMATTER.format(src));
	}

}

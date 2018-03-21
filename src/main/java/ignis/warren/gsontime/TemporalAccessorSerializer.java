package ignis.warren.gsontime;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class TemporalAccessorSerializer<T extends TemporalAccessor> implements JsonSerializer<T>, JsonDeserializer<T> {

	DateTimeFormatter formatter;

	TemporalQuery<T> query;

	TemporalAccessorSerializer(DateTimeFormatter formatter, TemporalQuery<T> temporalQuery) {
		this.formatter = formatter;
		this.query = temporalQuery;
	}

	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(formatter.format(src));
	}

	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return formatter.parse(json.getAsString(), query);
	}

	TemporalAccessorSerializer<T> overrideFormatter(DateTimeFormatter dateTimeFormatter) {
		return new TemporalAccessorSerializer<>(dateTimeFormatter, query);
	}

}

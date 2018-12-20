package ignis.warren.gsontime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GsonJava8TimeAdapter {


    private final Map<Class<? extends TemporalAccessor>, TemporalAccessorSerializer<?>> instanceMap = new HashMap<>();

    private GsonJava8TimeAdapter() {
        instanceMap.putAll(Java8TimeSerializers.SERIALIZERS_MAP);
    }

    public static Gson create() {
        return register(new GsonBuilder()).create();
    }

    public static GsonBuilder register(GsonBuilder gsonBuilder) {
        return new GsonJava8TimeAdapter().registerAllSerializers(gsonBuilder, Collections.emptyMap());
    }

    public static Gson create(Map<Class<? extends TemporalAccessor>, DateTimeFormatter> formatMap) {
        Objects.requireNonNull(formatMap);
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

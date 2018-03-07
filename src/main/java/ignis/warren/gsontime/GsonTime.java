package ignis.warren.gsontime;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTime {

	public static Gson create() {
		return new GsonBuilder().registerTypeAdapter(Instant.class, InstantConverter.INSTANCE)
				.create();
	}

}

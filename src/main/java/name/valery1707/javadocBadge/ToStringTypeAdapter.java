package name.valery1707.javadocBadge;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ToStringTypeAdapter extends TypeAdapter<Object> {
	@Override
	public void write(JsonWriter out, Object value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			out.value(value.toString());
		}
	}

	@Override
	public Object read(JsonReader in) throws IOException {
		return null;
	}
}

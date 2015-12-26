package name.valery1707.javadocBadge;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Utils {

	public static final CacheControl JSON_CACHE_CONTROL = CacheControl.noCache().sMaxAge(0, TimeUnit.DAYS).mustRevalidate();

	private Utils() {
	}

	public static ResponseEntity<String> buildJsonResponse(Object value) {
		return ResponseEntity.ok()
				.header("Cache-Control", JSON_CACHE_CONTROL.getHeaderValue())
				.header("Pragma", "no-cache")
				.header("Expires", "0")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(toJson(value));
	}

	private static final Pattern STR_ESCAPE_ITEMS = Pattern.compile("(\\\\|\")");

	public static String toJson(Object value) {
		if (value instanceof Boolean || value instanceof Number) {
			return value.toString();
		} else if (value instanceof CharSequence) {
			return "\"" + STR_ESCAPE_ITEMS.matcher(value.toString()).replaceAll("\\\\$1") + "\"";
		} else if (value instanceof Map) {
			Map<?, ?> map = (Map) value;
			return map.entrySet().stream()
					.map(entry -> toJson(entry.getKey().toString()) + ": " + toJson(entry.getValue()))
					.collect(Collectors.joining(", ", "{", "}"));
		} else {
			return toJson(value.toString());
		}
	}
}

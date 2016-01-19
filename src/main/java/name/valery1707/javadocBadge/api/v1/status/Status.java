package name.valery1707.javadocBadge.api.v1.status;

import com.google.gson.annotations.JsonAdapter;
import name.valery1707.javadocBadge.ToStringTypeAdapter;

import java.time.Duration;
import java.util.Map;

@SuppressWarnings("unused")
public class Status {

	private final String version;
	@JsonAdapter(ToStringTypeAdapter.class)
	private final Duration uptime;
	private final Map<String, Object> cache;
	private final Map<String, Long> memory;

	public Status(String version, Duration uptime, Map<String, Object> cache, Map<String, Long> memory) {
		this.version = version;
		this.uptime = uptime;
		this.cache = cache;
		this.memory = memory;
	}

	public String getVersion() {
		return version;
	}

	public Duration getUptime() {
		return uptime;
	}

	public Map<String, Object> getCache() {
		return cache;
	}

	public Map<String, Long> getMemory() {
		return memory;
	}
}

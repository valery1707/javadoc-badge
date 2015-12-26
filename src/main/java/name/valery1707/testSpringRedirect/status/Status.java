package name.valery1707.testSpringRedirect.status;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static name.valery1707.testSpringRedirect.Utils.toJson;

public class Status {

	private final String version;
	private final Duration uptime;
	private final Map<String, ?> cache = new HashMap<>();
	private final Map<String, Long> memory = new HashMap<>();

	public Status(String version, Duration uptime) {
		this.version = version;
		this.uptime = uptime;
		Runtime runtime = Runtime.getRuntime();
		this.memory.put("total", runtime.totalMemory());
		this.memory.put("free", runtime.freeMemory());
		this.memory.put("max", runtime.maxMemory());
		this.memory.put("used", runtime.totalMemory() - runtime.freeMemory());
	}

	public String getVersion() {
		return version;
	}

	public Duration getUptime() {
		return uptime;
	}

	public Map<String, ?> getCache() {
		return cache;
	}

	public Map<String, Long> getMemory() {
		return memory;
	}

	@Override
	public String toString() {
		Map<String, Object> presentation = new HashMap<>(4);
		presentation.put("version", getVersion());
		presentation.put("uptime", getUptime());
		presentation.put("cache", getCache());
		presentation.put("memory", getMemory());
		return toJson(presentation);
	}
}
package name.valery1707.javadocBadge.status;

import com.github.benmanes.caffeine.cache.stats.CacheStats;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static name.valery1707.javadocBadge.Utils.toJson;

public class Status {

	private final String version;
	private final Duration uptime;
	private final Map<String, Object> cache = new TreeMap<>();
	private final Map<String, Long> memory = new HashMap<>();

	public Status(String version, Duration uptime, CacheStats stats) {
		this.version = version;
		this.uptime = uptime;
		Runtime runtime = Runtime.getRuntime();
		this.memory.put("total", runtime.totalMemory());
		this.memory.put("free", runtime.freeMemory());
		this.memory.put("max", runtime.maxMemory());
		this.memory.put("used", runtime.totalMemory() - runtime.freeMemory());
		this.cache.put("averageLoadPenalty", stats.averageLoadPenalty());
		this.cache.put("evictionCount", stats.evictionCount());
		this.cache.put("hitCount", stats.hitCount());
		this.cache.put("hitRate", stats.hitRate());
		this.cache.put("loadCount", stats.loadCount());
		this.cache.put("loadFailureCount", stats.loadFailureCount());
		this.cache.put("loadFailureRate", stats.loadFailureRate());
		this.cache.put("loadSuccessCount", stats.loadSuccessCount());
		this.cache.put("missCount", stats.missCount());
		this.cache.put("missRate", stats.missRate());
		this.cache.put("requestCount", stats.requestCount());
		this.cache.put("totalLoadTime", stats.totalLoadTime());
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
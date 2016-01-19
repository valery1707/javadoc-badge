package name.valery1707.javadocBadge.api.v1.status;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import name.valery1707.javadocBadge.version.VersionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Manifest;

@Repository
public class StatusRepository {
	private static final Logger LOG = LoggerFactory.getLogger(StatusRepository.class);

	@Value("${implementationVendor}")
	private String implementationVendor;

	@Inject
	private VersionCache versionCache;

	private ZonedDateTime startAt;
	private String version;

	@PostConstruct
	public void init() {
		startAt = ZonedDateTime.now();
		version = detectVersion();
	}

	private String detectVersion() {
		Enumeration<URL> resources;
		try {
			resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
				Manifest manifest = new Manifest(url.openStream());
				String vendor = manifest.getMainAttributes().getValue("Implementation-Vendor");
				if (implementationVendor.equals(vendor)) {
					return manifest.getMainAttributes().getValue("Implementation-Version");
				}
			}
			return "Unknown: could not found manifest";
		} catch (IOException e) {
			LOG.error("Could not detect version", e);
			return "Unknown: " + e.getMessage();
		}
	}

	public String getVersion() {
		return version;
	}

	public Duration getUptime() {
		return Duration.between(startAt, ZonedDateTime.now());
	}

	public Map<String, Object> getCache() {
		CacheStats stats = versionCache.getStats();
		Map<String, Object> cache = new TreeMap<>();
		cache.put("averageLoadPenalty", stats.averageLoadPenalty());
		cache.put("evictionCount", stats.evictionCount());
		cache.put("hitCount", stats.hitCount());
		cache.put("hitRate", stats.hitRate());
		cache.put("loadCount", stats.loadCount());
		cache.put("loadFailureCount", stats.loadFailureCount());
		cache.put("loadFailureRate", stats.loadFailureRate());
		cache.put("loadSuccessCount", stats.loadSuccessCount());
		cache.put("missCount", stats.missCount());
		cache.put("missRate", stats.missRate());
		cache.put("requestCount", stats.requestCount());
		cache.put("totalLoadTime", stats.totalLoadTime());
		cache.put("estimatedSize", versionCache.estimatedSize());
		return cache;
	}

	public Map<String, Long> getMemory() {
		Runtime runtime = Runtime.getRuntime();
		Map<String, Long> memory = new HashMap<>(4);
		memory.put("total", runtime.totalMemory());
		memory.put("free", runtime.freeMemory());
		memory.put("max", runtime.maxMemory());
		memory.put("used", runtime.totalMemory() - runtime.freeMemory());
		return memory;
	}

	public Status getStatus() {
		return new Status(getVersion(), getUptime(), getCache(), getMemory());
	}
}

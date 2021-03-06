package name.valery1707.javadocBadge.version;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@Singleton
public class VersionCache {
	@Value("${cache.expireAfterWrite}")
	private String expireAfterWriteRaw;
	private Duration expireAfterWrite;

	@Value("${cache.maximumSize}")
	private int maximumSize;

	@Inject
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private List<VersionProvider> versionProviders;

	private LoadingCache<Entry<String, String>, String> versions;

	@PostConstruct
	public void init() {
		expireAfterWrite = Duration.parse(this.expireAfterWriteRaw);
		versions = Caffeine.newBuilder()
				.expireAfterWrite(expireAfterWrite.toMinutes(), TimeUnit.MINUTES)
				.maximumSize(maximumSize)
				.recordStats()
				.build(this::findActualVersion);//todo Use async
	}

	private String findActualVersion(Entry<String, String> key) {
		return versionProviders.stream()
				.map(p -> p.findActualVersion(key.getKey(), key.getValue()))
				.filter(Optional::isPresent)
				.findFirst().orElse(Optional.empty())
				.orElse("Unknown");
	}

	public String getActualVersion(String groupId, String artifactId) {
		return versions.get(new SimpleEntry<>(groupId, artifactId));
	}

	public CacheStats getStats() {
		return versions.stats();
	}

	public long estimatedSize() {
		return versions.estimatedSize();
	}

	public Duration getExpireAfterWrite() {
		return expireAfterWrite;
	}
}

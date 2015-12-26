package name.valery1707.javadocBadge.doc;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Repository
@Singleton
public class VersionCache {
	@Value("${badge.javadocURL}")
	private String javadocURL;

	private LoadingCache<Entry<String, String>, String> versions;
	private OkHttpClient client;

	@PostConstruct
	public void init() {
		versions = Caffeine.newBuilder()
				.expireAfterWrite(2, TimeUnit.HOURS)//todo Configure
				.maximumSize(10_000)
				.build(this::findActualVersion);//todo Use async
		client = new OkHttpClient();
		client.setFollowRedirects(false);
		client.setFollowSslRedirects(false);
	}

	private String findActualVersion(Entry<String, String> key) {
		String uriString = fromHttpUrl(javadocURL)
				.path("{groupId}/{versionId}")
				.build()
				.expand(key.getKey(), key.getValue())
				.toUriString();

		Request request = new Request.Builder().url(uriString).head().build();

		try {
			Response response = client.newCall(request).execute();
			String location = response.header("Location");
			if (response.isRedirect() && location != null) {
				int i = location.lastIndexOf('/');
				if (i >= 0) {
					return location.substring(i + 1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Unknown group");
	}

	public String getActualVersion(String groupId, String artifactId) {
		return versions.get(new SimpleEntry<>(groupId, artifactId));
	}
}

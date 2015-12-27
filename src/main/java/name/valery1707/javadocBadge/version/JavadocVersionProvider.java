package name.valery1707.javadocBadge.version;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
@Order(10)
public class JavadocVersionProvider implements VersionProvider {
	private static final Logger LOG = LoggerFactory.getLogger(JavadocVersionProvider.class);

	@Value("${badge.javadocURL}")
	private String javadocURL;

	private OkHttpClient client;

	@PostConstruct
	public void init() {
		client = new OkHttpClient();
		client.setFollowRedirects(false);
		client.setFollowSslRedirects(false);
	}

	@Override
	public Optional<String> findActualVersion(String groupId, String versionId) {
		String uriString = fromHttpUrl(javadocURL)
				.path("{groupId}/{versionId}")
				.build()
				.expand(groupId, versionId)
				.toUriString();

		Request request = new Request.Builder().url(uriString).head().build();

		try {
			Response response = client.newCall(request).execute();
			String location = response.header("Location");
			if (response.isRedirect() && location != null) {
				int i = location.lastIndexOf('/');
				if (i >= 0 && !location.substring(i + 1).isEmpty()) {
					return Optional.of(location.substring(i + 1));
				}
			}
		} catch (IOException e) {
			LOG.warn("Catch IO exception while search version for '{}:{}' in {}", groupId, versionId, javadocURL, e);
		}
		return Optional.empty();
	}
}

package name.valery1707.javadocBadge.api.v1.status;

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

	public Status getStatus() {
		return new Status(version, Duration.between(startAt, ZonedDateTime.now()), versionCache.getStats(), versionCache.estimatedSize());
	}
}

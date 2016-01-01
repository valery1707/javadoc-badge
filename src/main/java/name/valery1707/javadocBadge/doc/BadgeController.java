package name.valery1707.javadocBadge.doc;

import name.valery1707.javadocBadge.version.VersionCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("doc")
public class BadgeController {
	@Value("${badge.shieldsURL}")
	private String shieldsBaseURL;

	@Value("${badge.defaultColor}")
	private String badgeColor;

	@Value("${badge.subject}")
	private String badgeSubject;

	@Value("${badge.version.prefix}")
	private String badgeVersionPrefix;

	@Value("${badge.version.suffix}")
	private String badgeVersionSuffix;

	@Inject
	private VersionCache versionCache;

	@RequestMapping(value = "{groupId}/{artifactId}/badge.{ext}", method = RequestMethod.GET)
	//todo version?
	//todo HTTP/HTTPS?
	public ResponseEntity<String> badge(
			@PathVariable("groupId") String groupId,
			@PathVariable("artifactId") String artifactId,
			@PathVariable("ext") String ext,
			@RequestParam("style") Optional<String> style,
			@RequestParam("color") Optional<String> color
			, @RequestParam("subject") Optional<String> subject
			, @RequestParam("prefix") Optional<String> prefix
			, @RequestParam("suffix") Optional<String> suffix
	) {
		String version = versionCache.getActualVersion(groupId, artifactId);
		version = prefix.orElse(badgeVersionPrefix) + version + suffix.orElse(badgeVersionSuffix);
		UriComponentsBuilder shieldURIBuilder = ServletUriComponentsBuilder.fromHttpUrl(shieldsBaseURL);
		shieldURIBuilder.path("{subject}-{version}-{color}.{ext}");
		if (style.isPresent()) {
			shieldURIBuilder.queryParam("style", style.orElse(null));
		}
		Map<String, String> params = new HashMap<>(4);
		params.put("subject", badgePart(subject.orElse(badgeSubject)));
		params.put("version", badgePart(version));
		params.put("color", badgePart(color.orElse(badgeColor)));
		params.put("ext", ext);
		UriComponents shieldURI = shieldURIBuilder.build().expand(params);
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
				.header("Location", shieldURI.toUriString())
				.body(null);
	}

	private static String badgePart(String src) {
		return src.replace("-", "--");
	}
}

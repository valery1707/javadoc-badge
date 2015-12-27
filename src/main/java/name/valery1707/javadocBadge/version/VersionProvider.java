package name.valery1707.javadocBadge.version;

import java.util.Optional;

public interface VersionProvider {
	Optional<String> findActualVersion(String groupId, String versionId);
}

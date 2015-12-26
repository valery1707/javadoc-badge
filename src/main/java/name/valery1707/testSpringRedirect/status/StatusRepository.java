package name.valery1707.testSpringRedirect.status;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.ZonedDateTime;

@Repository
public class StatusRepository {

	private ZonedDateTime startAt;
	private String version;

	@PostConstruct
	public void init(){
		startAt = ZonedDateTime.now();
		version = "???";//todo Detect version
	}

	public Status getStatus() {
		return new Status(version, Duration.between(startAt, ZonedDateTime.now()));
	}
}

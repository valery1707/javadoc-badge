package name.valery1707.javadocBadge.api.v1.status;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
	@Inject
	private StatusRepository repository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Status root() {
		return repository.getStatus();
	}

	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public String version() {
		return repository.getStatus().getVersion();
	}

	@RequestMapping(value = "/uptime", method = RequestMethod.GET)
	public String uptime() {
		return repository.getStatus().getUptime().toString();
	}

	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public Map<String, Object> cache() {
		return repository.getStatus().getCache();
	}

	@RequestMapping(value = "/memory", method = RequestMethod.GET)
	public Map<String, Long> memory() {
		return repository.getStatus().getMemory();
	}
}

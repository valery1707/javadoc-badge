package name.valery1707.javadocBadge.api.v1.status;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Map;

@RestController
@CrossOrigin
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
		return repository.getVersion();
	}

	@RequestMapping(value = "/uptime", method = RequestMethod.GET)
	public String uptime() {
		return repository.getUptime().toString();
	}

	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public Map<String, Object> cache() {
		return repository.getCache();
	}

	@RequestMapping(value = "/memory", method = RequestMethod.GET)
	public Map<String, Long> memory() {
		return repository.getMemory();
	}
}

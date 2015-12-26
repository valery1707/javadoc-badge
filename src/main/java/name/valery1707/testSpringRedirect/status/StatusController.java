package name.valery1707.testSpringRedirect.status;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

import static name.valery1707.testSpringRedirect.Utils.buildJsonResponse;

@Controller
@RequestMapping("/status")
public class StatusController {
	@Inject
	private StatusRepository repository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<String> root() {
		return buildJsonResponse(repository.getStatus());
	}

	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public ResponseEntity<String> version() {
		return buildJsonResponse(repository.getStatus().getVersion());
	}

	@RequestMapping(value = "/uptime", method = RequestMethod.GET)
	public ResponseEntity<String> uptime() {
		return buildJsonResponse(repository.getStatus().getUptime());
	}

	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public ResponseEntity<String> cache() {
		return buildJsonResponse(repository.getStatus().getCache());
	}

	@RequestMapping(value = "/memory", method = RequestMethod.GET)
	public ResponseEntity<String> memory() {
		return buildJsonResponse(repository.getStatus().getMemory());
	}
}

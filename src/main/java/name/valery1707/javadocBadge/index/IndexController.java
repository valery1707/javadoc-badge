package name.valery1707.javadocBadge.index;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class IndexController {
	@Value("${githubURL}")
	private String githubURL;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> root() {
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
				.header("Location", githubURL)
				.body(null);
	}
}

package name.valery1707.testSpringRedirect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
public class Launcher {
	private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		LOG.info("Launcher started at {}", ZonedDateTime.now().format(ISO_OFFSET_DATE_TIME));
		new SpringApplicationBuilder()
				.sources(Launcher.class)
				.bannerMode(Banner.Mode.OFF)
				.build()
				.run(args);
	}
}

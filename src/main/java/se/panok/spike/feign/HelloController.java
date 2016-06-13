package se.panok.spike.feign;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HelloClient client;

	@RequestMapping("/")
	public String hello() {
		logger.info("\r\n\r\n\t---==# START #==---");
		try {
			return client.hello();
		} catch (Exception e) {
			logger.error("Top-level exception = {}", e.getClass().getSimpleName());
			logger.error("Root-cause exception = {}", ExceptionUtils.getRootCause(e).getClass().getSimpleName());
		} finally {
			logger.info("\t---==# END #==---\r\n\r\n");
		}
		return "default";
	}
}

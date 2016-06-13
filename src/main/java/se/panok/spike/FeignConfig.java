package se.panok.spike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Response;
import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	public ErrorDecoder errorDecoder() {
		logger.info("\r\n\r\n\tCreating custom ErrorDecoder.\r\n\r\n");
		return new ErrorDecoder() {

			@Override
			public Exception decode(String s, Response response) {

				System.err.println("s = " + s + ", response = " + response);

				return null;
			}

		};
	}
}

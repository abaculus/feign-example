package se.panok.spike.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import se.panok.spike.RibbonConfig;

@Configuration
@RibbonClient(name = ClientConstants.CLIENT_HELLO_SERVICE, configuration = RibbonConfig.class)
public class CustomConfig {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * Feign Retryer is not configurable and overlaps with the ribbon retry.
	 *
	 * https://github.com/spring-cloud/spring-cloud-netflix/issues/467
	 */
	@Bean
	public Retryer retryer() {
		logger.info("\r\n\r\n\tCreating custom Retryer.\r\n\r\n");
		return new Retryer() {

			@Override
			public void continueOrPropagate(final RetryableException e) {
				throw e;
			}

			@Override
			public Retryer clone() {
				return this;
			}
		};
	}
}

package se.panok.spike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.RetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.DummyPing;
import com.netflix.loadbalancer.IPing;

@Configuration
public class RibbonConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * https://github.com/spring-cloud/spring-cloud-netflix/issues/931
	 * https://github.com/spring-cloud/spring-cloud-netflix/commit/983221a7102161d79e6855d0e65d8f890cb3c3cf
	 * 
	 */
	@Bean
	public RetryHandler retryHandler(final IClientConfig config) {
		logger.info("\r\n\r\n\tCreating custom RetryHandler\r\n\r\n");
		return new CustomLoadBalancerRetryHandler(config);
	}

	@Bean
	public IPing ribbonPing(IClientConfig config) {
		logger.info("\r\n\r\n\tCreating custom NoOpPing\r\n\r\n");
		return new DummyPing();
	}
}

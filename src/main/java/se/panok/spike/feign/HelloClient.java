package se.panok.spike.feign;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(ClientConstants.CLIENT_HELLO_SERVICE)
public interface HelloClient {

	@RequestMapping(value = "/", method = GET)
	String hello();
}

package se.panok.spike;

import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.netflix.client.RetryHandler;
import com.netflix.client.Utils;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;

public class CustomLoadBalancerRetryHandler implements RetryHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	private List<Class<? extends Throwable>> retriable = Lists
			.<Class<? extends Throwable>> newArrayList(UnknownHostException.class);

	@SuppressWarnings("unchecked")
	private List<Class<? extends Throwable>> circuitRelated = Lists
			.<Class<? extends Throwable>> newArrayList(UnknownHostException.class);

	protected final int retrySameServer;
	protected final int retryNextServer;
	protected final boolean retryEnabled;

	public CustomLoadBalancerRetryHandler(IClientConfig clientConfig) {
		this.retrySameServer = clientConfig.get(CommonClientConfigKey.MaxAutoRetries,
				DefaultClientConfigImpl.DEFAULT_MAX_AUTO_RETRIES);
		this.retryNextServer = clientConfig.get(CommonClientConfigKey.MaxAutoRetriesNextServer,
				DefaultClientConfigImpl.DEFAULT_MAX_AUTO_RETRIES_NEXT_SERVER);
		this.retryEnabled = clientConfig.get(CommonClientConfigKey.OkToRetryOnAllOperations, false);

		logger.info("retrySameServer = {}", retrySameServer);
		logger.info("retryNextServer = {}", retryNextServer);
		logger.info("retryEnabled = {}", retryEnabled);
	}

	@Override
	public boolean isRetriableException(Throwable e, boolean sameServer) {
		boolean isRetriableException = false;
		if (retryEnabled) {
			isRetriableException = Utils.isPresentAsCause(e, getRetriableExceptions());
		}
		return isRetriableException;
	}

	@Override
	public boolean isCircuitTrippingException(Throwable e) {
		return Utils.isPresentAsCause(e, getCircuitRelatedExceptions());
	}

	@Override
	public int getMaxRetriesOnSameServer() {
		return retrySameServer;
	}

	@Override
	public int getMaxRetriesOnNextServer() {
		return retryNextServer;
	}

	protected List<Class<? extends Throwable>> getRetriableExceptions() {
		return retriable;
	}

	protected List<Class<? extends Throwable>> getCircuitRelatedExceptions() {
		return circuitRelated;
	}
}

package com.aem.brightedge.core.utils;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.brightedge.ixf.IXFSDKClient;
import com.brightedge.ixf.util.IXFConfiguration;
import com.brightedge.ixf.util.IXFSDKConfiguration;
import com.brightedge.ixf.util.IXFSDKParameters;

@Model(adaptables = SlingHttpServletRequest.class, 
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BrightEdgeUtil {

	private IXFConfiguration ixfConfig;

	private String headOpen;
	private String bodyString;
	private String closeString;
	private String pagePath;
	private String hostname;
	private String country;

	@Self
	private SlingHttpServletRequest request;

	@Self
	private SlingHttpServletResponse response;

	@PostConstruct
	protected void init() throws Exception {
		String currenturl = request.getRequestURL().toString();

		IXFConfiguration ixfConfig = new IXFSDKConfiguration();
		ixfConfig.setProperty(IXFConfiguration.CAPSULE_MODE, IXFConfiguration.REMOTE_PROD_CAPSULE_MODE);
		ixfConfig.setProperty(IXFConfiguration.ACCOUNT_ID, "f00000000333784");
		ixfConfig.setProperty(IXFConfiguration.API_ENDPOINT, "https://ixfd1-api.bc0a.com/");

		// BE IXF: Update CHARSET if needed
		ixfConfig.setProperty(IXFConfiguration.CHARSET, "UTF-8");

		// BE IXF: By default, all URL parameters are ignored. If you have URL
		// parameters that add value to page content. Add them to this config value,
		// separated by the pipe character (|).
		ixfConfig.setProperty(IXFConfiguration.WHITELIST_PARAMETER_LIST, "ixf");

		// Log Level
		// options:LOG_LEVEL_ALL,LOG_LEVEL_INFO,LOG_LEVEL_WARN,LOG_LEVEL_ERROR,LOG_LEVEL_DEBUG
		ixfConfig.setProperty(IXFConfiguration.LOG_LEVEL, IXFConfiguration.LOG_LEVEL_ERROR);

		// ixfConfig.setProperty(IXFConfiguration.PAGE_ALIAS_URL,"https://"+hostname+"/"+this.pagePath);
		ixfConfig.setProperty(IXFConfiguration.PAGE_ALIAS_URL, currenturl);

		// Optional parameters
		// ixfConfig.setProperty(IXFConfiguration.PROXY_HOST, hostname);
		// ixfConfig.setProperty(IXFConfiguration.PROXY_PORT, "1234");
		// ixfConfig.setProperty(IXFConfiguration.PROXY_PROTOCOL, "https");
		// ixfConfig.setProperty(IXFConfiguration.CONNECT_TIMEOUT, 2000);
		// ixfConfig.setProperty(IXFConfiguration.SOCKET_TIMEOUT, 2000);
 
 

		IXFSDKParameters parameters = new IXFSDKParameters(request);
		IXFSDKClient client = new IXFSDKClient(ixfConfig, response, parameters);

		headOpen = client.getHeadOpen();
		bodyString = client.getBodyString("body_1");
		closeString = client.close();
	}

	public IXFConfiguration getIxfConfig() {
		return ixfConfig;
	}

	public String getHeadOpen() {
		return headOpen;
	}

	public String getBodyString() {
		return bodyString;
	}

	public String getCloseString() {
		return closeString;
	}

}

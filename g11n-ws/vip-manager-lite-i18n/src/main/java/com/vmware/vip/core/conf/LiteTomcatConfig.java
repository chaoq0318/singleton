/*
 * Copyright 2019-2020 VMware, Inc.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.vmware.vip.core.conf;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vmware.vip.common.constants.ConstantsTomcat;

/**
 * this is used to the Tomcat Configuration
 *
 */
@Configuration
public class LiteTomcatConfig {

	@Bean
	public ServletWebServerFactory servletContainer(LiteServerProperties serverProperties) {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addConnectorCustomizers(new VIPLiteTomcatConnectionCustomizer(serverProperties));
		if (serverProperties.getServerScheme().equalsIgnoreCase(ConstantsTomcat.HTTP_HTTPS) ||
				serverProperties.getServerScheme().equalsIgnoreCase(ConstantsTomcat.HTTPS_HTTP)) {
			tomcat.addAdditionalTomcatConnectors(initiateHttpsConnector(serverProperties));
		}
		return tomcat;
	}

	/**
	 * create the https additional connection for tomcat
	 */
	private Connector initiateHttpsConnector(LiteServerProperties liteServerProperties) {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme(ConstantsTomcat.HTTPS);
		connector.setPort(liteServerProperties.getServerPort());
		connector.setSecure(true);
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		protocol.setSSLEnabled(true);
		protocol.setKeystoreFile(liteServerProperties.getHttpsKeyStore());
		protocol.setKeystorePass(liteServerProperties.getHttpsKeyStorePassword());
		protocol.setKeystoreType(liteServerProperties.getHttpsKeyStoreType());
		protocol.setKeyPass(liteServerProperties.getHttpsKeyPassword());
		protocol.setKeyAlias(liteServerProperties.getHttpsKeyAlias());
		protocol.setMaxHttpHeaderSize(liteServerProperties.getMaxHttpHeaderSize());
		connector.setRedirectPort(ConstantsTomcat.REDIRECT_PORT);
		connector.setAllowTrace(liteServerProperties.isAllowTrace());
		return connector;
	}

}

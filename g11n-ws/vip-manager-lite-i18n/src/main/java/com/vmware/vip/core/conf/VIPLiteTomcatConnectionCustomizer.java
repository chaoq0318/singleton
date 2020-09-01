/*
 * Copyright 2019-2020 VMware, Inc.
 * SPDX-License-Identifier: EPL-2.0
 */
package com.vmware.vip.core.conf;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;

import com.vmware.vip.common.constants.ConstantsTomcat;

/**
 * 
 * this is custom tomcat Connector
 *
 */
public class VIPLiteTomcatConnectionCustomizer implements TomcatConnectorCustomizer {
	private static Logger logger = LoggerFactory.getLogger(VIPLiteTomcatConnectionCustomizer.class);
	private LiteServerProperties liteServerProperties;

	public VIPLiteTomcatConnectionCustomizer(LiteServerProperties prop) {
		this.liteServerProperties = prop;
	}

	@Override
	public void customize(Connector connector) {
		
		if (this.liteServerProperties.getServerScheme().equalsIgnoreCase(ConstantsTomcat.HTTP_HTTPS) ||
				this.liteServerProperties.getServerScheme().equalsIgnoreCase(ConstantsTomcat.HTTPS_HTTP)) {
			logger.info("the tomcat support http and https protocol");
			connector.setPort(liteServerProperties.getHttpPort());
			connector.setAttribute("protocol", ConstantsTomcat.HTTP);
			connector.setAttribute("redirectPort", ConstantsTomcat.REDIRECT_PORT);
			connector.setAllowTrace(liteServerProperties.isAllowTrace());
			Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
			protocol.setMaxHttpHeaderSize(liteServerProperties.getMaxHttpHeaderSize());
		} else if(liteServerProperties.getServerScheme().equalsIgnoreCase(ConstantsTomcat.HTTP)){
			logger.info("the tomcat only support http protocol");
			connector.setPort(liteServerProperties.getHttpPort());
			connector.setAttribute("protocol", ConstantsTomcat.HTTP);
			connector.setAttribute("redirectPort", ConstantsTomcat.REDIRECT_PORT);
			connector.setAllowTrace(liteServerProperties.isAllowTrace());
			Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
			protocol.setMaxHttpHeaderSize(liteServerProperties.getMaxHttpHeaderSize());
		}else{
			logger.info("the tomcat only support https protocol");
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

		}

	}
}

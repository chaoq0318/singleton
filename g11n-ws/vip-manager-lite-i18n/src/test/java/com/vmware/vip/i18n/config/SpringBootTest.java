package com.vmware.vip.i18n.config;

import com.vmware.vip.common.constants.ConstantsTomcat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vmware.vip.LiteBootApplication;



public class SpringBootTest {
	private static Logger logger = LoggerFactory.getLogger(SpringBootTest.class);

	private String[] schemes = {ConstantsTomcat.HTTP_HTTPS/*, ConstantsTomcat.HTTP, ConstantsTomcat.HTTPS*/};

	@Test
	public void test001serviceswich() {
		String[] args = { "-c" };
		try {
			for (String sceme : schemes) {
				System.setProperty("server.scheme", sceme);
				LiteBootApplication.main(args);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		

		logger.info("TestSpringBoot");
	}


}

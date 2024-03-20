package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.test.api.callback.uaefts.TestUaeftsFtsCallBackApi;

@SpringBootApplication
public class WssTestApplication extends SpringBootServletInitializer {


	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(WssTestApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.NONE);
		ConfigurableApplicationContext context = springApplication.run(args);
//		WssSecurityResponseUnmarshallTestForFTSUAEFTS wssSecurityTest = context.getBean(WssSecurityResponseUnmarshallTestForFTSUAEFTS.class);
//		WssSecurityRequestTest test = context.getBean(WssSecurityRequestTest.class);
//      bean.doCallback();
//		wssSecurityTest.unmarshalTest();
		TestUaeftsFtsCallBackApi test = context.getBean(TestUaeftsFtsCallBackApi.class);
		test.doCallback();
	}
	


}
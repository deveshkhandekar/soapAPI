package com.test.api.callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import com.test.config.fts.callback.response.stub.AMLScanResponse;

@Component
public class WssSecurityResponseUnmarshallTest extends WebServiceGatewaySupport {

	private static final Logger LOG = LogManager.getLogger(WssSecurityResponseUnmarshallTest.class);
	
	public void unmarshalTest() throws IOException, SOAPException {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("com.test.config.fts.callback.stub");
		String input = """
				<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope"><soapenv:Body><ser-root:AMLScanResponse xmlns:ser-root="NBFAMLScan" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><AMLScanResponse><Response><MessageId>TF232545847861-D21234567890-BNK</MessageId><Status>SUCCESS</Status></Response></AMLScanResponse></ser-root:AMLScanResponse></soapenv:Body></soapenv:Envelope>
				""";
		InputStream is = new ByteArrayInputStream(input.getBytes());
		SOAPMessage m1 = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, is);
		SaajSoapMessage m = new SaajSoapMessage(m1);
		AMLScanResponse unmarshal = (AMLScanResponse) MarshallingUtils.unmarshal(marshaller, m);
		LOG.info(unmarshal.getAMLScanResponse().getResponse().getStatus());
	}
}

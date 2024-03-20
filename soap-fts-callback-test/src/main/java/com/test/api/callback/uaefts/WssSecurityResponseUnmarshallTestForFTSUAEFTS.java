package com.test.api.callback.uaefts;

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

import com.test.config.uaefts.fts.callback.stub.FTSAMLUpdateAMLOutput;

@Component
public class WssSecurityResponseUnmarshallTestForFTSUAEFTS extends WebServiceGatewaySupport {

	private static final Logger LOG = LogManager.getLogger(WssSecurityResponseUnmarshallTestForFTSUAEFTS.class);
	
	public void unmarshalTest() throws IOException, SOAPException {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//		marshaller.setPackagesToScan("com.test.config.uaefts.fts.callback.stub");
		marshaller.setContextPath("com.test.config.uaefts.fts.callback.stub");
		String input = """
				<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv="http://www.w3.org/2003/05/soap-envelope"><soapenv:Body><ser-root:UpdateAMLResponse xmlns:ser-root="NBFAMLFTSCallback" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><ns2:UpdateAMLResponse xmlns:ns2="https://localhost/FTSAML/"><UpdateAMLResponseResponse><UpdateAMLResponseResult><UniqueReferenceNo>1997915</UniqueReferenceNo><ResponseDatetime>2/28/2024 9:55:24 AM</ResponseDatetime><ResponseCode>0000</ResponseCode><ResponseDescription>Request  Processed Successfully</ResponseDescription></UpdateAMLResponseResult></UpdateAMLResponseResponse></ns2:UpdateAMLResponse></ser-root:UpdateAMLResponse></soapenv:Body></soapenv:Envelope>
				""";
		InputStream is = new ByteArrayInputStream(input.getBytes());
		SOAPMessage m1 = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, is);
		SaajSoapMessage m = new SaajSoapMessage(m1);
		FTSAMLUpdateAMLOutput unmarshal = (FTSAMLUpdateAMLOutput) MarshallingUtils.unmarshal(marshaller, m);
		LOG.info("Response Received :: Response Description : {} & Response Code : {} & Unique Ref No :: {}",
				unmarshal.getUpdateAMLResponse().getUpdateAMLResponseResponse().getUpdateAMLResponseResult()
						.getResponseDescription(),
				unmarshal.getUpdateAMLResponse().getUpdateAMLResponseResponse().getUpdateAMLResponseResult()
						.getResponseCode(),
				unmarshal.getUpdateAMLResponse().getUpdateAMLResponseResponse().getUpdateAMLResponseResult()
						.getUniqueReferenceNo());
	}
}

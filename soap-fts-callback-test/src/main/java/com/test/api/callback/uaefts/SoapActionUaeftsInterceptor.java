package com.test.api.callback.uaefts;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import javax.xml.transform.Source;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

public class SoapActionUaeftsInterceptor implements ClientInterceptor {
	private static final Logger LOG = LogManager.getLogger(SoapActionUaeftsInterceptor.class);
	private String action;

	public SoapActionUaeftsInterceptor(String action) {
		this.action = action;
	}

	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
		soapMessage.setSoapAction(action);
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		if (messageContext.hasResponse()) {
			WebServiceMessage response = messageContext.getResponse();
			Source payloadSource = response.getPayloadSource();
			LOG.info("payloadSource :: {}", payloadSource);
		}
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

		if (Objects.nonNull(ex)) {
			LOG.error("Exception {}", ex);
		}

		try {
			if (Objects.nonNull(messageContext.getRequest())) {
				ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
				messageContext.getRequest().writeTo(requestStream);
				String request = new String(requestStream.toByteArray(), "UTF-8");
				LOG.info("request {}", request);
			}

			if (Objects.nonNull(messageContext.getResponse())) {
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				messageContext.getResponse().writeTo(responseStream);
				String response = new String(responseStream.toByteArray(), "UTF-8");
				LOG.info("response {}", response);
			}
		} catch (Exception e) {
			LOG.error("Exception {}", e);
		}

	}

}

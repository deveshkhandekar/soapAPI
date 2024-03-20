package com.test.api.callback;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

public class SoapActionInterceptor implements ClientInterceptor {
	
	private String action;

	public SoapActionInterceptor(String action) {
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
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

	}

}

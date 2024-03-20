package com.test.api.callback.uaefts;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

import com.test.config.uaefts.fts.callback.stub.CallbackInput;
import com.test.config.uaefts.fts.callback.stub.ObjAMLReq;
import com.test.config.uaefts.fts.callback.stub.ObjectFactory;
import com.test.config.uaefts.fts.callback.stub.UpdateAMLRequest;
import com.test.config.uaefts.fts.callback.stub.UpdateAMLRequestResponse;

@Component
public class TestUaeftsFtsCallBackApi extends WebServiceGatewaySupport {

	private static final Logger LOG = LogManager.getLogger(TestUaeftsFtsCallBackApi.class);

	private final String CALL_BACK_URL = "https://testappwmdev2.nbf.ae/ws/TESTFTSCallback.TESTCallbacksoap12http/1.0";
	private final String CALL_BACK_USER_NAME = "beetle";
	private final String CALL_BACK_PASSWORD = "beetle@2023";
	private final String CERTIFICATE_PATH = "C:\\config-file\\CERT\\2way_SSL_2021.pfx";
	private final String CERTIFICATE_PASSWORD = "2Way6745!ifntT";
	private final String CALL_BACK_API_KEY = "bbbef448-bda1-44e8-b23f-ea2b220802d1";
	private final String CALL_BACK_SOAP_ACTION = "UpdateMessage";

	public void doCallback() throws Exception {
//		Properties properties = loadCallbackProperties();
		populateWebServiceTemplate();
		WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
		Date currentDate = Calendar.getInstance().getTime();
		String callBackAPIUrl = CALL_BACK_URL;
		try {
			ObjectFactory objFactory = new ObjectFactory();

			ObjAMLReq objAMLReq = objFactory.createObjAMLReq();
			objAMLReq.setResponseCode("4");
			objAMLReq.setResponseDatetime(getFormattedStringDate(currentDate, "yyyy-MM-dd HH:mm:ss.S"));
			objAMLReq.setResponseDescription("Message Clean");
			objAMLReq.setUniqueReferenceNo("1997915");

			UpdateAMLRequestResponse requestResponse = objFactory.createUpdateAMLRequestResponse();
			requestResponse.setObjAMLReq(objAMLReq);

			UpdateAMLRequest request = objFactory.createUpdateAMLRequest();
			request.setUpdateAMLResponse(requestResponse);

			CallbackInput finalInputRequest = objFactory.createFTSAMLUpdateAMLInput();
			finalInputRequest.setUpdateAMLRequest(request);

//			FTSAMLUpdateAMLOutput response = (FTSAMLUpdateAMLOutput) webServiceTemplate
//					.marshalSendAndReceive(callBackAPIUrl, finalInputRequest, new WebServiceMessageCallback() {
//
//						@Override
//						public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
//							SaajSoapMessage soapMessage = (SaajSoapMessage) message;
//		                    MimeHeaders headers = soapMessage.getSaajMessage().getMimeHeaders();
//		                    headers.addHeader(TransportConstants.HEADER_CONTENT_TYPE, SoapVersion.SOAP_12.getContentType());
//						}
//			});

			com.test.config.uaefts.fts.callback.stub.FTSAMLUpdateAMLOutput response = (com.test.config.uaefts.fts.callback.stub.FTSAMLUpdateAMLOutput) webServiceTemplate
					.marshalSendAndReceive(callBackAPIUrl, finalInputRequest);

			LOG.info("Response Received :: Response Description : {} & Response Code : {} & Unique Ref No :: {}",
					response.getUpdateAMLResponse().getUpdateAMLResponseResponse().getUpdateAMLResponseResult()
							.getResponseDescription(),
					response.getUpdateAMLResponse().getUpdateAMLResponseResponse().getUpdateAMLResponseResult()
							.getResponseCode(),
					response.getUpdateAMLResponse().getUpdateAMLResponseResponse().getUpdateAMLResponseResult()
							.getUniqueReferenceNo());
		} catch (Exception e) {
			LOG.info("Error in doCallback {}", e);
		}
	}

	private boolean populateWebServiceTemplate() {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		try {
//			Properties properties = loadCallbackProperties();

			Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

//			marshaller.setPackagesToScan("com.test.config.uaefts.fts.callback.stub");
			marshaller.setContextPath("com.test.config.uaefts.fts.callback.stub");

			Wss4jSecurityInterceptor userNameInterceptor = new Wss4jSecurityInterceptor();
			userNameInterceptor.setSecurementActions("UsernameToken");
			userNameInterceptor.setSecurementUsername(CALL_BACK_USER_NAME);
			userNameInterceptor.setSecurementPassword(CALL_BACK_PASSWORD);

			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new FileInputStream(CERTIFICATE_PATH), CERTIFICATE_PASSWORD.toCharArray());
			TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, acceptingTrustStrategy)
					.loadKeyMaterial(keyStore, CERTIFICATE_PASSWORD.toCharArray()).build();

			List<Header> headers = new ArrayList<>();
			BasicHeader authHeader = new BasicHeader("NBF-APIKey", CALL_BACK_API_KEY);
			headers.add(authHeader);
//			BasicHeader basicAuthHeader = new BasicHeader("Authorization",
//					"Basic " + Base64.getEncoder().encodeToString((properties.getProperty("UAEFTSFTS_CALL_BACK_USER_NAME") + ":" + properties.getProperty("UAEFTSFTS_CALL_BACK_PASSWORD")).getBytes()));
//			headers.add(basicAuthHeader);

			RequestDefaultHeaders reqHeader = new RequestDefaultHeaders(headers);
			HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext)
					.addInterceptorFirst(new RemoveSoapHeadersInterceptor()).addInterceptorLast(reqHeader).build();

//			HttpClient httpClient = HttpClients.custom().addInterceptorFirst(new RemoveSoapHeadersInterceptor()).addInterceptorLast(reqHeader).build();

			// UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
			// properties.getProperty("UAEFTSFTS_CALL_BACK_USER_NAME"),
			// properties.getProperty("UAEFTSFTS_CALL_BACK_PASSWORD"));

			HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender() {
				@Override
				protected HttpContext createContext(URI uri) {
					BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();

					HttpHost target = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
					AuthScope authScope = new AuthScope(target);

					credsProvider.setCredentials(authScope,
							new UsernamePasswordCredentials(CALL_BACK_USER_NAME,
									CALL_BACK_PASSWORD));

					// Create AuthCache instance
					AuthCache authCache = new BasicAuthCache();

					BasicScheme basicAuth = new BasicScheme();
					authCache.put(target, basicAuth);

					// Add AuthCache to the execution context
					HttpClientContext localContext = HttpClientContext.create();
					localContext.setAuthCache(authCache);
					localContext.setCredentialsProvider(credsProvider);

					return localContext;
				}
			};
			messageSender.setHttpClient(httpClient);
//			messageSender.setCredentials(credentials);

			webServiceTemplate.setMessageSender(messageSender);
			webServiceTemplate.setMarshaller(marshaller);
			webServiceTemplate.setUnmarshaller(marshaller);

			SoapActionUaeftsInterceptor actionInterceptor = new SoapActionUaeftsInterceptor(
					CALL_BACK_SOAP_ACTION);
			ClientInterceptor[] clientInterceptor = { userNameInterceptor, actionInterceptor };
			webServiceTemplate.setInterceptors(clientInterceptor);
			webServiceTemplate.setDefaultUri(CALL_BACK_URL);

			MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
			SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
			saajSoapMessageFactory.setSoapVersion(SoapVersion.SOAP_12);

			webServiceTemplate.setMessageFactory(saajSoapMessageFactory);

			setWebServiceTemplate(webServiceTemplate);
			LOG.info("16 WebServiceTemplate init Interceptors, Marshallers, MessageSender {} {} {}",
					Arrays.asList(webServiceTemplate.getInterceptors()), webServiceTemplate.getMarshaller(),
					webServiceTemplate.getMessageSenders());
			return true;
		} catch (Exception e) {
			LOG.error("Error in populateWebServiceTemplate {}", e);
			webServiceTemplate = new WebServiceTemplate();
		}
		return false;
	}

	private Properties loadCallbackProperties() throws Exception {
		Properties properties = new Properties();
		Iterator<Path> rootDirectories = new File("").toPath().getFileSystem().getRootDirectories().iterator();
		while (rootDirectories.hasNext()) {
			Path path = rootDirectories.next();
			String absolutePath = path.toFile().getAbsolutePath();
			File propertiesFile = new File(
					absolutePath + File.separator + "config-file" + File.separator + "ServiceConfig.properties");
			if (propertiesFile.exists()) {
				FileInputStream stream = new FileInputStream(propertiesFile);
				properties.load(stream);
				stream.close();
				break;
			}
		}
		return properties;
	}

	public static String getFormattedStringDate(Date date, String dateFormat) {
		String output = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			if (!Objects.isNull(date)) {
				output = format.format(date);
			}
		} catch (Exception e) {
			LOG.error("Error in getFormattedStringDate : ", e);
		}
		return output;
	}
}

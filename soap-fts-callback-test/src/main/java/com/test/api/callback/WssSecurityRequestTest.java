package com.test.api.callback;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;

import org.apache.http.Header;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender.RemoveSoapHeadersInterceptor;

import com.test.config.fts.callback.stub.AMLScan;
import com.test.config.fts.callback.stub.AMLScanResponse;
import com.test.config.fts.callback.stub.AmlScanRequest;
import com.test.config.fts.callback.stub.NBFAMLHEADER;
import com.test.config.fts.callback.stub.ObjectFactory;
import com.test.config.fts.callback.stub.Response;



@Component
public class WssSecurityRequestTest extends WebServiceGatewaySupport {

	private static final Logger LOG = LogManager.getLogger(WssSecurityRequestTest.class);
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
		String callbackMessage = """
					{1:F01NBFUAEAFAFUJ.SN...ISN.}{2:I103DUIBAEADXXXXN}
					{3:{108:xxxxx}{121:2d453e34-67ac-442a-bc37-16da23ef5ed5}}{4:
					:20:FT1921709193
					:23B:CRED
					:32A:190801AED500,00
					:50K:/AE12334546
					OSAMA BIN LADEN
					:53A:CBAUAEAA
					:59:/AE490240006520479855401
					OSAMA BIN LADEN
					:70:/REF/SO-1408
					:71A:SHA
					:77B:/ORDERRES/AE//TOF/TRANSFER OF FUN
					}
				""";
		String callBackAPIUrl = CALL_BACK_URL;
		try {
			ObjectFactory objFactory = new ObjectFactory();
			AMLScan scan = objFactory.createAMLScan();
			
			NBFAMLHEADER nbfAmlHeader = objFactory.createNBFAMLHEADER();
			nbfAmlHeader.setRequestorChannelId("SWIFT");
			nbfAmlHeader.setMsgFormat("AML");
			nbfAmlHeader.setMsgVersion("000");
			nbfAmlHeader.setRequestorId("NBF");
			nbfAmlHeader.setRequestorSecurityInfo("Secure");
			nbfAmlHeader.setRequestorUserId("?");
			nbfAmlHeader.setRequestorLanguage("E");
			
		
			Response requestReponse = objFactory.createResponse();
			requestReponse.setMessageData(callbackMessage);
			requestReponse.setMessageId("TF232545847861-D21234567890-BNK");
			requestReponse.setMessageType("OUT");
			requestReponse.setStatus("CLEAN");
			
			AmlScanRequest amlScanRequest = objFactory.createAmlScanRequest();
			amlScanRequest.setNBFAMLHEADER(nbfAmlHeader);
			amlScanRequest.setResponse(requestReponse);
			
			scan.setAMLScanRequest(amlScanRequest);

			AMLScanResponse response = (AMLScanResponse) webServiceTemplate.marshalSendAndReceive(callBackAPIUrl, scan);
			
			LOG.info("Response Received :: Message id : {} & Message status : {}", response.getAMLScanResponse().getResponse().getMessageId(), response.getAMLScanResponse().getResponse().getStatus());
		} catch (Exception e) {
			LOG.info("Error in doCallback {}", e);
		}
	}
	
	private boolean populateWebServiceTemplate() {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		try {
//			Properties properties = loadCallbackProperties();
			
			Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
			marshaller.setPackagesToScan("com.test.config.fts.callback.stub");
			
			Wss4jSecurityInterceptor userNameInterceptor = new Wss4jSecurityInterceptor();
	        userNameInterceptor.setSecurementActions("UsernameToken");
	        userNameInterceptor.setSecurementUsername(CALL_BACK_USER_NAME);
	        userNameInterceptor.setSecurementPassword(CALL_BACK_PASSWORD);
	        
	        KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new FileInputStream(CERTIFICATE_PATH), CERTIFICATE_PASSWORD.toCharArray());
			TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, acceptingTrustStrategy).loadKeyMaterial(keyStore, CERTIFICATE_PASSWORD.toCharArray()).build();
			
			List<Header> headers = new ArrayList<>();
	        BasicHeader authHeader = new BasicHeader("NBF-APIKey", CALL_BACK_API_KEY);
	        headers.add(authHeader);
			RequestDefaultHeaders reqHeader = new RequestDefaultHeaders(headers);
			HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).addInterceptorFirst(new RemoveSoapHeadersInterceptor()).addInterceptorLast(reqHeader).build();
			
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(CALL_BACK_USER_NAME, CALL_BACK_PASSWORD);
			
	        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
	        messageSender.setHttpClient(httpClient);
	        messageSender.setCredentials(credentials);
			
			webServiceTemplate.setMessageSender(messageSender);
			webServiceTemplate.setMarshaller(marshaller);
			webServiceTemplate.setUnmarshaller(marshaller);
			
			SoapActionInterceptor actionInterceptor = new SoapActionInterceptor(CALL_BACK_SOAP_ACTION);
			ClientInterceptor[] clientInterceptor = {userNameInterceptor, actionInterceptor};
			webServiceTemplate.setInterceptors(clientInterceptor);
			webServiceTemplate.setDefaultUri(CALL_BACK_URL);
			
		    MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
		    SaajSoapMessageFactory saajSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		    webServiceTemplate.setMessageFactory(saajSoapMessageFactory);
		    
			setWebServiceTemplate(webServiceTemplate);
	        LOG.info("WebServiceTemplate init Interceptors, Marshallers, MessageSender {} {} {}", 
	        		Arrays.asList(webServiceTemplate.getInterceptors()), webServiceTemplate.getMarshaller(), webServiceTemplate.getMessageSenders());
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
				FileInputStream  stream = new FileInputStream(propertiesFile);
				properties.load(stream);
				stream.close();
				break;
			}
		}
		return properties;
	}
}

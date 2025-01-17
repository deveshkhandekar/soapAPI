//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.02.15 at 12:41:31 PM IST 
//


package com.test.config.fts.callback.request.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface  
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AMLScan_QNAME = new QName("http://devwbin01.nbf.ae/NBFAML.provider.ws:NBFAMLScan", "nbf:AMLScan");

    /**
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AMLScan }
     * 
     */
    public AMLScan createAMLScan() {
        return new AMLScan();
    }


    /**
     * Create an instance of {@link AmlScanRequest }
     * 
     */
    public AmlScanRequest createAmlScanRequest() {
        return new AmlScanRequest();
    }

    /**
     * Create an instance of {@link NBFAMLHEADER }
     * 
     */
    public NBFAMLHEADER createNBFAMLHEADER() {
        return new NBFAMLHEADER();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AMLScan }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AMLScan }{@code >}
     */
    @XmlElementDecl(namespace = "http://devwbin01.nbf.ae/NBFAML.provider.ws:NBFAMLScan", name = "AMLScan")
    public JAXBElement<AMLScan> createAMLScan(AMLScan value) {
        return new JAXBElement<AMLScan>(_AMLScan_QNAME, AMLScan.class, null, value);
    }

}

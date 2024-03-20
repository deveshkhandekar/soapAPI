//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.02.15 at 12:41:31 PM IST 
//


package com.test.config.fts.callback.request.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NBF_AML_HEADER complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NBF_AML_HEADER"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MsgFormat" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MsgVersion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestorId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestorChannelId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestorUserId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestorLanguage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RequestorSecurityInfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ReturnCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NBF_AML_HEADER", propOrder = {
    "msgFormat",
    "msgVersion",
    "requestorId",
    "requestorChannelId",
    "requestorUserId",
    "requestorLanguage",
    "requestorSecurityInfo"
})
public class NBFAMLHEADER {

//	@Value("AML")
    @XmlElement(name = "MsgFormat", required = true, nillable = true)
    protected String msgFormat;
	
//	@Value("000")
    @XmlElement(name = "MsgVersion", required = true, nillable = true)
    protected String msgVersion;
	
//	@Value("NBF")
    @XmlElement(name = "RequestorId", required = true, nillable = true)
    protected String requestorId;
	
	
    @XmlElement(name = "RequestorChannelId", required = true, nillable = true)
    protected String requestorChannelId;
    
//    @Value("?")
    @XmlElement(name = "RequestorUserId", required = true, nillable = true)
    protected String requestorUserId;
    
//    @Value("E")
    @XmlElement(name = "RequestorLanguage", required = true, nillable = true)
    protected String requestorLanguage;
    
//    @Value("Secure")
    @XmlElement(name = "RequestorSecurityInfo", required = true, nillable = true)
    protected String requestorSecurityInfo;
    
//    @XmlElement(name = "ReturnCode", required = false, nillable = true)
//    protected String returnCode;

    /**
     * Gets the value of the msgFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgFormat() {
        return msgFormat;
    }

    /**
     * Sets the value of the msgFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgFormat(String value) {
        this.msgFormat = value;
    }

    /**
     * Gets the value of the msgVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgVersion() {
        return msgVersion;
    }

    /**
     * Sets the value of the msgVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgVersion(String value) {
        this.msgVersion = value;
    }

    /**
     * Gets the value of the requestorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorId() {
        return requestorId;
    }

    /**
     * Sets the value of the requestorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorId(String value) {
        this.requestorId = value;
    }

    /**
     * Gets the value of the requestorChannelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorChannelId() {
        return requestorChannelId;
    }

    /**
     * Sets the value of the requestorChannelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorChannelId(String value) {
        this.requestorChannelId = value;
    }

    /**
     * Gets the value of the requestorUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorUserId() {
        return requestorUserId;
    }

    /**
     * Sets the value of the requestorUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorUserId(String value) {
        this.requestorUserId = value;
    }

    /**
     * Gets the value of the requestorLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorLanguage() {
        return requestorLanguage;
    }

    /**
     * Sets the value of the requestorLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorLanguage(String value) {
        this.requestorLanguage = value;
    }

    /**
     * Gets the value of the requestorSecurityInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorSecurityInfo() {
        return requestorSecurityInfo;
    }

    /**
     * Sets the value of the requestorSecurityInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorSecurityInfo(String value) {
        this.requestorSecurityInfo = value;
    }

    /**
     * Gets the value of the returnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
//    public String getReturnCode() {
//        return returnCode;
//    }
//
//    /**
//     * Sets the value of the returnCode property.
//     * 
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *     
//     */
//    public void setReturnCode(String value) {
//        this.returnCode = value;
//    }

}
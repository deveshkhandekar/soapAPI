package com.test.config.fts.callback.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AMLScanResponse", propOrder={"amlScanResponse"})
@XmlRootElement(name="AMLScanResponse", namespace="NBFAMLScan")
public class AMLScanResponse
{
  @XmlElement(name="AMLScanResponse", required=true, nillable=true)
  protected AmlScanResponse2 amlScanResponse;
  
  public AmlScanResponse2 getAMLScanResponse()
  {
    return amlScanResponse;
  }
  
  public void setAMLScanResponse(AmlScanResponse2 value)
  {
    amlScanResponse = value;
  }
}
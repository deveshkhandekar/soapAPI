package com.test.config.fts.callback.stub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AMLScan", propOrder={"amlScanRequest"})
@XmlRootElement(name="AMLScan")
public class AMLScan
{
  @XmlElement(name="AMLScanRequest", required=true, nillable=true)
  protected AmlScanRequest amlScanRequest;
  
  public AmlScanRequest getAMLScanRequest()
  {
    return amlScanRequest;
  }
  
  public void setAMLScanRequest(AmlScanRequest value)
  {
    amlScanRequest = value;
  }
}
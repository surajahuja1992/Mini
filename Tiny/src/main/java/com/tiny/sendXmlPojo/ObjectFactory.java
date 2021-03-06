//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.07 at 11:00:18 AM IST 
//


package com.tiny.sendXmlPojo;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sendDocXML package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sendDocXML
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FIXML }
     * 
     */
    public FIXML createFIXML() {
        return new FIXML();
    }

    /**
     * Create an instance of {@link FIXML.Body }
     * 
     */
    public FIXML.Body createFIXMLBody() {
        return new FIXML.Body();
    }

    /**
     * Create an instance of {@link FIXML.Body.CCAddDoc }
     * 
     */
    public FIXML.Body.CCAddDoc createFIXMLBodyCCAddDoc() {
        return new FIXML.Body.CCAddDoc();
    }

    /**
     * Create an instance of {@link FIXML.Body.CCAddDoc.DataClassAttribs }
     * 
     */
    public FIXML.Body.CCAddDoc.DataClassAttribs createFIXMLBodyCCAddDocDataClassAttribs() {
        return new FIXML.Body.CCAddDoc.DataClassAttribs();
    }

    /**
     * Create an instance of {@link FIXML.Header }
     * 
     */
    public FIXML.Header createFIXMLHeader() {
        return new FIXML.Header();
    }

    /**
     * Create an instance of {@link FIXML.Header.RequestHeader }
     * 
     */
    public FIXML.Header.RequestHeader createFIXMLHeaderRequestHeader() {
        return new FIXML.Header.RequestHeader();
    }

    /**
     * Create an instance of {@link FIXML.Header.RequestHeader.Security }
     * 
     */
    public FIXML.Header.RequestHeader.Security createFIXMLHeaderRequestHeaderSecurity() {
        return new FIXML.Header.RequestHeader.Security();
    }

    /**
     * Create an instance of {@link FIXML.Header.RequestHeader.Security.Token }
     * 
     */
    public String createFIXMLHeaderRequestHeaderSecurityToken() {
        return "";
    }

    /**
     * Create an instance of {@link FIXML.Body.CCAddDoc.DocumentProps }
     * 
     */
    public FIXML.Body.CCAddDoc.DocumentProps createFIXMLBodyCCAddDocDocumentProps() {
        return new FIXML.Body.CCAddDoc.DocumentProps();
    }

    /**
     * Create an instance of {@link FIXML.Body.CCAddDoc.DataClassAttribs.IndexInfo }
     * 
     */
    public FIXML.Body.CCAddDoc.DataClassAttribs.IndexInfo createFIXMLBodyCCAddDocDataClassAttribsIndexInfo() {
        return new FIXML.Body.CCAddDoc.DataClassAttribs.IndexInfo();
    }

    /**
     * Create an instance of {@link FIXML.Header.RequestHeader.MessageKey }
     * 
     */
    public FIXML.Header.RequestHeader.MessageKey createFIXMLHeaderRequestHeaderMessageKey() {
        return new FIXML.Header.RequestHeader.MessageKey();
    }

    /**
     * Create an instance of {@link FIXML.Header.RequestHeader.RequestMessageInfo }
     * 
     */
    public FIXML.Header.RequestHeader.RequestMessageInfo createFIXMLHeaderRequestHeaderRequestMessageInfo() {
        return new FIXML.Header.RequestHeader.RequestMessageInfo();
    }

    /**
     * Create an instance of {@link FIXML.Header.RequestHeader.Security.Token.PasswordToken }
     * 
     */
    public FIXML.Header.RequestHeader.Security.Token.PasswordToken createFIXMLHeaderRequestHeaderSecurityTokenPasswordToken() {
        return new FIXML.Header.RequestHeader.Security.Token.PasswordToken();
    }

}

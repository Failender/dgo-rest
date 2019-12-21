//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.26 at 12:11:01 PM CET 
//


package de.failender.heldensoftware.xml.datenxml;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}nummer"/>
 *         &lt;element ref="{}moeglich"/>
 *         &lt;element ref="{}getName"/>
 *         &lt;element ref="{}spalte2"/>
 *         &lt;element ref="{}dk"/>
 *         &lt;element ref="{}tp"/>
 *         &lt;element ref="{}tpkk"/>
 *         &lt;element ref="{}ini"/>
 *         &lt;element ref="{}wm"/>
 *         &lt;element ref="{}at"/>
 *         &lt;element ref="{}pa"/>
 *         &lt;element ref="{}tpinkl"/>
 *         &lt;element ref="{}bereich"/>
 *         &lt;element ref="{}bfmin"/>
 *         &lt;element ref="{}bfakt"/>
 *         &lt;element ref="{}waffentalentkurz"/>
 *         &lt;element ref="{}waffentalent"/>
 *         &lt;element ref="{}be"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nummer",
    "moeglich",
    "name",
    "spalte2",
    "dk",
    "tp",
    "tpkk",
    "ini",
    "wm",
    "at",
    "pa",
    "tpinkl",
    "bereich",
    "bfmin",
    "bfakt",
    "waffentalentkurz",
    "waffentalent",
    "be"
})
@XmlRootElement(name = "nahkampfwaffe")
public class Nahkampfwaffe {

    @XmlElement(required = true)
    protected BigInteger nummer;
    protected boolean moeglich;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String spalte2;
    @XmlElement(required = true)
    protected String dk;
    @XmlElement(required = true)
    protected String tp;
    @XmlElement(required = true)
    protected Tpkk tpkk;
    @XmlElement(required = true)
    protected BigInteger ini;
    @XmlElement(required = true)
    protected String wm;
    @XmlElement(required = true)
    protected String at;
    @XmlElement(required = true)
    protected String pa;
    @XmlElement(required = true)
    protected String tpinkl;
    @XmlElement(required = true)
    protected String bereich;
    @XmlElement(required = true)
    protected BigInteger bfmin;
    @XmlElement(required = true)
    protected BigInteger bfakt;
    @XmlElement(required = true)
    protected String waffentalentkurz;
    @XmlElement(required = true)
    protected String waffentalent;
    @XmlElement(required = true)
    protected String be;

    /**
     * Gets the value of the nummer property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNummer() {
        return nummer;
    }

    /**
     * Sets the value of the nummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNummer(BigInteger value) {
        this.nummer = value;
    }

    /**
     * Gets the value of the moeglich property.
     * 
     */
    public boolean isMoeglich() {
        return moeglich;
    }

    /**
     * Sets the value of the moeglich property.
     * 
     */
    public void setMoeglich(boolean value) {
        this.moeglich = value;
    }

    /**
     * Gets the value of the getName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the getName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the spalte2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpalte2() {
        return spalte2;
    }

    /**
     * Sets the value of the spalte2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpalte2(String value) {
        this.spalte2 = value;
    }

    /**
     * Gets the value of the dk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDk() {
        return dk;
    }

    /**
     * Sets the value of the dk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDk(String value) {
        this.dk = value;
    }

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Gets the value of the tpkk property.
     * 
     * @return
     *     possible object is
     *     {@link Tpkk }
     *     
     */
    public Tpkk getTpkk() {
        return tpkk;
    }

    public String getTpKKValue() {
        return tpkk.value;
    }

    /**
     * Sets the value of the tpkk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tpkk }
     *     
     */
    public void setTpkk(Tpkk value) {
        this.tpkk = value;
    }

    /**
     * Gets the value of the ini property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIni() {
        return ini;
    }

    /**
     * Sets the value of the ini property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIni(BigInteger value) {
        this.ini = value;
    }

    /**
     * Gets the value of the wm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWm() {
        return wm;
    }

    /**
     * Sets the value of the wm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWm(String value) {
        this.wm = value;
    }

    /**
     * Gets the value of the at property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAt() {
        return at;
    }

    /**
     * Sets the value of the at property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAt(String value) {
        this.at = value;
    }

    /**
     * Gets the value of the pa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPa() {
        return pa;
    }

    /**
     * Sets the value of the pa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPa(String value) {
        this.pa = value;
    }

    /**
     * Gets the value of the tpinkl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTpinkl() {
        return tpinkl;
    }

    /**
     * Sets the value of the tpinkl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTpinkl(String value) {
        this.tpinkl = value;
    }

    /**
     * Gets the value of the bereich property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBereich() {
        return bereich;
    }

    /**
     * Sets the value of the bereich property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBereich(String value) {
        this.bereich = value;
    }

    /**
     * Gets the value of the bfmin property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBfmin() {
        return bfmin;
    }

    /**
     * Sets the value of the bfmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBfmin(BigInteger value) {
        this.bfmin = value;
    }

    /**
     * Gets the value of the bfakt property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBfakt() {
        return bfakt;
    }

    /**
     * Sets the value of the bfakt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBfakt(BigInteger value) {
        this.bfakt = value;
    }

    /**
     * Gets the value of the waffentalentkurz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaffentalentkurz() {
        return waffentalentkurz;
    }

    /**
     * Sets the value of the waffentalentkurz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaffentalentkurz(String value) {
        this.waffentalentkurz = value;
    }

    /**
     * Gets the value of the waffentalent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaffentalent() {
        return waffentalent;
    }

    /**
     * Sets the value of the waffentalent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaffentalent(String value) {
        this.waffentalent = value;
    }

    /**
     * Gets the value of the be property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBe() {
        return be;
    }

    /**
     * Sets the value of the be property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBe(String value) {
        this.be = value;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.07 at 08:51:44 PM CEST 
//


package com.javacook.dddschach.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * Das Aggregat Schachpartie
 * 
 * <p>Java class for Schachpartie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Schachpartie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.informatikbuero.com/services/dddschach/api/model}SpielId"/>
 *         &lt;element name="halbzugHistorie" type="{http://www.informatikbuero.com/services/dddschach/api/model}HalbzugHistorie"/>
 *         &lt;element name="spielbrett" type="{http://www.informatikbuero.com/services/dddschach/api/model}Spielbrett"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Schachpartie", propOrder = {
    "id",
    "halbzugHistorie",
    "spielbrett"
})
public class Schachpartie implements Serializable
{

    @XmlElement(required = true)
    @NotNull
    @Valid
    protected SpielId id;
    @XmlElement(required = true)
    @NotNull
    @Valid
    protected HalbzugHistorie halbzugHistorie;
    @XmlElement(required = true)
    @NotNull
    @Valid
    protected Spielbrett spielbrett;

    /**
     * Default no-arg constructor
     * 
     */
    public Schachpartie() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Schachpartie(final SpielId id, final HalbzugHistorie halbzugHistorie, final Spielbrett spielbrett) {
        this.id = id;
        this.halbzugHistorie = halbzugHistorie;
        this.spielbrett = spielbrett;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link SpielId }
     *     
     */
    public SpielId getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpielId }
     *     
     */
    public void setId(SpielId value) {
        this.id = value;
    }

    /**
     * Gets the value of the halbzugHistorie property.
     * 
     * @return
     *     possible object is
     *     {@link HalbzugHistorie }
     *     
     */
    public HalbzugHistorie getHalbzugHistorie() {
        return halbzugHistorie;
    }

    /**
     * Sets the value of the halbzugHistorie property.
     * 
     * @param value
     *     allowed object is
     *     {@link HalbzugHistorie }
     *     
     */
    public void setHalbzugHistorie(HalbzugHistorie value) {
        this.halbzugHistorie = value;
    }

    /**
     * Gets the value of the spielbrett property.
     * 
     * @return
     *     possible object is
     *     {@link Spielbrett }
     *     
     */
    public Spielbrett getSpielbrett() {
        return spielbrett;
    }

    /**
     * Sets the value of the spielbrett property.
     * 
     * @param value
     *     allowed object is
     *     {@link Spielbrett }
     *     
     */
    public void setSpielbrett(Spielbrett value) {
        this.spielbrett = value;
    }


}

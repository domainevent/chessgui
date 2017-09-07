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
 * Spielfeld
 * 
 * <p>Java class for Spielfeld complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Spielfeld">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="position" type="{http://www.informatikbuero.com/services/dddschach/api/model}Position"/>
 *         &lt;element name="spielfigur" type="{http://www.informatikbuero.com/services/dddschach/api/model}Spielfigur"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Spielfeld", propOrder = {
    "position",
    "spielfigur"
})
public class Spielfeld implements Serializable
{

    @XmlElement(required = true)
    @NotNull
    @Valid
    protected Position position;
    @XmlElement(required = true)
    @NotNull
    @Valid
    protected Spielfigur spielfigur;

    /**
     * Default no-arg constructor
     * 
     */
    public Spielfeld() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Spielfeld(final Position position, final Spielfigur spielfigur) {
        this.position = position;
        this.spielfigur = spielfigur;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setPosition(Position value) {
        this.position = value;
    }

    /**
     * Gets the value of the spielfigur property.
     * 
     * @return
     *     possible object is
     *     {@link Spielfigur }
     *     
     */
    public Spielfigur getSpielfigur() {
        return spielfigur;
    }

    /**
     * Sets the value of the spielfigur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Spielfigur }
     *     
     */
    public void setSpielfigur(Spielfigur value) {
        this.spielfigur = value;
    }

}
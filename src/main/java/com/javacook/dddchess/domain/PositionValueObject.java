package com.javacook.dddchess.domain;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Value Object
 */
@XmlType
public class PositionValueObject {

    @XmlEnum
    public enum HorCoord {
        A, B, C, D, E, F, G, H;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }


    @XmlEnum
    public enum VertCoord {
        _1, _2, _3, _4, _5, _6, _7, _8;

        public static VertCoord[] valuesInverted() {
            return new VertCoord[]{_8, _7, _6, _5, _4, _3, _2, _1};
        }

        @Override
        public String toString() {
            return String.valueOf(ordinal()+1);
        }
    }


    public final HorCoord spalte;
    public final VertCoord zeile;

    public PositionValueObject(HorCoord spalte, VertCoord zeile) {
        this.spalte = spalte;
        this.zeile = zeile;
    }


    public PositionValueObject(String coordEncoded) {
        spalte = HorCoord.valueOf(coordEncoded.substring(0,1).toUpperCase());
        zeile = VertCoord.valueOf("_" + coordEncoded.substring(1,2));
    }

    /**
     * Wird zum Unmarshallen der Json-Objekte benoetigt
     */
    public PositionValueObject() {
        this(null, null);
    }

    @Override
    public String toString() {
        return spalte.toString() + zeile.toString();
    }

}

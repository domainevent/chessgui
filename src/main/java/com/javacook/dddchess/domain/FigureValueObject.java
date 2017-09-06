package com.javacook.dddchess.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


@XmlType
public class FigureValueObject {

    @XmlEnum
    public enum FigureEnum {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;

        @JsonValue
        public Character marshal() {
            switch (this) {
                case KING: return 'K';
                case QUEEN: return 'Q';
                case ROOK: return 'R';
                case BISHOP: return 'B';
                case KNIGHT: return 'N';
                case PAWN: return 'P';
            }
            throw new IllegalArgumentException("Unexpected enum " + this);
        }

        @JsonCreator
        public static FigureEnum unmarshal(Character c) {
            switch (c) {
                case 'K': return KING;
                case 'Q': return QUEEN;
                case 'R': return ROOK;
                case 'B': return BISHOP;
                case 'N': return KNIGHT;
                case 'P': return PAWN;
            }
            throw new IllegalArgumentException("Unexpected marshalled character " + c);
        }
    };


    @XmlEnum
    public enum ColorEnum {
        WHITE, BLACK;

        @JsonValue
        public Character marshal() {
            switch (this) {
                case WHITE: return 'w';
                case BLACK: return 'b';
                default:
                    throw new IllegalArgumentException("Unexpected enum " + this);
            }
        }

        @JsonCreator
        public static ColorEnum unmarshal(Character c) {
            switch (c) {
                case 'w': return WHITE;
                case 'b': return BLACK;
            }
            throw new IllegalArgumentException("Unexpected marshalle character " + c);
        }
    };

    public final FigureEnum figurTyp;
    public final ColorEnum farbe;


    public FigureValueObject(FigureEnum figurTyp, ColorEnum farbe) {
        this.figurTyp = figurTyp;
        this.farbe = farbe;
    }

    /**
     * Wird zum Unmarshallen der Json-Objekte benoetigt
     */
    public FigureValueObject() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "FigureValueObject{" +
                "figurTyp=" + figurTyp +
                ", farbe=" + farbe +
                '}';
    }

    public String abbreviation() {
        return "" + figurTyp.marshal() + farbe.marshal();
    }
}

package com.javacook.dddchess.domain;

import static com.javacook.dddchess.domain.PositionValueObject.VertCoord._1;


/**
 * Created by vollmer on 05.05.17.
 */
public class ChessBoardValueObject {

    public final FigureValueObject[][] board;


    public ChessBoardValueObject(FigureValueObject[][] board) {
        this.board = board;
    }

    public ChessBoardValueObject() {
        this(null);
    }

    @Override
    public String toString() {
        final String horLine = "-------------------------";
        String boardAsStr = horLine + System.lineSeparator();
        for(PositionValueObject.VertCoord vertCoord : PositionValueObject.VertCoord.valuesInverted()) {
            boardAsStr += "|";
            for(PositionValueObject.HorCoord horCoord : PositionValueObject.HorCoord.values()) {
                final FigureValueObject figure = board[horCoord.ordinal()][vertCoord.ordinal()];
                boardAsStr += figure == null? "  " : figure.abbreviation();
                boardAsStr += "|";
            }
            boardAsStr += System.lineSeparator() + horLine;
            if (vertCoord != _1) {
                boardAsStr += System.lineSeparator();
            }
        }
        return boardAsStr;
    }


}

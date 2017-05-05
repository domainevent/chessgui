package com.javacook.chessgui.figure;

import com.javacook.chessgui.MoveList;


public class Bishop extends Piece {

    public Bishop(boolean color) {
        //this calls the constructor of com.javacook.chessgui.figure.Piece
        super(color);
    }


    protected MoveList[] getPieceMoves() {
        MoveList[] m = {
                MoveList.UP_RIGHT,
                MoveList.DOWN_RIGHT,
                MoveList.DOWN_LEFT,
                MoveList.UP_LEFT
        };
        return m;
    }


    protected boolean usesSingleMove() {return false;}


    protected String getName() {return "bishop";}
}

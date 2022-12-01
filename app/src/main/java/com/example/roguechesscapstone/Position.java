package com.example.roguechesscapstone;

import com.example.roguechesscapstone.pieces.PieceMov;
import com.example.roguechesscapstone.pieces.PieceMov;

//class for the position and to just return the position of the piece when it is selected
public class Position {
    private PieceMov pieceMov;


    Position(PieceMov pieceMov) {
        this.pieceMov = pieceMov;
    }

    public PieceMov getPiece() {
        return pieceMov;

    }

    void setPiece(PieceMov pieceMov) {
        this.pieceMov = pieceMov;
    }

}
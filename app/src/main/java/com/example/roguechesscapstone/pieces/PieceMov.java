package com.example.roguechesscapstone.pieces;

import com.example.roguechesscapstone.Coord;
import com.example.roguechesscapstone.Position;

import java.util.ArrayList;

public class PieceMov {

    private boolean white;

    PieceMov(boolean white) {
        this.white = white;
    }

    public ArrayList<Coord> ValidMoves(Coord coord , Position[][] currentBoard){
        ArrayList<Coord> validMoves = new ArrayList<>();
        Coord coordinates;
        for(int a=0;a<8;a++){
            for(int b=0;b<8;b++){
                coordinates = new Coord(a,b);
                validMoves.add(coordinates);
            }
        }
        return validMoves;
    }

    public boolean isWhite() {
        return white;
    }

}

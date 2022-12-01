package com.example.roguechesscapstone.pieces;
import com.example.roguechesscapstone.Coord;
import com.example.roguechesscapstone.Position;

import java.util.ArrayList;

//The pawn can only move up or down and only attack diagonally
public class Pa extends PieceMov {

    public Pa(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coord> ValidMoves(Coord coord , Position[][] currentBoard){

        ArrayList<Coord> validMoves = new ArrayList<>();
        validMoves.clear();
        Coord c;

        if(currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){

            if((coord.getX()-1)<8 && (coord.getX()-1)>=0 && (coord.getY()-1)<8 && (coord.getY()-1)>=0) {
                if (currentBoard[coord.getX() - 1][coord.getY() - 1].getPiece() != null) {
                    if(currentBoard[coord.getX() - 1][coord.getY() - 1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                        c = new Coord(coord.getX() - 1, coord.getY() - 1);
                        validMoves.add(c);
                    }
                }
            }

            if(coord.getX()<8 && coord.getX()>=0 && (coord.getY()-1)<8 && (coord.getY()-1)>=0){
                if(currentBoard[coord.getX()][coord.getY()-1].getPiece()==null){
                    c=new Coord(coord.getX() , coord.getY() - 1);
                    validMoves.add(c);

                    if((coord.getY() == 6) && (currentBoard[coord.getX()][coord.getY() - 2].getPiece() == null)){
                        c = new Coord(coord.getX(), coord.getY() - 2);
                        validMoves.add(c);
                    }
                }
            }

            if((coord.getX()+1)<8 && (coord.getX()+1)>=0 && (coord.getY()-1)<8 && (coord.getY()-1)>=0) {
                if (currentBoard[coord.getX() + 1][coord.getY() - 1].getPiece() != null) {
                    if(currentBoard[coord.getX() + 1][coord.getY() - 1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                        c = new Coord(coord.getX() + 1, coord.getY() - 1);
                        validMoves.add(c);
                    }
                }

            }


        }else{

            if((coord.getX())<8 && (coord.getX())>=0 && (coord.getY()+1)<8 && (coord.getY()+1)>=0) {
                if (currentBoard[coord.getX()][coord.getY() + 1].getPiece() == null) {
                    c = new Coord(coord.getX(), coord.getY() + 1);
                    validMoves.add(c);

                    if(coord.getY() == 1 && (currentBoard[coord.getX()][coord.getY() + 2].getPiece() == null)){
                        c = new Coord(coord.getX(), coord.getY() + 2);
                        validMoves.add(c);
                    }
                }
            }


            if((coord.getX()-1)<8 && (coord.getX()-1)>=0 && (coord.getY()+1)<8 && (coord.getY()+1)>=0) {
                if (currentBoard[coord.getX() - 1][coord.getY() + 1].getPiece() != null) {
                    if(currentBoard[coord.getX() - 1][coord.getY() + 1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                        c = new Coord(coord.getX() - 1, coord.getY() + 1);
                        validMoves.add(c);
                    }
                }
            }

        }

        if((coord.getX()+1)<8 && (coord.getX()+1)>=0 && (coord.getY()+1)<8 && (coord.getY()+1)>=0) {
            if (currentBoard[coord.getX() + 1][coord.getY() + 1].getPiece() != null) {
                if(currentBoard[coord.getX() + 1][coord.getY() + 1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                    c = new Coord(coord.getX() + 1, coord.getY() + 1);
                    validMoves.add(c);
                }
            }
        }
        //check locations at board
        //work on the coord and return the allowed moves
        return validMoves;
    }
}

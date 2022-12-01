package com.example.roguechesscapstone.pieces;

import com.example.roguechesscapstone.Coord;
import com.example.roguechesscapstone.Position;

import java.util.ArrayList;

//This is the class for the Bishop moves
//bishop only moves diagonally
public class B extends PieceMov {

    public B(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coord> ValidMoves(Coord coord, Position[][] currentBoard){
        ArrayList<Coord> validMoves = new ArrayList<>();
        Coord c;

        for(int i=1 ; i<8 ; i++){
            if((coord.getX()+i)<8 && (coord.getY()+i)<8){ //8 columns and 8 rows moves diagonally up right
                if(currentBoard[coord.getX()+i][coord.getY()+i].getPiece() == null){
                    c = new Coord(coord.getX()+i , coord.getY()+i); //new coordinates are right and up 1
                    validMoves.add(c);
                }else{
                    //if the selected piece is white get the coordinates
                    if(currentBoard[coord.getX()+i][coord.getY()+i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                        c = new Coord(coord.getX()+i , coord.getY()+i);
                        validMoves.add(c);
                    }
                    break;
                }
            }
        }

        for(int i=1 ; i<8 ; i++){
            if((coord.getX()-i)>=0 && (coord.getY()+i)<8){ //moves diagonally left up
                if(currentBoard[coord.getX()-i][coord.getY()+i].getPiece() == null){
                    c = new Coord(coord.getX()-i , coord.getY()+i); //x moves left 1 and y moves up 1
                    validMoves.add(c);
                }else{
                    if(currentBoard[coord.getX()-i][coord.getY()+i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){ //if allowed moves does not equal selected piece position
                        c = new Coord(coord.getX()-i , coord.getY()+i);
                        validMoves.add(c);
                    }
                    break;
                }

            }
        }

        for(int i=1 ; i<8 ; i++){
            if((coord.getX()-i)>=0 && (coord.getY()-i)>=0){ //moves diagonally left down
                if(currentBoard[coord.getX()-i][coord.getY()-i].getPiece() == null){
                    c = new Coord(coord.getX()-i , coord.getY()-i); //x moves left i and y moves down i
                    validMoves.add(c);
                }else{
                    if(currentBoard[coord.getX()-i][coord.getY()-i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                        c = new Coord(coord.getX()-i , coord.getY()-i);
                        validMoves.add(c);
                    }
                    break;
                }

            }
        }

        for(int i=1 ; i<8 ; i++){
            if((coord.getX()+i)<8 && (coord.getY()-i)>=0){ //moves diagonally right down
                if(currentBoard[coord.getX()+i][coord.getY()-i].getPiece() == null){
                    c = new Coord(coord.getX()+i , coord.getY()-i);
                    validMoves.add(c);
                }else{
                    if(currentBoard[coord.getX()+i][coord.getY()-i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                        c = new Coord(coord.getX()+i , coord.getY()-i);
                        validMoves.add(c);
                    }
                    break;
                }

            }
        }
        return validMoves; //return the coordinates
    }

}
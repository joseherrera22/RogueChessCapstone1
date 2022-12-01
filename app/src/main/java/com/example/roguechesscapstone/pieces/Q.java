package com.example.roguechesscapstone.pieces;

import com.example.roguechesscapstone.Coord;
import com.example.roguechesscapstone.Position;

import java.util.ArrayList;

public class Q extends PieceMov {

    public Q(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coord> ValidMoves(Coord coord, Position[][] currentBoard){
        ArrayList<Coord> validMoves = new ArrayList<>();
        Coord c;
        validMoves.clear();

        for(int i = (coord.getY()+1); i<8 ; i++){
            if(currentBoard[coord.getX()][i].getPiece() == null){
                c = new Coord(coord.getX() , i);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()][i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(coord.getX() , i);
                    validMoves.add(c);
                }
                break;
            }
        }

        for(int i = (coord.getX()+1); i<8 ; i++){
            if(currentBoard[i][coord.getY()].getPiece() == null){
                c = new Coord(i , coord.getY());
                validMoves.add(c);
            }else{
                if(currentBoard[i][coord.getY()].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(i , coord.getY());
                    validMoves.add(c);
                }
                break;
            }
        }

        for(int i = (coord.getY()-1); i>=0 ; i--){
            if(currentBoard[coord.getX()][i].getPiece() == null){
                c = new Coord( coord.getX() , i);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()][i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord( coord.getX() , i);
                    validMoves.add(c);
                }
                break;
            }
        }

        for(int i = (coord.getX()-1); i>=0 ; i--){
            if(currentBoard[i][coord.getY()].getPiece() == null){
                c = new Coord(i , coord.getY());
                validMoves.add(c);
            }else{
                if(currentBoard[i][coord.getY()].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(i , coord.getY());
                    validMoves.add(c);
                }
                break;
            }
        }


        /*............................*/
        for(int i=1 ; i<8 ; i++){
            if((coord.getX()+i)<8 && (coord.getY()-i)>=0){
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

        for(int i=1 ; i<8 ; i++){
            if((coord.getX()+i)<8 && (coord.getY()+i)<8){
                if(currentBoard[coord.getX()+i][coord.getY()+i].getPiece() == null){
                    c = new Coord(coord.getX()+i , coord.getY()+i);
                    validMoves.add(c);
                }else{
                    if(currentBoard[coord.getX()+i][coord.getY()+i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                        c = new Coord(coord.getX()+i , coord.getY()+i);
                        validMoves.add(c);
                    }
                    break;
                }
            }
        }


        for(int i=1 ; i<8 ; i++){
            if((coord.getX()-i)>=0 && (coord.getY()-i)>=0){
                if(currentBoard[coord.getX()-i][coord.getY()-i].getPiece() == null){
                    c = new Coord(coord.getX()-i , coord.getY()-i);
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
            if((coord.getX()-i)>=0 && (coord.getY()+i)<8){
                if(currentBoard[coord.getX()-i][coord.getY()+i].getPiece() == null){
                    c = new Coord(coord.getX()-i , coord.getY()+i);
                    validMoves.add(c);
                }else{
                    if(currentBoard[coord.getX()-i][coord.getY()+i].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                        c = new Coord(coord.getX()-i , coord.getY()+i);
                        validMoves.add(c);
                    }
                    break;
                }

            }
        }

        //check locations at board
        //work on the coordinates and return the allowed moves
        return validMoves;
    }
}

package com.example.roguechesscapstone.pieces;

import com.example.roguechesscapstone.Coord;
import com.example.roguechesscapstone.Position;

import java.util.ArrayList;

//the King can move anywhere on the board
public class Kin extends PieceMov {

    public Kin(boolean white) {
        super( white);
    }

//    //Castling
//    private Boolean castlingDone = false;
//
//    public boolean isCastlingDone(){
//        return this.castlingDone;
//    }
//
//    public void setCastlingDone(boolean castlingDone){
//        this.castlingDone = castlingDone;
//    }
//
////    public Position[][] Board = new Position[8][8];
////    public boolean canMove(Position[][] board, Coordinates start, Coordinates end,Piece p){
////
////    }


    @Override
    public ArrayList<Coord> ValidMoves(Coord Coord , Position[][] currentBoard){
        ArrayList<com.example.roguechesscapstone.Coord> ValidMoves = new ArrayList<>();
        ValidMoves.clear();
        com.example.roguechesscapstone.Coord coor;

        if((Coord.getX()+1) <8 && (Coord.getY()-1)>=0){ //move diagonally right and down
            if(currentBoard[Coord.getX()+1][Coord.getY()-1].getPiece() == null){
                coor = new Coord(Coord.getX()+1 , Coord.getY()-1);
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()+1][Coord.getY()-1].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX()+1 , Coord.getY()-1);
                    ValidMoves.add(coor);
                }
            }
        }

        if((Coord.getY()+1)<8){
            if(currentBoard[Coord.getX()][Coord.getY()+1].getPiece() == null){ //move up
                coor = new Coord(Coord.getX() , Coord.getY()+1);
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()][Coord.getY()+1].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX() , Coord.getY()+1);
                    ValidMoves.add(coor);
                }
            }
        }

        if((Coord.getX()-1) >=0 && (Coord.getY()+1)<8){ //move diagonally left and up
            if(currentBoard[Coord.getX()-1][Coord.getY()+1].getPiece() == null){
                coor = new Coord(Coord.getX()-1 , Coord.getY()+1);
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()-1][Coord.getY()+1].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX()-1 , Coord.getY()+1);
                    ValidMoves.add(coor);
                }
            }
        }


        if((Coord.getX()+1) <8 ){
            if(currentBoard[Coord.getX()+1][Coord.getY()].getPiece() == null){ //move right
                coor = new Coord(Coord.getX()+1 , Coord.getY());
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()+1][Coord.getY()].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX()+1 , Coord.getY());
                    ValidMoves.add(coor);
                }
            }
        }

        if((Coord.getY()-1)>=0){
            if(currentBoard[Coord.getX()][Coord.getY()-1].getPiece() == null){ //move down
                coor = new Coord(Coord.getX() , Coord.getY()-1);
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()][Coord.getY()-1].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX() , Coord.getY()-1);
                    ValidMoves.add(coor);
                }
            }
        }

        if((Coord.getX()-1) <8 && (Coord.getY()-1)>=0){ //move diagonally left and down
            if(currentBoard[Coord.getX()-1][Coord.getY()-1].getPiece() == null){
                coor = new Coord(Coord.getX()-1 , Coord.getY()-1);
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()-1][Coord.getY()-1].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX()-1 , Coord.getY()-1);
                    ValidMoves.add(coor);
                }
            }
        }

        if((Coord.getX()-1) <8 ){ // move left
            if(currentBoard[Coord.getX()-1][Coord.getY()].getPiece() == null){
                coor = new Coord(Coord.getX()-1 , Coord.getY());
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()-1][Coord.getY()].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){
                    coor = new Coord(Coord.getX()-1 , Coord.getY());
                    ValidMoves.add(coor);
                }
            }
        }

        if((Coord.getX()+1) <8 && (Coord.getY()+1)<8){ //move diagonally right and up by i
            if(currentBoard[Coord.getX()+1][Coord.getY()+1].getPiece() == null){
                coor = new Coord(Coord.getX()+1 , Coord.getY()+1);
                ValidMoves.add(coor);
            }else{
                if(currentBoard[Coord.getX()+1][Coord.getY()+1].getPiece().isWhite() != currentBoard[Coord.getX()][Coord.getY()].getPiece().isWhite()){ //if allowed moves does not equal selected piece position
                    coor = new Coord(Coord.getX()+1 , Coord.getY()+1);
                    ValidMoves.add(coor);
                }
            }
        }
        return ValidMoves;
    }
}
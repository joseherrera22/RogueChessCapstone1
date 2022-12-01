package com.example.roguechesscapstone.pieces;

import com.example.roguechesscapstone.Coord;
import com.example.roguechesscapstone.Position;

import java.util.ArrayList;

//This is the class for the knight's move
public class Kni extends PieceMov {

    public Kni(boolean white) {
        super(white);
    }

    //Final Position Coordinates
//    private final Coordinates[] moves = {
//            new Coordinates(-1, 2),
//            new Coordinates(-2, 1),
//            new Coordinates(-2, -1),
//            new Coordinates(-1, -2),
//            new Coordinates(1, -2),
//            new Coordinates(2, -1),
//            new Coordinates(2, 1),
//            new Coordinates(1, 2)};


    @Override
    public ArrayList<Coord> ValidMoves(Coord coord , Position[][] currentBoard){
        ArrayList<Coord> validMoves = new ArrayList<>();
        validMoves.clear();
        Coord c;

        if (coord.getX()+1 <8 && coord.getY()-2 >=0){ //move right 1 and down 2 a single time
            if(currentBoard[coord.getX()+1][coord.getY()-2].getPiece() ==null){
                c = new Coord(coord.getX()+1 , coord.getY()-2);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()+1][coord.getY()-2].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                    c = new Coord(coord.getX()+1 , coord.getY()-2);
                    validMoves.add(c);
                }
            }
        }

        if (coord.getX()+2 <8 && coord.getY()+1 <8){ //move right 2 and up 1 a single time
            if(currentBoard[coord.getX()+2][coord.getY()+1].getPiece() ==null){
                c = new Coord(coord.getX()+2 , coord.getY()+1);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()+2][coord.getY()+1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(coord.getX()+2 , coord.getY()+1);
                    validMoves.add(c);
                }
            }
        }

        if (coord.getX()-2 >= 0 && coord.getY()-1 >=0){ //move left 2 and down 1 a single time
            if(currentBoard[coord.getX()-2][coord.getY()-1].getPiece() ==null){
                c = new Coord(coord.getX()-2 , coord.getY()-1);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()-2][coord.getY()-1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                    c = new Coord(coord.getX()-2 , coord.getY()-1);
                    validMoves.add(c);
                }
            }
        }

        if (coord.getX()-1 >=0  && coord.getY()-2 >=0){ //move left 1 and down 2 a single time
            if(currentBoard[coord.getX()-1][coord.getY()-2].getPiece() ==null){
                c = new Coord(coord.getX()-1 , coord.getY()-2);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()-1][coord.getY()-2].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(coord.getX()-1 , coord.getY()-2);
                    validMoves.add(c);
                }
            }
        }


        if (coord.getX()+1 <8 && coord.getY()+2 <8){ //move right 1 and up 2 a single time
            if(currentBoard[coord.getX()+1][coord.getY()+2].getPiece() ==null){
                c = new Coord(coord.getX()+1 , coord.getY()+2);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()+1][coord.getY()+2].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(coord.getX()+1 , coord.getY()+2);
                    validMoves.add(c);
                }
            }
        }

        if (coord.getX()+2 <8 && coord.getY()-1 >=0){ //move right 2 and down 1 a single time
            if(currentBoard[coord.getX()+2][coord.getY()-1].getPiece() ==null){
                c = new Coord(coord.getX()+2 , coord.getY()-1);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()+2][coord.getY()-1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){//if allowed moves does not equal selected piece position
                    c = new Coord(coord.getX()+2 , coord.getY()-1);
                    validMoves.add(c);
                }
            }
        }

        if (coord.getX()-1 >=0 && coord.getY()+2 <8){ //move left 1 and up 2 a single time
            if(currentBoard[coord.getX()-1][coord.getY()+2].getPiece() ==null){
                c = new Coord(coord.getX()-1 , coord.getY()+2);
                validMoves.add(c);
            }else {
                if (currentBoard[coord.getX() - 1][coord.getY() + 2].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()) {
                    c = new Coord(coord.getX() - 1, coord.getY() + 2);
                    validMoves.add(c);
                }
            }

        }

        if (coord.getX()-2 >=0 && coord.getY()+1 <8){ //move left 2 and up 1 a single time
            if(currentBoard[coord.getX()-2][coord.getY()+1].getPiece() ==null){
                c = new Coord(coord.getX()-2 , coord.getY()+1);
                validMoves.add(c);
            }else{
                if(currentBoard[coord.getX()-2][coord.getY()+1].getPiece().isWhite() != currentBoard[coord.getX()][coord.getY()].getPiece().isWhite()){
                    c = new Coord(coord.getX()-2 , coord.getY()+1);
                    validMoves.add(c);
                }
            }
        }

//        if (!allowedMoves.isEmpty()) {
//            Random rand = new Random();
//            // Move choice is done here
//            Coordinates chosenMove = (allowedMoves).get(rand.nextInt(allowedMoves.size()));
//
//        }

        return validMoves;
    }

}

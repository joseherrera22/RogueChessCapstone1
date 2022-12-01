package com.example.roguechesscapstone;
//This is the coordinates class for the X and Y coordinates
//get = when piece is chosen
//set = when piece is set
public class Coord {
    private int x;
    private int y;


    public Coord(int x, int y) {
        this.x = x;
        this.y = y;

    }

    void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }
}
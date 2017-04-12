package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 */

public class Cell {
    private Cell[] neighbours;
    private Position position;

    public Cell(Position position) {
        this.position = position;
        this.neighbours = new Cell[4];
    }

    public Cell() {
    }

    public Cell getNeighbour(short direction) {
        return null;
    }

    public short getDirection(Cell nightbour){
        return 0;
    }

    public void append (short direction, Tile tile){  // co ma ta funkcja zwracac? i czy przyjmuje Tile, czy Cell?

    }

    public boolean isTile(){  // czy to jest potrzebne?
        return false;
    }
}

package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 */

public class Tile extends Cell {
    private int type;

    public Tile(int type) {
        this.type = type;
        // poinformuj wszystkich sasiadow, ze jestes
    }

    public int getType() {
        return type;
    }

    public boolean getColor(short direction){ //na pewno ma byc boolean?
        return false;
    }

    public Tile nextOnLine(Tile from){
        return null;
    }

    public Tile getWhiteNeighbour(int which){ //jaki typ ma miec which?
        return null;
    }

    public Tile getBlackNeighbour(int which){ //jaki typ ma miec which?
        return null;
    }

}

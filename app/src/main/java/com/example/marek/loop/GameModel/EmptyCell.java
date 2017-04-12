package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 */

public class EmptyCell extends Cell{
    private boolean hasWhiteNeighbours;
    private boolean hasBlackNeighbours;

    public Tile createTile(){
        return null;
    }

    public boolean tileFits(int type){
        return false;
    }

    public boolean isDetermited(){
        return false;
    }
}

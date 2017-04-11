package com.example.marek.loop;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marek on 11.04.17.
 */

public class Board {
    private Map<Position, Cell> graph;

    //TODO


    public Board() {
        this.graph = new HashMap<>();
    }

    public Map<Position, Cell> getCrrPosition() {  // czy dobry typ?
        return graph;
    }

    public void setCrrPosition(Map<Position, Cell> graph) { // ?????
        this.graph = graph;
    }

    public void addCell(Cell c){

    }

    public boolean checkWin (Tile t, boolean color){ // czy to nie powinno byc w game?
        return false;
    }
}

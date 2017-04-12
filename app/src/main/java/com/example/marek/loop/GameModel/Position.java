package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 */

public class Position {
    private int x,y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Position getNeighbour (short direction){
        return null;
    }
}

package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 * Updated by Piotr on 13.04.17
 */

public class Position
{
    private int x,y;

    public Position(int xx, int yy) { x=xx; y=yy; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return y == position.y && x==position.x;
    }

    @Override
    public int hashCode()
    {
        return 65537 * x + y;
    }

    public Position getNeighbour (short direction)
    {
        if (direction<0 || direction >3) throw new IllegalArgumentException("Invalid direction.");
        return new Position(
                x+(1-(direction & 1))*(1-direction),
                y+(direction & 1)*(direction-2) );
    }

    // Stałe opisujące kierunki
    public static final short EAST =0;
    public static final short NORTH=1;
    public static final short WEST =2;
    public static final short SOUTH=3;
}

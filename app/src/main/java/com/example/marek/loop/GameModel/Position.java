package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 * Beeing updated by Piotr since 13.04.17.
 */

public class Position
{
    private int x,y;

    public Position(int xx, int yy) { x=xx; y=yy; }

    /** Metoda dekodująca - tworzy Position na podstawie hashCode
     * Powinna działać, ale trzeba przetestować
     */
    public static Position fromHashCode(int hash)
    {
        hash-= 65537/2;
        int x= hash >0 ? hash/65537 : hash/65537 -1;
        int y= hash- x*65537+ 65537/2; // poprawiona reszta z dzielenia
        return new Position(x,y);
    }

    // Dostęp do współrzędnych
    public int getX() { return x; }
    public int getY() { return y; }

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
    { // Duża liczba pierwsza zapewnia większa niepowtarzalność, skoro x i y są "bliskie" 0.
        return 65537 * x + y; // W praktyce będzie to kodowanie 1 do 1 :)
    }

    public Position getNeighbour (int direction)
    {
        if (direction<0 || direction >3) throw new IllegalArgumentException("Invalid direction.");
        return new Position(
                x+ (1-(direction & 1))*(1-direction),
                y+ (direction & 1)*(direction-2) );
    }

    @Override
    public String toString() // To może się przydać do debugowania
    {
        return String.format("(%3d,%3d)",x,y);
    }

    // Oblicza różnicę współrzędnych w zadanym kierunku.
    public int distanceInDirection(Position other, int direction)
    {
        switch (direction)
        {
            case NORTH: return other.y - y; // other jest (powinien być) powyżej
            case SOUTH: return y - other.y; // other jest (powinien być) poniżej
            case EAST : return other.x - x; // other jest (powinien być) po prawej
            case WEST : return x - other.x; // other jest (powinien być) po lewej
        }
        throw new IllegalArgumentException("Invalid direction.");
    }

    // Stałe opisujące kierunki
    public static final int EAST =0;
    public static final int NORTH=1;
    public static final int WEST =2;
    public static final int SOUTH=3;
}

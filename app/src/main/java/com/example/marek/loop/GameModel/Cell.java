package com.example.marek.loop.GameModel;

import java.util.Arrays;

/**
 * Created by marek on 11.04.17.
 * Beeing updated by Piotr since 13.04.17
 */

public abstract class Cell
{
    private Cell[] neighbours;
    private Position position;

    public Cell(Position position) {
        this.position = position;
        neighbours = new Cell[4];
    }

    protected Cell(Cell other) //
    {
        this.position = other.position;
        this.neighbours = new Cell[4];
        for(int dir =0; dir<4; dir++)
        {
            if (other.neighbours[dir]!=null)
                append(dir,other.neighbours[dir]);
        }
    }

    public final Position getPosition()
    {
        return position;
    }

    public Cell getNeighbour(int direction)
    {
        if (direction<0 || direction >3) throw new IllegalArgumentException("Invalid direction.");
        return neighbours[direction];
    }

    public int getDirection(Cell nighbour)
    {
        if (nighbour==null) return -1;
        // Rozwiązanie obliczeniowe
        int dx = 1+position.getX()-nighbour.position.getX();
        int dy = 1-position.getY()+nighbour.position.getY();
        /* Jeśli mamy do czynienia z sąsiadami, (dx,dy) jest postaci:
            (1,2) - siąsiad południowy  (2,1) - sąsiad zachodni
            (1,0) - sąsiad północny     (0,1) - sąsiad wschodni
        */
        // iloczyn dx*dy jest 0 lub 2 i jedna z nich jest 1 - pełna charakteryzacja
        int c=dx*dy;
        if ( (c | 2)!=2 || (dx+dy-c)!=1 ) return -1; // To nie są sąsiedzi
        int direction= ( (dx & 1)+ c ); // Wygląda niejasno, ale działa.
        /*
        short direction=-1; // Rozwiązanie czytelne:
        if (nighbour.getX() == getX()-1) direction = Position.WEST;
        if (nighbour.getX() == getX()+1) direction = Position.EAST;
        if (nighbour.getY() == getY()-1) direction = Position.NORTH;
        if (nighbour.getY() == getY()+1) direction = Position.SOUTH;
        if (direction<0) return -1;
        // */
        if (neighbours[direction]!=nighbour) // Sąsiedzi wg. współrzędnych, ale coś się nie zgadza
            throw new IllegalStateException("Invalid cell graph configuration!");
        return direction;
    }

    /** Ta metoda reaguje na zmianę sąsiada.
     * Będzie jej używała klasa EmptyCell aby poznać swoje otoczenie,
     * a klasa tile będzie
      */
    protected void fireAppend(int direction, Cell cell) {}

    // Ta metoda
    public void append(int direction, Cell cell)
    {
        if (direction<0 || direction >3) throw new IllegalArgumentException("Invalid direction.");
        if (neighbours[direction]==cell) return;
        if (neighbours[direction]!=null)
            neighbours[direction].neighbours[direction ^2]=null; // Skasuj starego sąsiada
        fireAppend(direction,cell); // Zastanów się, czy zmiana sasiada coś znaczy
        cell.fireAppend(direction ^2,this); // Powiadom sąsiada o zmianie
        neighbours[direction] =cell; // Ustaw nowego sąsiada
        cell.neighbours[direction ^ 2]=this; // Ustaw sąsiada (this) nowemu sąsiadowi
    }

    public boolean isTile()
    {
        return false;
    }

    /**
     * Usuwa wszystkie grafowe połączenia komórki. Wywoływać dla pewności, przy usuwaniu z grafu.
     */
    protected void forgetMe()
    {
        for (int dir = 0; dir < 3; dir++)
            if (neighbours[dir] != null)
                neighbours[dir].neighbours[dir ^ 2] = null;
    }

    // Maski bitowe kierunków na potrzeby klas konretnych
    protected static final byte[] mask= new byte[]{1,2,4,8};
}

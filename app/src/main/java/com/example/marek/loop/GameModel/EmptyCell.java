package com.example.marek.loop.GameModel;

/**
 * Created by marek on 11.04.17.
 * Updated by Piotr on 14.04.17
 */

public class EmptyCell extends Cell
{
    public EmptyCell(Position pos)
    {
        super(pos);
        knownWhite=knownBlack=0;
    }

    private byte knownWhite, knownBlack;

    public Tile createTile()
    {
        int black = countBits(knownBlack);
        int white = countBits(knownWhite);
        if (black<2 && white<2) throw new IllegalArgumentException("Cell is not determined!");
        if ((knownWhite & knownBlack)!=0) throw new IllegalStateException("Unbelievable overlap of black and white line!");
        if (black>2 || white>2) // Trzy linie tego samego koloru?
            return null; // Nie istnieje taka płytka!
        byte type = black==2 ? knownBlack : (byte) ~knownWhite;
        forgetMe(); // Odepnij od sąsiadów
        return new Tile(this,type);
    }

    public boolean tileFits(byte type)
    {
        // "type" to końce linii czarnej, a "~type" to końce linii białej
        return (type & knownWhite) == 0 && (~type & knownBlack)==0;
    }

    //Tutaj reagujemy na zmianę sąsiada.
    @Override
    protected void fireAppend(int direction, Cell cell)
    {
        if(cell.isTile())
        {
            Tile tile = (Tile) cell;
            if(tile.getColor(direction ^2))
            {
                knownWhite &= 15^ Cell.mask[direction]; // Skasuj ewnetualny biały
                knownBlack |= Cell.mask[direction]; // Dopisz czarny
            }
            else
            {
                knownWhite |= Cell.mask[direction]; // Dopisz biały
                knownBlack &= 15^ Cell.mask[direction]; // Skasuj ewentualny czarny
            }
        }
    }

    public boolean isDetermited()
    {
        return countBits(knownBlack)>1 || countBits(knownWhite) >1;
    }

    private static int countBits(byte val)
    {
        return (val & 1) + ((val & 2) >> 1) + ((val & 4) >> 2) + ((val & 8) >> 3);
    }
}

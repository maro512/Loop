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

    /** Tworzy płytkę zdeterminowaną. */
    Tile createTile()
    {
        int black = countBits(knownBlack);
        int white = countBits(knownWhite);
        if (black<2 && white<2) throw new IllegalArgumentException("Cell is not determined!");
        // if ((knownWhite & knownBlack)!=0) throw new IllegalStateException("Unbelievable overlap of black and white line!");
        if (black>2 || white>2) // Trzy linie tego samego koloru?
            return null; // Nie istnieje taka płytka!
        byte type = black==2 ? knownBlack : (byte) (~knownWhite & 15);
        Tile tile =  new Tile(getPosition(),type);
        replaceMe(tile);
        return tile;
    }

    /** Wstawia płytkę danego typu. */
    Tile createTile(byte type)
    {
        if (!tileFits(type)) throw new IllegalArgumentException("The given tile type does not fit.");
        Tile tile =  new Tile(getPosition(),type);
        replaceMe(tile);
        return tile;
    }

    public boolean tileFits(byte type)
    {
        // "type" to końce linii czarnej, a "~type" to końce linii białej
        return countBits(type)==2 && (type & knownWhite) == 0 && (~type & knownBlack)==0;
    }

    // Pole zdeterminowane to takie na które pasuje conajwyżej jedna płytka.
    public boolean isDetermited()
    {
        return countBits(knownBlack)>1 || countBits(knownWhite) >1;
    }

    /*  MARTWE POLE to takie, na które nie pasuje żadna płytka. Board nie udostępnia martwych pól,
    ale ich też nie odpina od grafu, ponieważ płytki nie powinny mieć sąsiadów null. */
    public boolean isDead() { return countBits(knownBlack)>2 || countBits(knownWhite) >2; }

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

    private static int countBits(byte val)
    {
        return (val & 1) + ((val & 2) >> 1) + ((val & 4) >> 2) + ((val & 8) >> 3);
    }
}

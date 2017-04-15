package com.example.marek.loop.GameModel;

import java.util.*;

/**
 * Created by marek on 11.04.17.
 */

public class Board
{
    private Map<Position, Cell> graph;
    private boolean whiteWin, blackWin;

    public Board()
    {
        graph = new HashMap<>();
        minimalWinningLength=8;
        Cell first = new EmptyCell( new Position(0,0) );
        graph.put(first.getPosition(),first );
        whiteWin=blackWin= false; // Liczba zwycięskich linii
    }

    //TODO
    public Map<Position, Cell> getCrrPosition() {  // czy dobry typ?
        return Collections.unmodifiableMap(graph);
    }
    /* //Wywaliłem, bo to sprzeczne z zasadą hermatyzacji danych.
    public void setCrrPosition(Map<Position, Cell> graph) { // ?????
        this.graph = graph;
    } // */

    // Stała opisująca długość linii wygrywającej niebędącej pętlą.
    private int minimalWinningLength;
    public int getMinimalWinningLength() { return minimalWinningLength; }
    public void setMinimalWinningLength(int minimalWinningLength)
    {
        if (minimalWinningLength<5) throw new IllegalArgumentException("Winning length cannot be less than 5.");
        this.minimalWinningLength = minimalWinningLength;
    }

    public boolean isWhiteWin()
    {
        return whiteWin;
    }

    public boolean isBlackWin()
    {
        return blackWin;
    }

    /*
    public byte getCell(int x, int y)
    {
        Cell cell = graph.get(new Position(x,y));
        if (cell==null) return 0;
        if (cell.isTile()) return ((Tile) cell).getType();
        return 1;
    }
    */

    /** Zwraca wolne pole znajdujące się na pozycji (x,y).
     * Jeśli nie podanej pozycji nie ma wolnego pola (np. jest płytka), zwraca null.
     */
    public EmptyCell getAvailablePlace(int x, int y)
    {
        Position p = new Position(x,y);
        Cell place = graph.get(p);
        if (place==null || place.isTile()) return null;
        return (EmptyCell) place;
    }

    /** Wstawia płytkę podanego typu na zadane wolne pole.
     * @param place wolne pole (MUSI być w mapie na dobrej pozycji) na które wstawiamy płytkę
     * @param type typ płytki, który musi pasować na podane wolne pole.
     */
    public void setTile(EmptyCell place, byte type)
    {
        if (graph.get(place.getPosition())!=place) throw new IllegalArgumentException("The given position is not on the map!");
        Tile tile = place.createTile(type); // Tworzy płytkę.
        addTile(tile);
    }

    private void addTile(Tile tile)
    {
        graph.put(tile.getPosition(),tile); // Dodaj płytkę do mapy (nadpisując poprzednią w tym miejscu)
        for(int dir =0; dir<4; dir++) // Sprawdź reakcje sąsiadów
        {
            Cell cell = tile.getNeighbour(dir);
            if (cell == null) // Brak sąsiada!
            {
                Position next = tile.getPosition().getNeighbour(dir);
                EmptyCell newplace = new EmptyCell(next); // Stwórz nowe wolne pole
                graph.put(next, newplace); // Dodaj pole do listy
                for (int dir2 = 0; dir2 < 4; dir2++) // Dopnij sąsiadów do nowego pustego pola
                {
                    Position next2 = next.getNeighbour(dir2); // Pobierz pozycję
                    newplace.append(dir2, graph.get(next2)); // Dodaj sąsiada (pobierz z mapy)
                }
                continue;
            }
            if (cell.isTile()) continue; // Sąsiad-płytka już się nie zmieni
            EmptyCell place = (EmptyCell) cell;
            if (place.isDetermited()) {
                if (place.isDead()) graph.remove(place.getPosition()); // Błąd! Usuń pole
                else addTile(place.createTile()); // Stwórz i wstaw deterministyczną płytkę.
            }
        }
        if (!whiteWin && checkWin(tile, false)) whiteWin=true;
        if (!blackWin && checkWin(tile, true) ) blackWin=true;
    }

    // Metoda sprawdzająca, czy z płytki nie wychodzi linia wygrywająca w danym kolorze.
    private boolean checkWin(Tile t, boolean color)
    {
        Cell end1 = t.getColorNeighbour(color);
        if (!end1.isTile()) return false; // Płytka nie jest połączona tym kolorem z innymi
        Tile end1prev= t;
        Tile end2prev= (Tile) end1;
        do // Szukaj pierwszego końca linii
        {
            Tile tile2= (Tile) end1;
            end1= tile2.nextOnLine(end1prev); // Kolejna płytka (lub puste pole)
            end1prev=tile2;
            if (end1==t) return true; // Pętla
            if (end1==null) return false; // Plansza jeszcze nie gotowa (będzie drugi test w przyszłości!)
        } while (end1.isTile());
        Cell end2 = t; //.getColorNeighbour(color,false);
        do  // Szukaj drugiego końca linii
        {
            Tile tile2= (Tile) end2;
            end2= tile2.nextOnLine(end2prev); // Kolejna płytka (lub puste pole)
            end2prev=tile2;
            if (end2==null) return false; // Plansza jeszcze nie gotowa (będzie drugi test w przyszłości!)
        } while (end2.isTile());
        int end1dir = end1.getDirection(end1prev); // Wyznacz kierunki zakończeń linii
        int end2dir = end2.getDirection(end2prev);
        if ((end1dir ^ end2dir)!=2) return false;  //Sprawdź przeciwstawność kierunków
        int length= end1.getPosition().distanceInDirection(end2.getPosition(),end1dir);
        return length> minimalWinningLength;
    }
}

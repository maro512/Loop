package com.example.marek.loop.GameModel;

import java.util.List;

/**
 * Created by marek on 11.04.17.
 */

public class Game {
    private Board board;
    private Player[] players;

    public Game(Board board) {
        this.board = board;
        this.players = new Player[2];
    }

    public List<Position> getPosiibleMoves(){ // co ma przyjmowac i co zwracac?
        return null;
    }

    public void startGame(){

    }

    public boolean isTerminal(){
        return false;
    }

    public Position getInitPosition(){ // co ma zwracac?
        return null;
    }

    public Player whoWon(){
        return null;
    }

    public Player getCrrPlayer(){
        return null;
    }
}

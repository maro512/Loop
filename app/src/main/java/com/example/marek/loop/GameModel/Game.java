package com.example.marek.loop.GameModel;

import java.util.List;
import java.util.Random;

/**
 * Created by marek on 11.04.17.
 */

public class Game {
    private Board board;
    private final int BLACK = 1;
    private final int WHITE = 0;
    private Player[] players;
    private int currentPlayer; // liczba z {0, 1}, do wyboru z tablicy players

    public Game(Board board) {
        this.board = board;
        this.players = new Player[2];
    }

    /**
     * Metoda zwraca możliwe typy płytek, które mogą zostać położone
     * na danym polu.
     * @param x, @param y - współrzędowolnego
     * @return lista typów jako lista obiektów Byte,
     *         null, gdy nie da się postawić płytki
     */
    public List<Byte> getPossibleMoves(int x, int y) {
        EmptyCell cell = board.getAvailablePlace(x, y);

        /*
         * TODO:
         *  - sprawdzanie każdego sąsiada i usuwanie niedozwolonych typów
         *    z listy wszystkich typów
         */

        if (cell != null || !cell.isDead()) {
          if (currentPlayer == BLACK)
        } else {
          return null;
        }
    }

    public void startGame() {
      pickFirstPlayer();

      /**
       * TODO:
       *  - makeMove
       *  - kontrola wygranej
       *  - zmiana gracza (while)
       */
    }

    public boolean isTerminal(){
        return board.isWhiteWin() || board.isBlackWin();
    }

    // możliwe, że do usunięcia
    public Position getInitPosition() { // co ma zwracac?
        return null;
    }

    public Player whoWon() {
      if (board.isWhiteWin()) {
        return players[WHITE];
      } else if (board.isBlackWin) {
        return players[BLACK];
      }
    }

    public Player getCrrPlayer() { return players[currentPlayer]; }

    private void pickFirstPlayer() {
        Random rnd = new Random();
        currentPlayer = rnd.nextInt(2);
    }
}

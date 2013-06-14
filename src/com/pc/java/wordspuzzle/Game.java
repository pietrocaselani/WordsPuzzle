package com.pc.java.wordspuzzle;

import com.pc.java.wordspuzzle.models.Player;

import java.util.ArrayList;

import static com.pc.java.wordspuzzle.Game.GameMode.MULTI;
import static com.pc.java.wordspuzzle.Game.GameMode.SINGLE;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public class Game {
    //region Fields
    private static Game sInstance;
    private GameMode mGameMode;
    private Player mPlayer1, mPlayer2;
    private ArrayList<String> mWords;
    //endregion

    //region Singleton
    public static Game getInstance() {
        if (sInstance == null) sInstance = new Game();
        return sInstance;
    }

    private Game() {}
    //endregion

    //region Public methods
    public void newGame(GameMode gameMode, Player player1, Player player2, ArrayList<String> words) {
        if (player1 == null) {
            throw new RuntimeException("Player 1 can't be null");
        } else if (gameMode == MULTI && player2 == null) {
            throw new RuntimeException("Multi player, second player can't be null");
        } else if (gameMode == SINGLE && player2 != null) {
            throw new RuntimeException("Single player, second player must be null");
        }

        Utils.clearScreen();

        String message = "Novo jogo com " + player1.getName() + " vs " + (player2 == null ? " PC " : player2.getName());

        System.out.println(message);
    }
    //endregion

    public enum GameMode {
        SINGLE, MULTI
    }
}
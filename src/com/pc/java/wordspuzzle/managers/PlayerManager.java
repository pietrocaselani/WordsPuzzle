package com.pc.java.wordspuzzle.managers;

import com.pc.java.wordspuzzle.models.Player;

import java.util.ArrayList;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public final class PlayerManager {
    //region Fields
    private static PlayerManager sInstance;
    private ArrayList<Player> mPlayers;
    private Player mCurrentPlayer;
    //endregion

    //region Singleton
    public static PlayerManager getInstance() {
        if (sInstance == null) sInstance = new PlayerManager();
        return sInstance;
    }

    private PlayerManager() {}
    //endregion

    //region Getters and Setters
    public ArrayList<Player> getPlayers() {
        if (mPlayers == null) {
            //TODO buscar players do JSON
        }

        return mPlayers;
    }

    public Player getCurrentPlayer() {
        return mCurrentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        mCurrentPlayer = currentPlayer;
    }
    //endregion

    //region Public methods
    public void savePlayer(Player player) {
        if (player != null) {
            // TODO salvar o player no JSON
        }
    }

    public void registerPlayer(String name) {
        registerPlayer(new Player(name));
    }

    public void registerPlayer(Player player) {
        getPlayers().add(player);
        //TODO apagar o json e salvar again
    }

    public void deletePlayer(Player player) {
        deletePlayer(player.getName());
    }

    public void deletePlayer(String name) {
        ArrayList<Player> players = getPlayers();
        if (players.size() > 0) {
            for (Player player : players) {
                if (player.getName().equalsIgnoreCase(name)) {
                    players.remove(player);
                    break;
                }
            }
            //TODO apagar o json e salvar again
        }
    }
    //endregion
}

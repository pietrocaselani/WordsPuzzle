package com.pc.java.wordspuzzle.managers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pc.java.wordspuzzle.models.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public final class PlayerManager {
    //region Fields
    private static final File JSON_FILE = new File("players.json");
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
            //TODO Checar isso!
            if (!JSON_FILE.exists()) {
                mPlayers = new ArrayList<Player>();
            } else {
                try {
                    mPlayers = new ObjectMapper().readValue(JSON_FILE, new TypeReference<List<Player>>() {});
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
        if (player != null && getPlayers().contains(player)) {
            savePlayers();
        }
    }

    public void registerPlayer(String name) {
        registerPlayer(new Player(name));
    }

    public void registerPlayer(Player player) {
        getPlayers().add(player);
        savePlayers();
    }

    public void deletePlayer(Player player) {
        if (player != null && getPlayers().size() > 0) {
            if (getPlayers().remove(player)) {
                savePlayers();
            } else {
                deletePlayer(player.getName());
            }
        }
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
            savePlayers();
        }
    }
    //endregion

    //region Private methods
    public boolean savePlayers() {
        ArrayList<Player> players = getPlayers();

        if (JSON_FILE.exists() && !JSON_FILE.delete()) {
            throw new RuntimeException("Não deletou o arquivo");
        }

        try {
            if (JSON_FILE.createNewFile()) {
                new ObjectMapper().writeValue(JSON_FILE, players);
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    //endregion
}
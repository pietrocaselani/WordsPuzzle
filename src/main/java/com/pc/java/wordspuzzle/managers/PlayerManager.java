package com.pc.java.wordspuzzle.managers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pc.java.wordspuzzle.models.Player;

import java.io.File;
import java.io.FileOutputStream;
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

    private PlayerManager() {
        try {
            if (!JSON_FILE.exists() && !JSON_FILE.createNewFile()) {
                throw new RuntimeException("Não criou o players.json");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //endregion

    //region Getters and Setters
    public ArrayList<Player> getPlayers() {
        if (mPlayers == null) {
            try {
                mPlayers = new ObjectMapper().readValue(JSON_FILE, new TypeReference<List<Player>>() {});
            } catch (IOException e) {
                mPlayers = new ArrayList<Player>();
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

        try {
            FileOutputStream fos = new FileOutputStream(JSON_FILE, false);
            new ObjectMapper().writeValue(fos, players);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}
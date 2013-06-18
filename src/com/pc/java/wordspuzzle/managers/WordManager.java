package com.pc.java.wordspuzzle.managers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pc.java.wordspuzzle.exceptions.InvalidWordException;

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
public final class WordManager {
    //region Fields
    private static final File JSON_FILE = new File("words.json");
    private static WordManager sInstance;
    private ArrayList<String> mWords, mCurrentWords;
    //endregion

    //region Singleton
    public static WordManager getInstance() {
        if (sInstance == null) sInstance = new WordManager();
        return sInstance;
    }

    private WordManager() {
        try {
            if (!JSON_FILE.exists() && !JSON_FILE.createNewFile()) {
                throw new RuntimeException("Não criou o words.json");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region Getters and Setters
    public ArrayList<String> getWords() {
        if (mWords == null) {
            try {
                mWords = new ObjectMapper().readValue(JSON_FILE, new TypeReference<List<String>>() {});
            } catch (IOException e) {
                mWords = new ArrayList<String>();
            }
        }

        return mWords;
    }
    //endregion

    //region Public methods
    public ArrayList<String> getGameWords() {
        return getGameWords(false);
    }

    public ArrayList<String> getGameWords(boolean reset) {
        if (mCurrentWords == null || reset) {
            //TODO pegar 3 palavras randomicas de 4 chars e 1 de um char
        }
        return mCurrentWords;
    }

    public void registerWord(String word) throws InvalidWordException {
        if (word.length() != 3 && word.length() != 4) throw new InvalidWordException();

        getWords().add(word);
        saveWords();
    }

    public void deleteWord(String word) {
        ArrayList<String> words = getWords();
        if (words.size() > 0) {
            for (String w : words) {
                if (w.equalsIgnoreCase(word)) {
                    words.remove(w);
                    break;
                }
            }
            saveWords();
        }
    }

    public boolean containsWord(String word) {
        ArrayList<String> words = getWords();
        for (String w : words) {
            if (w.equalsIgnoreCase(word)) return true;
        }

        return false;
    }
    //endregion

    //region Private methods
    private boolean saveWords() {
        ArrayList<String> words = getWords();

        try {
            FileOutputStream fos = new FileOutputStream(JSON_FILE, false);
            new ObjectMapper().writeValue(fos, words);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
    //endregion
}
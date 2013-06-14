package com.pc.java.wordspuzzle.managers;

import com.pc.java.wordspuzzle.exceptions.InvalidWordException;

import java.util.ArrayList;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public final class WordManager {
    //region Fields
    private static WordManager sInstance;
    private ArrayList<String> mWords, mCurrentWords;
    //endregion

    //region Singleton
    public static WordManager getInstance() {
        if (sInstance == null) sInstance = new WordManager();
        return sInstance;
    }

    private WordManager() {}
    //endregion

    //region Getters and Setters
    public ArrayList<String> getWords() {
        if (mWords == null) {
            //TODO buscar words do JSON
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
        //TODO apagar o json e salvar again
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
            //TODO apagar o json e salvar again
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
}
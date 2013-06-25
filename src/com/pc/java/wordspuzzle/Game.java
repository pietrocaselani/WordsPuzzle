package com.pc.java.wordspuzzle;

import com.pc.java.wordspuzzle.managers.PlayerManager;
import com.pc.java.wordspuzzle.models.Matrix;
import com.pc.java.wordspuzzle.models.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static com.pc.java.wordspuzzle.Game.GameMode.COMPUTER;
import static com.pc.java.wordspuzzle.Game.GameMode.MULTI;
import static com.pc.java.wordspuzzle.Game.GameMode.SINGLE;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public class Game {
    //region Fields
    private static final int EMPTY_INDEX = 1;
    private static Game sInstance;
    private GameMode mGameMode;
    private Player mPlayer1, mPlayer2;
    private ArrayList<String> mWords;
    private Matrix<Integer> mBoard, mOtherBoard, mCurrentBoard;
    //endregion

    //region Singleton
    public static Game getInstance() {
        if (sInstance == null) sInstance = new Game();
        return sInstance;
    }

    private Game() {
        mBoard = new Matrix<Integer>(4, 4, EMPTY_INDEX);
    }
    //endregion

    //region Public methods
    public void newGame(GameMode gameMode, Player player1, Player player2, ArrayList<String> words) {
        if (player1 == null) {
            throw new RuntimeException("Player 1 can't be null");
        } else if ((gameMode == MULTI || gameMode == COMPUTER) && player2 == null) {
            throw new RuntimeException("Multi player or computer mode, second player can't be null");
        }

        mPlayer1 = player1;
        mPlayer2 = player2;
        mGameMode = gameMode;
        mWords = words;

        int size = 1;
        for (String word : words) size += word.length();

        ArrayList<Integer> pieces = new ArrayList<Integer>(size);

        for (String word : words) {
            int length = word.length();
            for (int i = 0; i < length; i++) {
                pieces.add((int) word.charAt(i));
            }
        }

        pieces.add(EMPTY_INDEX);

        Random random = new Random();
        int fromIndex, toIndex, oldValue, newValue;

        for (int i = 0; i < pieces.size() * 2; i++) {
            fromIndex = random.nextInt(pieces.size());
            toIndex = random.nextInt(pieces.size());

            oldValue = pieces.get(fromIndex);
            newValue = pieces.get(toIndex);

            pieces.set(toIndex, oldValue);
            pieces.set(fromIndex, newValue);
        }

        for (int i = 0; i < size; i++) {
            mBoard.setValue(pieces.get(i), i);
        }

        if (mGameMode == MULTI || mGameMode == COMPUTER) {
            //TODO não tem como fazer isso usando copy/clone??!!
            mOtherBoard = new Matrix<Integer>(4, 4);
            for (int i = 0; i < size; i++) {
                mOtherBoard.setValue(mBoard.getValue(i), i);
            }
        }

        mCurrentBoard = mBoard;

        drawBoard();

        PlayerManager.getInstance().setCurrentPlayer(player1);
        play();
    }
    //endregion

    //region Private methods
    private void play() {
        PlayerManager pm = PlayerManager.getInstance();
        System.out.println("Jogadando: " + pm.getCurrentPlayer().getName());

        if (isComputer()) {

        } else {
            Scanner scanner = new Scanner(System.in);

            int row, column;
            boolean canMove;

            do {
                System.out.print("Digite a linha da peça que deseja mover: ");
                row = scanner.nextInt();
                System.out.print("Digite a coluna da peça que deseja mover: ");
                column = scanner.nextInt();

                column--;
                row--;

                canMove = canMovePeace(row, column);
            } while (!canMove);

            int oldValue = mCurrentBoard.getValue(row, column);
            int oldEmpty = getEmptyIndex();
            mCurrentBoard.setValue(oldValue, oldEmpty);
            mCurrentBoard.setValue(EMPTY_INDEX, row, column);
            if (checkWin()) {
                System.out.println("O jogador " + pm.getCurrentPlayer().getName() + " venceu!");
            } else {
                tooglePlayers();
                drawBoard();
                play();
            }
        }
    }

    private void tooglePlayers() {
        if (mGameMode == SINGLE) return;

        PlayerManager pm = PlayerManager.getInstance();
        if (pm.getCurrentPlayer() == mPlayer1) {
            pm.setCurrentPlayer(mPlayer2);
            mCurrentBoard = mOtherBoard;
        } else {
            pm.setCurrentPlayer(mPlayer1);
            mCurrentBoard = mBoard;
        }
    }

    private boolean isComputer() {
        return mGameMode == COMPUTER && PlayerManager.getInstance().getCurrentPlayer().getName().equals("Computer");
    }

    private void drawBoard() {
        Utils.clearScreen();

        System.out.println(mWords.toString() + "\n");

        int rows = mCurrentBoard.getRowCount(), columns = mCurrentBoard.getColumnCount();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                int value = mCurrentBoard.getValue(r, c);
                if (value == EMPTY_INDEX) {
                    System.out.print("\t");
                } else {
                    System.out.print(String.format("%c\t", (char) value));
                }
            }
            System.out.print("\n");
        }
    }

    private int getEmptyIndex() {
        int size = mCurrentBoard.getColumnCount() * mCurrentBoard.getRowCount();
        for (int i = 0; i < size; i++) {
            if (mCurrentBoard.getValue(i) == EMPTY_INDEX)
                return i;
        }

        return EMPTY_INDEX;
    }

    private boolean canMovePeace(int row, int column) {
        boolean canMove = column > 0 && mCurrentBoard.getValue(row, column - 1) == EMPTY_INDEX;
        if (canMove) return true;

        canMove = column < mCurrentBoard.getColumnCount() - 1 && mCurrentBoard.getValue(row, column + 1) == EMPTY_INDEX;
        if (canMove) return true;

        canMove = row > 0 && mCurrentBoard.getValue(row - 1, column) == EMPTY_INDEX;
        if (canMove) return true;

        canMove = row < mCurrentBoard.getRowCount() - 1 && mCurrentBoard.getValue(row + 1, column) == EMPTY_INDEX;
        return canMove;

    }

    private boolean checkWin() {
        boolean win = true;
        int i, value, length, boardIndex = 0;
        char c;

        for (String word : mWords) {
            length = word.length();
            for (i = 0; i < length; i++) {
                c = word.charAt(i);
                value = mCurrentBoard.getValue(boardIndex++);
                if (value != c) {
                    win = false;
                    break;
                }
            }

            if (!win) break;
        }

        return win;
    }
    //endregion

    public enum GameMode {
        SINGLE, MULTI, COMPUTER
    }
}
package com.pc.java.wordspuzzle;

import com.pc.java.wordspuzzle.exceptions.InvalidWordException;
import com.pc.java.wordspuzzle.managers.PlayerManager;
import com.pc.java.wordspuzzle.managers.WordManager;
import com.pc.java.wordspuzzle.models.Player;

import java.util.ArrayList;
import java.util.Scanner;

import static com.pc.java.wordspuzzle.Game.GameMode;

/**
 * Created by Pietro Caselani
 * On 13/06/13
 * WordsPuzzle
 */
public class Menu {
    //region Fields
    private Scanner mScanner;
    //endregion

    //region No instances
    private Menu() {
        mScanner = new Scanner(System.in);
    }
    //endregion

    //region Public methods
    public static void showMenu() {
        Menu menu = new Menu();
        ArrayList<Player> players = PlayerManager.getInstance().getPlayers();

        if (players == null || players.size() == 0) {
            menu.showInitialMenu();
        } else {
            menu.showGameMenu();
        }
    }
    //endregion

    //region Private methods
    private void showInitialMenu() {
        registerNewPlayer("Registre o seu perfil: ");
    }

    private void showGameMenu() {
        System.out.println("1 - Visualizar jogadores e ranking;");
        System.out.println("2 - Visualizar palavras cadastradas;");
        System.out.println("3 - Registrar novo jogador;");
        System.out.println("4 - Registrar nova palavra;");
        System.out.println("5 - Excluir jogador;");
        System.out.println("6 - Excluir palavra;");
        System.out.println("7 - Pesquisar palavra;");
        System.out.println("8 - Novo jogo;");
        System.out.println("9 - Limpar a tela;");
        System.out.print("Digite uma opção: ");
        int option = mScanner.nextInt();
        mScanner.nextLine();
        switch (option) {
            case 1:
                showPlayers();
                break;
            case 2:
                showWords();
                break;
            case 3:
                registerNewPlayer("Digite o nome do novo jogador: ");
                break;
            case 4:
                registerNewWord();
                break;
            case 5:
                deletePlayer();
                break;
            case 6:
                deleteWord();
                break;
            case 7:
                searchWord();
                break;
            case 8:
                startNewGame();
                break;
            case 9:
                Utils.clearScreen();
                showGameMenu();
                break;
            default:
                Utils.clearScreen();
                System.out.print("Digite uma opção válida!\n\n");
                showGameMenu();
        }
    }

    private void startNewGame() {
        Utils.clearScreen();
        if (WordManager.getInstance().getWords().size() < 4) {
            String message = Utils.isWindows() ? "E necessario" : "É necessário";
            System.out.print("\n\n******** " + message + " cadastrar 4 palavras para começar! ********\n\n");
            showGameMenu();
        } else {
            System.out.println("1 - Single player;");
            System.out.println("2 - Multi player;") ;
            System.out.println("3 - Contra o computador;");
            System.out.print("--> ");

            int op = mScanner.nextInt();
            if (op == 1) {
                newSingleGame();
            } else if (op == 2 || op == 3) {
                if (op == 2 && PlayerManager.getInstance().getPlayers().size() < 2) {
                    String message = Utils.isWindows() ? "E necessario" : "É necessário";
                    System.out.print("\n\n******** " + message + " ter 2 jogadores para começar! ********\n\n");
                    showGameMenu();
                } else {
                    newMultiGame(op == 2);
                }
            } else {
                Utils.clearScreen();
                startNewGame();
            }
        }
    }

    private void newSingleGame() {
        Player player1 = getPlayer("\n\nSelecione o jogador: ");
        ArrayList<String> gameWords = WordManager.getInstance().getGameWords();
        GameMode gameMode = GameMode.SINGLE;

        Game.getInstance().newGame(gameMode, player1, null, gameWords);
    }

    private void newMultiGame(boolean twoPlayers) {
        Player player1 = getPlayer("\n\nSelecione o jogador: ");
        Player player2;
        GameMode gameMode;

        if (twoPlayers) {
            do {
                player2 = getPlayer("\n\nSelecione o segundo jogador: ");
            } while (player1 == player2);
            gameMode = GameMode.MULTI;
        } else {
            player2 = new Player("Computer");
            gameMode = GameMode.COMPUTER;
        }

        ArrayList<String> gameWords = WordManager.getInstance().getGameWords();

        Game.getInstance().newGame(gameMode, player1, player2, gameWords);
    }

    private Player getPlayer(String message) {
        ArrayList<Player> players = PlayerManager.getInstance().getPlayers();
        for (int i = 0 ; i < players.size(); i++) {
            System.out.println(String.format("%d - %s", i + 1, players.get(i).getName()));
        }

        System.out.print(message);
        int index = mScanner.nextInt();
        index--;
        if (index < 0 || index >= players.size()) {
            Utils.clearScreen();
            System.out.print("\n\n******** Digite um número válido! ********\n\n");
            return getPlayer(message);
        }

        return players.get(index);
    }

    private void searchWord() {
        System.out.print("\n\nDigite a palavra para pesquisar: ");
        String word = mScanner.nextLine();

        String message = WordManager.getInstance().containsWord(word) ?
                " foi encontrada" :  Utils.isWindows() ?
                " nao foi encontrada" : " não foi encontada";

        System.out.print("A palavra " + word + message + "\n\n");

        showGameMenu();
    }

    private void deleteWord() {
        System.out.print("\n\nPalavras:\n");
        ArrayList<String> words = WordManager.getInstance().getWords();
        for (int i = 0 ; i < words.size(); i++) {
            System.out.println(String.format("%d - %s", i + 1, words.get(i)));
        }

        System.out.print("\n\nSelecione o número do jogador para excluir: ");
        int index = mScanner.nextInt();
        index--;
        if (index < 0 || index >= words.size()) {
            Utils.clearScreen();
            System.out.print("\n\n******** Digite um número válido! ********\n\n");
            deleteWord();
        } else {
            WordManager.getInstance().deleteWord(words.get(index));

            showGameMenu();
        }
    }

    private void deletePlayer() {
        System.out.print("\n\nJogadores:\n");
        ArrayList<Player> players = PlayerManager.getInstance().getPlayers();
        for (int i = 0 ; i < players.size(); i++) {
            System.out.println(String.format("%d - %s", i + 1, players.get(i).getName()));
        }

        System.out.print("\n\nSelecione o número do jogador para excluir: ");
        int index = mScanner.nextInt();
        index--;
        if (index < 0 || index >= players.size()) {
            Utils.clearScreen();
            System.out.print("\n\n******** Digite um número válido! ********\n\n");
            deletePlayer();
        } else {
            PlayerManager.getInstance().deletePlayer(players.get(index));

            showGameMenu();
        }
    }

    private void registerNewWord() {
        String message = Utils.isWindows() ?
                "(Maximo de 4 letras, minimo de 3 letras)" : "(Máximo de 4 letras, mínimo de 3 letras)";
        System.out.print("\n\nDigite a nova palavra " + message + ": ");
        String word = mScanner.nextLine();

        try {
            WordManager.getInstance().registerWord(word);
        } catch (InvalidWordException e) {
            Utils.clearScreen();
            System.out.print("\n\n******** Digite uma palavra válida! ********\n\n");
            registerNewWord();
        }

        showGameMenu();
    }

    private void registerNewPlayer(String message) {
        System.out.print(message);
        String playerName = mScanner.nextLine();

        if (playerName == null || playerName.length() == 0) {
            Utils.clearScreen();
            System.out.print("\n\n******** Digite um nome válido! ********\n\n");
            registerNewPlayer(message);
        } else {
            Player player = new Player(playerName);
            PlayerManager.getInstance().registerPlayer(player);
            PlayerManager.getInstance().setCurrentPlayer(player);
        }

        showGameMenu();
    }

    private void showWords() {
        Utils.clearScreen();
        System.out.print("Palavras:\n");
        ArrayList<String> words = WordManager.getInstance().getWords();
        for (String word : words) System.out.println(word);

        showGameMenu();
    }

    private void showPlayers() {
        Utils.clearScreen();
        System.out.print("Jogadores:\n");
        ArrayList<Player> players = PlayerManager.getInstance().getPlayers();
        for (Player player : players) System.out.println(player.toString());

        showGameMenu();
    }
    //endregion
}
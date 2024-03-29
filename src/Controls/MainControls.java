package controls;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cardcollections.*;
import gui.*;
import game.Game;
import game.GameState;
import player.*;

public class MainControls extends KeyAdapter {
    private Set<Integer> pressedKeys = new HashSet<>();
    private Game game;
    private MainGUI GUI;

    public MainControls(Game game, MainGUI GUI) {
        this.game = game;
        this.GUI = GUI;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newKeyPress = e.getKeyCode();
        pressedKeys.add(newKeyPress);
        switch (game.getGameState()) {
            case START_SCREEN:
                handleStartScreenKeyPress(newKeyPress);
                break;
            case OPEN_FIRST_CARDS:
                handleOpenFirstCardsKeyPress();
                break;
            case PLAYING:
                handlePlayingKeyPress(newKeyPress);
                break;
            case STRESS, NO_VALID_MOVES:
                handleStressNoValidMovesKeyPress(newKeyPress);
                break;
            case STALEMATE, PLAYER1_WINS, PLAYER2_WINS:
                handleEndGameKeyPress(newKeyPress);
                break;
        }
        System.out.println("Keys Pressed: " + pressedKeys.stream().map(i -> (char) i.intValue()).collect(Collectors.toList()));
        System.out.println(game);
        game.updateGameState();
    }

    private void handleStartScreenKeyPress(int newKeyPress) {
        if (newKeyPress == KeyEvent.VK_SPACE) {
            GUI.changeToPanel("Game");
            SoundUtility.bgmSound();
            game.setGameState(GameState.OPEN_FIRST_CARDS);
        }
    }

    private void handleOpenFirstCardsKeyPress() {
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            game.openCardsFromDeck();
            game.setGameState(GameState.PLAYING);
        }
    }

    private void handlePlayingKeyPress(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        // Allow switching piles regardless of blocking
        switch (newKeyPress) {
            case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
            case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
            case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
            case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
        }

        Pile[] piles = game.getBothPiles();
        // Processing player 1's controls if player 1 is not blocked.
        if (!player1.isBlocked()) {
            switch (newKeyPress) {
                case KeyEvent.VK_Q -> player1.throwCardToPile(0, piles);
                case KeyEvent.VK_W -> player1.throwCardToPile(1, piles);
                case KeyEvent.VK_E -> player1.throwCardToPile(2, piles);
                case KeyEvent.VK_R -> player1.throwCardToPile(3, piles);
                case KeyEvent.VK_S -> game.stress(player1, player2);            
            }
        }

        // Processing player 2's controls if player 2 is not blocked.
        if (!player2.isBlocked()) {
            switch (newKeyPress) {
                case KeyEvent.VK_U -> player2.throwCardToPile(0, piles);
                case KeyEvent.VK_I -> player2.throwCardToPile(1, piles);
                case KeyEvent.VK_O -> player2.throwCardToPile(2, piles);
                case KeyEvent.VK_P -> player2.throwCardToPile(3, piles);
                case KeyEvent.VK_K -> game.stress(player2, player1); 
            }
        }
    }


    private void handleStressNoValidMovesKeyPress(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        switch (newKeyPress) {
            case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
            case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
            case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
            case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
        }
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            game.openCardsFromDeck();
            game.setGameState(GameState.PLAYING);
        }
    }

    private void handleEndGameKeyPress(int newKeyPress) {
        if (newKeyPress == KeyEvent.VK_PERIOD) {
            GUI.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
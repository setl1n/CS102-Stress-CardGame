package game;

import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

import cardcollections.Pile;
import gui.*;

public class MainControls extends KeyAdapter {
    private Set<Integer> pressedKeys = new HashSet<>();
    private Game game;
    private MainGUI mainGUI;

    public MainControls(Game game, MainGUI mainGUI) {
        this.game = game;
        this.mainGUI = mainGUI;
        Sounds.menuSound();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newKeyPress = e.getKeyCode();
        pressedKeys.add(newKeyPress);
        switch (game.getGameState()) {
            case START_SCREEN:
                handleStartScreenKeyPress(newKeyPress);
                break;
            case OPEN_FIRST_CARDS, NO_VALID_MOVES:
                handleOpenCardsKeyPress(newKeyPress);
                break;
            case PLAYING:
                handlePlayingKeyPress(newKeyPress);
                break;
            case STRESS:
                handleStressKeyPress(newKeyPress);
                break;
            case END:
                handleEndGameKeyPress(newKeyPress);
                break;
            default:
                System.out.println("Game State Not Found");
                break;
        }
        System.out.println(
                "Keys Pressed: " + pressedKeys.stream().map(i -> (char) i.intValue()).collect(Collectors.toList()));
        System.out.println(game);
    }

    private void handleStartScreenKeyPress(int newKeyPress) {
        if (newKeyPress == KeyEvent.VK_SPACE) {
            mainGUI.changeToPanel("Game");
            Sounds.bgmSound();
            game.start();
        }
    }

    private void handleOpenCardsKeyPress(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        switch (newKeyPress) {
            case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
            case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
            case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
            case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
            default -> System.out.println("Invalid move");
        }
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            game.openCardsFromDeck();
            game.setGameState(GameState.PLAYING);
            Overlays.clear();
            Sounds.resumeBgm();
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
            default -> System.out.println("Invalid move");
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
                default -> System.out.println("Invalid move");
            }
        } else {
            // else plays invalid sound
            switch (newKeyPress) {
                case KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_S:
                    Sounds.invalidSound();
                    break;
                default:
                    System.out.println("Invalid move");
                    break;
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
                default -> System.out.println("Invalid move");
            }
        } else {
            //// else plays invalid sound
            switch (newKeyPress) {
                case KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_K:
                    Sounds.invalidSound();
                    break;
                default:
                    System.out.println("Invalid move");
                    break;
            }
        }
        game.updateGameState();
    }

    private void handleStressKeyPress(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        switch (newKeyPress) {
            case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
            case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
            case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
            case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
            default -> System.out.println("Invalid move");
        }
    }

    private void handleEndGameKeyPress(int newKeyPress) {
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            // Restart the game
            Overlays.clear();
            game.restart();
            mainGUI.restart(game);
            mainGUI.changeToPanel("Game");
            Sounds.bgmSound();
        } else if (newKeyPress == KeyEvent.VK_ESCAPE) {
            mainGUI.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
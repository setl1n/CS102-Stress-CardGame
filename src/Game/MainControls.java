package game;

import java.awt.event.*;
import java.util.*;
import java.util.stream.Collectors;

import cardcollections.Pile;
import gui.*;

/**
 * This class handles the main controls of the game.
 */
public class MainControls extends KeyAdapter {
    private Set<Integer> pressedKeys = new HashSet<>();
    private Game game;
    private MainGUI mainGUI;

    /**
     * Constructor for the MainControls class.
     * @param game The game object.
     * @param mainGUI The main GUI object.
     */
    public MainControls(Game game, MainGUI mainGUI) {
        this.game = game;
        this.mainGUI = mainGUI;
        Sounds.menuSound();
    }

    /**
     * Handles key press events.
     * @param e The key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int newKeyPress = e.getKeyCode();
        pressedKeys.add(newKeyPress);
        switch (game.getGameState()) {
            case START_SCREEN:
                handleStartScreenKeyPress(newKeyPress);
                break;
            case NO_VALID_MOVES:
                handleChangePilesControls(newKeyPress);
                handleOpenCardsKeyPress(newKeyPress);
                break;
            case PLAYING:
                handleChangePilesControls(newKeyPress);
                handleStressControls(newKeyPress);
                handlePlayingKeyPress(newKeyPress);
                break;
            case STRESS:
                handleChangePilesControls(newKeyPress);
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

    /**
     * Handles key press events at the start screen.
     * @param newKeyPress The key code of the pressed key.
     */
    private void handleStartScreenKeyPress(int newKeyPress) {
        if (newKeyPress == KeyEvent.VK_SPACE) {
            mainGUI.changeToPanel("Game");
            Sounds.bgmSound();
            game.loadDialog();
        }
    }

    /**
     * Handles key press events when there are no valid moves.
     * Checks for players holding down S and K to continue
     * @param newKeyPress The key code of the pressed key.
     */
    private void handleOpenCardsKeyPress(int newKeyPress) {
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            Overlays.clear();
            Sounds.resumeBgm();
            game.start();
        }
    }

    private void handleStressControls(int newKeyPress) {
        if (newKeyPress == KeyEvent.VK_S) {
            game.stress(game.getPlayer1(), game.getPlayer2());
        }
        if (newKeyPress == KeyEvent.VK_K) {
            game.stress(game.getPlayer2(), game.getPlayer1());
        }
    }

    /**
     * Handles key press events during gameplay.
     * If keypress is invalid, play invalid sound and lock controls for a delay
     * @param newKeyPress The key code of the pressed key.
     */
    private void handlePlayingKeyPress(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Pile[] piles = game.getBothPiles();

        if (!player1.isBlocked() && isPlayer1Control(newKeyPress)) {
            processPlayer1Actions(newKeyPress, player1, piles);
        } 
        if (!player2.isBlocked() && isPlayer2Control(newKeyPress)) {
            processPlayer2Actions(newKeyPress, player2, piles);
        }

        game.updateGameState();
    }

    private boolean isPlayer1Control(int keyCode) {
        return Set.of(KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_S).contains(keyCode);
    }

    private boolean isPlayer2Control(int keyCode) {
        return Set.of(KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_K).contains(keyCode);
    }

    private void processPlayer1Actions(int keyCode, Player player, Pile[] piles) {
        switch (keyCode) {
            case KeyEvent.VK_Q -> player.throwCardToPile(0, piles);
            case KeyEvent.VK_W -> player.throwCardToPile(1, piles);
            case KeyEvent.VK_E -> player.throwCardToPile(2, piles);
            case KeyEvent.VK_R -> player.throwCardToPile(3, piles);
            default -> handleInvalidMove();
        }
    }

    private void processPlayer2Actions(int keyCode, Player player, Pile[] piles) {
        switch (keyCode) {
            case KeyEvent.VK_U -> player.throwCardToPile(0, piles);
            case KeyEvent.VK_I -> player.throwCardToPile(1, piles);
            case KeyEvent.VK_O -> player.throwCardToPile(2, piles);
            case KeyEvent.VK_P -> player.throwCardToPile(3, piles);
            default -> handleInvalidMove();
        }
    }

    private void handleInvalidMove() {
        Sounds.invalidSound();
        System.out.println("Invalid move");
    }
    
    private void handleChangePilesControls(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        switch (newKeyPress) {
            case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
            case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
            case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
            case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
        }
    }

    /**
     * Handles key press events at the end of the game.
     * Checks for restart or quit
     * @param newKeyPress The key code of the pressed key.
     */
    private void handleEndGameKeyPress(int newKeyPress) {
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            // Restart the game
            Overlays.clear();
            game.restart();
            mainGUI.restart(game);
            Sounds.bgmSound();
        } else if (newKeyPress == KeyEvent.VK_ESCAPE) {
            mainGUI.dispose();
        }
    }
    
    /**
     * Handles key release events.
     * @param e The key event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}



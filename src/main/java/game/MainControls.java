package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import cardcollections.Pile;
import gui.MainGUI;
import gui.Overlays;
import gui.Sounds;

/**
 * This class handles the main controls of the game.
 */
public class MainControls extends KeyAdapter {
    private Set<Integer> pressedKeys = new HashSet<>();
    private final Set<Integer> PLAYER1_CONTROLS = Set.of(KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_S);
    private final Set<Integer> PLAYER2_CONTROLS = Set.of(KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_K);
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
                handleStartScreenControls(newKeyPress);
                break;
            case NO_VALID_MOVES:
                handleChangePilesControls(newKeyPress);
                handleOpenCardsControls();
                break;
            case PLAYING:
                handleChangePilesControls(newKeyPress);
                handlePlayCardsControls(newKeyPress);
                break;
            case STRESS:
                handleChangePilesControls(newKeyPress);
                break;
            case END:
                handleEndGameControls(newKeyPress);
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
    private void handleStartScreenControls(int newKeyPress) {
        if (newKeyPress == KeyEvent.VK_SPACE) {
            mainGUI.changeToPanel("Game");
            Sounds.bgmSound();
            game.loadDialog();
        }
    }

    /**
     * Handles key press events when there are no valid moves.
     * Checks for players holding down S and K to continue
     */
    private void handleOpenCardsControls() {
        if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
            Overlays.clear();
            Sounds.resumeBgm();
            game.start();
        }
    }

    /**
     * Handles key press events for card playing moves
     * @param newKeyPress The key code of the pressed key.
     */
    private void handlePlayCardsControls(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Pile[] piles = game.getBothPiles();

        if (!player1.isBlocked() && PLAYER1_CONTROLS.contains(newKeyPress)) {
            processPlayer1Actions(newKeyPress, player1, piles);
        } 
        if (!player2.isBlocked() && PLAYER2_CONTROLS.contains(newKeyPress)) {
            processPlayer2Actions(newKeyPress, player2, piles);
        }

        game.updateGameState();
    }

    /**
     * Processes actions for player 1 based on the pressed key.
     * If the action is valid, it is executed. Otherwise, an invalid move handling is triggered.
     * @param keyCode The key code of the pressed key.
     * @param player The player 1 object.
     * @param piles The array of pile objects.
     */
    private void processPlayer1Actions(int keyCode, Player player, Pile[] piles) {
        switch (keyCode) {
            case KeyEvent.VK_Q -> player.throwCardToPile(0, piles);
            case KeyEvent.VK_W -> player.throwCardToPile(1, piles);
            case KeyEvent.VK_E -> player.throwCardToPile(2, piles);
            case KeyEvent.VK_R -> player.throwCardToPile(3, piles);
            case KeyEvent.VK_S -> game.stress(player, game.getPlayer2());
            default -> {}
        }
    }

    /**
     * Processes actions for player 2 based on the pressed key.
     * If the action is valid, it is executed. Otherwise, an invalid move handling is triggered.
     * @param keyCode The key code of the pressed key.
     * @param player The player 2 object.
     * @param piles The array of pile objects.
     */
    private void processPlayer2Actions(int keyCode, Player player, Pile[] piles) {
        switch (keyCode) {
            case KeyEvent.VK_U -> player.throwCardToPile(0, piles);
            case KeyEvent.VK_I -> player.throwCardToPile(1, piles);
            case KeyEvent.VK_O -> player.throwCardToPile(2, piles);
            case KeyEvent.VK_P -> player.throwCardToPile(3, piles);
            case KeyEvent.VK_K -> game.stress(player, game.getPlayer1());
            default -> {}
        }
    }
   
    /**
     * Handles the control changes for pile target index for both players.
     * Allows changing the target pile based on the key pressed.
     * @param newKeyPress The key code of the pressed key.
     */
    private void handleChangePilesControls(int newKeyPress) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        switch (newKeyPress) {
            case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
            case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
            case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
            case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
            default -> {}
        }
    }

    /**
     * Handles key press events at the end of the game.
     * Checks for restart or quit
     * @param newKeyPress The key code of the pressed key.
     */
    private void handleEndGameControls(int newKeyPress) {
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



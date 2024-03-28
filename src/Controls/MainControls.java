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
        SoundUtility.menuSound();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newKeyPress = e.getKeyCode();
        pressedKeys.add(newKeyPress);
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Pile[] piles = game.getBothPiles();
        switch (game.getGameState()) {
            // start screen
            case START_SCREEN:
                if (newKeyPress == KeyEvent.VK_SPACE) {
                    GUI.changeToPanel("Game");
                    SoundUtility.bgmSound();
                    game.setGameState(GameState.OPEN_FIRST_CARDS);
                }
                break;
            case OPEN_FIRST_CARDS:
                if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_K)) {
                    game.openCardsFromDeck();
                    game.setGameState(GameState.PLAYING);
                }
                break;
            // standard gameplay conditions
            case PLAYING:
                switch (newKeyPress) {
                    case KeyEvent.VK_Q -> player1.throwCardToPile(0, piles);
                    case KeyEvent.VK_W -> player1.throwCardToPile(1, piles);
                    case KeyEvent.VK_E -> player1.throwCardToPile(2, piles);
                    case KeyEvent.VK_R -> player1.throwCardToPile(3, piles);
                    case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
                    case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
                    case KeyEvent.VK_S -> game.stress(player2);
                    case KeyEvent.VK_U -> player2.throwCardToPile(0, piles);
                    case KeyEvent.VK_I -> player2.throwCardToPile(1, piles);
                    case KeyEvent.VK_O -> player2.throwCardToPile(2, piles);
                    case KeyEvent.VK_P -> player2.throwCardToPile(3, piles);
                    case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
                    case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
                    case KeyEvent.VK_K -> game.stress(player1);
                }
                break;
                // freeze screen conditions
                case NO_VALID_MOVES:
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
                // play stress cut screen, lock
                break;
                case STRESS:
                // switch (newKeyPress) {
                    //     case KeyEvent.VK_A -> player1.setTargetPileIndex(0);
                    //     case KeyEvent.VK_D -> player1.setTargetPileIndex(1);
                    //     case KeyEvent.VK_J -> player2.setTargetPileIndex(0);
                    //     case KeyEvent.VK_L -> player2.setTargetPileIndex(1);
                    // }
                    break;
    
                    // end game conditions
                case STALEMATE, PLAYER1_WINS, PLAYER2_WINS:
                System.out.println("END CONDITION DETECTED");
                switch (newKeyPress) {
                    case KeyEvent.VK_PERIOD -> GUI.dispose();
                }
                break;
            }
            game.updateGameState();
            System.out.println(
                "Keys Pressed: " + pressedKeys.stream().map(i -> (char) i.intValue()).collect(Collectors.toList()));
                System.out.println(game);
            }
            
            @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}

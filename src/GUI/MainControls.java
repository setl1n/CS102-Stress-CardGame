package GUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Game.Game;
import Game.GameState;

public class MainControls extends KeyAdapter {
    private Game game;
    private MainGUI GUI;

    public MainControls(Game game, MainGUI GUI) {
        this.game = game;
        this.GUI = GUI;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        switch (GameState.STATE) {
            // start screen
            case START_SCREEN:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.openCardsToStart();
                    GUI.changeToPanel("Game");
                    GameState.STATE = GameState.PLAYING;
                }
                break;
            case OPEN_FIRST_CARDS:
                break;
            // standard gameplay conditions
            case PLAYING:
                break;
            // freeze screen conditions
            case STRESS, NOVALIDMOVES:
                // play stress cut screen, lock
                break;
            // end game conditions
            case STALEMATE, RED_WINS, BLU_WINS, EDGECASE:
                break;
        }
    }
}

package app;
import javax.swing.SwingUtilities;

import gui.MainGUI;
import game.Game;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            MainGUI gui = new MainGUI(game);
            gui.setVisible(true);
        });
    }
}

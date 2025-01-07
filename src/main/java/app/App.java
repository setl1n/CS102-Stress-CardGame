package app;
import javax.swing.SwingUtilities;

import game.Game;
import gui.MainGUI;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            MainGUI gui = new MainGUI(game);
            gui.setVisible(true);
        });
    }
}

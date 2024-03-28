import javax.swing.SwingUtilities;

import gui.MainGUI;
import game.Game;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            MainGUI GUI = new MainGUI(game);
            GUI.setVisible(true);
        });
    }
}

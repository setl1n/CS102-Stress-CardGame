import javax.swing.SwingUtilities;

import GUI.MainGUI;
import Game.Game;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            MainGUI GUI = new MainGUI(game);
            GUI.setVisible(true);
        });
    }
}

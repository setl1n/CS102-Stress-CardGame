import javax.swing.SwingUtilities;

import GUI.MainGUI;
import game.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            MainGUI GUI = new MainGUI(game);
            GUI.setVisible(true);
        });
    }
}

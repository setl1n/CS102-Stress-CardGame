
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(ExamplePlayer player1, ExamplePlayer player2) {
        super("Card Game");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /*
         * Player 1: SOUTH (BOTTOM) OF SCREEN
         */
        PlayerPanel player1Panel = new PlayerPanel(player1);
        add(player1Panel, BorderLayout.SOUTH);

        /*
         * Player 2: NORTH (TOP) OF SCREEN
         */
        PlayerPanel player2Panel = new PlayerPanel(player2);
        add(player2Panel, BorderLayout.NORTH);

        /*
         * Pile: CENTRE OF SCREEN
         */

        PilePanel pilePanel = new PilePanel();
        add(pilePanel, BorderLayout.CENTER);

        /*
         * Indicators
         */

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        final ExamplePlayer player1 = new ExamplePlayer("Player 1");
        final ExamplePlayer player2 = new ExamplePlayer("Player 2");
        SwingUtilities.invokeLater(() -> new GameFrame(player1, player2));
    }
}


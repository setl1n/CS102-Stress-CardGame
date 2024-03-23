package gui;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        super("Card Game");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        /*
         * Player 1: SOUTH (BOTTOM) OF SCREEN
         */
        PlayerPanel player1Panel = new PlayerPanel("Player 1");
        add(player1Panel, BorderLayout.SOUTH);

        /*
         * Player 2: NORTH (TOP) OF SCREEN
         */
        PlayerPanel player2Panel = new PlayerPanel("Player 2");
        add(player2Panel, BorderLayout.NORTH);

        /*
         * Pile: CENTRE OF SCREEN
         */

        PilePanel pilePanel = new PilePanel();
        add(pilePanel, BorderLayout.CENTER);            

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}


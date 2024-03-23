package GUI.Panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import GUI.Panels.GamePanelConponents.PilePanel;
import GUI.Panels.GamePanelConponents.PlayerPanel;
import Game.Game;

public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        super();
        this.game = game;
        setLayout(new BorderLayout());

        PlayerPanel player1Panel = new PlayerPanel(game.getPlayer1());
        add(player1Panel, BorderLayout.SOUTH);

        PlayerPanel player2Panel = new PlayerPanel(game.getPlayer2());
        add(player2Panel, BorderLayout.NORTH);

        PilePanel pilePanel = new PilePanel();
        add(pilePanel, BorderLayout.CENTER);
    }

}

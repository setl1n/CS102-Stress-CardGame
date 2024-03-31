package gui.panels;

import java.awt.*;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gui.panels.gamecontainer.pilecontainer.PileContainer;
import gui.panels.gamecontainer.playercontainer.PlayerPanel;

import game.Game;
import game.Player;

public class GamePanel extends JPanel {

    private ImageIcon bgImage;
    private static final int ORIGIN = 0;

    public GamePanel(Game game) {
        super();
        game.setGamePanel(this);

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        this.setOpaque(false);
        setLayout(new BorderLayout());
        renderBackground();

        PlayerPanel player1Panel = new PlayerPanel(player1);
        add(player1Panel, BorderLayout.SOUTH);

        PlayerPanel player2Panel = new PlayerPanel(player2);
        add(player2Panel, BorderLayout.NORTH);

        PileContainer pilePanel = new PileContainer(game.getPile(0), game.getPile(1),
                                                    player1, player2);
        add(pilePanel, BorderLayout.CENTER);
    }

    public void renderBackground() {
        URL imageUrl = getClass().getResource("/assets/bg.jpg");
        if (imageUrl != null) {
            bgImage = new ImageIcon(imageUrl);
        }
        this.repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage.getImage(), ORIGIN, ORIGIN, getWidth(), getHeight(), this);
        }
    }

}

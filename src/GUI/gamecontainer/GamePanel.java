package GUI.gamecontainer;

import java.awt.*;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.gamecontainer.pilecontainer.PileContainer;
import GUI.gamecontainer.playercontainer.PlayerPanel;
import Game.Game;

import Player.Player;

public class GamePanel extends JPanel {

    private ImageIcon bgImage;
    private Game game;

    private Player player1;
    private Player player2;

    public GamePanel(Game game) {

        super();
        this.game = game;
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();

        this.setOpaque(false);

        setLayout(new BorderLayout());
    
        URL imageUrl = getClass().getResource("/assets/bg.jpg");
        if (imageUrl != null) {
            bgImage = new ImageIcon(imageUrl);
        }
        this.repaint(); 

        PlayerPanel player1Panel = new PlayerPanel(player1);
        add(player1Panel, BorderLayout.SOUTH);

        PlayerPanel player2Panel = new PlayerPanel(player2);
        add(player2Panel, BorderLayout.NORTH);

        PileContainer pilePanel = new PileContainer(game.getPile(0), game.getPile(1),
                                                    player1, player2);
        add(pilePanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

}
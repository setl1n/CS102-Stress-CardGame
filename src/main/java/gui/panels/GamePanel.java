package gui.panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import game.Game;
import game.Player;
import gui.panels.gamecontainer.pilecontainer.PileContainer;
import gui.panels.gamecontainer.playercontainer.PlayerPanel;

public class GamePanel extends JPanel {

    private ImageIcon bgImage;
    private static final int ORIGIN = 0;

    public GamePanel(Game game) {
        super();
        game.setGamePanel(this);

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        this.setOpaque(false);

        /*
         * BorderLayout is used for its NORTH/SOUTH/EAST/WEST
         * layout, which allows us to more easily position elements
         * in the corenrs of the screen (or in the CENTER)
         */

        setLayout(new BorderLayout());
        renderBackground();

        /*
         * Adds Player 1 panel to the bottom left
         * of the screen (SOUTH)
         */

        PlayerPanel player1Panel = new PlayerPanel(player1);
        add(player1Panel, BorderLayout.SOUTH);

        /*
         * Adds Player 2 panel to the top right
         * of the screen (NORTH)
         */

        PlayerPanel player2Panel = new PlayerPanel(player2);
        add(player2Panel, BorderLayout.NORTH);

        /*
         * Piles in the centre of the screen
         */

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

    /*
     * Paint component is used here because the background is
     * not meant to be modified or changed throughout the 
     * running of the program
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage.getImage(), ORIGIN, ORIGIN, getWidth(), getHeight(), this);
        }
    }

}

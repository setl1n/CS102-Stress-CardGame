package GUI.Panels.GamePanelConponents;

import java.net.URL;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Player.Player;

public class PlayerPanel extends JPanel {

    /*
     * this is the example of linking the panel
     * to the instance that its referencing in a
     * bidirectional manner
     */

    private Player player;
    private Image digit1Image;
    private Image digit2Image;

    public PlayerPanel(Player player) {

        player.setPlayerPanel(this);
        this.setOpaque(false);

        setPreferredSize(new Dimension(1080, 240));

        if ("Player 1".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

            for (int i = 0; i < 4; i++) {
                add(new CardPanel(player.getHand().getCardAtIndex(i)));
            }
            add(new DeckPanel(player.getDeck()));
            JPanel numPanel = createNumPanel(player);
            add(numPanel);

        } else if ("Player 2".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

            JPanel numPanel = createNumPanel(player);
            add(numPanel);
            add(new DeckPanel(player.getDeck()));
            for (int i = 0; i < 4; i++) {
                add(new CardPanel(player.getHand().getCardAtIndex(i)));
            }
        }
    }

    private JPanel createNumPanel(Player player) {

        JPanel numPanel = new JPanel();
        numPanel.setOpaque(false);
        numPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0 ,0));
        numPanel.setPreferredSize(new Dimension(120, 50));

        int num = 20;

        if (player != null) {
            num = player.getDeck().size();
            // create digit 1
            System.out.println(player.getName());
        }
        
        String path = "/assets/" + (num / 10) + ".png";
        System.out.println(path);
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/0.png");
        }

        digit1Image = new ImageIcon(imgUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(digit1Image), JLabel.CENTER);
        numPanel.add(labelComponent);

        // create digit 2
        path = "/assets/" + (num % 10) + ".png";
        imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/0.png");
        }

        digit2Image = new ImageIcon(imgUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        labelComponent = new JLabel(new ImageIcon(digit2Image), JLabel.CENTER);
        numPanel.add(labelComponent);

        return numPanel;
    }
}


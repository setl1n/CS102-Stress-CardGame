package GUI.gamecontainer.playercontainer;

import java.util.*;

import java.net.URL;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Player.Player;
import Collections.DeckComponents.Card;

public class PlayerPanel extends JPanel {

    /*
     * this is the example of linking the panel
     * to the instance that its referencing in a
     * bidirectional manner
     */

    private Player player;

    private ArrayList<CardPanel> cardPanels = new ArrayList<>();
    private ArrayList<JLabel> numLabels = new ArrayList<>();
    private DeckPanel deckPanel;
    
    private Image digit1Image;
    private Image digit2Image;

    public PlayerPanel(Player player) {

        this.player = player;
        player.setPlayerPanel(this);
        this.setOpaque(false);
        setPreferredSize(new Dimension(1080, 240));

        if ("Player 1".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

            for (int i = 0; i < 4; i++) {
                CardPanel temp = new CardPanel(player.getHand().getCardAtIndex(i));
                cardPanels.add(temp);
                add(temp);
            }

            this.deckPanel = new DeckPanel(player.getDeck());
            add(deckPanel);
            JPanel numPanel = createNumPanel(player);
            add(numPanel);

        } else if ("Player 2".equals(player.getName())) {

            setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

            JPanel numPanel = createNumPanel(player);
            add(numPanel);
            this.deckPanel = new DeckPanel(player.getDeck());
            add(deckPanel);

            for (int i = 0; i < 4; i++) {
                CardPanel temp = new CardPanel(player.getHand().getCardAtIndex(i));
                cardPanels.add(temp);
                add(temp);
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
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/0.png");
        }

        /*
         * should condense imageicon to jlabel in one function
         * or filepath to jlabel instead, with error handling
         */

        digit1Image = new ImageIcon(imgUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(digit1Image), JLabel.CENTER);
        numLabels.add(labelComponent);
        numPanel.add(labelComponent);

        // create digit 2
        path = "/assets/" + (num % 10) + ".png";
        imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/0.png");
        }

        digit2Image = new ImageIcon(imgUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        labelComponent = new JLabel(new ImageIcon(digit2Image), JLabel.CENTER);
        numLabels.add(labelComponent);
        numPanel.add(labelComponent);

        return numPanel;
    }

    public void updateCardPanel(int idx, Card card) {
        CardPanel cardPanel = cardPanels.get(idx);
        cardPanel.update(card);
    }

    public void updateDeckPanel() {
        deckPanel.updateDeck();
    }

    public void updateNumPanel() {
        int num = player.getDeck().size();
        
        // first digit
        System.out.println("num is " + num);
        String path = "/assets/" + (num / 10) + ".png";
        URL imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/0.png");
        }
        JLabel numLabel = numLabels.get(0);
        Image image = new ImageIcon(imgUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        numLabel.setIcon(new ImageIcon(image));
        numLabel.repaint();

        // second digit
        path = "/assets/" + (num % 10) + ".png";
        imgUrl = getClass().getResource(path);
        if (imgUrl == null) {
            imgUrl = getClass().getResource("/assets/0.png");
        }
        numLabel = numLabels.get(1);
        image = new ImageIcon(imgUrl).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        numLabel.setIcon(new ImageIcon(image));
        numLabel.repaint();
        
        System.out.println("changed");
    }


}


package GUI.gamecontainer.playercontainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Collections.DeckComponents.Card;

public class CardPanel extends JPanel {

    static final int width = 138;
    static final int height = 186;

    private JLabel jLabel;

    public CardPanel(Card card) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());
        this.setOpaque(false);

        Image image = card.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        jLabel = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(jLabel, BorderLayout.CENTER);
    }

    public void update(Card card) {
        Image image = card.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        jLabel.setIcon(new ImageIcon(image));
        jLabel.repaint();
    }
}


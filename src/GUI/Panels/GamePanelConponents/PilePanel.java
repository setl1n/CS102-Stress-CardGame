package GUI.Panels.GamePanelConponents;

import java.awt.*;
import javax.swing.*;

import Collections.Pile;
import Collections.DeckComponents.Card;

public class PilePanel extends JPanel {

    static final int width = 138;
    static final int height = 186;

    public PilePanel(Pile pile) {

        pile.setPilePanel(this);
        this.setOpaque(false);

        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout());

        Card topCard = pile.peekTopCard();
        Image image = topCard.getCardImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel labelComponent = new JLabel(new ImageIcon(image), JLabel.CENTER);
        add(labelComponent, BorderLayout.CENTER);

    }
}

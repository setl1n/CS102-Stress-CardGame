package TESTING.gui;

import java.util.ArrayList;
import java.util.List;

import gui.PlayerPanel;

public class ExamplePlayer {
    private String name;
    private List<Card> cards = new ArrayList<>();
    private PlayerPanel playerPanel;

    public ExamplePlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public void addCard(Card card) {
        cards.add(card);
        if (playerPanel != null) {
            playerPanel.repaint();
        }
    }

}

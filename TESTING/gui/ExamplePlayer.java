import java.util.ArrayList;
import java.util.List;

public class ExamplePlayer {
    private String name;
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

    public void addCard() {
        //cards.add(card);
        if (playerPanel != null) {
            playerPanel.repaint();
        }
    }

}

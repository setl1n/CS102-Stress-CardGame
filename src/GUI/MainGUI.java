package GUI;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.Panels.GamePanel;
import GUI.Panels.IntroPanel;
import Game.Game;
import Game.GameState;

public class MainGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public MainGUI(Game game) {
        setTitle("Stress! The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Center the window

        MainControls controls = new MainControls(game, this);
        addKeyListener(controls); // Add PlayerControls as a KeyListener

        setFocusable(true);
        requestFocusInWindow();

        mainPanel.add(new IntroPanel("/assets/intro.gif"), "GIFIntro");
        mainPanel.add(new IntroPanel("/assets/intro2.png"), "PNGIntro");
        mainPanel.add(new GamePanel(game), "Game");

        setContentPane(mainPanel);
        changeGIFtoPNGAftAnimation();
    }

    private void changeGIFtoPNGAftAnimation() {
        Timer timer = new Timer(2500, e -> {
            if (GameState.STATE == GameState.START_SCREEN)
                cardLayout.show(mainPanel, "PNGIntro");
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void changeToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public CardLayout getCardLayout() {
        return this.cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}

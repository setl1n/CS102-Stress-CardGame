package gui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;
import game.MainControls;
import gui.panels.GamePanel;
import gui.panels.IntroPanel;

public class MainGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainGUI(Game game) {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);

        configureFrameSettings();
        addKeyListenerToFrame(game);
        initialiseAndAddPanels(game);
    }

    /**
     * Configure for MainGUI Instance
     */
    private void configureFrameSettings() {
        setTitle("Stress! The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Center the window
        setFocusable(true);
        requestFocusInWindow();
        setContentPane(mainPanel);
    }

    /**
     * Attach MainControls KeyListener to MainGUI
     */
    private void addKeyListenerToFrame(Game game) {
        MainControls controls = new MainControls(game, this);
        addKeyListener(controls); // Add PlayerControls as a KeyListener
    }

    /**
     * Initialises and adds all necessary panels
     */
    private void initialiseAndAddPanels(Game game) {
        mainPanel.add(new IntroPanel("/assets/intro"), "Intro");
        mainPanel.add(new GamePanel(game), "Game");
    }

    /**
     * Select which panel is being shown
     */
    public void changeToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    /**
     * Changes panels to be linked to new game instance when game restarts
     */
    public void restart(Game game) {
        // Remove the old panels
        mainPanel.removeAll();

        // Add the new panels
        initialiseAndAddPanels(game);
        changeToPanel("Game");

        // Redraw the GUI
        revalidate();
        repaint();
    }
}

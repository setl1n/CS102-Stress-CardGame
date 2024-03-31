package gui;

import java.awt.CardLayout;

import javax.swing.*;

import gui.panels.*;
import game.*;

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

        mainPanel.add(new IntroPanel("/assets/intro"), "Intro");
        mainPanel.add(new GamePanel(game), "Game");

        setContentPane(mainPanel);
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
    public void restart(Game game) {
        // Remove the old panels
        mainPanel.removeAll();
    
        // Add the new panels
        mainPanel.add(new IntroPanel("/assets/intro"), "Intro");
        mainPanel.add(new GamePanel(game), "Game");
    
        // Show the intro panel
        changeToPanel("Intro");
    
        // Request focus
        setFocusable(true);
        requestFocusInWindow();
    
        // Redraw the GUI
        revalidate();
        repaint();
    }

}

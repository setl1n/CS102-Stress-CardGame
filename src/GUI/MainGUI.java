package gui;

import java.awt.CardLayout;

import javax.swing.*;

import gui.panels.*;
import game.*;

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

    private void configureFrameSettings() {
        setTitle("Stress! The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Center the window
        setFocusable(true);
        requestFocusInWindow();
        setContentPane(mainPanel);
    }

    private void addKeyListenerToFrame(Game game) {
        MainControls controls = new MainControls(game, this);
        addKeyListener(controls); // Add PlayerControls as a KeyListener
    }

    private void initialiseAndAddPanels(Game game) {
        mainPanel.add(new IntroPanel("/assets/intro"), "Intro");
        mainPanel.add(new GamePanel(game), "Game");
    }

    public void changeToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

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

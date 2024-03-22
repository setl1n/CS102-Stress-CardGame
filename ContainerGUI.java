import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * okay hear me out
 * 
 * now that i have the container, im thinking of a good way to
 * draw the cards based on the context of it being called
 * 
 * this is currently a basic interpretation of how to draw the containers
 * with placeholder images (such as blank.png for the cards), which is
 * fixed and therefore impossible to change
 * 
 * to-do:
 * convert the values of each panel/margin to set dimensions, especially for
 * parent containers (calculate them? i guess) and set them as final
 * variables so we dont get shot by lay foo
 * 
 * refactor this messy code, maybe make them class specific methods instead,
 * i think it will make it might easier to read and debug
 */

public class ContainerGUI {

    private static List<JPanel> animationPanels = new ArrayList<>();
    private static final Map<String, JPanel> panelRegistry = new HashMap<>();

    /*
     * i havent implemented this but this gives us the ability to reference
     * panels by name so we can access them regardless of hierarchy, i have
     * more code in my chatGPT conversations to extend this functionality
     */

    private static void registerPanel(String name, JPanel panel) {
        panelRegistry.put(name, panel);
    }
    
    public static JPanel getPanelByName(String name) {
        return panelRegistry.get(name);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContainerGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*
         * the gamepanel uses BorderLayout, which splits the screen into
         * North South East and West so you can flush elements to different
         * parts of the screen (refer to documentation for more)
         */

        frame.setLayout(new BorderLayout());

        // Create the Player 1 panel with cards and deck, aligned at the bottom
        JPanel player1Panel = createPlayerPanel("Player 1");
        player1Panel.setPreferredSize(new Dimension(1080, 220));

        // Create the Player 2 panel with cards and deck, aligned to the right
        JPanel player2Panel = createPlayerPanel("Player 2");
        player2Panel.setPreferredSize(new Dimension(1080, 220));
        player2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));

        // Create the central pile container with pile1 and pile2, center-aligned
        JPanel pilePanel = createPilePanel();
        pilePanel.setPreferredSize(new Dimension(300, 186)); // Set preferred size for visual representation

        frame.add(player1Panel, BorderLayout.SOUTH);
        frame.add(player2Panel, BorderLayout.NORTH);
        frame.add(pilePanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'g' || e.getKeyChar() == 'G') {

                    /*
                     * added a keypress listener to create an animation relative
                     * to a specified panel (for better control)
                     */


                    createAnimation(player1Panel, frame);
                    player1Panel.revalidate();
                    player1Panel.repaint();
                }
            }
        });
    }

    private static JPanel createPlayerPanel(String playerName) {
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());

        /*
         * this is where flow layout comes in, which allows us to
         * add elements in a row horizontally with a set margin
         * this is good for hand and deck which is along a line
         */

        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
    
        // Create the "num" container
        JPanel numPanel = createNumPanel();

        /*
         * compare the .add to the draw image function offered
         * by the original implementation. in general, we should
         * avoid using paintComponent directly at all
         */
    
        if ("Player 1".equals(playerName)) {
            // For "Player 1", add cards first, then the deck, and finally the "num" container
            for (int i = 0; i < 4; i++) {
                playerPanel.add(createCard());
            }
            playerPanel.add(createDeck());
            // Adding the "num" container after the deck
            playerPanel.add(numPanel);
        } else if ("Player 2".equals(playerName)) {
            // For "Player 2", first add the "num" container, then the deck, and finally the cards
            // Adding the "num" container before the deck
            playerPanel.add(numPanel);
            playerPanel.add(createDeck());
            for (int i = 0; i < 4; i++) {
                playerPanel.add(createCard());
            }
            // Adjust the FlowLayout to align right for "Player 2"
            playerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        }
    
        borderLayoutPanel.add(playerPanel, BorderLayout.SOUTH);
        return borderLayoutPanel;
    }
    
    private static JPanel createNumPanel() {
        JPanel numPanel = new JPanel();
        numPanel.setPreferredSize(new Dimension(100, 100)); // Set the size of the "num" container
        numPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border for visualization
        numPanel.add(new JLabel("NUM")); // Label for identification
        return numPanel;
    }
    
    private static JPanel createPilePanel() {
        // Use GridBagLayout for more control over component alignment
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        // Create the panel that directly contains "Pile 1" and "Pile 2"
        JPanel pilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        pilePanel.add(createPile("Pile 1"));
        pilePanel.add(createPile("Pile 2"));

        /*
         * i dont know how this gbc thing works
         * but i need it to keep the thing centred
         */
    
        gbc.anchor = GridBagConstraints.CENTER; // Center the component
    
        // Add the pilePanel to the centerPanel using GridBagConstraints
        centerPanel.add(pilePanel, gbc);
    
        return centerPanel;
    }
    

    private static JPanel createPile(String pileName) {
        JPanel pile = new JPanel();
        pile.setPreferredSize(new Dimension(138, 186)); // Size similar to cards
        pile.setBorder(BorderFactory.createLineBorder(Color.BLUE)); // Different color for distinction
        pile.add(new JLabel(pileName));
        return pile;
    }

    private static JPanel createCard() {
        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(138, 186));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.add(new JLabel("Card"));
        return cardPanel;
    }

    private static JPanel createDeck() {

        JPanel deckPanel = new JPanel();
        deckPanel.setPreferredSize(new Dimension(138, 186));
        deckPanel.setLayout(new BorderLayout()); // Use BorderLayout for better control of the label


        /*
         * this provides the ability to resize element
         * we could make use of the instance variable image in card
         * if you want to make this more seamless
         * 
         * we could modularise this to draw images in components?
         * thoughts
         */

        ImageIcon deckIcon = new ImageIcon("./images/empty.png");
        Image image = deckIcon.getImage().getScaledInstance(138, 186, Image.SCALE_SMOOTH);
        deckIcon = new ImageIcon(image);
        
        JLabel deckLabel = new JLabel(deckIcon);
        deckLabel.setBorder(BorderFactory.createEmptyBorder()); // Remove any default borders/margins
        // Add the label to the center of the panel to avoid any layout manager padding issues
        deckPanel.add(deckLabel, BorderLayout.CENTER);
    
        return deckPanel;
    }

    private static void createAnimation(JPanel basePanel, JFrame frame) {
        JPanel animationPanel = new JPanel(new BorderLayout());
        animationPanel.setOpaque(false);
        animationPanel.setSize(100, 100);

        // Add GIF to the panel
        ImageIcon gifIcon = new ImageIcon("./images/redstress.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        animationPanel.add(gifLabel, BorderLayout.CENTER);

        // Calculate and set the position for the animation panel
        Point baseLocation = SwingUtilities.convertPoint(basePanel, 0, 0, frame.getLayeredPane());
        animationPanel.setLocation(baseLocation.x + (basePanel.getWidth() - animationPanel.getWidth()) / 2,
                                   baseLocation.y + (basePanel.getHeight() - animationPanel.getHeight()) / 2);

        // Add the animation panel to the frame's layered pane
        frame.getLayeredPane().add(animationPanel, JLayeredPane.POPUP_LAYER);
        frame.getLayeredPane().moveToFront(animationPanel);
        animationPanel.setVisible(true);

        // Keep track of this panel for potential removal later
        animationPanels.add(animationPanel);

        // Setup removal for this specific animation
        Timer timer = new Timer(3000, e -> removeAnimation(frame, animationPanel));
        timer.setRepeats(false);
        timer.start();
    }

    // Method to remove a specific animation panel
    private static void removeAnimation(JFrame frame, JPanel animationPanel) {
        frame.getLayeredPane().remove(animationPanel);
        frame.getLayeredPane().revalidate();
        frame.getLayeredPane().repaint();
        animationPanels.remove(animationPanel); // Remove the reference from the list
    }
    
}

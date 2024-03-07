package src;

import javax.swing.*;
import src.Cards.Card;
import src.Cards.Hand;
import src.Cards.Pile;
import src.Game.Player;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class CardGameGUI extends JFrame {
    // 100
    private static final int CARD_WIDTH = 138;
    // 150
    private static final int CARD_HEIGHT = 186;

    private Player player1 = new Player(new Hand(), "Player1");
    private Player player2 = new Player(new Hand(), "Player2");

    private Pile pile1 = new Pile();
    private Pile pile2 = new Pile();

    private Image bgImage;

    public CardGameGUI() {

        // create the background
        URL bgImageUrl = getClass().getResource("/assets/bg.jpg");
        if (bgImageUrl == null) {
            throw new RuntimeException("Background image not found");
        }
        bgImage = new ImageIcon(bgImageUrl).getImage();

        initializeHands(player1, player2);
        initializePiles(); // Initialize piles with example cards for demonstration

        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout(new BorderLayout());

        JPanel basePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        basePanel.setLayout(new BorderLayout()); // Use BorderLayout

        JPanel gamePanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                // Draw player 1's cards at the bottom left
                for (int i = 0; i < player1.getHand().size(); i++) {
                    Card card = player1.getHand().getCard(i);
                    drawCard(g, card, 20 + (CARD_WIDTH + 20) * i, getHeight() - CARD_HEIGHT - 20);
                }
                // Draw deck (TO DO)

                // Draw player 2's cards at the top right
                for (int i = 0; i < player2.getHand().size(); i++) {
                    Card card = player2.getHand().getCard(i);
                    drawCard(g, card, getWidth() - (CARD_WIDTH + 20) * (4 - i) - 20, 20);
                }
                // Draw deck (TO DO)

                int pile1X = getWidth() / 2 - CARD_WIDTH - 10;
                int pile1Y = getHeight() / 2 - CARD_HEIGHT / 2;
                int pile2X = getWidth() / 2 + 10;
                int pile2Y = getHeight() / 2 - CARD_HEIGHT / 2;

                Card topCardPile1 = pile1.getTopCard();
                drawCard(g, topCardPile1, pile1X, pile1Y);

                Card topCardPile2 = pile2.getTopCard();
                drawCard(g, topCardPile2, pile2X, pile2Y);

                if (player1.isPile1Active()) {
                    drawIndicator(g, "redtri", pile1X + 38, pile1Y + CARD_HEIGHT + 10);  // Below pile1
                } else {
                    drawIndicator(g, "redtri", pile2X + 40, pile2Y + CARD_HEIGHT + 10);  // Below pile2
                }
            
                if (player2.isPile1Active()) {
                    drawIndicator(g, "bluetri", pile1X + 38, pile1Y - 60);
                } else {
                    drawIndicator(g, "bluetri", pile2X + 38, pile2Y - 60);
                }
            }

            private void drawCard(Graphics g, Card card, int x, int y) {
                try {

                    URL cardImageUrl = card.getCardImage();
                    ImageIcon icon = new ImageIcon(cardImageUrl);
                    Image image = icon.getImage();
                    g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, this);

                } catch (NullPointerException e) {
                    
                    String imagePath = "/assets/" + "empty" + ".png";
                    URL imgUrl = getClass().getResource(imagePath);
                    ImageIcon icon = new ImageIcon(imgUrl);
                    Image image = icon.getImage();
                    g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, this);
                }
            }

            private void drawIndicator(Graphics g, String indicatorName, int x, int y) {
                URL indicatorUrl = getClass().getResource("/assets/" + indicatorName + ".png");
                if (indicatorUrl != null) {
                    ImageIcon icon = new ImageIcon(indicatorUrl);
                    Image image = icon.getImage();
                    g.drawImage(image, x, y, this);
                } else {
                    throw new RuntimeException("Indicator image not found: /assets/" + indicatorName + ".png");
                }
            }
        };
        gamePanel.setOpaque(false); // Make gamePanel transparent
    
        basePanel.add(gamePanel, BorderLayout.CENTER); // Add gamePanel to basePanel
        setContentPane(basePanel);

        // setContentPane(gamePanel);
        // setSize(900, 600);
        setLocationRelativeTo(null); // Center the window

        // key listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch(Character.toLowerCase((e.getKeyChar()))) {

                    // PLAYER 1 CARD THROWS
                    case 'q':
                        handtoPile(player1, 0);
                        break;

                    case 'w':
                        handtoPile(player1, 1);
                        break;

                    case 'e':
                        handtoPile(player1, 2);
                        break;

                    case 'r':
                        handtoPile(player1, 3);
                        break;

                    // PLAYER 1 DECK SWITCH
                    case 's':
                        player1.toggleActivePile();
                        repaint();
                        break;

                    // PLAYER 2 CARD THROWS
                    case 'u':
                        handtoPile(player2, 0);
                        break;

                    case 'i':
                        handtoPile(player2, 1);
                        break;

                    case 'o':
                        handtoPile(player2, 2);
                        break;

                    case 'p':
                        handtoPile(player2, 3);
                        break;
                        
                    // PLAYER 2 DECK SWITCH
                    case 'k':
                        player2.toggleActivePile();
                        repaint();
                        break;
                        
                }

            }

        });
    }

    private void initializeHands(Player player1, Player player2) {
        // Initialize the hands with
        Hand player1Hand = player1.getHand();
        player1Hand.addCard(new Card("D", "4"));
        player1Hand.addCard(new Card("S", "5"));
        player1Hand.addCard(new Card("H", "2"));
        player1Hand.addCard(new Card("C", "3"));

        Hand player2Hand = player2.getHand();
        player2Hand.addCard(new Card("D", "10"));
        player2Hand.addCard(new Card("S", "4"));
        player2Hand.addCard(new Card("H", "Q"));
        player2Hand.addCard(new Card("C", "K"));
    }
    
    private void initializePiles() {
        // Initialize piles with example cards for demonstration
        pile1.addCard(new Card("S", "A"));
        pile2.addCard(new Card("H", "A"));
    }

    private void handtoPile(Player player, int index) {
        // infer deck from player
        Card deckTopCard = null;

        // infer pile from player
        Pile pile;
        if (player.isPile1Active()) {
            pile = pile1; 
        } else {
            pile = pile2;
        }

        // infer hand from player
        Hand hand = player.getHand();

        if (!hand.isEmpty()) {
            Card cardToMove = hand.getCard(index);
            if (cardToMove != null) {
                hand.setCard(deckTopCard, index); // Remove card from player's hand
                pile.addCard(cardToMove); // Add card to the pile
                repaint(); // Ask the GUI to repaint to reflect changes
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CardGameGUI game = new CardGameGUI();
            game.setVisible(true);
            
        });
    }
}
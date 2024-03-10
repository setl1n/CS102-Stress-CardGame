/*
 * @OWNER zane
 */

package src.GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import src.Cards.Card;
import src.Cards.Hand;
import src.Cards.Pile;
import src.Game.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

public class GameController {

    private SoundController soundController = new SoundController();

    private static final int CARD_WIDTH = 138;
    private static final int CARD_HEIGHT = 186;

    private Player player1;
    private Player player2;
    private Pile pile1;
    private Pile pile2;
    private Image bgImage;

    public GameController() {
        loadBackgroundImage();
        initializeGame();
    }

    private void loadBackgroundImage() {
        URL bgImageUrl = getClass().getResource("/assets/bg.jpg");
        if (bgImageUrl == null) {
            throw new RuntimeException("Background image not found");
        }
        bgImage = new ImageIcon(bgImageUrl).getImage();
    }

    private void initializeGame() {
        player1 = new Player(new Hand(), "Player1");
        player2 = new Player(new Hand(), "Player2");
        pile1 = new Pile();
        pile2 = new Pile();

        initializeHands();
        initializePiles();
    }

    private void initializeHands() {
        // Initialize player1's hand
        player1.getHand().addCard(new Card("D", "4"));
        player1.getHand().addCard(new Card("S", "5"));
        player1.getHand().addCard(new Card("H", "2"));
        player1.getHand().addCard(new Card("C", "3"));

        // Initialize player2's hand
        player2.getHand().addCard(new Card("D", "10"));
        player2.getHand().addCard(new Card("S", "4"));
        player2.getHand().addCard(new Card("H", "Q"));
        player2.getHand().addCard(new Card("C", "K"));
    }

    private void initializePiles() {
        pile1.addCard(new Card("S", "A"));
        pile2.addCard(new Card("H", "A"));
    }

    public void keyPressed(KeyEvent e) {
        switch (Character.toLowerCase(e.getKeyChar())) {
            case 'q':
                handToPile(player1, 0);
                break;
            case 'w':
                handToPile(player1, 1);
                break;
            case 'e':
                handToPile(player1, 2);
                break;
            case 'r':
                handToPile(player1, 3);
                break;
            case 's':
                player1.toggleActivePile();
                break;
            case 'u':
                handToPile(player2, 0);
                break;
            case 'i':
                handToPile(player2, 1);
                break;
            case 'o':
                handToPile(player2, 2);
                break;
            case 'p':
                handToPile(player2, 3);
                break;
            case 'k':
                player2.toggleActivePile();
                break;
        }
    }

    private void handToPile(Player player, int cardIndex) {
        Hand hand = player.getHand();
        Pile targetPile = player.isPile1Active() ? pile1 : pile2;
    
        if (!hand.isEmpty() && hand.getCard(cardIndex) != null) {
            Card cardToMove = hand.getCard(cardIndex);
            hand.setCard(null, cardIndex); // Assuming removeCard does what setCard(deckTopCard, index) did
            targetPile.addCard(cardToMove);
            soundController.cardSound(); // Play sound after moving the card
        }
    }

    public void draw(Graphics g, int width, int height) {
        drawBackground(g, width, height);
        drawHands(g, width, height);
        drawPiles(g, width, height);
        drawIndicators(g, width, height);
    }

    private void drawBackground(Graphics g, int width, int height) {
        g.drawImage(bgImage, 0, 0, width, height, null);
    }

    private void drawHands(Graphics g, int width, int height) {
        for (int i = 0; i < player1.getHand().size(); i++) {
            Card card = player1.getHand().getCard(i);
            drawCard(g, card, 20 + (CARD_WIDTH + 20) * i, height - CARD_HEIGHT - 20);
        }
        for (int i = 0; i < player2.getHand().size(); i++) {
            Card card = player2.getHand().getCard(i);
            drawCard(g, card, width - (CARD_WIDTH + 20) * (4 - i) - 20, 20);
        }
    }

    private void drawPiles(Graphics g, int width, int height) {
        int pile1X = width / 2 - CARD_WIDTH - 10;
        int pile1Y = height / 2 - CARD_HEIGHT / 2;
        int pile2X = width / 2 + 10;
        int pile2Y = height / 2 - CARD_HEIGHT / 2;

        Card topCardPile1 = pile1.getTopCard();
        Card topCardPile2 = pile2.getTopCard();
        drawCard(g, topCardPile1, pile1X, pile1Y);
        drawCard(g, topCardPile2, pile2X, pile2Y);
    }

    private void drawIndicators(Graphics g, int width, int height) {
        int pile1X = width / 2 - CARD_WIDTH - 10;
        int pile1Y = height / 2 - CARD_HEIGHT / 2;
        int pile2X = width / 2 + 10;
        int pile2Y = height / 2 - CARD_HEIGHT / 2;

        if (player1.isPile1Active()) {
            drawIndicator(g, "redtri", pile1X + 38, pile1Y + CARD_HEIGHT + 10);
        } else {
            drawIndicator(g, "redtri", pile2X + 40, pile2Y + CARD_HEIGHT + 10);
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
            Image image = new ImageIcon(cardImageUrl).getImage();
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
        } catch (NullPointerException e) {
            // Draw a placeholder or default image
            String imagePath = "/assets/empty.png";
            URL imgUrl = getClass().getResource(imagePath);
            Image image = new ImageIcon(imgUrl).getImage();
            g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    private void drawIndicator(Graphics g, String indicatorName, int x, int y) {
        URL indicatorUrl = getClass().getResource("/assets/" + indicatorName + ".png");
        if (indicatorUrl != null) {
            Image image = new ImageIcon(indicatorUrl).getImage();
            g.drawImage(image, x, y, null);
        } else {
            throw new RuntimeException("Indicator image not found: /assets/" + indicatorName + ".png");
        }
    }
}

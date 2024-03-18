package GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

import Collections.DeckComponents.*;
import Collections.*;
import Player.*;
import Game.GameLogicUtils;

public class GameController {

    private static final int CARD_WIDTH = 138;
    private static final int CARD_HEIGHT = 186;

    private Player player1;
    private Player player2;
    private Pile[] piles;
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

    // Just initialise Game() from here, currently copying the code from Game() constructor
    private void initializeGame() {
        // sets up the game
        Deck startingDeck = new Deck(false);
        // commented out shuffling for easy debug, utyalls
        // startingDeck.shuffle();
        Deck halfDeck = startingDeck.splitAndReturnHalf();
        player1 = new Player(startingDeck);
        player2 = new Player(halfDeck);
        piles = new Pile[2];
        for (int i = 0; i < 2; i++) {
            piles[i] = new Pile();
        }

        openCardsToStart();
        player1.drawFourCards();
        player2.drawFourCards();
    }

    public void openCardsToStart() {
        if (!player1.isEmptyDeck() && !player2.isEmptyDeck()) {
            player1.openCardToPile(piles[0]);
            player2.openCardToPile(piles[1]);
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (Character.toLowerCase(e.getKeyChar())) {
            case 'q':
                player1.throwCardToPile(0, piles[player1.getTargetPile()]);
                break;
            case 'w':
                player1.throwCardToPile(1, piles[player1.getTargetPile()]);
                break;
            case 'e':
                player1.throwCardToPile(2, piles[player1.getTargetPile()]);
                break;
            case 'r':
                player1.throwCardToPile(3, piles[player1.getTargetPile()]);
                break;
            case 's':
                player1.selectTargetPile('l');
                break;
            case 'd':
                player1.selectTargetPile('r');
                break;

            case 'u':
                player2.throwCardToPile(0, piles[player2.getTargetPile()]);
                break;
            case 'i':
                player2.throwCardToPile(1, piles[player2.getTargetPile()]);
                break;
            case 'o':
                player2.throwCardToPile(2, piles[player2.getTargetPile()]);
                break;
            case 'p':
                player2.throwCardToPile(3, piles[player2.getTargetPile()]);
                break;
            case 'k':
                player2.selectTargetPile('l');
                break;
            case 'l':
                player2.selectTargetPile('r');
                break;
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
        for (int i = 0; i < 4; i++) {
            Card card = player1.getHand().getCardAtIndex(i);
            drawCard(g, card, 20 + (CARD_WIDTH + 20) * i, height - CARD_HEIGHT - 20);
        }
        for (int i = 0; i < 4; i++) {
            Card card = player2.getHand().getCardAtIndex(i);
            drawCard(g, card, width - (CARD_WIDTH + 20) * (4 - i) - 20, 20);
        }
    }

    private void drawPiles(Graphics g, int width, int height) {
        int pile1X = width / 2 - CARD_WIDTH - 10;
        int pile1Y = height / 2 - CARD_HEIGHT / 2;
        int pile2X = width / 2 + 10;
        int pile2Y = height / 2 - CARD_HEIGHT / 2;

        Card topCardPile1 = piles[0].peekTopCard();
        Card topCardPile2 = piles[1].peekTopCard();
        drawCard(g, topCardPile1, pile1X, pile1Y);
        drawCard(g, topCardPile2, pile2X, pile2Y);
    }

    private void drawIndicators(Graphics g, int width, int height) {
        int pile1X = width / 2 - CARD_WIDTH - 10;
        int pile1Y = height / 2 - CARD_HEIGHT / 2;
        int pile2X = width / 2 + 10;
        int pile2Y = height / 2 - CARD_HEIGHT / 2;

        if (player1.getTargetPile() == 0) {
            drawIndicator(g, "redtri", pile1X + 38, pile1Y + CARD_HEIGHT + 10);
        } else {
            drawIndicator(g, "redtri", pile2X + 40, pile2Y + CARD_HEIGHT + 10);
        }
        if (player2.getTargetPile() == 0) {
            drawIndicator(g, "bluetri", pile1X + 38, pile1Y - 60);
        } else {
            drawIndicator(g, "bluetri", pile2X + 38, pile2Y - 60);
        }
    }

    private void drawCard(Graphics g, Card card, int x, int y) {
        g.drawImage(card.getCardImage(), x, y, CARD_WIDTH, CARD_HEIGHT, null);
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

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
import Game.Game;

public class GameController {

    private static final int CARD_WIDTH = 138;
    private static final int CARD_HEIGHT = 186;

    private Game game;
    private Image bgImage;

    public GameController(Game game) {
        this.game = game;
        loadBackgroundImage();
    }

    public Game getGame() {
        return game;
    }

    private void loadBackgroundImage() {
        URL bgImageUrl = getClass().getResource("/assets/bg.jpg");
        if (bgImageUrl == null) {
            throw new RuntimeException("Background image not found");
        }
        bgImage = new ImageIcon(bgImageUrl).getImage();
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
            Card card = game.getPlayer1().getHand().getCardAtIndex(i);
            drawCard(g, card, 20 + (CARD_WIDTH + 20) * i, height - CARD_HEIGHT - 20);
        }
        for (int i = 0; i < 4; i++) {
            Card card = game.getPlayer2().getHand().getCardAtIndex(i);
            drawCard(g, card, width - (CARD_WIDTH + 20) * (4 - i) - 20, 20);
        }
    }

    private void drawPiles(Graphics g, int width, int height) {
        int pile1X = width / 2 - CARD_WIDTH - 10;
        int pile1Y = height / 2 - CARD_HEIGHT / 2;
        int pile2X = width / 2 + 10;
        int pile2Y = height / 2 - CARD_HEIGHT / 2;

        Card topCardPile1 = game.getPile(0).peekTopCard();
        Card topCardPile2 = game.getPile(1).peekTopCard();
        drawCard(g, topCardPile1, pile1X, pile1Y);
        drawCard(g, topCardPile2, pile2X, pile2Y);
    }

    private void drawIndicators(Graphics g, int width, int height) {
        int pile1X = width / 2 - CARD_WIDTH - 10;
        int pile1Y = height / 2 - CARD_HEIGHT / 2;
        int pile2X = width / 2 + 10;
        int pile2Y = height / 2 - CARD_HEIGHT / 2;

        if (game.getPlayer1().getTargetPile() == 0) {
            drawIndicator(g, "redtri", pile1X + 38, pile1Y + CARD_HEIGHT + 10);
        } else {
            drawIndicator(g, "redtri", pile2X + 40, pile2Y + CARD_HEIGHT + 10);
        }
        if (game.getPlayer2().getTargetPile() == 0) {
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

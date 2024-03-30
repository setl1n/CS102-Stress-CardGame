package game;

import javax.swing.JPanel;
import javax.swing.Timer;

import cardcollections.Deck;
import cardcollections.Pile;
import gui.Overlays;
import gui.SoundUtility;
import player.Player;

public class Game {
    private Player player1;
    private Player player2;
    private Pile[] piles;
    private GameState gameState;

    private JPanel gamePanel;

    public Game() {
        Deck startingDeck = new Deck(false);
        startingDeck.shuffle();
        Deck halfDeck = startingDeck.splitAndReturnHalf();
        halfDeck.changeColour();
        player1 = new Player("Player 1", startingDeck);
        player2 = new Player("Player 2", halfDeck);
        piles = new Pile[] { new Pile(), new Pile() };
        gameState = GameState.START_SCREEN;
    }

    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    private boolean areBothPlayersOutOfMoves() {
        return !player1.getHand().hasValidMoves(piles) && !player2.getHand().hasValidMoves(piles);
    }

    private boolean doBothPlayersHaveAtLeast1CardInDeck() {
        return !player1.getDeck().isEmpty() && !player2.getDeck().isEmpty();
    }

    private boolean doesPlayer1HaveAtLeast2CardsInDeck() {
        return player1.getDeck().size() >= 2;
    }

    private boolean doesPlayer2HaveAtLeast2CardsInDeck() {
        return player2.getDeck().size() >= 2;
    }

    public void openCardsFromDeck() {

        if (doBothPlayersHaveAtLeast1CardInDeck()) {
            player1.openCardToPile(piles[0], true);
            player2.openCardToPile(piles[1], false);

        } else if (doesPlayer1HaveAtLeast2CardsInDeck()) {
            player1.openCardToPile(piles[0], true);
            player1.openCardToPile(piles[1], false);

        } else if (doesPlayer2HaveAtLeast2CardsInDeck()) {
            player2.openCardToPile(piles[0], true);
            player2.openCardToPile(piles[1], false);
        }

    }

    public void updateGameState() {
        boolean isPlayer1HandEmpty = player1.getHand().isEmpty();
        boolean isPlayer2HandEmpty = player2.getHand().isEmpty();

        if (isPlayer1HandEmpty && !isPlayer2HandEmpty) {
            SoundUtility.endSound();
            Overlays.renderGameTransition(gamePanel, player1);
            gameState = GameState.END;

        } else if (isPlayer2HandEmpty && !isPlayer1HandEmpty) {
            SoundUtility.endSound();
            Overlays.renderGameTransition(gamePanel, player2);
            gameState = GameState.END;

        } else if (areBothPlayersOutOfMoves()) {

            if (doBothPlayersHaveAtLeast1CardInDeck() || doesPlayer1HaveAtLeast2CardsInDeck()
                    || doesPlayer2HaveAtLeast2CardsInDeck()) {
                        Overlays.renderTimeoutTransition(gamePanel);
                SoundUtility.pauseClip();
                gameState = GameState.NO_VALID_MOVES;

            } else {
                SoundUtility.endSound();
                Overlays.renderTieTransition(gamePanel);
                gameState = GameState.END;
            }
        }
    }

    public Pile getPile(int index) {
        return piles[index];
    }

    public Pile[] getBothPiles() {
        return piles;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    // for debugging
    @Override
    public String toString() {
        return String.format("Pile 1 %s\nPile 2 %s \n%s\n%s\n", piles[0], piles[1], player1, player2);
    }

    public void stress(Player actionPlayer, Player opponent) {
        Deck opponentDeck = opponent.getDeck();

        if (GameLogicUtils.isValidStress(piles)) {
            gameState = GameState.STRESS;
            Overlays.renderStressTransition(gamePanel, opponent);
            SoundUtility.stressSound();
            // Change gamestate temporarily to lock user's inputs
            int delay = 3000;
            Timer timer = new Timer(delay, e -> {
                gameState = GameState.PLAYING;
            });
            timer.setRepeats(false);
            timer.start();

            // add pile to loser's hand
            for (Pile p : piles) {
                opponentDeck.transfer(p);
            }
            opponentDeck.shuffle();
            opponent.drawFourCards(false);
            openCardsFromDeck();

        } else {
            // Penalty for invalid throwing card to pile: 5 seconds
            System.out.println("Invalid Stress, blocked for 5s");
            actionPlayer.blockFor(5000);
        }
    }

}
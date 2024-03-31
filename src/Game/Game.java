package game;

import javax.swing.*;
import cardcollections.*;
import gui.*;

/**
 * This class represents a game. It manages the game state, players, and piles of cards.
 */
public class Game {
    private Player player1;
    private Player player2;
    private Pile[] piles;
    private GameState gameState;

    private JPanel gamePanel;
    /**
     * The penalty for invalid stress.
     */
    private static final int INVALID_STRESS_PENALTY = 2000;

    /**
     * The duration of the stress animation.
     */
    private static final int STRESS_ANIMATION_DURATION = 2000;

    /**
     * The delay for card transfer.
     */
    private static final int CARD_TRANSFER_DELAY = 2000;

    /**
     * Constructs a new game.
     */
    public Game() {
        initialise();
        gameState = GameState.START_SCREEN;
    }
    
    /**
     * Initialises the game.
     */
    private void initialise() {
        // Create new decks for the players
        Deck startingDeck = new Deck(false);
        startingDeck.shuffle();
        Deck halfDeck = startingDeck.splitAndReturnHalf();
        halfDeck.changeColour();

        // Initialise the players
        player1 = new Player("Player 1", startingDeck);
        player2 = new Player("Player 2", halfDeck);

        // Initialise the piles
        piles = new Pile[] { new Pile(), new Pile() };
    }
    
    /**
     * Starts the game by setting gameState and opening the first two cards.
     */
    public void start() {
        gameState = GameState.PLAYING;
        openCardsFromDeck();
    }
    
    /**
     * Loads the help dialog as a tutorial.
     */
    public void loadDialog() {
        gameState = GameState.NO_VALID_MOVES;
        Overlays.renderHelpDialog(gamePanel);
    }

    /**
     * Restarts the game.
     */
    public void restart() {
        initialise();
        loadDialog();
    }

    /**
     * Opens cards from the deck when there is an invalid move/at the start
     */
    public void openCardsFromDeck() {
        // Opens cards until someone has a valid move
        do {
            if (GameLogicUtils.eachPlayerHasAtLeast1CardInDeck(player1, player2)) {
                player1.openCardToPile(piles[0], true);
                player2.openCardToPile(piles[1], false);

            } else if (player1.getDeck().isSizeAtLeast2()) {
                player1.openCardToPile(piles[0], true);
                player1.openCardToPile(piles[1], false);

            } else if (player2.getDeck().isSizeAtLeast2()) {
                player2.openCardToPile(piles[0], true);
                player2.openCardToPile(piles[1], false);
            } else {
                // If no more cards to open and still no valid moves, udpate Game State and Return
                updateGameState();
                return;
            }
        } while (GameLogicUtils.bothPlayersOutOfMoves(player1, player2, piles));
    }

    /**
     * Updates the game state after every key press
     */
    public void updateGameState() {
        boolean isPlayer1HandEmpty = player1.getHand().isEmpty();
        boolean isPlayer2HandEmpty = player2.getHand().isEmpty();

        if (isPlayer1HandEmpty && !isPlayer2HandEmpty) {
            Sounds.endSound();
            Overlays.renderGameTransition(gamePanel, player1.getName());
            gameState = GameState.END;
        } else if (isPlayer2HandEmpty && !isPlayer1HandEmpty) {
            Sounds.endSound();
            Overlays.renderGameTransition(gamePanel, player2.getName());
            gameState = GameState.END;
        } else if (GameLogicUtils.bothPlayersOutOfMoves(player1, player2, piles)) {

            if (GameLogicUtils.eachPlayerHasAtLeast1CardInDeck(player1, player2)
                    || player1.getDeck().isSizeAtLeast2()
                    || player2.getDeck().isSizeAtLeast2()) {
                Overlays.renderTimeoutTransition(gamePanel);
                Sounds.pauseClip();
                gameState = GameState.NO_VALID_MOVES;

            } else {
                Sounds.endSound();
                Overlays.renderTieTransition(gamePanel);
                gameState = GameState.END;
            }
        }
    }
    
    /**
     * Applies stress to a player.
     * Transfers the cards in piles to opponent
     * @param actionPlayer The player applying the stress.
     * @param opponent The opponent player.
     */
    public void stress(Player actionPlayer, Player opponent) {
        if (GameLogicUtils.isValidStress(piles)) {
            Overlays.renderStressTransition(gamePanel, opponent.getName());
            Sounds.stressSound();
            temporaryDisableInputs();
            addPilesToLoserHand(opponent);
        } else {
            // Penalty for invalid throwing card to pile: 2 seconds
            System.out.println("Invalid Stress, blocked for 2s");
            actionPlayer.blockFor(INVALID_STRESS_PENALTY);
        }
    }

    /**
     * Temporarily disables inputs when invalid key entered
     */
    private void temporaryDisableInputs() {
        gameState = GameState.STRESS;
        // Change gamestate to re-enable inputs after animation
        Timer timer = new Timer(STRESS_ANIMATION_DURATION, e -> {
            gameState = GameState.PLAYING;
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Adds piles to the opponent's hand.
     * @param opponent The opponent player.
     */
    private void addPilesToLoserHand(Player opponent) {
        Deck opponentDeck = opponent.getDeck();
        // Times the transfer to occur when screen is covered by animation
        Timer changeCardsWhenAnimationHideScreen = new Timer(CARD_TRANSFER_DELAY, e -> {
            // add pile to loser's hand
            for (Pile p : piles) {
                opponentDeck.transfer(p);
            }
            opponentDeck.shuffle();
            opponent.drawFourCards();
            openCardsFromDeck();
        });
        changeCardsWhenAnimationHideScreen.setRepeats(false);
        changeCardsWhenAnimationHideScreen.start();
    }

    /**
     * Returns the pile at the specified index.
     * @param index The index of the pile.
     * @return The pile at the specified index.
     */
    public Pile getPile(int index) {
        return piles[index];
    }

    /**
     * Returns an array of both piles.
     * @return an array of both piles.
     */
    public Pile[] getBothPiles() {
        return piles;
    }

    /**
     * Returns the first player.
     * @return The first player.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Returns the second player.
     * @return The second player.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Returns the current game state.
     * @return The current game state.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the game panel.
     * @param gamePanel The new game panel.
     */
    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Returns a string representation of the game.
     * @return A string representation of the game.
     */
    @Override
    public String toString() {
        return String.format("Pile 1 %s\nPile 2 %s \n%s\n%s\n", piles[0], piles[1], player1, player2);
    }
}

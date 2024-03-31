package game;

import javax.swing.*;
import cardcollections.*;
import gui.*;

public class Game {
    private Player player1;
    private Player player2;
    private Pile[] piles;
    private GameState gameState;

    private JPanel gamePanel;
    private static final int WRONG_STRESS_PENALTY = 2000;

    public Game() {
        initialise();
        gameState = GameState.START_SCREEN;
    }
    
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
    
    public void start() {
        gameState = GameState.PLAYING;
        openCardsFromDeck();
    }
    
    public void loadDialog() {
        gameState = GameState.OPEN_FIRST_CARDS;
        Overlays.renderHelpDialog(gamePanel);
    }

    public void restart() {
        initialise();
        loadDialog();
    }

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
    
    public void stress(Player actionPlayer, Player opponent) {
        if (GameLogicUtils.isValidStress(piles)) {
            Overlays.renderStressTransition(gamePanel, opponent.getName());
            Sounds.stressSound();
            temporaryDisableInputs();
            addPilesToLoserHand(opponent);
        } else {
            // Penalty for invalid throwing card to pile: 2 seconds
            System.out.println("Invalid Stress, blocked for 2s");
            actionPlayer.blockFor(WRONG_STRESS_PENALTY);
        }
    }

    private void temporaryDisableInputs() {
        gameState = GameState.STRESS;
        // Change gamestate to re-enable inputs after animation
        Timer timer = new Timer(3000, e -> {
            gameState = GameState.PLAYING;
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void addPilesToLoserHand(Player opponent) {
        Deck opponentDeck = opponent.getDeck();
        // Times the transfer to occur when screen is covered by animation
        Timer changeCardsWhenAnimationHideScreen = new Timer(2000, e -> {
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

    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // for debugging
    @Override
    public String toString() {
        return String.format("Pile 1 %s\nPile 2 %s \n%s\n%s\n", piles[0], piles[1], player1, player2);
    }

}
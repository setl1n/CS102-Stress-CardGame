package game;

/**
 * This enum represents the possible states of the game.
 */
public enum GameState {
    /**
     * The game is at the start screen.
     */
    START_SCREEN,

    /**
     * There are no valid moves after the current move.
     */
    NO_VALID_MOVES,

    /**
     * The game is currently being played in its general state.
     */
    PLAYING,

    /**
     * A player has called stress.
     */
    STRESS,

    /**
     * The game has ended.
     */
    END;
}
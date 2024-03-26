package game;

public enum GameState {
    START_SCREEN,
    OPEN_FIRST_CARDS,
    PLAYING,
    STRESS,
    STALEMATE,
    PLAYER1_WINS,
    PLAYER2_WINS,
    NO_VALID_MOVES;
    public static GameState STATE = START_SCREEN;
}
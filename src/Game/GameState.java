package game;

public enum GameState {
    START_SCREEN,
    OPEN_FIRST_CARDS,
    PLAYING,
    STRESS,
    NOVALIDMOVES,
    STALEMATE,
    RED_WINS,
    BLU_WINS,
    EDGECASE;
    public static GameState STATE = START_SCREEN;
}
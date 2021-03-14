package logic;

/**
 * classic game Tic Tac Toe. In this game two player play against each other on a 3x3 grid.
 * The players alternatively place their respective symbols (usually "X" and "O") in one of the cells of the field.
 * Aim of this game is to place three of one's own symbols in a horizontal, vertical or diagonal row.
 * A winner is announced , once a match is found.
 *
 * @author SHIVAKRISHNA DONTHI
 */
public class GameTicTacToe extends Game {

    /**
     * Test Constructor for a game of tic tac toe. Initializes the field.
     *
     * @param p1   name of the first player
     * @param p2   name of the second player
     * @param size size of the game field
     * @param gui  connection to the gui
     */
    GameTicTacToe(String p1, String p2, int size, GUIConnector gui) {
        super(p1, p2, size, gui, size);
    }

    /**
     * Public constructor is called when player names, symbols and gui is given.
     *
     * @param players players
     * @param symbols symbols
     * @param gui     gui
     */
    public GameTicTacToe(String[] players, Symbol[][] symbols, GUIConnector gui) {
        super(players, symbols, gui, symbols.length);
    }

    /**
     * which from a given click position determines where the symbol/token of the current players has to be placed.
     *
     * @param clickCoord coordinates
     * @return coord
     */
    @Override
    protected int[] determineCoordinates(int[] clickCoord) {
        assert clickCoord.length == 2;
        return isCellEmpty(clickCoord) ? clickCoord : null;
    }
}

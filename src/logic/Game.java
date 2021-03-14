package logic;

import java.util.Arrays;

/**
 * Abstract class for a game (extended by the classes GameTicTacToe and GameConnectFour).
 *
 * @author SHIVAKRISHNA DONTHI
 */
public abstract class Game {

    /**
     * Name of the players in an array. Length must be 2.
     */
    private final String[] players;

    /**
     *  the number of tokens in a line required to win.
     */
    private final int tokensToWin;
    /**
     * Index of the current player. Must be either 0 or 1.
     */
    private int idxCurrPlayer = 0;
    /**
     * Connection to the gui.
     */
    private final GUIConnector gui;
    /**
     * The 2-dimensional field on which the player play.
     */
    protected final Symbol[][] fields;

    /**
     * Enum for the symbols used by the players. The ordinal value of the
     * respective symbol of a player should correspond with the index of this
     * player in the player array. The additional value "EMPTY" (marking an
     * empty cell) thus has the highest ordinal value.
     */
    public enum Symbol {
        X, O, EMPTY
    }

    /**
     * Constructor for a game of tic tac toe. Initializes the field.
     *
     * @param p1   name of the first player
     * @param p2   name of the second player
     * @param size size of the game field
     * @param numWin    required  no of tokens to win
     * @param gui  connection to the gui
     */
    Game(String p1, String p2, int size, GUIConnector gui, int numWin) {
        this.players = new String[]{p1, p2};
        this.gui = gui;
        this.fields = new Symbol[size][size];
        for (Symbol[] field : fields) {
            Arrays.fill(field, Symbol.EMPTY);
        }
        this.tokensToWin = numWin;
        this.gui.setCurrentPlayer(this.players[this.idxCurrPlayer], Symbol.values()[this.idxCurrPlayer]);
    }

    /**
     * Constructor for a game of tic tac toe. Initializes the field.
     *
     * @param players   name of the players
     * @param symbols   name of the second player
     * @param numWin    required  no of tokens to win
     * @param gui  connection to the gui
     */
    public Game(String[] players, Symbol[][] symbols, GUIConnector gui, int numWin) {
        this.players = players;
        this.gui = gui;
        this.fields = symbols;
        this.tokensToWin = numWin;
        this.gui.setCurrentPlayer(this.players[this.idxCurrPlayer], Symbol.values()[this.idxCurrPlayer]);
    }

    /**
     * Handles the turn of a player. If the cell chosen by the player is not
     * empty, nothing happens (the player can chose another cell). If it was
     * empty, the symbol of the current player is placed and the update of the
     * gui is initiated. Then the current player is changed, so that it is the
     * turn of the next player. Finally, the method checks if through this turn
     * a player has won the game.
     *
     * @param coord coordinate in the field at which the player wants to place
     *              his/her symbol
     */
    public void playerTurn(int[] coord) {
        assert coord.length == 2 : "length of coordinates must be 2";

            int[] co = this.determineCoordinates(coord);
            if(co != null) {
                this.fields[co[0]][co[1]] = Symbol.values()[this.idxCurrPlayer];
                this.gui.displaySymbol(co, Symbol.values()[this.idxCurrPlayer]);
                int[][] winCoord = getWinnerCoords();
                if (winCoord != null) {
                    String winner = this.getWinnerName(winCoord[0]);
                    gui.onGameEnd(winner, winCoord);
                } else {
                    this.idxCurrPlayer = idxCurrPlayer ^ 1;
                    this.gui.setCurrentPlayer(this.players[this.idxCurrPlayer], Symbol.values()[this.idxCurrPlayer]);
                }
                if (!emptyCellsLeft()) {
                    gui.onGameEnd(null, winCoord);
                }
            }
    }

    /**
     * returns the size of the game field (as it is always square, the first dimension is good enough)
     *
     * @return size
     */
    protected int getSize() {
        // as it is always square, the first dimension is good enough
        return this.fields.length;
    }

    /**
     * returns true, if the given cell is empty
     * @param coord, returns true, if the given cell is empty
     * @return true, if the given cell is empty
     */
    protected boolean isCellEmpty(int[] coord) {
        assert coord.length == 2;
        return this.fields[coord[0]][coord[1]] == Symbol.EMPTY;
    }

    /**
     * Package-Private method for
     * @return Returns a String representation of the field
     */
    String fieldToString() {
        StringBuilder str = new StringBuilder();
        for (int symbol = 0; symbol < fields[0].length; symbol++) {
            for (Symbol[] field : fields) {
                str.append(String.format("%5s", field[symbol]));
                str.append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * determines if any empty fields are left. Must not check more cells than necessary
     * @return whether empty cells are left or not
     */
    protected boolean emptyCellsLeft() {
        boolean isEmptyFieldFound = false;
        for (int i = 0; i < fields.length && !isEmptyFieldFound; i++) {
            for (int j = 0; j < fields[i].length && !isEmptyFieldFound; j++) {
                isEmptyFieldFound = fields[i][j] == Symbol.EMPTY;
            }
        }
        return isEmptyFieldFound;
    }

    /**
     *  determines if there is a winner and returns the coordinates of the winning line or null if there is no winner (yet)
     * @return winning co-ordinate
     */
    protected int[][] getWinnerCoords() {
        int[][] rowWinningCoords = findRowWise();
        int[][] columnWinningCoords = findColumnWise();
        int[][] diagonalWinningCoords = findDiagonalWise();

        if (rowWinningCoords != null) {
            return rowWinningCoords;
        } else if (columnWinningCoords != null) {
            return columnWinningCoords;
        } else if (diagonalWinningCoords != null) {
            return diagonalWinningCoords;
        }
        return null;
    }

    /**
     * helper method find diagonal wise
     * @return coord
      */
    private int[][] findDiagonalWise() {
        int count = 0;
        boolean notFound = true;
        int[][] winnerCoord = new int[tokensToWin][2];
        for (int i = 0; i < fields.length && notFound; i++) {
            for (int j = 0; j < fields.length && notFound; j++) {
                if (fields[i][j] != Symbol.EMPTY) {
                    if (rightDiagonalConstraint(new int[]{i, j})) {
                        for (int ctr = 0, a = i, b = j; (a < fields.length) && (b < fields.length) && (ctr < tokensToWin); ctr++, a++, b++) {
                            if (fields[i][j] == fields[a][b])
                                count++;
                        }
                        if (count == tokensToWin) {
                            notFound = false;
                            for (int ctr = 0, a = i, b = j; (a < fields.length) && (b < fields.length) && (ctr < tokensToWin); ctr++, a++, b++) {
                                winnerCoord[ctr][0] = a;
                                winnerCoord[ctr][1] = b;
                            }
                        } else {
                            count = 0;
                        }
                    }
                    if (leftDiagonalConstraint(new int[]{i, j})) {
                        for (int ctr = 0, a = i, b = j; (a < fields.length) && (b < fields.length) && (ctr < tokensToWin); ctr++, a++, b--) {
                            if (fields[i][j] == fields[a][b])
                                count++;
                        }
                        if (count == tokensToWin) {
                            notFound = false;
                            for (int ctr = 0, a = i, b = j; (a < fields.length) && (b < fields.length) && (ctr < tokensToWin); ctr++, a++, b--) {
                                winnerCoord[ctr][0] = a;
                                winnerCoord[ctr][1] = b;
                            }
                        } else {
                            count = 0;
                        }
                    }
                }
            }
        }
        return notFound ? null : winnerCoord;
    }
    /**
     * helper method find diagonal wise
     * @return win coord
     */
    private boolean rightDiagonalConstraint(int[] coord){
        assert coord.length == 2;
        return coord[0] < tokensToWin && coord[1] < tokensToWin;
    }
    /**
     * helper method constraint of diagonal wise
     * @return win percentage
     */
    private boolean leftDiagonalConstraint(int[] coord){
        assert coord.length == 2;
        return coord[0] < tokensToWin && coord[1] >= tokensToWin - 1;
    }

    /**
     * helper method find row wise
     * @return coord
     */
    private int[][] findRowWise() {
        int count = 0;
        boolean notFound = true;
        int[][] winnerCoord = new int[tokensToWin][2];
        int size = getSize();
        for (int i = 0; i < size && notFound; i++) {
            for (int j = 0; (j < size - 1) && notFound; j++) {
                if (fields[i][j] != Symbol.EMPTY) {
                    for (int col = j, ptr = 0; (col < size) && (ptr < tokensToWin); col++, ptr++) {
                        if (fields[i][col] == fields[i][j]) {
                            count++;
                        }
                    }
                    if (count == tokensToWin) {
                        notFound = false;
                        for (int a = 0, b = j; a < tokensToWin; a++, b++) {
                            winnerCoord[a][0] = i;
                            winnerCoord[a][1] = b;
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        return notFound ? null : winnerCoord;
    }

    /**
     * helper method find column wise
     * @return coord
     */
    private int[][] findColumnWise() {
        int count = 0;
        boolean notFound = true;
        int[][] winnerCoord = new int[tokensToWin][2];
        int size = getSize();
        for (int i = 0; i < size && notFound; i++) {
            for (int j = 0; (j < size - 1) && notFound; j++) {
                if (fields[j][i] != Symbol.EMPTY) {
                    for (int col = j, ptr = 0; (col < size) && (ptr < tokensToWin); col++, ptr++) {
                        if (fields[col][i] == fields[j][i]) {
                            count++;
                        }
                    }
                    if (count == tokensToWin) {
                        notFound = false;
                        for (int a = 0, b = j; a < tokensToWin; a++, b++) {
                            winnerCoord[a][0] = b;
                            winnerCoord[a][1] = i;
                        }
                    } else {
                        count = 0;
                    }
                }
            }
        }
        return notFound ? null : winnerCoord;
    }

    /**
     *  determines the name of the winner from the given winning coordinates
     * @param winnerCoord winner co-ordinates
     * @return name of the winner in string
     */
    protected String getWinnerName(int[] winnerCoord) {
        assert winnerCoord.length == 2;
        Symbol s = fields[winnerCoord[0]][winnerCoord[1]];
        return players[s.ordinal()];
    }

    /**
     * which from a given click position determines where the symbol/token of the current players has to be placed.
     * @param clickCoord coordinat
     * @return determined co-odrinate
     */
    protected abstract int[] determineCoordinates(int[] clickCoord);
}

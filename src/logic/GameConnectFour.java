package logic;

/**
 * @author SHIVAKRISHNA DONTHI
 */
public class GameConnectFour extends Game {

    public final int size;
    public static final int numWin = 4;

    public GameConnectFour(String[] players, Symbol[][] fields, GUIConnector gui) {
        super(players, fields, gui, numWin);
        this.size = fields[0].length;
    }

    /**
     * which from a given click position determines where the symbol/token of the current players has to be placed.
     *
     * @param clickCoord coordinate
     * @return coord
     */
    @Override
    protected int[] determineCoordinates(int[] clickCoord) {
        assert clickCoord.length == 2;
        int[] returnCoord = null;
        for (int i = size - 1; i >= 0 && returnCoord == null; i--) {
            int[] coord = new int[]{clickCoord[0], i};
            if (isCellEmpty(coord)) {
                returnCoord = coord;
            }
        }
        return returnCoord;
    }
}

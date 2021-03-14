package logic;

import org.junit.Test;

import static logic.Game.Symbol.*;
import static logic.Game.Symbol.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author SHIVAKRISHNA DONTHI
 */
public class ReqTest {

    @Test
    public void testFieldToString_1() {
        Game game = new GameConnectFour(new String[]{"Shiva", "Krishna"},
                new Game.Symbol[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,     X},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }, new FakeGUI());
        assertEquals("EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n"
                        + "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n"
                        + "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n"
                        + "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n"
                        + "EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY \n"
                        + "    X EMPTY EMPTY EMPTY EMPTY EMPTY \n",
                game.fieldToString());
    }

    @Test
    public void testWinnerDiagonalBottomLeftToTopRight_1() {
        Game game = new GameConnectFour(new String[]{"Shiva", "Krishna"},
                new Game.Symbol[][]{
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,     X},
                        {EMPTY, EMPTY, EMPTY, EMPTY,     O,     O},
                        {EMPTY, EMPTY, EMPTY,     O,     X,     X},
                        {EMPTY, EMPTY,     O,     X,     X,     X},
                        {EMPTY, O, EMPTY,     O,     O,     X},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }, new FakeGUI());

        assertTrue(game.emptyCellsLeft());
        assertEquals("Krishna", game.getWinnerName(game.getWinnerCoords()[0]));
    }

    @Test
    public void testWinnerCol_2() {
        Game game = new GameConnectFour(new String[]{"Shiva", "Krishna"},
                new Game.Symbol[][]{
                        {X, EMPTY, EMPTY, EMPTY, EMPTY,     EMPTY},
                        {X, EMPTY, EMPTY, EMPTY,     O,     EMPTY},
                        {X, EMPTY, EMPTY,     O,     X,     EMPTY},
                        {X, EMPTY,     O,     X,     X,     EMPTY},
                        {X, O, EMPTY,     O,     O,     X},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                }, new FakeGUI());

        assertTrue(game.emptyCellsLeft());
        assertEquals("Shiva", game.getWinnerName(game.getWinnerCoords()[0]));
    }


}

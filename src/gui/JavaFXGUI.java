package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.GUIConnector;
import logic.Game.Symbol;

/**
 * This class is responsible for changing the gui when the logic deems it
 * necessary. Created by the gui and then passed as a parameter into the logic.

 * @author SHIVAKRISHNA DONTHI
 */
public class JavaFXGUI implements GUIConnector {

    // Images used to represent the symbols of the player.
    private final ImageView[][] imgs;
    private final Image[] images;
    private final ImageView currentToken;

    private final Button btnNewGame;
    private final GridPane grdPnField;
    private final Label lblDiscrCurrentPlayer;
    private final Label lblCurrentPlayer;
    private final Label lblWinner;

    private static final Image GREY = new Image("gui/img/greysolid.png");
    private static final Image STAR = new Image("gui/img/star.png");

    /**
     * The constructor. Gets passed all components of the gui that may change
     * due to actions in the logic.
     *
     * @param imgs array of images that will be displayed for the symbols of the
     *             players. The length of the array equals to the number of players. First
     *             image is for the first player etc
     */
    public JavaFXGUI(Image[] images, ImageView[][] imgs, Button btnNewGame, GridPane grdPnField,
                     Label lblDiscrCurrentPlayer, Label lblCurrentPlayer, Label lblWinner, ImageView currentToken) {
        this.imgs = imgs;
        this.images = images;
        this.currentToken = currentToken;
        this.btnNewGame = btnNewGame;
        this.grdPnField = grdPnField;
        this.lblDiscrCurrentPlayer = lblDiscrCurrentPlayer;
        this.lblCurrentPlayer = lblCurrentPlayer;
        this.lblWinner = lblWinner;

        for (ImageView[] img : this.imgs) {
            for (int j = 0; j < this.imgs[0].length; j++) {
                img[j].setImage(GREY);
            }
        }
    }

    /**
     * Displays symbols of co-ordinates given.
     *
     * @param coord  the coordinates at which in the field the given symbol
     *               should be displayed
     * @param symbol the symbol to be displayed
     */
    @Override
    public void displaySymbol(int[] coord, Symbol symbol) {
        imgs[coord[0]][coord[1]].setImage(images[symbol.ordinal()]);
        currentToken.setImage(images[symbol.ordinal()]);
    }

    /**
     * Describes the current player name,in string representation.
     *
     * @param name   name of the current player
     * @param symbol symbol of the current player
     */
    @Override
    public void setCurrentPlayer(String name, Symbol symbol) {
        lblCurrentPlayer.setText(name + "(" + symbol + ")");
        currentToken.setImage(images[symbol.ordinal()]);
    }

    /**
     * Gives the winner  of game tictactoe/ConnectFour.
     *
     * @param winnerName   name of the player than won, null if there is no winner
     * @param winnerCoords array of the coordinates that were involved in the
     *                     win (length 1st dimension equals to the number of same symbol cells
     */
    @Override
    public void onGameEnd(String winnerName, int[][] winnerCoords) {
        this.lblWinner.setVisible(true);
        String winner = winnerName != null ? winnerName : "no one";
        this.lblWinner.setText("winner \n" + "is \n" + winner);
        btnNewGame.setDisable(false);
        grdPnField.setDisable(true);
        lblWinner.setVisible(true);
        lblCurrentPlayer.setDisable(true);
        lblDiscrCurrentPlayer.setDisable(true);
        if (winnerCoords != null) {
            for (int[] winnerCoord : winnerCoords) {
                imgs[winnerCoord[0]][winnerCoord[1]] = new ImageView(STAR);
                grdPnField.add(imgs[winnerCoord[0]][winnerCoord[1]], winnerCoord[0], winnerCoord[1]);
                imgs[winnerCoord[0]][winnerCoord[1]].fitWidthProperty().bind(grdPnField.widthProperty().divide(imgs.length).subtract(grdPnField.getHgap()));
                imgs[winnerCoord[0]][winnerCoord[1]].fitHeightProperty().bind(grdPnField.heightProperty().divide(imgs.length).subtract(grdPnField.getVgap()));

            }
        }
        this.grdPnField.setDisable(true);
        this.lblDiscrCurrentPlayer.setDisable(true);
        this.lblCurrentPlayer.setDisable(true);
    }
}

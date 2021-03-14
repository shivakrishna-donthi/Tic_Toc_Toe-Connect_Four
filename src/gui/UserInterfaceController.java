package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import logic.Game;
import logic.GameConnectFour;
import logic.GameTicTacToe;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for changing labels, buttons, panes and also handles action events when
 * logics deems for it.
 *
 * @author SHIVAKRISHNA DONTHI
 */

public class UserInterfaceController implements Initializable {

    @FXML
    private GridPane grdPn;
    @FXML
    private Label lblDiscrCurrentPlayer;
    @FXML
    private Label lblCurrentPlayer;
    @FXML
    private Label lblWinner;
    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnStartGame;
    @FXML
    private TextField txtFldPlayerOne;
    @FXML
    private TextField txtFldPlayerTwo;
    @FXML
    private RadioButton btnTicTacToe;
    @FXML
    private RadioButton btnFourConnect;
    @FXML
    private ToggleGroup tglGrpGame;
    @FXML
    private ImageView currentToken;
    @FXML
    private Label lblPlayerOne;
    @FXML
    private Label lblPlayerTwo;

    // the attribute used to hold the GameTicTacToe instance
    private Game game;

    /**
     * Called to initialize a controller after its root element has been completely processed.
     *
     * @param url            unused
     * @param resourceBundle unused
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnNewGame.setVisible(true);
        btnNewGame.setDisable(false);
        btnTicTacToe.setDisable(true);
        btnFourConnect.setDisable(true);
        btnStartGame.setDisable(true);
        lblPlayerOne.setDisable(true);
        txtFldPlayerOne.setDisable(true);
        lblPlayerTwo.setDisable(true);
        txtFldPlayerTwo.setDisable(true);
        grdPn.setDisable(true);
        grdPn.setVisible(true);
        lblWinner.setVisible(false);
        lblDiscrCurrentPlayer.setDisable(true);
        lblCurrentPlayer.setDisable(true);
        txtFldPlayerOne.setText("Shiva");
        txtFldPlayerTwo.setText("Krishna");
        grdPn.setVisible(true);
        grdPn.setHgap(10); // horizontal gap in pixels
        grdPn.setVgap(10); // vertical gap in pixels
        grdPn.setPadding(new Insets(10, 10, 10, 10)); // margins around the whole grid
    }


    /**
     * handles various labels , buttons and grid pane once button new game is clicked.
     * actionEvent is invoked after a Click on Button New game in VBOX.
     */
    @FXML
    private void handleBtnNewGame() {
        btnNewGame.setDisable(false);
        btnFourConnect.setDisable(false);
        btnFourConnect.setVisible(true);
        btnTicTacToe.setDisable(false);
        btnTicTacToe.setVisible(true);
        lblPlayerOne.setDisable(false);
        lblPlayerOne.setVisible(true);
        lblPlayerTwo.setDisable(false);
        lblPlayerTwo.setVisible(true);
        txtFldPlayerOne.setDisable(false);
        txtFldPlayerOne.setVisible(true);
        txtFldPlayerTwo.setDisable(false);
        txtFldPlayerTwo.setVisible(true);
        lblWinner.setVisible(false);
        currentToken.setVisible(false);
        grdPn.setVisible(false);
        btnStartGame.setDisable(false);
        btnStartGame.setVisible(true);
        lblCurrentPlayer.setVisible(false);
        // default initialise the tic-toc-toe game
        btnTicTacToe.setSelected(true);

        // prepare grid if anyone pre-selected
        if(btnTicTacToe.isSelected() || btnFourConnect.isSelected()){
            onOptionChange();
        }
    }

    /**
     * handles various labels , buttons and grid pane once button start game button is clicked.
     * action event is invoked after a Click on Button New game in Borderpane.
     */
    @FXML
    private void handleBtnStartGame() {
        btnNewGame.setVisible(true);
        btnNewGame.setDisable(false);
        btnTicTacToe.setVisible(false);
        btnFourConnect.setVisible(false);
        btnStartGame.setVisible(false);
        lblPlayerOne.setVisible(false);
        txtFldPlayerOne.setVisible(false);
        lblPlayerTwo.setVisible(false);
        txtFldPlayerTwo.setVisible(false);
        currentToken.setVisible(true);
        lblCurrentPlayer.setVisible(true);


        grdPn.setDisable(false);
        grdPn.setVisible(true);

        lblWinner.setVisible(false);
        lblDiscrCurrentPlayer.setDisable(false);
        lblCurrentPlayer.setDisable(false);

        prepareGrid();
    }

    /**
     * helper method to re use grid construction
     */
    private void prepareGrid() {
        grdPn.getRowConstraints().clear();
        grdPn.getColumnConstraints().clear();
        grdPn.getChildren().clear();

        boolean isTTT = tglGrpGame.getSelectedToggle().equals(btnTicTacToe);
        boolean isFourConnect = tglGrpGame.getSelectedToggle().equals(btnFourConnect);
        if(isFourConnect || isTTT) {
            int size = isFourConnect ? 7 : 3;
            Image[] images = new Image[2];
            if (isFourConnect) {
                images[0] = new Image("gui/img/red.png");
                images[1] = new Image("gui/img/blue.png");
            } else {
                images[0] = new Image("gui/img/x.png");
                images[1] = new Image("gui/img/o.png");
            }

            // add columns to grid
            for (int i = 0; i < size; i++) {
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setMinWidth(10.0);
                grdPn.getColumnConstraints().add(col1);
            }

            // add rows to the grid
            for (int i = 0; i < size; i++) {
                RowConstraints row1 = new RowConstraints();
                row1.setMinHeight(10.0);
                grdPn.getRowConstraints().add(row1);
            }

            // fill the grid with EMPTY symbols
            Game.Symbol[][] symbols = new Game.Symbol[grdPn.getColumnCount()][grdPn.getRowCount()];
            for (int i = 0; i < grdPn.getColumnCount(); i++) {
                for (int j = 0; j < grdPn.getRowCount(); j++) {
                    symbols[i][j] = Game.Symbol.EMPTY;
                }
            }

            ImageView[][] imageViews = this.initImages(grdPn);
            JavaFXGUI gui = new JavaFXGUI(images, imageViews, btnNewGame, grdPn,
                    lblDiscrCurrentPlayer, lblCurrentPlayer, lblWinner, currentToken);
            String[] players = new String[]{txtFldPlayerOne.getText(), txtFldPlayerTwo.getText()};
            if (isTTT) {
                game = new GameTicTacToe(players, symbols, gui);
            } else {
                game = new GameConnectFour(players, symbols, gui);
            }
        }
    }

    /**
     * Creates an array of imageviews corresponding to the gridPane. Each
     * imageView becomes a child of the gridPane and fills a cell. For proper
     * resizing it is binded to the gridPanes width and height. If the GridPane
     * has a hgap or a vgap it is necessary to also consider these when binding.
     * A default image could be added by passing another parameter into this method.
     *
     * @param grdPn the GridPane to which ImageViews should be added
     * @return an array of imageviews added to the gridPane
     */
    private ImageView[][] initImages(GridPane grdPn)  {
        int colcount = grdPn.getColumnConstraints().size();
        int rowcount = grdPn.getRowConstraints().size();

        ImageView[][] imageViews = new ImageView[colcount][rowcount];
        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                //creates an empty imageview
                imageViews[x][y] = new ImageView();

                //add the imageview to the cell and assign the correct indicees for
                //this imageview, so you later can use GridPane.getColumnIndex(Node)
                grdPn.add(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(grdPn.widthProperty().
                        divide(colcount).subtract(grdPn.getHgap()));
                imageViews[x][y].fitHeightProperty().bind(grdPn.heightProperty().
                        divide(rowcount).subtract(grdPn.getVgap()));
            }
        }
        return imageViews;
    }

    /**
     * Reaction to the mouse click event on the GridPane.
     * @param event the mouse click event that is fired when the grid pane is clicked
     */
    @FXML
    private void onMouseClickGridPane(MouseEvent event) {
        int x = -1;
        int y = -1;
        boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
        boolean rightClicked = event.getButton() == MouseButton.SECONDARY;

        //determine the imageview of the grid that contains the coordinates of
        //the mouseclick to determine the board-coordinates
        for (Node node : grdPn.getChildren()) {
            if (node instanceof ImageView) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    //to use following methods the columnIndex and rowIndex
                    //must have been set when adding the imageview to the grid
                    x = GridPane.getColumnIndex(node);
                    y = GridPane.getRowIndex(node);
                }
            }
        }

        // assert (x >= 0 && y >= 0): "dem Klick ist keine Koordinate zuzuordnen";
        if ((x >= 0 && y >= 0) && (leftClicked || rightClicked)) {
            //react on left click or right click
            game.playerTurn(new int[]{x, y});
        } else {
            System.err.println("Unable to find co-ordinates due to clicked outside of the grid pane");
        }
    }

    /**
     * To take action when radio button selection changed
     */
    @FXML
    private void onOptionChange() {
        grdPn.setVisible(true);
        grdPn.setDisable(true);
        prepareGrid();
    }

}

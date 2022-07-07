package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;

/**
 * A JavaFX GUI for the Connect Four game.
 *
 * @author RIT CS
 * @author Jaden Seaton
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    /**
     * represent the connectFourBoard
     */
    private ConnectFourBoard model;
    /**
     * The label for the moves made
     */
    private Label movesMade;
    /**
     * the label for the current player
     */
    private Label currentPlayer;
    /**
     * the label for the game status
     */
    private Label status;
    /**
     * the array of buttons on the gridPane
     */
    private ConnectFourButton [][] buttons = new ConnectFourButton[6][7];
    /**
     * the Label for the player 1 win count
     */
    private Label p1WinCount = new Label();
    /**
     * the Label for player 2 win count
     */
    private Label p2WinCount = new Label();
    /**
     * the Label for tie count
     */
    private Label tieCount= new Label();
    /**
     * the label for total moves made
     */
    private Label totalMoves= new Label();
    /**
     * the label for total rounds played
     */
    private Label totalRounds= new Label();


    /**
     * Initializes the model and adds to the observer list
     */
    @Override
    public void init() {
        this.model = new ConnectFourBoard();
        model.addObserver(this);
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the GUI
     * @throws Exception if there is a problem
     */
    public void start( Stage stage ) throws Exception {

        BorderPane borderPane = new BorderPane();

        GridPane pane = makeGridPane();
        borderPane.setCenter(pane);


        HBox box = new HBox();
        Button reset = new Button("Reset");
        reset.setOnAction(actionEvent -> {
            model.reset();
            for (int row = 0; row < 6; row ++){
                for (int col = 0; col < 7; col ++){
                    buttons[row][col].setDisable(false);
                }
            }

        });
        box.getChildren().add(reset);
        Button stats = new Button("Statistics");
        stats.setOnAction(actionEvent -> {

            VBox vbox = new VBox();
            p1WinCount = new Label("Player 1 win count: " + model.getP1WinCount());
            vbox.getChildren().add(p1WinCount);
            p2WinCount = new Label("Player 2 win count: " + model.getP2WinCount());
            vbox.getChildren().add(p2WinCount);
            tieCount = new Label("Tie count: " + model.getTieCount());
            vbox.getChildren().add(tieCount);
            totalMoves = new Label("Total moves made: " + model.getTotalMoves());
            vbox.getChildren().add(totalMoves);
            totalRounds = new Label("Total rounds played: " + model.getTotalRounds());
            vbox.getChildren().add(totalRounds);
            Stage stage1 = new Stage();
            stage1.setWidth(250);
            stage1.setHeight(150);
            Scene scene = new Scene(vbox);
            stage1.setScene(scene);
            stage1.setTitle("Statistics");
            stage1.show();
        });
        box.getChildren().add(stats);
        borderPane.setTop(box);

        HBox hBox = new HBox(130);
        movesMade = new Label(model.getMovesMade() + " moves made");
        hBox.getChildren().add(movesMade);
        currentPlayer = new Label("Current player: " + model.getCurrentPlayer());
        hBox.getChildren().add(currentPlayer);
        status = new Label("Status: " + model.getGameStatus());
        hBox.getChildren().add(status);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("Connect Four GUI");
        stage.show();
    }

    /**
     * Helper method to making the gridPane
     * @return the gridPane created
     */
    public GridPane makeGridPane(){
        GridPane gridPane = new GridPane();
        ConnectFourButton button;

        for (int row = 0; row < 6; row ++){
            for (int col = 0; col < 7; col++){
                if (model.getContents(row, col) == ConnectFourBoard.Player.NONE){
                    button = new ConnectFourButton("empty", col);
                }
                else if (model.getContents(row,col) == ConnectFourBoard.Player.P1){
                    button = new ConnectFourButton("p1", col);
                }
                else{
                    button = new ConnectFourButton("p2", col);
                }
                ConnectFourButton finalButton = button;
                button.setOnAction(actionEvent -> {
                    if (model.isValidMove(finalButton.getColumn())){
                        model.makeMove(finalButton.getColumn());
                    }
                });
                buttons[row][col] = button;
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    /**
     * Called by the model, model.ConnectFourBoard, whenever there is a state change
     * that needs to be updated by the GUI.
     *
     * @param connectFourBoard the board
     */
    @Override
    public void update(ConnectFourBoard connectFourBoard) {

        ConnectFourBoard.Status status1 = connectFourBoard.getGameStatus();

        movesMade.setText(connectFourBoard.getMovesMade() + " moves made");
        currentPlayer.setText("Current Player: " + connectFourBoard.getCurrentPlayer());
        status.setText("Status " + status1);
        p1WinCount.setText("Player 1 win count: " + connectFourBoard.getP1WinCount());
        p2WinCount.setText("Player 2 win count: " + connectFourBoard.getP2WinCount());
        tieCount.setText("Tie count: " + connectFourBoard.getTieCount());
        totalMoves.setText("Total moves made: " + connectFourBoard.getTotalMoves());
        totalRounds.setText("Total rounds played: " + connectFourBoard.getTotalRounds());

        for (int row = 0; row < 6; row ++){
            for (int col = 0; col < 7; col ++){
                if (connectFourBoard.getContents(row, col).equals(ConnectFourBoard.Player.P1)){
                    buttons[row][col].setGraphic(new ImageView(ConnectFourButton.getImage(1)));
                }
                else if (connectFourBoard.getContents(row, col).equals(ConnectFourBoard.Player.P2)){
                    buttons[row][col].setGraphic(new ImageView(ConnectFourButton.getImage(2)));
                }
                else {
                    buttons[row][col].setGraphic(new ImageView(ConnectFourButton.getImage(0)));
                }
            }
        }

        if (status1 != ConnectFourBoard.Status.NOT_OVER){
            for (int row = 0; row < 6; row ++){
                for (int col = 0; col < 7; col ++){
                    buttons[row][col].setDisable(true);
                }
            }
        }

    }

    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}

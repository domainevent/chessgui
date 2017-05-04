package com.javacook.chessgui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Optional;


/**
 * Start of the Chess GUI application
 */
public class ChessGUI extends Application {

    public static void main(String[] args) {
        // Automatic VM reset, thanks to Joseph Rachmuth.
        try {
            launch(args);
            System.exit(0);
        }
        catch (Exception error) {
            error.printStackTrace();
            System.exit(0);
        }
    }


    private ChessBoard board;
    private boolean playerIsWhite; // white player = server


    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("Chess Game");
        mainStage.getIcons().add(new Image("assets/icons/app_icon.png"));

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        // add stylesheet
        mainScene.getStylesheets().add("assets/stylesheet.css");

        // prompt user to select team color
        choosePlayerColor();

        // draw chessboard
        board = new ChessBoard(this, playerIsWhite);
        root.setCenter(board); // sized 400x400

        // add menuBar
        MenuBar menuBar = generateMenuBar();
        root.setTop(menuBar);

        mainStage.show();
    }


    /**
     * Starts a new game
     */
    public void onNewGame() {
        // prompt user to select team color
        choosePlayerColor();

        // draw chessboard
        board = new ChessBoard(this, playerIsWhite);
    }


    public void stop() {
        try {
            // TODO vollmerj: Is there something to do?
        }
        catch (NullPointerException e) {
            // Nothing to close. Connention never initialized and/or established
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Prompts the player to choose team color
     */
    public void choosePlayerColor() {
        // Prompt user for new game
        Alert newGameAlert = new Alert(AlertType.CONFIRMATION);
        newGameAlert.setTitle("Start new game");
        newGameAlert.setHeaderText(null);
        newGameAlert.setContentText("Pick your color");

        ButtonType buttonTypeWhite = new ButtonType("White");
        ButtonType buttonTypeBlack = new ButtonType("Black");

        newGameAlert.getButtonTypes().setAll(buttonTypeWhite, buttonTypeBlack);
        Optional<ButtonType> result = newGameAlert.showAndWait();

        if (result.get() == buttonTypeWhite) {
            this.playerIsWhite = true;
        }
        else if (result.get() == buttonTypeBlack) {
            this.playerIsWhite = false;
        }
        else {
            this.playerIsWhite = true;
        }
    }


    /**
     * Creates a alert box showing the text <code>hint</code>.
     * @param hint text being displayed
     */
    public void showHint(AlertType alertType, String hint) {
        Alert newGameAlert = new Alert(alertType);
        newGameAlert.setTitle("Hint");
        newGameAlert.setHeaderText(null);
        newGameAlert.setContentText(hint);

        ButtonType buttonTypeWhite = new ButtonType("OK");
        newGameAlert.getButtonTypes().setAll(buttonTypeWhite);
        Optional<ButtonType> result = newGameAlert.showAndWait();
    }




    /**
     * Quits program
     */
    public void onQuit() {
        Platform.exit();
        System.exit(0);
    }


    /**
     * Display 'about' menu
     */
    public void onDisplayAbout() {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("About this program");
        infoAlert.setHeaderText(null);

        // set window icon
        Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("assets/icons/about.png"));

        // the graphic replaces the standard icon on the left
        //infoAlert.setGraphic( new ImageView( new Image("assets/icons/cat.png", 64, 64, true, true) ) );

        infoAlert.setContentText("Programmed by Maxwell Sirotin and Steven Vascellaro.\n\n" +
                "Chess icons by \"Colin M.L. Burnett\".\n\n" +
                "App icon by BlackVariant.\n\n" +
                "Modified for dddchess (a Domain Driven Design tutorial) " +
                "by Jörg Vollmer (javacook).");
        infoAlert.showAndWait();
    }


    /**
     * Generate main menu bar
     */
    private MenuBar generateMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu gameMenu = new Menu("Game");
        menuBar.getMenus().add(gameMenu);


        MenuItem menuItemNewGame = new MenuItem("New Game");
        menuItemNewGame.setOnAction(e -> onNewGame());
        menuItemNewGame.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        gameMenu.getItems().add(menuItemNewGame);

        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem("About");
        //menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        // Note: Accelerator F1 does not work if TextField is
        //       in focus. This is a known issue in JavaFX.
        //       https://bugs.openjdk.java.net/browse/JDK-8148857
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }
}
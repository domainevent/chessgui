package com.javacook.chessgui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;


/**
 * Start of the Chess GUI application
 */
public class ChessGUI extends Application {

    public static void main(String[] args) {
        // Automatic VM reset, thanks to Joseph Rachmuth.
        try {
            extractAndSetServerUrl(args);
            launch(args);
            System.exit(0);
        }
        catch (Exception error) {
            error.printStackTrace();
            System.exit(0);
        }
    }

    private static void extractAndSetServerUrl(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("server=")) {
                String serverUrl = arg.substring(7).trim();
                if (!serverUrl.startsWith("http")) {
                    serverUrl = "http://" + serverUrl;
                }
                RestClient.SERVER_URL = serverUrl;
            }
        }
    }

    public final static String CR = System.lineSeparator();
    public final static GUITexts TEXTS = new GUITexts();
    private ChessBoard board;
    private boolean playerIsWhite; // white player = server
    private String gameId;


    public boolean isPlayerIsWhite() {
        return playerIsWhite;
    }

    public String getGameId() {
        return gameId;
    }


    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle(TEXTS.title());
        mainStage.getIcons().add(new Image("assets/icons/app_icon.png"));

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        // add stylesheet
        mainScene.getStylesheets().add("assets/stylesheet.css");

        // prompt user to select team color
        generateStartDialog();
//        choosePlayerColor();

        // draw chessboard
        board = new ChessBoard(this);
        root.setCenter(board); // sized 400x400

        // add menuBar
        MenuBar menuBar = generateMenuBar();
        root.setTop(menuBar);

        mainStage.show();
    }


    @Override
    public void stop() {
        // TODO vollmerj: Is there something to do?
    }


    /**
     * Creates a alert box showing the text <code>hint</code>.
     * @param hint text being displayed
     */
    public void showHint(AlertType alertType, String hint) {
        Alert newGameAlert = new Alert(alertType);
        newGameAlert.setTitle(TEXTS.alertTitleHint());
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
        infoAlert.setTitle(TEXTS.alertTitleInfo());
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

        Menu gameMenu = new Menu(TEXTS.menuLabelGame());
        menuBar.getMenus().add(gameMenu);

        MenuItem menuItemQuit = new MenuItem(TEXTS.menuLabelGameQuit());
        menuItemQuit.setOnAction(e -> onQuit());
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu(TEXTS.menuLabelHelp());
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem(TEXTS.menuLabelHelpAbout());
        //menuItemAbout.setGraphic( new ImageView( new Image("assets/icons/about.png", 16, 16, true, true) ) );
        // Note: Accelerator F1 does not work if TextField is
        //       in focus. This is a known issue in JavaFX.
        //       https://bugs.openjdk.java.net/browse/JDK-8148857
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }


    /**
     * Prompts the player to choose team color
     */
    public void choosePlayerColor() {
        // Prompt user for new game
        Alert newGameAlert = new Alert(AlertType.CONFIRMATION);
        newGameAlert.setTitle(TEXTS.alertTitleNewGame());
        newGameAlert.setHeaderText(null);
        newGameAlert.setContentText(TEXTS.alertTitlePickColor());

        ButtonType buttonTypeWhite = new ButtonType(TEXTS.buttonLabelColorWhite());
        ButtonType buttonTypeBlack = new ButtonType(TEXTS.buttonLabelColorBlack());

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


    void generateStartDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, Boolean>> dialog = new Dialog<>();
        dialog.setTitle("Start-Dialog");
        dialog.setHeaderText("Willkommen zu ChessGUI!" + CR + CR +
                "Die GUI für DDD-Schach," + CR +
                "einem Schach-Server für" + CR +
                "verteilte Schach-Partien.");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("/assets/icons/app_icon.png").toString()));

        // Set the button types.
        ButtonType buttonTypeWhite = new ButtonType(TEXTS.buttonLabelColorWhite());
        ButtonType buttonTypeBlack = new ButtonType(TEXTS.buttonLabelColorBlack());
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeWhite, buttonTypeBlack);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        final ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton newGameBut = new RadioButton("Neues Spiel");
        newGameBut.setSelected(true);
        RadioButton joinGame = new RadioButton("Spiel mit Id");
        newGameBut.setToggleGroup(toggleGroup);
        joinGame.setToggleGroup(toggleGroup);

        TextField gameIdTextField = new TextField();
//        gameId.setPromptText("Spiel-Id");

        grid.add(newGameBut, 0, 0);
//        grid.add(newGame, 1, 0);
        grid.add(joinGame, 0, 1);
        grid.add(gameIdTextField, 1, 1);

        dialog.getDialogPane().setContent(grid);

//        // Request focus on the username field by default.
//        Platform.runLater(() -> username.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
                    return new Pair<String, Boolean>(gameIdTextField.getText(), dialogButton != buttonTypeBlack);
        });

        Optional<Pair<String, Boolean>> result = dialog.showAndWait();

        result.ifPresent(gameIdAndColor -> {
            playerIsWhite = gameIdAndColor.getValue();
            gameId = gameIdAndColor.getKey();
        });
    }
}
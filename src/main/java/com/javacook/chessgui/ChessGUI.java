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
import javafx.stage.Window;
import javafx.util.Pair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;


/**
 * Start of the Chess GUI application
 */
public class ChessGUI extends Application {

    public static void main(String[] args) {
        // Automatic VM reset, thanks to Joseph Rachmuth.
        try {
            System.out.println("Starting ChessGUI with args: " + Arrays.asList(args));
            extractAndSetServerUrl(args);
            launch(args);
            System.exit(0);
        }
        catch (Exception error) {
            error.printStackTrace();
            System.exit(0);
        }
    }

    private static void extractAndSetServerUrl(String[] args) throws URISyntaxException {
        for (String arg : args) {
            if (arg.startsWith("server=")) {
                String serverUrl = arg.substring(7).trim();
                if (!serverUrl.startsWith("http")) {
                    serverUrl = "http://" + serverUrl;
                }
                URI uri = new URI(serverUrl);
                final String pathNormed = uri.getPath().replace("/", "");
                if ("".equals(pathNormed)) {
                    serverUrl = uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + "/dddschach/api";
                }
                System.out.println("Using server: " + serverUrl);
                RestClient.SERVER_URL = serverUrl;
            }
        }
    }

    public final static String CR = System.lineSeparator();
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
        mainStage.setTitle(GUITexts.title());
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
    public Optional<ButtonType> showHint(AlertType alertType, String hint) {
        Alert alertBox = new Alert(alertType);
        alertBox.setTitle(GUITexts.alertTitleHint());
        alertBox.setHeaderText(null);
        alertBox.setContentText(hint);
        return alertBox.showAndWait();
    }


    /**
     * Shows the GameId
     */
    private void onGameId() {
        Alert newGameAlert = new Alert(AlertType.INFORMATION);
        newGameAlert.setTitle(GUITexts.alertTitleHint());
        newGameAlert.setHeaderText(GUITexts.alertLabelGameIdIs());
        TextField textField = new TextField(board.getGameId().id);
        textField.setEditable(false);
        textField.setMaxWidth(300);
        Platform.runLater(() -> textField.requestFocus());
        newGameAlert.getDialogPane().setContent(textField);
        newGameAlert.getDialogPane().setMinWidth(333);
        Optional<ButtonType> result = newGameAlert.showAndWait();
    }

    /**
     * Quits program
     */
    private void onQuit() {
        Platform.exit();
        System.exit(0);
    }


    /**
     * Display 'about' menu
     */
    public void onDisplayAbout() {
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle(GUITexts.alertTitleInfo());
        infoAlert.setHeaderText(null);

        // set window icon
        Stage alertStage = (Stage) infoAlert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("assets/icons/about.png"));

        // the graphic replaces the standard icon on the left
        //infoAlert.setGraphic( new ImageView( new Image("assets/icons/cat.png", 64, 64, true, true) ) );

        infoAlert.setContentText(GUITexts.altetTextInfo());
        infoAlert.showAndWait();
    }


    void generateStartDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, Boolean>> dialog = new Dialog<>();
        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> System.exit(0));

        dialog.setTitle(GUITexts.startDialogTitle());

        dialog.setHeaderText(GUITexts.startDialogWelcomeText());
        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("/assets/icons/app_icon.png").toString()));

        // Set the button types.
        ButtonType buttonTypeWhite = new ButtonType(GUITexts.buttonLabelColorWhite());
        ButtonType buttonTypeBlack = new ButtonType(GUITexts.buttonLabelColorBlack());
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeWhite, buttonTypeBlack);

        Node buttonWhite = dialog.getDialogPane().lookupButton(buttonTypeWhite);
        Node buttonBlack = dialog.getDialogPane().lookupButton(buttonTypeBlack);


        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 20));

        TextField gameIdTextField = new TextField();
        gameIdTextField.setMinWidth(300);

        final ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton newGameBut = new RadioButton(GUITexts.startDialogLabelNewGame());
        newGameBut.setSelected(true);
        gameIdTextField.setDisable(true);
        RadioButton joinGame = new RadioButton(GUITexts.startDialogLabelWithId());
        newGameBut.setToggleGroup(toggleGroup);
        joinGame.setToggleGroup(toggleGroup);


        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (new_toggle == newGameBut) {
                buttonWhite.setDisable(false);
                buttonBlack.setDisable(false);
                gameIdTextField.setDisable(true);
            }
            else if (new_toggle == joinGame) {
                boolean isGameIdEmpty = gameIdTextField.getText().trim().isEmpty();
                buttonWhite.setDisable(isGameIdEmpty);
                buttonBlack.setDisable(isGameIdEmpty);
                gameIdTextField.setDisable(false);
                Platform.runLater(() -> gameIdTextField.requestFocus());
            }
        });

        gameIdTextField.setOnKeyTyped(e -> {
            Platform.runLater( () -> {
                boolean isGameIdEmpty = gameIdTextField.getText().trim().isEmpty();
                buttonWhite.setDisable(isGameIdEmpty);
                buttonBlack.setDisable(isGameIdEmpty);
            });
        });

        grid.add(newGameBut, 0, 0);
        grid.add(joinGame, 0, 1);
        grid.add(gameIdTextField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->
                new Pair<>(gameIdTextField.getText(), dialogButton != buttonTypeBlack)
        );

        Optional<Pair<String, Boolean>> result = dialog.showAndWait();

        result.ifPresent(gameIdAndColor -> {
            playerIsWhite = gameIdAndColor.getValue();
            gameId = gameIdAndColor.getKey();
        });
    }


    /**
     * Generate main menu bar
     */
    private MenuBar generateMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu gameMenu = new Menu(GUITexts.menuLabelGame());
        menuBar.getMenus().add(gameMenu);

        MenuItem menuItemGameId = new MenuItem(GUITexts.menuLabelGameId());
        menuItemGameId.setOnAction(e -> onGameId());
        menuItemGameId.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));
        gameMenu.getItems().add(menuItemGameId);

        MenuItem menuItemQuit = new MenuItem(GUITexts.menuLabelGameQuit());
        menuItemQuit.setOnAction(e -> onQuit());
        menuItemQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        gameMenu.getItems().add(menuItemQuit);

        Menu menuHelp = new Menu(GUITexts.menuLabelHelp());
        menuBar.getMenus().add(menuHelp);

        MenuItem menuItemAbout = new MenuItem(GUITexts.menuLabelHelpAbout());
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        menuItemAbout.setOnAction(e -> onDisplayAbout());
        menuHelp.getItems().add(menuItemAbout);

        return menuBar;
    }

}
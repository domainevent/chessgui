package com.javacook.chessgui;

import com.javacook.chessgui.exception.MoveException;
import com.javacook.chessgui.exception.NotFoundException;
import com.javacook.chessgui.exception.RestException;
import com.javacook.chessgui.exception.TimeoutException;
import com.javacook.chessgui.figure.*;
import com.javacook.dddchess.domain.ErrorCode;
import com.javacook.dddchess.domain.GameIdValueObject;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Map;

import static com.javacook.chessgui.RestClient.CLIENT;
import static com.javacook.chessgui.RestClient.SERVER_URL;
import static javafx.scene.control.Alert.AlertType.*;


public class ChessBoard extends GridPane {

    private final boolean playerIsWhite;
    private Space[][] spaces = new Space[8][8];
    private GameIdValueObject gameId;

    /**
     * last clicked space
     */
    private Space activeSpace = null;

    public GameIdValueObject getGameId() {
        return gameId;
    }

    /**
     * Constructor
     * @param chessGUI
     * @param playerIsWhite
     */
    public ChessBoard(ChessGUI chessGUI, boolean playerIsWhite) {
        this.playerIsWhite = playerIsWhite;
        // initialize 8x8 array of spaces
        for (int x = 0; x < spaces[0].length; x++) {
            for (int y = 0; y < spaces[1].length; y++) {
                boolean light = ((x + y) % 2 != 0); // checkerboard space colors
                spaces[x][y] = new Space(light, x, y);

                // if white, add Spaces so ensured bottom left is 0,0
                // if Black, add Spaces so ensured bottom left is 7,7
                if (playerIsWhite) {
                    add(spaces[x][y], x, 7 - y);
                }
                else {
                    add(spaces[x][y], 7 - x, y);
                }

                // Gets values into event handler
                final int xVal = x;
                final int yVal = y;
                // runs things that happen when a space is clicked
                spaces[x][y].setOnAction(e -> {
                    try {
                        onSpaceClick(xVal, yVal);
                    }
                    catch (TimeoutException e1) {
                        chessGUI.showHint(INFORMATION, "A server timeout has occured.");
                    }
                    catch (MoveException e1) {
                        chessGUI.showHint(Alert.AlertType.WARNING, "Invalid Move: " + e1.getMessage());
                    }
                    catch (ConnectException e1) {
                        chessGUI.showHint(WARNING, "No connection to server:" + System.lineSeparator() + SERVER_URL);
                    }
                    catch (Throwable e1) {
                        chessGUI.showHint(ERROR, "Unknown Error: " + e1);
                    }
                });
            }
        }

        try {
            postNewGame(playerIsWhite);
        }
        catch (ConnectException e1) {
            chessGUI.showHint(WARNING, "No connection to server:" + System.lineSeparator() + SERVER_URL
                    + System.lineSeparator() + "Please try again later...");
            System.exit(-3);
        }
        catch (NotFoundException | UnknownHostException e1) {
            chessGUI.showHint(WARNING, "Communication error. Invalid URI:" + System.lineSeparator() + e1.getMessage()
                    + System.lineSeparator() + "Please contact the administrator javacook@gmx.de");
            System.exit(-2);
        }
        catch (Throwable e1) {
            chessGUI.showHint(ERROR, "Unknown error: " + e1
                    + System.lineSeparator() + "Please contact the administrator javacook@gmx.de");
            System.exit(-1);
        }

        //put pieces in start positions
        this.defineStartPositions();

        new Thread(new UpdateBoardTask(this)).start();
    }


    /**
     * Use this to get a space, using GridPane methods will (I think) cause color problems
     */
    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }


    public void setActiveSpace(Space s) {
        // Remove style from old active space
        if (activeSpace != null) {
            activeSpace.getStyleClass().removeAll("chess-space-active");
        }

        activeSpace = s;

        // Add style to new active space
        if (activeSpace != null) {
            activeSpace.getStyleClass().add("chess-space-active");
        }
    }


    /**
     * Use this to get a space, using GridPane methods will (I think) cause color problems
     */
    public Space getActiveSpace() {
        return this.activeSpace;
    }


    /**
     * Define the starting piece positions
     */
    public void defineStartPositions() {
        // white pieces
        spaces[0][0].setPiece(new Rook(true));
        spaces[1][0].setPiece(new Knight(true));
        spaces[2][0].setPiece(new Bishop(true));
        spaces[3][0].setPiece(new Queen(true));
        spaces[4][0].setPiece(new King(true));
        spaces[5][0].setPiece(new Bishop(true));
        spaces[6][0].setPiece(new Knight(true));
        spaces[7][0].setPiece(new Rook(true));

        for (int i = 0; i < spaces[0].length; i++) {
            spaces[i][1].setPiece(new Pawn(true));
        }

        // black pieces
        spaces[0][7].setPiece(new Rook(false));
        spaces[1][7].setPiece(new Knight(false));
        spaces[2][7].setPiece(new Bishop(false));
        spaces[3][7].setPiece(new Queen(false));
        spaces[4][7].setPiece(new King(false));
        spaces[5][7].setPiece(new Bishop(false));
        spaces[6][7].setPiece(new Knight(false));
        spaces[7][7].setPiece(new Rook(false));

        for (int i = 0; i < spaces[0].length; i++) {
            spaces[i][6].setPiece(new Pawn(false));
        }
    }


    /**
     * Is called when someone clicks on a space (field), not when he uses the space bar
     */
    public void onSpaceClick(int x, int y) throws Throwable {
        Space clickedSpace = spaces[x][y];
        // if piece is selected && user didn't click on allied piece
        if (activeSpace != null &&
                activeSpace.getPiece() != null &&
                clickedSpace.getPieceColor() != activeSpace.getPieceColor()) {

            MoveInfo p;
            p = new MoveInfo(activeSpace.getX(), activeSpace.getY(), x, y);

            try {
                this.processMove(p);
            }
            finally {
                //decouples space from space on board
                this.setActiveSpace(null);
            }
        }
        else {
            final Piece piece = spaces[x][y].getPiece();
            // if there's a piece on the selected square when no active square
            // and the color of the piece equals the player color
            if (piece != null && piece.isWhite() == playerIsWhite) {
                //make active square clicked square
                this.setActiveSpace(spaces[x][y]);
            }
        }
    }


    private void postNewGame(boolean isPlayerWhite) throws Throwable {
        System.out.println("New game, player plays " + (isPlayerWhite? "white" : "black"));

        WebTarget webTarget = CLIENT.target(SERVER_URL).path("games");
        Form form = new Form();
        form.param("note", "A new chess game started by the client 'chessgui'");

        try {
            final Response response = webTarget
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

            switch (response.getStatus()) {
                case 200: gameId = response.readEntity(GameIdValueObject.class);
                    break;
                case 404:
                    throw new NotFoundException(webTarget.getUri());
                default:
                    throw new RestException("Unexpected response: " + response);
            }
        }
        catch (ProcessingException e) {
            Throwable cause = e;
            while (cause.getCause() != null) cause = cause.getCause();
            throw cause;
        }
    }


    /**
     * Process a move after it has been made by a player
     */
    private void processMove(MoveInfo p) throws Throwable {
        System.out.println("Move: " + p);

        WebTarget webTarget = CLIENT.target(SERVER_URL)
                .path("games").path(gameId.id).path("moves");

        Form form = new Form();
        form.param("move", p.toString());
        try {

            final Response response = webTarget
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

            final Map<String, Object> json =
                    response.readEntity(new GenericType<Map<String, Object>>() {});

            System.out.println("Response" + json);
            boolean success = json.containsKey("move index");

            switch (response.getStatus()) {
                case 200:
                case 201: {
                    Space oldSpace = spaces[p.getOldX()][p.getOldY()];
                    Space newSpace = spaces[p.getNewX()][p.getNewY()];
                    newSpace.setPiece(oldSpace.releasePiece());
                    System.out.println("Location: " + response.getHeaderString("Location"));
                    break;
                }
                case 404:
                    throw new NotFoundException(webTarget.getUri());
                case 422:
                case 503: {
                    final String errorCodeKey = (String) json.get(ErrorCode.ERROR_CODE_KEY);
                    if (errorCodeKey == null) {
                        throw new RestException("Missing JSON attribute " + ErrorCode.ERROR_CODE_KEY);
                    }
                    ErrorCode errorCode = null;
                    try {
                        errorCode = ErrorCode.valueOf(errorCodeKey);
                    }
                    catch (IllegalArgumentException e) {
                        throw new RestException("Unknown error code: " + errorCode);
                    }
                    final String errorDescr = (String) json.get(errorCodeKey);
                    System.out.println("Status code " + response.getStatus() + ", message: " +
                            (errorDescr == null? "no further information" : errorDescr));
                    switch (errorCode) {
                        case INVALID_MOVE:
                            throw new MoveException(errorDescr);
                        case TIMEOUT:
                            throw new TimeoutException();
                        default:
                            throw new RestException("Unexpected error code: " + errorCode);
                    }
                }
                default:
                    throw new RestException("Unexpected response: " + response);
            }
        } catch (ProcessingException e) {
            Throwable cause = e;
            while (cause.getCause() != null) cause = cause.getCause();
            throw cause;
        }
    }

}

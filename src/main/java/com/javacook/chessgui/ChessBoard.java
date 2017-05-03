package com.javacook.chessgui;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import javafx.scene.layout.GridPane;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


public class ChessBoard extends GridPane {

    private final ChessGUI chessGUI;
    public Space[][] spaces = new Space[8][8];
    // const

    /**
     * last clicked space
     */
    public Space activeSpace = null;


    public ChessBoard(ChessGUI chessGUI, boolean playerIsWhite) {
        this.chessGUI = chessGUI;

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
                    catch (MoveException e1) {
                        chessGUI.showHint(e1.getMessage());
                    }
                });
            }
        }

        //put pieces in start positions
        this.defineStartPositions();
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
     * prints location of all pieces on the board
     */
    public String toString() {
        // TODO: Unfinished
        String pieceList = "";
        for (int i = 0; i < spaces[0].length; i++) {
            for (int j = 0; j < spaces[1].length; j++) {
                if (spaces[i][j].isOccupied()) {
                    //pieceList += spaces[i][j].toString();
                }
            }
        }
        return pieceList;
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
     *
     * @param x
     * @param y
     */
    public void onSpaceClick(int x, int y) throws MoveException {
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
            //if there's a piece on the selected square when no active square
            if (spaces[x][y].getPiece() != null) {
                //make active square clicked square
                this.setActiveSpace(spaces[x][y]);
            }
        }
    }


    /**
     * Process a move after it has been made by a player
     */
    protected void processMove(MoveInfo p) throws MoveException {
        System.out.println("Move: " + p);

        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        WebTarget webTarget = client
                .target("http://localhost:8080/dddtutorial/chessgame")
                .path("move");
        Form form = new Form();
        form.param("move", p.toString());
        final Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        final Map<String, Object> json =
                response.readEntity(new GenericType<Map<String, Object>>() {});

        System.out.println("Response" + json);
        boolean success = json.containsKey("move index");

        switch (response.getStatus()) {
            case 200:
            case 201:
                Space oldSpace = spaces[p.getOldX()][p.getOldY()];
                Space newSpace = spaces[p.getNewX()][p.getNewY()];
                newSpace.setPiece(oldSpace.releasePiece());
                System.out.println("Location: " + response.getHeaderString("Location"));
                break;
            case 422:
                final String errorDescr = (String) json.get("invalid move");
                System.out.println("Status code 422: " + errorDescr);
                throw new MoveException(errorDescr);
            default:
                throw new MoveException(response.toString());
        }
    }

}

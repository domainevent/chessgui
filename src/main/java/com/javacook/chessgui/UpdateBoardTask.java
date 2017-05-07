package com.javacook.chessgui;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.javacook.chessgui.figure.*;
import com.javacook.dddchess.domain.ChessBoardValueObject;
import com.javacook.dddchess.domain.FigureValueObject;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

import static com.javacook.chessgui.RestClient.CLIENT;
import static com.javacook.chessgui.RestClient.SERVER_URL;
import static com.javacook.dddchess.domain.FigureValueObject.ColorEnum.WHITE;


/**
 * For polling the current chess board from the server
 */
public class UpdateBoardTask extends Task<Object> {

    private final Space[][] spaces;

    public UpdateBoardTask(Space[][] spaces) {
        this.spaces = spaces;
    }

    @Override
    protected Object call() throws Exception {
        EntityTag entityTag = null;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                WebTarget webTarget = CLIENT.target(SERVER_URL).path("board");
                System.out.println("Update call: " + webTarget.getUri());
                final Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
                if (entityTag != null) {
                    builder.header("If-None-Match", '"'+entityTag.getValue()+'"');
                }
                final Response response = builder.get();
                entityTag = response.getEntityTag();

                System.out.println("Status: " + response.getStatus() + ", ETag: " + entityTag.getValue());
                switch (response.getStatus()) {
                    case 200:
                        final ChessBoardValueObject chessBoard = response.readEntity(ChessBoardValueObject.class);
                        // System.out.println(chessBoard);
                        Platform.runLater(() -> {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    final FigureValueObject figure = chessBoard.board[i][j];
                                    if (figure == null) {
                                        spaces[i][j].setPiece(null);
                                    }
                                    else {
                                        boolean isWhite = figure.color == WHITE;
                                        switch (figure.figure) {
                                            case BISHOP:
                                                spaces[i][j].setPiece(new Bishop(isWhite)); break;
                                            case QUEEN:
                                                spaces[i][j].setPiece(new Queen(isWhite)); break;
                                            case KING:
                                                spaces[i][j].setPiece(new King(isWhite)); break;
                                            case ROOK:
                                                spaces[i][j].setPiece(new Rook(isWhite)); break;
                                            case KNIGHT:
                                                spaces[i][j].setPiece(new Knight(isWhite)); break;
                                            case PAWN:
                                                spaces[i][j].setPiece(new Pawn(isWhite)); break;
                                        }
                                    }// if
                                }// for
                            }// for
                        });
                        break;

                    case 304:
                        System.out.println("Chess board not changed");
                        break;

                    default:
                        System.out.println("Unexpected status code: " + response.getStatus());
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

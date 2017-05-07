package com.javacook.chessgui;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.javacook.chessgui.figure.*;
import com.javacook.dddchess.domain.ChessBoardValueObject;
import com.javacook.dddchess.domain.FigureValueObject;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

import static com.javacook.dddchess.domain.FigureValueObject.ColorEnum.WHITE;


/**
 * Created by vollmer on 05.05.17.
 */
public class UpdateBoardTask extends Task<Object> {

    public final static String SERVER_URL = "http://localhost:8080/dddtutorial/chessgames";
    public final static Client CLIENT = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    private final Space[][] spaces;


    public UpdateBoardTask(Space[][] spaces) {
        this.spaces = spaces;
    }


    @Override
    protected Object call() throws Exception {
        while (true) {
            try {
                WebTarget webTarget = CLIENT.target(SERVER_URL).path("board");
                System.out.println("Update call: " + webTarget.getUri());
                final Response response = webTarget
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                System.out.println(response.getStatus());
                final ChessBoardValueObject chessBoard = response.readEntity(ChessBoardValueObject.class);
                // System.out.println(chessBoard);
                TimeUnit.SECONDS.sleep(1);

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
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

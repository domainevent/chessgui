package com.javacook.chessgui;

import com.javacook.chessgui.figure.*;
import com.javacook.dddchess.domain.ChessBoardValueObject;
import com.javacook.dddchess.domain.FigureValueObject;
import javafx.application.Platform;
import javafx.concurrent.Task;

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
 * Polling the current chess board from the server
 */
public class UpdateBoardTask extends Task<Object> {

    private final ChessBoard chessBoardGui;

    public UpdateBoardTask(ChessBoard chessBoard) {
        this.chessBoardGui = chessBoard;
    }

    @Override
    protected Object call() throws Exception {
        EntityTag entityTag = null;
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
                WebTarget webTarget = CLIENT.target(SERVER_URL)
                        .path("games")
                        .path(chessBoardGui.getGameId().id)
                        .path("board");

                System.out.println("Update call: " + webTarget.getUri());
                final Invocation.Builder builder = webTarget.request(MediaType.APPLICATION_JSON);
                if (entityTag != null) {
                    builder.header("If-None-Match", '"'+entityTag.getValue()+'"');
                }
                final Response response = builder.get();
                entityTag = response.getEntityTag();
                String eTagValue = (entityTag == null)? "<not present>" : entityTag.getValue();

                System.out.println("Status: " + response.getStatus() + ", ETag: " + eTagValue);
                switch (response.getStatus()) {
                    case 200:
                        final ChessBoardValueObject chessBoard = response.readEntity(ChessBoardValueObject.class);
                        // System.out.println(chessBoard);
                        Platform.runLater(() -> {
                            for (int i = 0; i < 8; i++) {
                                for (int j = 0; j < 8; j++) {
                                    final FigureValueObject figure = chessBoard.board[i][j];
                                    if (figure == null) {
                                        chessBoardGui.getSpace(i,j).setPiece(null);
                                    }
                                    else {
                                        boolean isWhite = figure.color == WHITE;
                                        switch (figure.figure) {
                                            case BISHOP:
                                                chessBoardGui.getSpace(i,j).setPiece(new Bishop(isWhite)); break;
                                            case QUEEN:
                                                chessBoardGui.getSpace(i,j).setPiece(new Queen(isWhite)); break;
                                            case KING:
                                                chessBoardGui.getSpace(i,j).setPiece(new King(isWhite)); break;
                                            case ROOK:
                                                chessBoardGui.getSpace(i,j).setPiece(new Rook(isWhite)); break;
                                            case KNIGHT:
                                                chessBoardGui.getSpace(i,j).setPiece(new Knight(isWhite)); break;
                                            case PAWN:
                                                chessBoardGui.getSpace(i,j).setPiece(new Pawn(isWhite)); break;
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
                // TODO vollmerj: hier evtl. eine Fehlerbox?
                // System.err.println(e);
                e.printStackTrace();
            }
        }
    }
}

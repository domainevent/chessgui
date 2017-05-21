package com.javacook.dddchess.domain;


/**
 * Created by vollmer on 02.05.17.
 */
public enum ErrorCode {

    TIMEOUT,
    INVALID_MOVE,
    INVALID_GAMEID;

    public final static String ERROR_CODE_KEY = "error code";
}

package com.javacook.chessgui;

/**
 * Created by vollmer on 12.05.17.
 */
public class GUITexts {

    public final static String CR = System.lineSeparator();

    public static String title() {
        return "DDD-Schach"; // "Chess Game"
    }

    // Menues

    public static String menuLabelGame() {
        return "Spiel"; // Game
    }

    public static String menuLabelGameQuit() {
        return "Beenden"; // Quit
    }

    public static String menuLabelHelp() {
        return "Hilfe"; // "Help"
    }

    public static String menuLabelGameId() {
        return "Spiel-ID"; // "Game ID"
    }

    public static String menuLabelHelpAbout() {
        return "Über";
    }

    public static String startDialogTitle() {
        return "DDD-Schach";
    }

    public static String startDialogLabelNewGame() {
        return "Neues Spiel"; // "Start new game"
    }

    public static String startDialogLabelWithId() {
        return "Spiel mit ID"; // "Game with id"
    }

    public static String startDialogWelcomeText() {
        return "Willkommen zu ChessGUI!" + CR + CR +
                "Die GUI für DDD-Schach," + CR +
                "einem Schach-Server für" + CR +
                "verteilte Schach-Partien.";
    }


    public static String buttonLabelColorWhite() {
        return "weiß";
    }

    public static String buttonLabelColorBlack() {
        return "schwarz";
    }


    public static String alertTitleHint() {
        return "Hinweis"; // "Hint"
    }


    public static String alertTitleInfo() {
        return "Über ChessGUI"; // "About this program"
    }


    public static String altetTextInfo() {
        return "Entwickelt ursprünglich von M. Sirotin, S. Vascellaro" + CR + CR +
                "Design der Schach-Figuren von Colin M.L. Burnett" + CR + CR +
                "Angepasst für DDD-Schach von Jörg Vollmer," + CR +
                "E-Mail: info@informatikbuero.com";
    }

    public static String alertLabelGameIdIs() {
        return "Die Spiel-Id lautet";
    }


    // Fehlermeldungen
    public static String noServerConnection() {
        return "Keine Verbindung zum Server"; // No connection to server
    }

    public static String unknownError() {
        return "Unbekannter Fehler"; // "Unknown error
    }

    public static String communicationError() {
        // Communication error. Invalid URI
        return "Kommunikatioinsfehler, ungültige URI";
    }

    public static String notFound() {
        return "Eine Partie mit folgender Id ist nicht bekannt";
    }

    public static String serverTimeout() {
        return "Ein Server-Timeout ist eingetreten"; // A server timeout has occured
    }

    public static String tryAgainLater() {
        return "Versuchen Sie es später noch einmal..."; // Please try again later...
    }

    public static String tryAgain() {
        return "Versuchen Sie es noch einmal..."; // Please try again later...
    }

    public static String contactAdmin() {
        // "Please contact the administrator javacook@gmx.de"
        return "Bitte kontaktieren Sie den Administrator javacook@gmx.de";
    }

    public static String invalidMove() {
        return "Ungültiger Zug"; // Invalid Move
    }

    public static String invalidGameId() {
        return "Ungültige SpielId"; // Invalid Game IdMove
    }


    public static String moveHint(String code) {
        switch (code) {
            case "DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN":
                return "Der andere Spieler ist am Zug.";

            case "STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN":
                return "Es existiert keine Spielfigur auf den Feld.";

            case "ZUG_STRECKE_MUSS_FREI_SEIN":
                return "Es befinden sich Spielfiguren auf dem Zug-Weg.";

            case "EIGENE_FIGUREN_LASSEN_SICH_NICHT_SCHLAGEN":
                 return "Eine eigene Figur lässt sich nicht schlagen.";

            case "KOENIG_KANN_NICHT_GESCHLAGEN_WERDEN":
                 return "Der König kann nicht geschlagen werden";

            case "LAUEFER_ZIEHT_DIAGONAL":
                return "Der Läufer darf nur diagonale Strecken ziehen.";

            case "TURM_ZIEHT_GERADE":
                return "Der Turm darf nur geradlinig ziehen.";

            case "DAME_ZIEHT_GERADE_ODER_DIAGONAL":
                return "Die Dame darf nur geradlinig oder diagonal ziehen.";

            case "KOENIG_ZIEHT_NUR_EIN_FELD":
                return "Der König darf nur ein Feld ziehen.";

            case "SPRINGER_ZIEHT_EINEN_WINKEL":
                return "Der Springer darf nur eine 2x1-Haken ziehen.";

            case "BAUER_SCHLAEGT_NUR_SCHRAEG":
                return "Der Bauer darf nur ein Feld schräg nach vorne schlagen.";

            case "BAUER_ZIEHT_NUR_GEREADE_FALLS_ER_NICHT_SCHLAEGT":
                return "Der Bauer darf nur nach vorne rücken, falls er keine Figur schlägt.";

            case "BAUER_ZIEHT_EIN_FELD_VORWAERTS_AUSSER_AM_ANFANG_ZWEI":
                return "Der Bauer darf nur ein Feld (bzw. zwei zu Beginn) nach vorne rücken.";

            case "BAUER_ZIEHT_EIN_FELD_VORWAERTS_FALLS_ER_SCHLAEGT":
                return "Der Bauer muss beim Schlagen ein Feld nach vorne rücken.";

            case "BAUER_ZIEHT_MAXIMAL_EIN_FELD_SEITWAERTS":
                return "Der Bauer darf (für das Schlagen) maximal ein Feld seitwärts ziehen.";

            case "KOENIG_STEHT_IM_SCHACH":
                return "Der König steht im Schach.";

            default: return code;
        }
    }

}

package com.javacook.chessgui;

/**
 * Created by vollmer on 12.05.17.
 */
public class GUITexts {

    public final static String CR = System.lineSeparator();

    public String title() {
        return "DDD-Schach"; // "Chess Game"
    }

    // Menues

    public String menuLabelGame() {
        return "Spiel"; // Game
    }

    public String menuLabelGameQuit() {
        return "Beenden"; // Quit
    }

    public String menuLabelHelp() {
        return "Hilfe"; // "Help"
    }

    public String menuLabelGameId() {
        return "Spiel-Id"; // "Help"
    }

    public String menuLabelHelpAbout() {
        return "Über";
    }


    public String startDialogTitle() {
        return "DDD-Schach";
    }

    public String startDialogLabelNewGame() {
        return "Neues Spiel"; // "Start new game"
    }

    public String startDialogLabelWithId() {
        return "Spiel mit Id"; // "Game with id"
    }

    public String startDialogWelcomeText() {
        return "Willkommen zu ChessGUI!" + CR + CR +
                "Die GUI für DDD-Schach," + CR +
                "einem Schach-Server für" + CR +
                "verteilte Schach-Partien.";
    }


    public String buttonLabelColorWhite() {
        return "weiß";
    }

    public String buttonLabelColorBlack() {
        return "schwarz";
    }


    public String alertTitleHint() {
        return "Hinweis"; // "Hint"
    }


    public String alertTitleInfo() {
        return "Über ChessGUI"; // "About this program"
    }


    public String altetTextInfo() {
        return "Entwickelt ursprünglich von M. Sirotin, S. Vascellaro" + CR + CR +
                "Design der Schach-Figuren von Colin M.L. Burnett" + CR + CR +
                "Angepasst für DDD-Schach von Jörg Vollmer," + CR +
                "E-Mail: info@informatikbuero.com";
    }

    public String alertLabelGameIdIs() {
        return "Die Spiel-Id lautet";
    }


}

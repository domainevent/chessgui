package com.javacook.dddschach.restclient;

import com.javacook.dddschach.domain.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;


/**
 * Schnittstelle zu com.javacook.dddschach.
 */
public interface SchachpartieApi {


    /**
     * Erzeugt ein neues Spiel
     *
     * @param request eine beliebiger Vermerk zu dieser Partie, z.B. Spieler, Ort, etc.
     * @return eine weltweit eindeutige Id
     * @throws Exception falls kein neues Spiel erzeugt werden konnte (technische Probleme)
     */
    NeuesSpielResponse neuesSpiel(@Valid @NotNull NeuesSpielRequest request);


    /**
     * Fuehrt einen Halbzug in der Schachpartie mit der Id <code>spielId</code> aus. Der Halbzug
     * ist nur dann gültig, falls sich auf der Startpositon des Halbzuges eine Figur befindet
     * und falls diese Figur die korrekte Farbe hat. Schwarz und Weiß müssen sich abwechseln;
     * beginnend mit Weiß.
     * Befindet sich auf der Zielposition bereits eine Spielfigur, wird diese geschlagen.
     *
     * Hinweis: Eine komplette Validation nach den Schachregeln soll im Rahmen des DDD-Workshops
     * nicht implementiert werden.
     *
     * @param request (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @return der Index des Zuges (beginnend mit 1)
     * @throws UngueltigerHalbzugException falls der Halbzug ungueltig ist
     * @throws UngueltigeSpielIdException falls es keine Partie zu <code>spielId</code> gibt
     * @throws IOException bei sonstigen (technischen) Problemen
     */
    FuehreHalbzugAusResponse fuehreHalbzugAus(@Valid @NotNull FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException;


    /**
     * Liefert das aktuelle Schachbrett zum Spiel mit der Id <code>spielId</code>
     *
     * @param request
     * @return das aktuelle Schachbrett
     * @throws UngueltigeSpielIdException falls es keine Partie zu <code>spielId</code> gibt
     * @throws IOException bei sonstigen (technischen) Problemen
     */
    AktuellesSpielbrettResponse aktuellesSpielbrett(@Valid @NotNull AktuellesSpielbrettRequest request) throws UngueltigeSpielIdException;

}
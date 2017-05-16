# ChessGUI

### Voraussetzungen 
Ein laufender Schach-Server dddschach (im Rahmen einer DDD-Schulung zu 
vervollständigen) oder dddchess (Prototyp). Wird dddschach verwendet 
(Dokumentation befindet sich wie hier im Projektordner in README.md), 
muss nichts gesondert konfiguriert werden. 

### Starten
Die GUI lässt sich mit <tt>mvn compile exec:java</tt> direkt starten.

**Hinweis:** ChessGUI verbindet sich per Default mit dem 
**dddschach**-Server unter <tt>http://localhost:8080/dddschach/api</tt>. 
Dies lässt sich ggf. (für dddchess) anpassen mit 
<tt>mvn exec:java -Dserver=localhost:8080/dddchess/api</tt>. 
 
#### Wie paaren sich zwei Spieler?
Beim Start von ChessGUI wird gefragt, ob es sich um eine neue Partie handelt 
oder ob man sich über die Eingabe einer Spiel-ID "paaren" möchten. 
Der Initiator einer neuen Partie wählt hier "Neues Spiel". Anschließend
ermittelt er über den Menüpunkt <it>Spiel -> Spiel-ID</it> die eindeutige ID
dieser Partie und teilt sie seinem Mitspieler mit. Dieser kann die ID dann bei
beim Start seiner ChessGUI eingeben. Anschließend synchronisieren die 
beiden ChessGUIs in regelmäßigen Abständen ihre Stellungen.


Viel Spaß...


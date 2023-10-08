# TicTacToe in Java

## Probleme:

### 1. Spielfortschritt-Anzeige:
- Aktuelles Verhalten: Während des Spiels ändert sich der Text des Start-Buttons dynamisch zu "In progress...", abhängig von verschiedenen Funktionsaufrufen.
- Gewünschtes Verhalten: Eine konsistente Hintergrundprüfung sollte sicherstellen, dass die Schaltfläche "In progress..." anzeigt, solange das Spiel aktiv ist. Nach Beendigung des Spiels (Sieg, Unentschieden usw.) sollte die Schaltfläche wieder "Start" anzeigen, bis die nächste Spielsitzung vom Benutzer initiiert wird.

### 2. Auswahl des Schwierigkeitsgrads für PC-Spieler:
- Problem A: Die Schaltfläche zur Auswahl des Schwierigkeitsgrads ist ausgeblendet, wenn nicht die Option "PC" ausgewählt ist.
  - Lösung: Zeigen Sie die Schaltfläche immer an. Wenn die Option "PC" nicht ausgewählt ist, grauen Sie sie aus. Wenn der Benutzer versucht, damit zu interagieren, zeigen Sie eine Warnung an: "Die Schwierigkeitseinstellungen sind nur für die Spieleroption 'PC' verfügbar".
- Problem B: Das aktuelle Schwierigkeitsauswahlfenster ist nicht benutzerfreundlich.
  - Lösung: Überarbeiten Sie die Benutzeroberfläche der Schwierigkeitsauswahl so, dass sie der Spieler-Combobox ähnelt und nicht in einem separaten Fenster geöffnet wird.

### 3. Verbesserung der Spielalgorithmen:
- Aktuelles Verhalten: Der "leichte" Schwierigkeitsgrad schneidet aufgrund der zufälligen Zugauswahl oft besser ab als der "schwere" Schwierigkeitsgrad, während der "schwere" Schwierigkeitsgrad vorhersehbar ist und in der oberen linken Ecke beginnt, ohne die möglichen Gewinnzüge des Gegners zu berücksichtigen.
- Gewünschtes Verhalten: Verbessern Sie die Algorithmen für "mittel" und "schwer". Der "schwere" Schwierigkeitsgrad sollte besonders herausfordernd sein, da er die potenziellen Gewinnzüge des Spielers voraussieht und die Strategie auf der Grundlage der vorhergesagten zukünftigen Züge entwickelt.

### 4. PC vs. PC Spiel:
- Aktuelles Verhalten: Wenn sowohl Spieler-1 als auch Spieler-2 auf "PC" eingestellt sind, erfolgt nur ein Zug, bevor das Spiel zum Stillstand kommt.
- Gewünschtes Verhalten: Im Modus "PC gegen PC" sollte das Spiel alle 0,5 Sekunden zwischen Spieler-1 und Spieler-2 wechseln, wobei die Züge entsprechend der jeweiligen Schwierigkeitseinstellung (leicht, mittel, schwer) ausgeführt werden.
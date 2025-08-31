# Minesweeper

## Aufgabe

- Java-Swing basiertes Minesweeper mit OOP-Prinzipien entwickeln
- Spielmenü implementieren
- Anzahl der Felder (n*n) und Anzahl von Bomben können vom User definiert werden
- Minesweeper-Spielmechanismen werden umgesetzt: User kann mit Linksklick aufdecken und mit Rechtsklick ein Feld mit einer Flagge markieren.
- Ein aufgedecktes Feld zeigt die Anzahl der benachbarten Bomben an, oder eine Mine (Spiel endet dadurch)
- Wenn ein leeres Feld aufgedeckt wird, werden alle(?) anliegenden Felder mit aufgedeckt
- klären, ob diagonal anliegende Felder auch als Nachbarn zählen sollten oder nicht (Ja: vereinfacht das Spiel, nein: höhere Komplexität)
- Spielstand, verbrauchte Flaggen usw. anzeigen
- Menüpunkte mit Java-Swing umsetzen (Neustart, Zurück zum Menü, beenden)

## Optionale Aufgaben:

- Lokaler Multiplayer kann umgesetzt werden. Die selbe Map wird an alle Spielenden
gegeben. Wer am schnellsten ist, gewinnt.
- Multiplayer-Option in der Menüleiste einbauen
- Spielstand speichern und laden
- Bestenliste mit Namen, Anzahl Feldern,Bomben und Zeit (lokal speichern)

## Vorgaben

- Eclipse 2023-03
- Java 11
- Keine externen Bibliotheken

## Git Workflow

Wir nutzen [Trunk-based Development](https://www.atlassian.com/continuous-delivery/continuous-integration/trunk-based-development).

### Regeln

- Der `main` Branch ist immer stabil und kann ohne Probleme kompiliert und ausgeführt werden.
- Wenn Issues bearbeitet werden, muss ein Feature Branch erstellt werden. Dort findet die Bearbeitung des jeweiligen Issues statt. Der Branch wird in `main` gemerged, wenn das Feature abgeschlossen ist.
- Es sollte niemals direkt auf den `main` Branch committed werden. Updates sollen nur über Merge Requests eingereicht werden.
- Merge Requests werden durch eine CI/CD Pipeline geprüft, sodass nur Code gemerged wird, der auch kompiliert werden kann. Wenn die Pipeline fehlschlägt, kann die MR nicht gemerged werden.

### Umsetzung

- Für alle neuen Features oder Bugs muss ein Issue angelegt werden
- Aus dem Issue heraus kann ein Branch erstellt werden
  - Dropdown Pfeil neben "Create merge request" -> Create branch
  - Als source Branch sollte `main` genutzt werden
- Lokal muss das Repo mit `git pull` aktualisiert und der Branch mit `git checkout <branchname>` ausgewählt werden
- Dann kann lokal an dem Branch gearbeitet werden
- Es sollte immer committed werden, wenn eine Arbeitseinheit fertiggestellt ist.
  - Die Definition von "Arbeitseinheit" ist immer unterschiedlich und subjektiv.
  - Commits sind wie Backups, zu denen man zurückspringen kann, d. h. man sollte immer dann committen, wenn man den geschriebenen Code nicht verlieren will
- Wenn das Feature/der Bug abgeschlossen ist, kann über das GitLab UI eine Merge Request erstellt werden
  - Meistens zeigt GitLab selbst schon eine Meldung mit einem "Merge Request erstellen" Button an
  - Sonst kann in der linken Seitenleiste über "Code" -> "Branches" eine Merge Request für den entsprechenden Branch erstellt werden
- Aus Zeitgründen machen wir keine Code Reviews. Alle Mitarbeitenden sind für ihre eigene Code Qualität verantwortlich.
  - Es kann auch ein Issue erstellt werden, mit dem Hinweis, dass etwas nochmal überarbeitet werden muss
  - Im Code kann auch ein Kommentar mit `// TODO: ` hinterlassen werden
- Wenn die CI Pipeline erfolgreich durchgelaufen ist, kann der Branch gemerged werden
  - Der Prozess kann auch mit dem "Set to auto-merge" Button automatisiert werden

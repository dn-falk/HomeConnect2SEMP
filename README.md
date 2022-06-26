# Übersicht
- [Changelog](https://github.com/dennis-nikolas-falk/HomeConnect2SEMP/blob/main/CHANGELOG.md)

## Release
### Aktuell
- [Download Release v.1.1.0](https://github.com/dennis-nikolas-falk/HomeConnect2SEMP/releases/download/v1.1.0/HomeConnect2SEMP-1.1.0.war)
### Archiv
- [Download Release v.1.0.0](https://github.com/dennis-nikolas-falk/HomeConnect2SEMP/releases/download/v1.0.0/HomeConnect2SEMP-1.0.0.war)

# Funktion
HomeConnect2SEMP stellt eine Verbindung zwischen Home Connect Haushaltsgeräten und
einem SMA-Energiemanagementsystem (z. B. Home Manager 2.0) her. Dadurch kann z. B. eine Waschmaschine bei der
Energieplanung des Energiemanagers berücksichtigt werden. Das Gerät wird dann zum optimalen Zeitpunkt gestartet.

# Installation
- Java wird benötigt (z.B. Temurin Java 17 (https://adoptium.net))
- Anwendung starten mit `java -jar HomeConnect2SEMP-1.1.0.war`
- http://localhost:8080 im Browser aufrufen
- Login: `user` Passwort: `password`

# Konfiguration
- Haushaltsgerät mit Home Connect verbinden (siehe Anleitung des Herstellers)
- Auf der Webseite [https://developer.home-connect.com](https://developer.home-connect.com) einen Developer Account 
erstellen und mit dem eigenen Home Connect Account verbinden
- [Hier](https://developer.home-connect.com/applications/add) eine neue Anwendung registrieren
- Application ID/Namen vergeben
- Folgendes unter Redirect URI eintragen: http://localhost:8080/oauth2/code
- Speichern und danach die Client ID und Client Secret hier in den Einstellungen speichern
- Die lokale IP in den SEMP Einstellungen eintragen und den Schalter "SEMP aktivieren" einschalten
- In der Navigationsleiste auf Gerät klicken und dort auf das große Plus
- Ein oder mehrere Home Connect Geräte hinzufügen
- Auf der Hauptseite nun noch die Anschlussleistung des Gerätes eintragen (siehe Typenschild z.B. 2200W)
- Da keine Messeinrichtung für die Leistungsmessung vorhanden ist, muss eine ca. Leistung für den Gerätebetrieb 
eingegeben werden
- Im Sunny Portal nach neuen Geräten suchen und die neuen Haushaltsgeräte hinzufügen
- Am Haushaltsgerät muss jetzt folgendes aktiviert sein, damit dieser vom Home Manager ferngestartet werden kann:
- Fernsteuerung und Fernstart muss aktiv sein. Es muss ein Programm und der verzögerte Start ausgewählt sein 
(spätester Einschaltzeitpunkt)
- Das Programm starten. Im Sunny Portal erscheint nun unter Prognose das eingeplante Haushaltsgerät im Zeitstrahl

# Disclaimer
Diese Anwendung wird nicht von der Home Connect GmbH oder SMA AG unterstützt oder hergestellt. Alle genannten 
Marken-, Produkt- und Warenzeichen sind Eigentum der jeweiligen Unternehmen.

# Willkommen im Repository des Alexa-Skills _Vergissmeinicht_. 
_Vergissmeinicht_ wird entwickelt, um Erlebtes festzuhalten und durch regelmäßige Wiederholung länger zu erhalten.
Konkret können dazu Erinnerungen (aktuell nur im mp3-Format, später auch Bilder/Fotoalben, Videos, beliebige Kombinationen) über die Begleit-App abgespeichert und anschließend über beliebige Alexa-fähige Geräte abgespielt werden.

Das Repository ist wie folgt gegliedert:

- _documents_ enthält Dateien, die den  Skill genauer beschreiben und dokumentieren
- Die Request Handler des Skills sind im Ordner _alexaLambda_ zu finden
- _uploadLambda_ enthält den Code zur Realisierung der Benutzerverknüpfung (Cognito ID <--> Federated ID)
- Im Verzeichnis _companion_ befinden sich alle Dateien, die zur Realisierung der Begleit-App benötigt werden (werden über ein privates Github-Repository automatisch via AWS Amplify deployed; nicht in Sonar integriert)
- _helper_ enthält schließlich alle Hilfsklassen. Hier sind beispielsweise Zugriffe auf S3, etc. gebündelt
- _skill-config_ beinhaltet das Skill Manifest und das Interaction Model in JSON-Format

Dieses Repository enthält alle für den Skill notwendigen Resourcen und kann anhand dieser mit einem Amazon-Developer-Account sowie AWS-Account realisiert werden. Die dafür einzurichtenden AWS-Services sind:

- Lambda
- API Gateway
- DynamoDB
- S3
- Cognito
- Amplify

Der Alexa-Skill selbst kann über die im Ordner _skill-config_ enthaltenen JSON-Dateien eingerichtet werden.


__ACHTUNG:__
Der Skill ist nur ein Prototyp und kann im aktuellen Zustand nicht veröffentlicht werden.
Insbesondere die Begleitapp und Datenspeicherung sind nicht DSGVO-konform!
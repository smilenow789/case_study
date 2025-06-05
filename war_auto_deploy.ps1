cls




# --- Konfiguration ---
# Pfad zum Verzeichnis, wo die WAR-Datei erstellt werden soll
$WarPfad = "C:\Users\roger\OneDrive\coding_projects\fallstudie_iu\new_maven_projekt_one\Fallstudie_IU"

# Pfad zum Quellverzeichnis der WAR-Datei (dem 'target'-Ordner deines Maven-Projekts)
$SourceWarDirectory = "C:\Users\roger\OneDrive\coding_projects\fallstudie_iu\new_maven_projekt_one\Fallstudie_IU\target"

# Name deiner WAR-Datei (muss genau dem 'finalName' in deiner pom.xml entsprechen)
$WarFileName = "Fallstudie_IU.war"

# Pfad zum 'webapps'-Verzeichnis deines Apache Tomcats
$TomcatWebappsDirectory = "C:\Users\roger\OneDrive\coding_projects\jsf\tools\apache-tomcat-10.1.36\webapps"

# Pfad zum 'bin'-Verzeichnis deines Apache Tomcats (wo catalina.bat liegt)
$TomcatBinDirectory = "C:\Users\roger\OneDrive\coding_projects\jsf\tools\apache-tomcat-10.1.36\bin"
# --- Ende Konfiguration ---


$SourceWarFilePath = Join-Path -Path $SourceWarDirectory -ChildPath $WarFileName
$DestinationWarFilePath = Join-Path -Path $TomcatWebappsDirectory -ChildPath $WarFileName
$ExplodedWarDirectory = Join-Path -Path $TomcatWebappsDirectory -ChildPath ($WarFileName -replace "\.war$", "")


Write-Host "--- WAR-Datei erstellen... ---" -ForegroundColor Cyan


try {
    Set-Location $WarPfad
    Write-Host "Wechsle zu: $($WarPfad)" -ForegroundColor Gray
    Write-Host "Führe 'mvn clean install' aus..." -ForegroundColor Yellow
    
    # Führt das Batch-Skript aus und zeigt die Ausgabe direkt in diesem Fenster an
    mvn clean install
}
catch {
    Write-Host "Fehler beim Starten von Tomcat: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}












Write-Host "--- Bereite die Bereitstellung vor ---" -ForegroundColor Cyan


# 1. Überprüfen, ob die Quell-WAR-Datei existiert
if (-not (Test-Path $SourceWarFilePath)) {
    Write-Host "Fehler: Die WAR-Datei '$SourceWarFilePath' wurde nicht gefunden." -ForegroundColor Red
    Write-Host "Bitte stelle sicher, dass dein Projekt erfolgreich mit Maven gebaut wurde (z.B. 'mvn clean install')." -ForegroundColor Red
    exit 1
}

# 2. Bestehendes entpacktes WAR-Verzeichnis in Tomcat löschen (für saubere Neuentfaltung)
if (Test-Path $ExplodedWarDirectory -PathType Container) {
    Write-Host "Lösche das alte entpackte WAR-Verzeichnis: '$($ExplodedWarDirectory)'..." -ForegroundColor Yellow
    try {
        Remove-Item -Path $ExplodedWarDirectory -Recurse -Force -ErrorAction Stop
        Write-Host "Altes Verzeichnis erfolgreich gelöscht." -ForegroundColor Green
    }
    catch {
        Write-Host "Fehler beim Löschen des alten Verzeichnisses: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "Stellen Sie sicher, dass Tomcat gestoppt ist und keine Dateien gesperrt sind." -ForegroundColor Red
        exit 1
    }
}

# 3. WAR-Datei kopieren
Write-Host "Kopiere WAR-Datei von '$SourceWarFilePath' nach '$TomcatWebappsDirectory'..." -ForegroundColor Yellow
try {
    Copy-Item -Path $SourceWarFilePath -Destination $TomcatWebappsDirectory -Force -ErrorAction Stop
    Write-Host "WAR-Datei erfolgreich kopiert." -ForegroundColor Green
}
catch {
    Write-Host "Fehler beim Kopieren der WAR-Datei: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host "`n--- Starte Apache Tomcat ---" -ForegroundColor Cyan

# 4. In das Tomcat bin-Verzeichnis wechseln und Tomcat starten
try {
    Set-Location $TomcatBinDirectory
    Write-Host "Wechsle zu: $($TomcatBinDirectory)" -ForegroundColor Gray
    Write-Host "Führe '.\catalina.bat run' aus..." -ForegroundColor Yellow
    
    # Führt das Batch-Skript aus und zeigt die Ausgabe direkt in diesem Fenster an
    .\catalina.bat run
}
catch {
    Write-Host "Fehler beim Starten von Tomcat: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host "`n--- Skript beendet ---" -ForegroundColor Cyan
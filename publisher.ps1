# Pornește Publisherul
$publisher = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar publisher/target/publisher-1.0-SNAPSHOT.jar 1' -PassThru

Write-Host "Publisherul a fost lansat. Apasa ENTER pentru a opri."
Read-Host

# Oprește Publisherul
$publisher | Stop-Process

# Închide terminalul
$publisher.CloseMainWindow()

Write-Host "Publisherul a fost oprit si terminalul inchis."
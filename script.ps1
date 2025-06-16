# Pornește Brokerul într-o nouă fereastră
$broker = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 1' -PassThru

Start-Sleep -Seconds 2

# Pornește Subscriberul
$subscriber = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 1' -PassThru

Start-Sleep -Seconds 2

# Pornește Publisherul
$publisher = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar publisher/target/publisher-1.0-SNAPSHOT.jar 1' -PassThru

Write-Host "Toate procesele au fost lansate. Apasa ENTER pentru a opri."
Read-Host

Write-Host "Procesele au fost oprite si terminalele inchise."

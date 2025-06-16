# docker-compose down
# docker-compose up -d

# Pornește Brokerul într-o nouă fereastră
$broker1 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 1 2' -PassThru
$broker2 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 2 1 3' -PassThru
$broker3 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 3 2' -PassThru

Start-Sleep -Seconds 2

# Pornește Subscriberul
$subscriber1 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 1 8082' -PassThru
$subscriber2 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 2 8083' -PassThru
$subscriber3 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 3 8084' -PassThru

Start-Sleep -Seconds 2

# Pornește Publisherul
$publisher = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar publisher/target/publisher-1.0-SNAPSHOT.jar 1' -PassThru

Write-Host "Toate procesele au fost lansate. Apasa ENTER pentru a opri."
Read-Host

Write-Host "Procesele au fost oprite si terminalele inchise."

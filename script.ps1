docker-compose down
docker-compose up -d

# Pornește Brokerul într-o nouă fereastră
$broker1 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 1 2' -PassThru
$broker2 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 2 1 3' -PassThru
$broker3 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 3 2' -PassThru

Start-Sleep -Seconds 20

# Pornește Subscriberul
$subscriber1 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 1 8082' -PassThru
$subscriber2 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 2 8083' -PassThru
$subscriber3 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 3 8084' -PassThru

Write-Host "Brokerii si Subscriberii au fost lansati. Apasa ENTER pentru a opri."
Read-Host

# Opreste procesele
$broker1 | Stop-Process
$broker2 | Stop-Process
$broker3 | Stop-Process
$subscriber1 | Stop-Process
$subscriber2 | Stop-Process
$subscriber3 | Stop-Process

# Inchide terminalele
$broker1.CloseMainWindow()
$broker2.CloseMainWindow()
$broker3.CloseMainWindow()
$subscriber1.CloseMainWindow()
$subscriber2.CloseMainWindow()
$subscriber3.CloseMainWindow()

Write-Host "Procesele au fost oprite si terminalele inchise."

docker-compose down
docker-compose up -d

# Start the Brokers in separate PowerShell windows
$broker1 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 1 2' -PassThru
$broker2 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 2 1 3' -PassThru
$broker3 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar broker/target/broker-1.0-SNAPSHOT.jar 3 2' -PassThru

Start-Sleep -Seconds 5

# Start the Subscribers in separate PowerShell windows
$subscriber1 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 1 8082' -PassThru
$subscriber2 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 2 8083' -PassThru
$subscriber3 = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar subscriber/target/subscriber-1.0-SNAPSHOT.jar 3 8084' -PassThru

Write-Host "All processes launched.  Press 'q' to stop everything."

do {
    $key = [Console]::ReadKey($true).KeyChar
} while ($key -ne 'q')

# Now q was pressed, shut down:
$broker1, $broker2, $broker3, $subscriber1, $subscriber2, $subscriber3 |
    ForEach-Object {
        if (-not $_.HasExited) {
            Stop-Process -Id $_.Id -Force
        }
    }

Write-Host "All processes stopped and their windows closed."

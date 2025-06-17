# Pornește Publisherul
$publisher = Start-Process powershell -ArgumentList '-NoExit', '-Command', 'java -jar publisher/target/publisher-1.0-SNAPSHOT.jar 1' -PassThru

Write-Host "The Publisher has been started.  Press 'q' to stop it"
do {
    $key = [Console]::ReadKey($true).KeyChar
} while ($key -ne 'q')

# Stop the Publisher
if (-not $publisher.HasExited) {
    Stop-Process -Id $publisher.Id -Force
}

Write-Host "The Publisher has been stopped and its window closed."
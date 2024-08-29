$dir = '.\'
$folder = '..\account-service'
$subdir = "$dir$folder"
$outputfile = "\log.txt"
$statusfile = "\status.txt"

#$process = Start-Process powershell.exe ("-noexit " + "Write-output $message") 

$HOSTNAME = "localhost"
$DATABASE_TCP_PORT = "18082"
$DATABASE_PG_PORT = "18083"
$DATABASE_WEB_PORT = "18084"
$REFERENCE_DATA_SERVICE_PORT = "18085"
$TRADE_FEED_PORT = "18086"
$ACCOUNT_SERVICE_PORT = "18088"
$PEOPLE_SERVICE_PORT = "18089"
$POSITION_SERVICE_PORT = "18090"
$TRADE_PROCESSOR_SERVICE_PORT = "18091"
$TRADING_SERVICE_PORT = "18092"
$WEB_SERVICE_ANGULAR_PORT = "18093"  #Angular
$WEB_SERVICE_REACT_PORT  = "18094"  #React

$DATABASE_DBUSER = "sa"
$DATABASE_DBPASS = "sa"
$DATABASE_H2JAR = "./build/libs/database.jar"
$DATABASE_DATA_DIR = "./_data"
$DATABASE_DBNAME = "traderx"
$DATABASE_HOSTNAME = $HOSTNAME
$DATABASE_JDBC_URL = "jdbc:h2:tcp://$HOSTNAME\:$DATABASE_TCP_PORT/$DATABASE_DBNAME"
$DATABASE_WEB_HOSTNAMES = $HOSTNAME

$Env:DATABASE_TCP_PORT = $DATABASE_TCP_PORT
$Env:DATABASE_TCP_PORT

echo "Data will be located in $DATABASE_DATA_DIR"
echo "Database name is $DATABASE_DBNAME"
echo 'Running schema setup script with log output to stdout below'
echo '---------------------------------------------------------------------------'
$Host.UI.RawUI.WindowTitle = "TraderX Database"

$process = Start-Process powershell.exe ("-NoExit " + "cd $folder;./gradlew bootRun | Out-File -filepath $dir$folder$outputfile") 

#$process = Start-Process powershell.exe ("-noexit " + "java --version")

echo $process
echo '---------------------------------------------------------------------------'

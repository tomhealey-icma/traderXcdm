
$env:DATABASE_TCP_PORT=18082
$env:DATABASE_PG_PORT=18083
$env:DATABASE_WEB_PORT=18084
$env:REFERENCE_DATA_SERVICE_PORT=18085
$env:TRADE_FEED_PORT=18086
$env:ACCOUNT_SERVICE_PORT=18088
$env:PEOPLE_SERVICE_PORT=18089
$env:POSITION_SERVICE_PORT=18090
$env:TRADE_PROCESSOR_SERVICE_PORT=18091
$env:TRADING_SERVICE_PORT=18092
$env:WEB_SERVICE_ANGULAR_PORT=18093
$env:WEB_SERVICE_REACT_PORT=18094
$env:TRADE_FEED_ADDRESS=18086
$env:JAVA_HOME=$env:JAVA17_HOME
$env:DATABASE_TCP_PORT=18082
$env:DATABASE_PG_PORT=18083
$env:DATABASE_WEB_PORT=18084
$env:DATABASE_DBUSER="sa"
$env:DATABASE_DBPASS="sa"
$env:DATABASE_H2JAR="./build/libs/database.jar"
$env:DATABASE_DATA_DIR="./_data"
$env:DATABASE_DBNAME="traderx"
$env:DATABASE_HOSTNAME="http://localhost"
$env:DATABASE_JDBC_URL="jdbc:h2:tcp:http://localhost:18082/traderx"
$env:DATABASE_WEB_HOSTNAMES="http://localhost"

echo "Data will be located in $DATABASE_DATA_DIR"
echo "Database name is $DATABASE_DBNAME"
echo 'Running schema setup script with log output to stdout below'
echo '---------------------------------------------------------------------------'
java -cp ./build/libs/database.jar org.h2.tools.RunScript -url "jdbc:h2:D:\IdeaProjects\finos\traderx\traderx-cdm4\traderX\database\_data/traderx;DATABASE_TO_UPPER=TRUE TRACE_LEVEL_SYSTEM_OUT=3" -user sa -password sa -script  initialSchema.sql

echo 'Starting Database Server - DB logs below'
echo '---------------------------------------------------------------------------'
java -jar ./build/libs/database.jar -pg -pgPort 18083 -pgAllowOthers -baseDir ./_data -tcp -tcpPort 18082 -tcpAllowOthers -web -webPort 18084 -webExternalNames localhost -webAllowOthers



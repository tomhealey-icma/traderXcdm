package com.finxis.traderxconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JAppMonitor {

   public String dir = ".\\";
   public String folder = "..\\database";
   public String subdir = "$dir$folder";
   public String outputfile = "\\log.txt";
   public String statusfile = "\\status.txt";

   public String HOSTNAME = "localhost";
   public String DATABASE_TCP_PORT = "18082";
   public String DATABASE_PG_PORT = "18083";
   public String DATABASE_WEB_PORT = "18084";
   public String REFERENCE_DATA_SERVICE_PORT = "18085";
   public String TRADE_FEED_PORT = "18086";
   public String ACCOUNT_SERVICE_PORT = "18088";
   public String PEOPLE_SERVICE_PORT = "18089";
   public String POSITION_SERVICE_PORT = "18090";
   public String TRADE_PROCESSOR_SERVICE_PORT = "18091";
   public String TRADING_SERVICE_PORT = "18092";
   public String WEB_SERVICE_ANGULAR_PORT = "18093"; // #Angular
   public String WEB_SERVICE_REACT_PORT  = "18094";  //#React

   public String DATABASE_DBUSER = "sa";
   public String DATABASE_DBPASS = "sa";
   public String DATABASE_H2JAR = "./build/libs/database.jar";
   public String DATABASE_DATA_DIR = "./_data";
   public String DATABASE_DBNAME = "traderx";
   public String DATABASE_HOSTNAME = HOSTNAME;
   public String DATABASE_JDBC_URL = "jdbc:h2:tcp://HOSTNAME\\:DATABASE_TCP_PORT/DATABASE_DBNAME";
   public String DATABASE_WEB_HOSTNAMES = HOSTNAME;


    public static void runJavaApp(String serviceName) throws IOException {

        JAppMonitor japm = new JAppMonitor();

        String cmd = japm.getDatabaseExecCommand();

        //Process proc = Runtime.getRuntime().exec("java -cp ../database/build/libs/database.jar ./org.h2.tools.RunScript -url 'jdbc:h2:$DATABASE_DATA_DIR/$DATABASE_DBNAME;DATABASE_TO_UPPER=TRUE;TRACE_LEVEL_SYSTEM_OUT=3' -userpublic String DATABASE_DBUSER -password $DATABASE_DBPASS -script initialSchema.sql;java -jar $DATABASE_H2JAR -pg -pgPort $DATABASE_PG_PORT -pgAllowOthers -baseDir $DATABASE_DATA_DIR -tcp -tcpPort $DATABASE_TCP_PORT -tcpAllowOthers -web -webPort $DATABASE_WEB_PORT -webExternalNames $DATABASE_WEB_HOSTNAMES -webAllowOthers");

        Process proc = Runtime.getRuntime().exec(cmd);
        // Then retreive the process output
        InputStream in = proc.getInputStream();
        InputStream err = proc.getErrorStream();
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
        stdout.close();
        System.out.println("Standard Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                proc.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println(line);
        }
        stderr.close();
        System.out.println("Done");
    }

    public String getDatabaseExecCommand(){

       String cmd = "java -cp " + "..\\database/build/libs/database.jar org.h2.tools.RunScript" + " -url 'jdbc:h2:./_data/traderx' " + "DATABASE_TO_UPPER=TRUE TRACE_LEVEL_SYSTEM_OUT=3'" + " -user sa -password sa " + " -script initialSchema.sql";

       String h =   "java -jar"  + "../database/build/libs/database.jar " +  " -pg -pgPort $DATABASE_PG_PORT " + "-pgAllowOthers -baseDir $DATABASE_DATA_DIR " +
               "-tcp -tcpPort $DATABASE_TCP_PORT -tcpAllowOthers -web -webPort $DATABASE_WEB_PORT " + " -webExternalNames $DATABASE_WEB_HOSTNAMES -webAllowOthers";

        //Process proc = Runtime.getRuntime().exec("java -cp ../database/build/libs/database.jar ./org.h2.tools.RunScript -url 'jdbc:h2:$DATABASE_DATA_DIR/$DATABASE_DBNAME;DATABASE_TO_UPPER=TRUE;TRACE_LEVEL_SYSTEM_OUT=3' -user $DATABASE_DBUSER -password $DATABASE_DBPASS -script initialSchema.sql;java -jar $DATABASE_H2JAR -pg -pgPort $DATABASE_PG_PORT -pgAllowOthers -baseDir $DATABASE_DATA_DIR -tcp -tcpPort $DATABASE_TCP_PORT -tcpAllowOthers -web -webPort $DATABASE_WEB_PORT -webExternalNames $DATABASE_WEB_HOSTNAMES -webAllowOthers");

    return cmd;

    }


}

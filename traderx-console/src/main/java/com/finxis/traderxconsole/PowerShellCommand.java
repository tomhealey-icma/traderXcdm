package com.finxis.traderxconsole;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PowerShellCommand {

    public static void runPowerShell(String serviceName) throws IOException {

        String command = "";
        String rd = "";
        //String command = args[0];
        //Getting the version
        //String command = "powershell.exe  $PSVersionTable.PSVersion";
        PowerShellCommand psc = new PowerShellCommand();

        command = psc.getCommand(serviceName);

        // Executing the command
        Process powerShellProcess = Runtime.getRuntime().exec(command);
        // Getting the results
        powerShellProcess.getOutputStream().close();
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
        stdout.close();
        System.out.println("Standard Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println(line);
        }
        stderr.close();
        Service service = new Service();
        service.setProcessLock(serviceName, (powerShellProcess.toString()));

        System.out.println("Done");

    }

    private String getCommand(String service){
        String command=null;

        switch (service){

            case "Database":
                command = "powershell.exe  \"..\\database\\run.ps1\"";
            break;

            case "RefData":
                command = "powershell.exe  \"..\\reference-data\\run.ps1\"";
            break;

            case "AccountService":
                command = "powershell.exe  \"..\\account-service\\run.ps1\"";
            break;

            case "TradeFeed":
                command = "powershell.exe  \"..\\trade-feed\\run.ps1\"";
            break;

            case "PeopleService":
                command = "powershell.exe  \"..\\people-service\\run.ps1\"";
            break;

            case "PositionService":
                command = "powershell.exe  \"..\\position-service\\run.ps1\"";
            break;

            case "TradeService":
                command = "powershell.exe  \"..\\trade-service\\run.ps1\"";
            break;

            case "TradeProcessor":
                command = "powershell.exe  \"..\\trade-processor\\run.ps1\"";
            break;
            default:
                command="";
        }
        return command;
    }

}
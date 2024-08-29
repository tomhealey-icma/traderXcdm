package com.finxis.traderxconsole;

import javax.swing.*;
import java.io.IOException;

public class Service {

    private String ID = null;
    private String service=null;
    private String status=null;
    private String start_stop=null;
    private String log=null;

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getID() {
        return ID;
    }

    public void setService (String service){
        this.service = service;
    }

    public String getService(){
        return this.service;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public String getLog(){
        return this.log;
    }

    public void setLog (String log){
        this.log = log;
    }

    public void setStart_stop(String startStop){
        this.start_stop = startStop;
    }

    public String getStart_stop(){
        return this.start_stop;
    }

    public void setProcessLock(String service, String processId) throws IOException {

        String rd = getWorkingDirectory(service);
        ProcessLock processLock = new ProcessLock();
        processLock.writeProcessIdToFile(rd, service, processId);
        this.status = "Running";

    }

    public boolean getProcessLock(String service) throws IOException {

        String rd = getWorkingDirectory(service);
        ProcessLock processLock = new ProcessLock();
        return processLock.getProcessLockStatus(rd,service);

    }

    private String getWorkingDirectory(String service){
        String rd = null;

        switch (service){

            case "Database":
                rd = "..\\database\\";
                break;

            case "RefData":
                rd = "..\\reference-data\\";
                break;

            case "AccountService":
                rd = "..\\account-service\\";
                break;

            case "TradeFeed":
                rd = "..\\trade-feed\\";
                break;

            case "PeopleService":
                rd = "..\\people-service\\";
                break;

            case "PositionService":
                rd = "..\\position-service\\";
                break;

            case "TradeService":
                rd = "..\\trade-service\\";
                break;

            case "TradeProcessor":
                rd = "..\\trade-processor\\";
                break;
            default:
                rd="";
        }
        return rd;
    }
}

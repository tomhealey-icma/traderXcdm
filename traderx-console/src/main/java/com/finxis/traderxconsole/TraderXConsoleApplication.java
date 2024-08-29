package com.finxis.traderxconsole;

public class TraderXConsoleApplication {

    private ServiceTableModel serviceTableModel = null;

    public TraderXConsoleApplication(ServiceTableModel serviceTableModel) {
        this.serviceTableModel = serviceTableModel;

        Service database = new Service();
        database.setID("1000");
        database.setService("Database");
        database.setStatus("Stopped");
        database.setStart_stop("Start");
        database.setLog("Open");
        serviceTableModel.addService(database);

        Service refdata = new Service();
        refdata.setID("1001");
        refdata.setService("RefData");
        refdata.setStatus("Stopped");
        refdata.setStart_stop("Start");
        refdata.setLog("Open");
        serviceTableModel.addService(refdata);

        Service tradeFeed = new Service();
        tradeFeed.setID("1002");
        tradeFeed.setService("TradeFeed");
        tradeFeed.setStatus("Stopped");
        tradeFeed.setStart_stop("Start");
        tradeFeed.setLog("Open");
        serviceTableModel.addService(tradeFeed);

        Service peopleService = new Service();
        peopleService.setID("1003");
        peopleService.setService("PeopleService");
        peopleService.setStatus("Stopped");
        peopleService.setStart_stop("Start");
        peopleService.setLog("Open");
        serviceTableModel.addService(peopleService);

        Service accountService = new Service();
        accountService.setID("1004");
        accountService.setService("AccountService");
        accountService.setStatus("Stopped");
        accountService.setStart_stop("Start");
        accountService.setLog("Open");
        serviceTableModel.addService(accountService);

        Service positionService = new Service();
        positionService.setID("1005");
        positionService.setService("PositionService");
        positionService.setStatus("Stopped");
        positionService.setStart_stop("Start");
        positionService.setLog("Open");
        serviceTableModel.addService(positionService);

        Service tradeService = new Service();
        tradeService.setID("1006");
        tradeService.setService("TradeService");
        tradeService.setStatus("Stopped");
        tradeService.setStart_stop("Start");
        tradeService.setLog("Open");
        serviceTableModel.addService(tradeService);


        Service tradeProcessor = new Service();
        tradeProcessor .setID("1007");
        tradeProcessor .setService("TradeProcessor");
        tradeProcessor .setStatus("Stopped");
        tradeProcessor .setStart_stop("Start");
        tradeProcessor .setLog("Open");
        serviceTableModel.addService(tradeProcessor);



    }
}

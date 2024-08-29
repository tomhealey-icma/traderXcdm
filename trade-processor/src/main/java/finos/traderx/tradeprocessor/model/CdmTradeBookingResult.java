package finos.traderx.tradeprocessor.model;

public class CdmTradeBookingResult {
    CdmTrade trade;
    Position position;

    public CdmTradeBookingResult(CdmTrade t, Position p){
        this.trade=t;
        this.position=p;
    }
    public CdmTrade getTrade() {
        return trade;
    }
    public Position getPosition() {
        return position;
    }
}


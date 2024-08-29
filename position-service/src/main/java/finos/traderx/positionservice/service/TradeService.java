package finos.traderx.positionservice.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cdm.base.staticdata.party.Party;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finos.traderx.positionservice.model.*;
import finos.traderx.positionservice.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;

import static java.lang.Integer.parseInt;

@Service
public class TradeService {

	@Autowired
	TradeRepository tradeRepository;

	@Autowired
	CdmTradeRepository cdmTradeRepository;

	public List<Trade> getAllTrades() throws JsonProcessingException {

		String cdmTradeJson=null;
		List<Trade> trades = new ArrayList<Trade>();
		List<CdmTrade> cdmTrades = new ArrayList<CdmTrade>();

		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		cdm.event.common.Trade cdmTrade = new cdm.event.common.Trade.TradeBuilderImpl();

		for(CdmTrade ct: this.cdmTradeRepository.findAll()) {

			cdmTradeJson = ct.getCdmTrade().trim().toString();
			cdmTradeJson = cdmTradeJson.replaceAll("\\\\","");
			cdmTradeJson = cdmTradeJson.replaceAll("^\"|\"$", "");

			cdmTrade = rosettaObjectMapper.readValue(cdmTradeJson , cdmTrade.getClass());
			Trade trade = new Trade();
			trade.setId((cdmTrade.getTradeIdentifier().get(0).getAssignedIdentifier().get(0).getIdentifier().getValue().toString()));
			trade.setAccountId(parseInt(cdmTrade.getParty().get(0).getName().getValue().toString()));
			trade.setCreated(Date.valueOf(cdmTrade.getTradeDate().getValue().toString()));
			trade.setUpdated(Date.valueOf(cdmTrade.getTradeDate().getValue().toString()));
			trade.setSecurity(cdmTrade.getTradableProduct().getProduct().getSecurity().getIdentifier().get(0).getIdentifier().getValue());
			trade.setSide(this.mapCdmTradeSide(cdmTrade.getPartyRole().get(0).getRole().toString()));
			trade.setQuantity(parseInt(cdmTrade.getTradableProduct().getTradeLot().get(0).getPriceQuantity().get(0).getQuantity().get(0).getValue().getValue().toString()));
			trade.setState(this.mapCdmTradeState("EXECUTED"));
			trades.add(trade);
		}

		return trades;
	}

	private String mapCdmTradeSide(String side){

		if(side.equals("Buyer"))
			return "BUY";
		else
			return "SELL";
	}

	private String mapCdmTradeState(String state){

		return "Settled";
	}


	public List<Trade> getTradesByAccountID(int id) throws JsonProcessingException {

		String cdmTradeJson=null;
		List<Trade> trades = new ArrayList<Trade>();
		List<CdmTrade> cdmTrades = new ArrayList<CdmTrade>();

		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		cdm.event.common.Trade cdmTrade = new cdm.event.common.Trade.TradeBuilderImpl();

		for(CdmTrade ct: this.cdmTradeRepository.findByAccountId(id)) {

			cdmTradeJson = ct.getCdmTrade().trim().toString();
			cdmTradeJson = cdmTradeJson.replaceAll("\\\\","");
			cdmTradeJson = cdmTradeJson.replaceAll("^\"|\"$", "");

			cdmTrade = rosettaObjectMapper.readValue(cdmTradeJson , cdmTrade.getClass());
			Trade trade = new Trade();
			trade.setId((cdmTrade.getTradeIdentifier().get(0).getAssignedIdentifier().get(0).getIdentifier().getValue().toString()));
			//TraderX requires integer Ids but CDM uses strings.
			//trade.setAccountId(parseInt(cdmTrade.getParty().get(0).getName().getValue().toString()));
			trade.setAccountId(id);
			trade.setCreated(Date.valueOf(cdmTrade.getTradeDate().getValue().toString()));
			trade.setUpdated(Date.valueOf(cdmTrade.getTradeDate().getValue().toString()));
			trade.setSecurity(cdmTrade.getTradableProduct().getProduct().getSecurity().getIdentifier().get(0).getIdentifier().getValue());
			trade.setSide(this.mapCdmTradeSide(cdmTrade.getPartyRole().get(0).getRole().toString()));
			trade.setQuantity(parseInt(cdmTrade.getTradableProduct().getTradeLot().get(0).getPriceQuantity().get(0).getQuantity().get(0).getValue().getValue().toString()));
			trade.setState(this.mapCdmTradeState("EXECUTED"));
			trades.add(trade);
		}

		return trades;

	}

}

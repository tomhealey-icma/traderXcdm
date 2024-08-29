package finos.traderx.tradeprocessor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cdm.base.math.NonNegativeQuantitySchedule;
import cdm.base.math.UnitType;
import cdm.base.math.metafields.FieldWithMetaNonNegativeQuantitySchedule;
import cdm.base.staticdata.asset.common.*;
import cdm.base.staticdata.identifier.Identifier;
import cdm.base.staticdata.identifier.TradeIdentifierTypeEnum;
import cdm.base.staticdata.party.*;
import cdm.event.common.*;
import cdm.event.common.functions.Create_BusinessEvent;
import cdm.event.workflow.EventTimestamp;
import cdm.event.workflow.MessageInformation;
import cdm.event.workflow.Workflow;
import cdm.event.workflow.WorkflowStep;
import cdm.event.workflow.functions.Create_WorkflowStep;
import cdm.observable.asset.Price;
import cdm.observable.asset.PriceExpressionEnum;
import cdm.observable.asset.PriceTypeEnum;
import cdm.observable.asset.metafields.FieldWithMetaPriceSchedule;
import cdm.product.common.settlement.PriceQuantity;
import cdm.product.template.Product;
import cdm.product.template.TradableProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.regnosys.rosetta.common.hashing.GlobalKeyProcessStep;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.lib.GlobalKey;
import com.rosetta.model.lib.RosettaModelObject;
import com.rosetta.model.lib.RosettaModelObjectBuilder;
import com.rosetta.model.lib.process.PostProcessStep;
import com.rosetta.model.metafields.FieldWithMetaDate;
import com.rosetta.model.metafields.FieldWithMetaString;
import com.rosetta.model.metafields.MetaFields;
import finos.traderx.messaging.CdmPublisher;
import finos.traderx.tradeprocessor.model.Trade;
import finos.traderx.tradeprocessor.model.TradeState;
import finos.traderx.tradeprocessor.util.CdmUtil;
import org.finos.cdm.CdmRuntimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import finos.traderx.messaging.PubSubException;
import finos.traderx.messaging.Publisher;
import finos.traderx.tradeprocessor.model.*;
import finos.traderx.tradeprocessor.repository.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.inject.Guice;

import cdm.base.staticdata.identifier.AssignedIdentifier;
import cdm.base.staticdata.party.Party;

import com.rosetta.model.lib.process.PostProcessStep;
import org.finos.cdm.CdmRuntimeModule;
import com.regnosys.rosetta.common.hashing.NonNullHashCollector;
@Service
public class TradeService {

	private final PostProcessStep keyProcessor;
	public TradeService () {keyProcessor = new GlobalKeyProcessStep(NonNullHashCollector::new);
	}


	private <T extends RosettaModelObject> T addGlobalKey(Class<T> type, T modelObject) {
		RosettaModelObjectBuilder builder = modelObject.toBuilder();
		keyProcessor.runProcessStep(type, builder);
		return type.cast(builder.build());
	}
	private String getGlobalReference(GlobalKey globalKey) {
		return globalKey.getMeta().getGlobalKey();
	}
	Logger log= LoggerFactory.getLogger(TradeService.class);
	@Autowired
	TradeRepository tradeRepository;

	@Autowired
	CdmTradeRepository cdmTradeRepository;

	@Autowired
	PositionRepository positionRepository;

	
    @Autowired 
    private Publisher<Trade> tradePublisher;

	@Autowired
	private CdmPublisher<CdmTrade> cdmTradePublisher;
    
    @Autowired
    private Publisher<Position> positionPublisher;
    
	public TradeBookingResult processTrade(TradeOrder order) throws IOException {
		log.info("Trade order received : "+order);
        Trade t=new Trade();
        t.setAccountId(order.getAccountId());

		log.info("Setting a random TradeID");
		t.setId(UUID.randomUUID().toString());


        t.setCreated(new Date());
        t.setUpdated(new Date());
        t.setSecurity(order.getSecurity());
        t.setSide(order.getSide());
        t.setQuantity(order.getQuantity());
		t.setState(TradeState.New);
		Position position=positionRepository.findByAccountIdAndSecurity(order.getAccountId(), order.getSecurity());
		log.info("Position for "+order.getAccountId()+" "+order.getSecurity()+" is "+position);
		if(position==null) {
			log.info("Creating new position for "+order.getAccountId()+" "+order.getSecurity());
			position=new Position();
			position.setAccountId(order.getAccountId());
			position.setSecurity(order.getSecurity());
			position.setQuantity(0);
		}
		int newQuantity=((order.getSide()==TradeSide.Buy)?1:-1)*t.getQuantity();
		position.setQuantity(position.getQuantity()+newQuantity);
		log.info("Trade {}",t);
		tradeRepository.save(t);
		positionRepository.save(position);
		// Simulate the handling of this trade...
		// Now mark as processing
		t.setUpdated(new Date());
		t.setState(TradeState.Processing);
		// Now mark as settled
		t.setUpdated(new Date());
		t.setState(TradeState.Settled);

		Workflow execution = this.executeTradeWorkflow(order.getSide().toString(), order.getSecurity().toString(), order.getQuantity().toString(), "100.0", t.getId().toString(), "2024-04-25");

		String executionStr = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(execution);
		System.out.println(executionStr);

		//String executionStr ="{Test}";
		//t.setCdmTrade(executionStr);
		//tradeRepository.save(t);


		TradeBookingResult result=new TradeBookingResult(t, position);
		//log.info("Trade Processing complete : "+result);
		//try{
		//	log.info("Publishing : "+result);
		//	tradePublisher.publish("/accounts/"+order.getAccountId()+"/trades", result.getTrade());
		//	positionPublisher.publish("/accounts/"+order.getAccountId()+"/positions", result.getPosition());
		//} catch (PubSubException exc){
		//	log.error("Error publishing trade "+order,exc);
		//}

		CdmTrade cdmt=new CdmTrade();
		CdmTradeBookingResult cmdTradeBookingResult = new CdmTradeBookingResult(cdmt,position);
		this.processCdmTrade(order);

		return result;	
	}
	
		public CdmTradeBookingResult processCdmTrade(TradeOrder order) throws IOException {
		log.info("Trade order received : "+order);
        CdmTrade cdmt=new CdmTrade();
        cdmt.setAccountId(order.getAccountId());

		log.info("Setting a random TradeID");
		String tid = UUID.randomUUID().toString();
		cdmt.setId(tid);


        cdmt.setCreated(new Date());
        cdmt.setUpdated(new Date());
        cdmt.setSecurity(order.getSecurity());
        cdmt.setSide(order.getSide());
        cdmt.setQuantity(order.getQuantity());
		cdmt.setState(TradeState.New);
		Position position=positionRepository.findByAccountIdAndSecurity(order.getAccountId(), order.getSecurity());
		log.info("Position for "+order.getAccountId()+" "+order.getSecurity()+" is "+position);
		if(position==null) {
			log.info("Creating new position for "+order.getAccountId()+" "+order.getSecurity());
			position=new Position();
			position.setAccountId(order.getAccountId());
			position.setSecurity(order.getSecurity());
			position.setQuantity(0);
		}
		int newQuantity=((order.getSide()==TradeSide.Buy)?1:-1)*cdmt.getQuantity();
		position.setQuantity(position.getQuantity()+newQuantity);
		log.info("Trade {}",cdmt);
		cdmTradeRepository.save(cdmt);
		//positionRepository.save(position);
		// Simulate the handling of this trade...
		// Now mark as processing
		cdmt.setUpdated(new Date());
		cdmt.setState(TradeState.Processing);
		// Now mark as settled
		cdmt.setUpdated(new Date());
		cdmt.setState(TradeState.Settled);

		Workflow execution = this.executeTradeWorkflow(order.getSide().toString(), order.getSecurity().toString(), order.getQuantity().toString(), "100.0", tid, "2024-04-25");


		String executionStr = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(execution);
		executionStr.substring(0, 150);
		System.out.println(executionStr);

		cdm.event.common.Trade cdmTrade = new cdm.event.common.Trade.TradeBuilderImpl();
		cdmTrade = execution.getSteps().get(0).getBusinessEvent().getAfter().get(0).getTrade();
		String cdmTradeJson = RosettaObjectMapper.getNewRosettaObjectMapper().writeValueAsString(cdmTrade);
		cdmt.setCdmTrade(cdmTradeJson);
		cdmTradeRepository.save(cdmt);

		CdmTradeBookingResult cdmResult=new CdmTradeBookingResult(cdmt, position);

		log.info("Trade Processing complete : "+ cdmResult);
		try{
			log.info("Publishing : "+ cdmResult);
			cdmTradePublisher.publish("/accounts/"+order.getAccountId()+"/trades", cdmResult.getTrade());
			positionPublisher.publish("/accounts/"+order.getAccountId()+"/positions", cdmResult.getPosition());
		} catch (PubSubException exc){
			log.error("Error publishing trade "+order,exc);
		}
		
		return cdmResult;
	}



	public Workflow executeTradeWorkflow(
				String sideStr,
				String symbolStr,
				String quantityStr,
				String priceStr,
				String tradeUTIStr,
				String tradeDateStr

    ) throws IOException {

			String executionTypeStr = "OnVenue";

			Party party1 = Party.builder()
					.setNameValue("BANK1")
					.build();

			Party party2 = Party.builder()
					.setNameValue("BANK2")
					.build();

			List<Party> parties = List.of(party1, party2);

			PartyRole party1Role = null;
			PartyRole party2Role = null;
			if (sideStr == "BUY") {
				party1Role = PartyRole.builder()
						.setRole(PartyRoleEnum.BUYER)
						.build();

				party2Role = PartyRole.builder()
						.setRole(PartyRoleEnum.SELLER)
						.build();
			}else{
				party1Role = PartyRole.builder()
						.setRole(PartyRoleEnum.SELLER)
						.build();

				party2Role = PartyRole.builder()
						.setRole(PartyRoleEnum.BUYER)
						.build();

			}

			List<PartyRole> partyRoles = List.of(party1Role, party2Role);

			DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

			tradeDateStr = tradeDateStr.replaceAll("\\s", "") + "T00:00:00.000+00:00";
			ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(tradeDateStr, formatter);
			ZonedDateTime zdtInLocalTimeline = zdtWithZoneOffset.withZoneSameInstant(ZoneId.systemDefault());

			CdmUtil util = new CdmUtil();

			FieldWithMetaDate tradeDate = addGlobalKey(FieldWithMetaDate.class,
					util.createTradeDate(zdtWithZoneOffset.getYear(), zdtWithZoneOffset.getMonthValue(), zdtWithZoneOffset.getDayOfMonth()));

			TradeIdentifier tradeId = TradeIdentifier.builder().addAssignedIdentifier(
							AssignedIdentifier.builder().
									setIdentifier(
											FieldWithMetaString.builder().setValue(tradeUTIStr)
													.setMeta(MetaFields.builder()
															.setScheme("UTI"))))
					.setIdentifierType(TradeIdentifierTypeEnum.valueOf("UNIQUE_TRANSACTION_IDENTIFIER"))
					.build();

			ExecutionDetails executionDetails = ExecutionDetails.builder()
					.setExecutionType(ExecutionTypeEnum.ON_VENUE)
					.build();

			Product product = Product.builder()
					.setSecurity(Security.builder()
							.setIdentifier(List.of(AssetIdentifier.builder()
											.setIdentifier(FieldWithMetaString.builder()
													.setValue(symbolStr)))))
							.build();

			PriceQuantity priceQuantity = PriceQuantity.builder()

					.addPrice(FieldWithMetaPriceSchedule.builder()
							.setValue(Price.builder()
									.setValue(BigDecimal.valueOf(Double.parseDouble(priceStr)))
									.setPriceType(PriceTypeEnum.ASSET_PRICE)
									.setPriceExpression(PriceExpressionEnum.PERCENTAGE_OF_NOTIONAL)))

					.addQuantity(FieldWithMetaNonNegativeQuantitySchedule.builder()
							.setValue(NonNegativeQuantitySchedule.builder()
									.setValue(new BigDecimal(quantityStr)))
							.build())

					.build();

			List<Counterparty> counterparties = List.of(Counterparty.builder()
					.setRole(CounterpartyRoleEnum.PARTY_1)
					.setPartyReferenceValue(party1)
					.setRole(CounterpartyRoleEnum.PARTY_2)
					.setPartyReferenceValue(party2)
					.build());


			ExecutionInstruction executionInstruction = ExecutionInstruction.builder()
					.setProduct(product)
					.addPriceQuantity(priceQuantity)
					.addCounterparty(counterparties)
					.addParties(parties)
					.addPartyRoles(partyRoles)
					.setExecutionDetails(executionDetails)
					.setTradeDate(tradeDate)
					.addTradeIdentifier(tradeId)
					.build();


			Instruction instruction = Instruction.builder()
					.setPrimitiveInstruction(PrimitiveInstruction.builder()
							.setExecution(executionInstruction))
					.setBeforeValue(null)
					.build();

			List<Instruction> instructionList = List.of(instruction);

			Injector injector = Guice.createInjector(new CdmRuntimeModule());

			Create_BusinessEvent execEvent = new Create_BusinessEvent.Create_BusinessEventDefault();
			injector.injectMembers(execEvent);

			DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime localDateTime = LocalDateTime.now();
			String eventDateStr = localDateTime.format(eventDateFormat);


			com.rosetta.model.lib.records.Date effectiveDate = com.rosetta.model.lib.records.Date.of(2024, 3, 28);
			com.rosetta.model.lib.records.Date eventDate = com.rosetta.model.lib.records.Date.of(2024, 3, 28);

			BusinessEvent businessEvent = execEvent.evaluate(instructionList, null, eventDate, effectiveDate);

			String businesseventJson = RosettaObjectMapper.getNewRosettaObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(businessEvent);

			DateTimeFormatter eventDateTimeFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
			String eventDateTime = localDateTime.format(eventDateTimeFormat);

			//FileWriter fileWriter = new FileWriter();

			//fileWriter.writeEventToFile("Execution", eventDateTime, businesseventJson);

			Workflow wf = createNewTradeWorkflow(businessEvent );

			return wf;

		}

	public Workflow createNewTradeWorkflow(BusinessEvent tradeEvent){

		//Repo Execution Step
		Create_WorkflowStep wfs = new Create_WorkflowStep.Create_WorkflowStepDefault();
		Injector injector = Guice.createInjector(new CdmRuntimeModule());
		injector.injectMembers(wfs);

		DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String eventDateTime = localDateTime.format(eventDateFormat);
		EventTimestamp eventTimeStamp = new EventTimestamp.EventTimestampBuilderImpl();
		eventTimeStamp.getDateTime();
		List<EventTimestamp> eventTimestampList = List.of(eventTimeStamp);

		Identifier eventIdentifier = new Identifier.IdentifierBuilderImpl();
		eventIdentifier.hashCode();
		List<Identifier> eventIdList = List.of(eventIdentifier);

		Party party1 = Party.builder()
				.setNameValue("BANK1")
				.build();

		Party party2 = Party.builder()
				.setNameValue("BANK2")
				.build();

		List<Party> parties = List.of(party1, party2);

		MessageInformation messageInformation = null;
		List<Account> accountList = null;
		WorkflowStep previousWorkflowStep = null;

		List<WorkflowStep> repoWorkflowList = List.of(
				wfs.evaluate(
						messageInformation,
						eventTimestampList,
						eventIdList,
						parties,
						accountList,
						previousWorkflowStep,
						ActionEnum.NEW,
						tradeEvent
				)
		);

		Workflow rwf = new Workflow.WorkflowBuilderImpl()
				.addSteps(repoWorkflowList)
				.build();

		return rwf;

	}

}

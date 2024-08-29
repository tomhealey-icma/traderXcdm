package finos.traderx.positionservice.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finos.traderx.positionservice.model.Trade;
import finos.traderx.positionservice.service.TradeService;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/trades", produces="application/json")
public class TradeController {

	@Autowired
	TradeService tradeService;
 

	@GetMapping("/{accountId}")
	public ResponseEntity<List<Trade>> getByAccountId(@PathVariable int accountId) throws JsonProcessingException {
		List<Trade> retVal = this.tradeService.getTradesByAccountID(accountId);
		return ResponseEntity.ok(retVal);
	}

	@GetMapping("/")
	public ResponseEntity<List<Trade>> getAllTrades() throws JsonProcessingException {
		return ResponseEntity.ok(this.tradeService.getAllTrades());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> generalError(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}

package finos.traderx.tradeprocessor.repository;

import java.util.List;

import finos.traderx.tradeprocessor.model.CdmTrade;
import org.springframework.data.jpa.repository.JpaRepository;

import finos.traderx.tradeprocessor.model.Trade;

public interface CdmTradeRepository extends JpaRepository<CdmTrade, Integer> {
    
    List<CdmTrade> findByAccountId(Integer id);
    
}

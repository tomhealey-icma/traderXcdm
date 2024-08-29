package finos.traderx.positionservice.repository;

import java.util.List;

import finos.traderx.positionservice.model.CdmTrade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CdmTradeRepository extends JpaRepository<CdmTrade, Integer> {
    
    List<CdmTrade> findByAccountId(Integer id);
    
}

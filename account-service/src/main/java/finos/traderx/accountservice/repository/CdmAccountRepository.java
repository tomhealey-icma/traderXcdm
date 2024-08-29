package finos.traderx.accountservice.repository;

import finos.traderx.accountservice.model.Account;

import finos.traderx.accountservice.model.CdmAccount;
import org.springframework.data.repository.CrudRepository;

public interface CdmAccountRepository extends CrudRepository<CdmAccount, Integer> {
}

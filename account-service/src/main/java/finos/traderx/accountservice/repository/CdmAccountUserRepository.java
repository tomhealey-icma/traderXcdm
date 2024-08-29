package finos.traderx.accountservice.repository;

import finos.traderx.accountservice.model.AccountUser;
import finos.traderx.accountservice.model.CdmAccountUser;

import org.springframework.data.repository.CrudRepository;

public interface CdmAccountUserRepository extends CrudRepository<CdmAccountUser, Integer> {
}

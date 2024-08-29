package finos.traderx.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rosetta.model.metafields.FieldWithMetaString;
import finos.traderx.accountservice.exceptions.ResourceNotFoundException;
import finos.traderx.accountservice.model.Account;
import finos.traderx.accountservice.model.CdmAccount;
import finos.traderx.accountservice.repository.CdmAccountRepository;

import finos.traderx.accountservice.repository.CdmAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdm.base.staticdata.party.*;
@Service
public class CdmAccountService {


	@Autowired
	CdmAccountRepository cdmAccountRepository;

	public List<CdmAccount> getAllAccount() {
		List<CdmAccount> accounts = new ArrayList<CdmAccount>();
		this.cdmAccountRepository.findAll().forEach(account -> accounts.add(account));
		return accounts;
	}

	public CdmAccount getAccountById(int id) throws ResourceNotFoundException {
		Optional<CdmAccount> cdmAccount = this.cdmAccountRepository.findById(id);

		if (cdmAccount.isEmpty()) {
			throw new ResourceNotFoundException("Account with id " + id + "not found");
		}
		return cdmAccount.get();
	}

	public CdmAccount upsertCdmAccount(CdmAccount cdmAccount) {
		return this.cdmAccountRepository.save(cdmAccount);
	}
}

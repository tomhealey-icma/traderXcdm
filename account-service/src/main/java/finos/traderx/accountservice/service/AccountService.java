package finos.traderx.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cdm.base.staticdata.identifier.AssignedIdentifier;
import cdm.base.staticdata.identifier.Identifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosetta.model.metafields.FieldWithMetaString;
import finos.traderx.accountservice.exceptions.ResourceNotFoundException;
import finos.traderx.accountservice.model.Account;
import finos.traderx.accountservice.model.CdmAccount;
import finos.traderx.accountservice.repository.AccountRepository;

import finos.traderx.accountservice.repository.CdmAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;

import cdm.base.staticdata.party.*;

import static java.lang.Integer.parseInt;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CdmAccountRepository cdmAccountRepository;



	public List<Account> getAllAccount() throws JsonProcessingException {

		String CdmPartyJson=null;
		List<Account> accounts = new ArrayList<Account>();
		List<CdmAccount> cdmAccounts = new ArrayList<CdmAccount>();

		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		Party cdmParty = new Party.PartyBuilderImpl();

				for(CdmAccount ca: this.cdmAccountRepository.findAll()) {

					CdmPartyJson = ca.getCdmAccountObj().trim().toString();
					CdmPartyJson = CdmPartyJson.replaceAll("\\\\","");
					CdmPartyJson = CdmPartyJson.replaceAll("^\"|\"$", "");

					cdmParty = rosettaObjectMapper.readValue(CdmPartyJson , cdmParty.getClass());
					Account account = new Account();
					account.setId(parseInt(cdmParty.getPartyId().get(0).getIdentifier().getValue()));
					account.setDisplayName(cdmParty.getName().getValue().toString());
					accounts.add(account);
				}


		//this.accountRepository.findAll().forEach(account -> accounts.add(account));

		return accounts;
	}

	public Account getAccountById(int id) throws ResourceNotFoundException, JsonProcessingException {

		String CdmPartyJson=null;
		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		Party cdmParty = new Party.PartyBuilderImpl();

		//Optional<Account> account = this.accountRepository.findById(id);
		Optional<CdmAccount> cdmAccount = this.cdmAccountRepository.findById(id);

		CdmPartyJson = cdmAccount.get().getCdmAccountObj().trim().toString();
		CdmPartyJson = CdmPartyJson.replaceAll("\\\\","");
		CdmPartyJson = CdmPartyJson.replaceAll("^\"|\"$", "");

		cdmParty = rosettaObjectMapper.readValue(CdmPartyJson , cdmParty.getClass());
		Account account = new Account();

		account.setId(parseInt(cdmParty.getPartyId().get(0).getIdentifier().getValue()));
		account.setDisplayName(cdmParty.getName().getValue().toString());

		if (cdmAccount.isEmpty()) {
			throw new ResourceNotFoundException("Account with id " + id + "not found");
		}
		return account;
	}


	public Account upsertAccount(Account account) throws JsonProcessingException {

		//This is a hack to generate an account ID and set it on the CDM Party. Normally the ID would be
		//the LEI and known ahead of time.
		//This example also does not change the TraderX UI interface or function signature.

		CdmAccount cdmAccount = new CdmAccount();
		cdmAccount.setId(account.getId());
		cdmAccount.setDisplayName(account.getDisplayName());
		this.cdmAccountRepository.save(cdmAccount);

		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		Party cdmParty = Party.builder()
				.setPartyId(List.of(PartyIdentifier.builder()
								.setIdentifier(FieldWithMetaString.builder()
										.setValue(String.valueOf(cdmAccount.getId())))))
				.setNameValue(account.getDisplayName())
				.build();

		cdmAccount.setId(cdmAccount.getId());
		String CdmPartyJson = null;
		CdmPartyJson = RosettaObjectMapper.getNewRosettaObjectMapper().writeValueAsString(cdmParty);
		cdmAccount.setCdmAccountObj(CdmPartyJson);
		this.cdmAccountRepository.save(cdmAccount);

		account.setId(parseInt(cdmParty.getPartyId().get(0).getIdentifier().getValue()));
		account.setDisplayName(cdmParty.getName().getValue().toString());

		return account;
	}
}

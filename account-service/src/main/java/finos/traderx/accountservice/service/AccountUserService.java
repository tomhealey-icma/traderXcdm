package finos.traderx.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cdm.base.staticdata.party.Party;
import cdm.base.staticdata.party.PartyIdentifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.regnosys.rosetta.common.serialisation.RosettaObjectMapper;
import com.rosetta.model.metafields.FieldWithMetaString;
import finos.traderx.accountservice.exceptions.ResourceNotFoundException;
import finos.traderx.accountservice.model.Account;
import finos.traderx.accountservice.model.AccountUser;
import finos.traderx.accountservice.model.CdmAccount;
import finos.traderx.accountservice.model.CdmAccountUser;
import finos.traderx.accountservice.repository.AccountRepository;
import finos.traderx.accountservice.repository.AccountUserRepository;
import finos.traderx.accountservice.repository.CdmAccountRepository;
import finos.traderx.accountservice.repository.CdmAccountUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;

@Service
public class AccountUserService {

	@Autowired
	AccountUserRepository accountUserRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CdmAccountUserRepository cdmAccountUserRepository;

	@Autowired
	CdmAccountRepository cdmAccountRepository;

	public List<AccountUser> getAllAccountUsers() throws JsonProcessingException {
		String CdmPartyJson=null;
		List<AccountUser> accountUsers = new ArrayList<AccountUser>();
		List<CdmAccountUser> cdmAccounts = new ArrayList<CdmAccountUser>();

		//Users and Accounts are all modeled as a CDM Party.
		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		Party cdmParty = new Party.PartyBuilderImpl();

		for(CdmAccountUser cau: this.cdmAccountUserRepository.findAll()) {

			CdmPartyJson = cau.getCdmAccountUserObj().trim().toString();
			CdmPartyJson = CdmPartyJson.replaceAll("\\\\","");
			CdmPartyJson = CdmPartyJson.replaceAll("^\"|\"$", "");

			cdmParty = rosettaObjectMapper.readValue(CdmPartyJson , cdmParty.getClass());
			AccountUser accountUser = new AccountUser();
			accountUser.setAccountId(parseInt(cdmParty.getPartyId().get(0).getIdentifier().getValue()));
			accountUser.setUsername(cdmParty.getName().getValue().toString());
			accountUsers.add(accountUser);
		}


		//this.accountUserRepository.findAll().forEach(accountUser -> accountUsers.add(accountUser));
		return accountUsers;
	}

	public AccountUser getAccountUserById(int id) throws ResourceNotFoundException, JsonProcessingException {

		String CdmPartyJson=null;
		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		Party cdmParty = new Party.PartyBuilderImpl();

		//Optional<Account> account = this.accountRepository.findById(id);
		Optional<CdmAccountUser> cdmAccountUser = this.cdmAccountUserRepository.findById(Integer.valueOf(id));

		CdmPartyJson = cdmAccountUser.get().getCdmAccountUserObj().trim().toString();
		CdmPartyJson = CdmPartyJson.replaceAll("\\\\","");
		CdmPartyJson = CdmPartyJson.replaceAll("^\"|\"$", "");

		cdmParty = rosettaObjectMapper.readValue(CdmPartyJson , cdmParty.getClass());
		AccountUser accountUser = new AccountUser();

		accountUser.setAccountId(parseInt(cdmParty.getPartyId().get(0).getIdentifier().getValue()));
		accountUser.setUsername(cdmParty.getName().getValue().toString());

		if (cdmAccountUser.isEmpty()) {
			throw new ResourceNotFoundException("AccountUser with id " + id + "not found");
		}
		return accountUser;
	}

	public AccountUser upsertAccountUser(AccountUser accountUser) throws JsonProcessingException {

		Optional<Account> account = this.accountRepository.findById(accountUser.getAccountId());
		if (account.isEmpty()) {
			throw new ResourceNotFoundException("Account with id " + accountUser.getAccountId() + "not found");
		}

		//This is a hack to generate an account ID and set it on the CDM Party. Normally the ID would be
		//the User Reference and known ahead of time.
		//This example also does not change the TraderX UI interface or function signature.

		CdmAccountUser cdmAccountUser = new CdmAccountUser();
		cdmAccountUser.setAccountId(accountUser.getAccountId());
		cdmAccountUser.setUsername(accountUser.getUsername());
		this.cdmAccountUserRepository.save(cdmAccountUser);

		ObjectMapper rosettaObjectMapper = RosettaObjectMapper.getNewRosettaObjectMapper();
		Party cdmParty = Party.builder()
				.setPartyId(List.of(PartyIdentifier.builder()
						.setIdentifier(FieldWithMetaString.builder()
								.setValue(String.valueOf(cdmAccountUser.getAccountId())))))
				.setNameValue(accountUser.getUsername())
				.build();

		cdmAccountUser.setAccountId(cdmAccountUser.getAccountId());
		String CdmPartyJson = null;
		CdmPartyJson = RosettaObjectMapper.getNewRosettaObjectMapper().writeValueAsString(cdmParty);
		cdmAccountUser.setCdmAccountUserObj(CdmPartyJson);
		this.cdmAccountUserRepository.save(cdmAccountUser);

		accountUser.setAccountId(parseInt(cdmParty.getPartyId().get(0).getIdentifier().getValue()));
		accountUser.setUsername(cdmParty.getName().getValue().toString());

		return accountUser;
	}

}

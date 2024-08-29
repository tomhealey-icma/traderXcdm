package finos.traderx.accountservice.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "CDMACCOUNTUSERS")
@IdClass(AccountUserID.class)
public class CdmAccountUser implements Serializable {

	@Id
	@Column(name = "AccountID")
	Integer accountId;

	@Id
	@Column(name = "Username")
	String username;

	@Column(length = 500, name = "CdmAccountUserObj")
	private String cdmAccountUserObj;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
	public String getCdmAccountUserObj() {
			return this.cdmAccountUserObj;
		}

	public void setCdmAccountUserObj(String cdmAccountUserObj)  {this.cdmAccountUserObj = cdmAccountUserObj;}


}

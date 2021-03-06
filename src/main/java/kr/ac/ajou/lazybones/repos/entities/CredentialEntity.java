package kr.ac.ajou.lazybones.repos.entities;

import java.util.*;


public class CredentialEntity {
	private String cid;
	private String uid;
	private String issuedAt;
	
	public CredentialEntity() {
		
	}
	
	public CredentialEntity(String cid, String uid, String issue) {
		this.cid = cid;
		this.uid = uid;
		this.issuedAt = issue;
	}
	
	public String getCID() {
		return cid;
	}

	public void setCID(String cid) {
		this.cid = cid;
	}

	public String getUID() {
		return uid;
	}

	public void setUID(String uid) {
		this.uid = uid;
	}

	public String getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(String issue) {
		this.issuedAt = issue;
	}

}
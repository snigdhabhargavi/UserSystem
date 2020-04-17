package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {
	
	@Id
	private int addressid;
	private String addresstype;
	private String addr;
	public Address() {
		super();
	}
	public Address(int addressid, String addresstype, String addr) {
		super();
		this.addressid = addressid;
		this.addresstype = addresstype;
		this.addr = addr;
	}
	public int getAddressid() {
		return addressid;
	}
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}
	public String getAddresstype() {
		return addresstype;
	}
	public void setAddresstype(String addresstype) {
		this.addresstype = addresstype;
	}
	public String getAddress() {
		return addr;
	}
	public void setAddress(String address) {
		this.addr = address;
	}
}

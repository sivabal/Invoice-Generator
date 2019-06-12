package com.bill.beans;

public class Address {
	
	private String number;
	private String street;
	private String area;
	private String town;
	private String district;
	private String pincode;
	private String telephone;
	private String mobile;
	
	
	public Address(String number, String street, String area, String town, String district, String pincode,
			String telephone, String mobile) {
		this.number = number;
		this.street = street;
		this.area = area;
		this.town = town;
		this.district = district;
		this.pincode = pincode;
		this.telephone = telephone;
		this.mobile = mobile;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getTown() {
		return town;
	}


	public void setTown(String town) {
		this.town = town;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String toString() {
		return number + ", " + street +", " + area +", "+ town+", " + district+"-" + pincode+", " + telephone +"/"+ mobile;
	}
	
}

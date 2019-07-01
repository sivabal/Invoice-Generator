package com.bill.beans;

public class Address {
	
	private String shopName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String district;
	private String state;
	private String pincode;
	private String telephone;
	private String mobile;
	private String gstNo;
	

	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getGstNo() {
		return gstNo;
	}


	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}


	public String getAddressLine1() {
		return addressLine1;
	}


	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}


	public String getAddressLine2() {
		return addressLine2;
	}


	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
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
		return addressLine1 + ", " + addressLine2 +", " + city +", "+ district+"-" + pincode+ ", "
	+ state + ", "+ telephone +"/"+ mobile + ", " + gstNo;
	}
	
}

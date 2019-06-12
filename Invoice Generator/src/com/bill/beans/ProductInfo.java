package com.bill.beans;

public class ProductInfo {
	
	private String itemName;
	private Float price;
	private Float sgst;
	private Float gst;
	
	public ProductInfo(String itemName, Float price, Float sgst, Float gst) {
		super();
		this.itemName = itemName;
		this.price = price;
		this.sgst = sgst;
		this.gst = gst;
	}
	
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getSgst() {
		return sgst;
	}
	public void setSgst(Float sgst) {
		this.sgst = sgst;
	}
	public Float getGst() {
		return gst;
	}
	public void setGst(Float gst) {
		this.gst = gst;
	}

	
}

package com.bill.beans;

import java.text.DecimalFormat;

import javafx.scene.control.TextField;

public class BilledProducts {
	
	private Integer sno;
	private TextField itemName;
	private TextField quantity;
	private TextField unitRate;
	private TextField sgst;
	private TextField cgst;
	private TextField amount;
	private Float sgstTotal;
	private Float cgstTotal;
	private Float orderAmount;
	
	public BilledProducts(Integer sno, TextField itemName, TextField quantity, TextField unitRate, TextField sgst, TextField cgst, TextField amount) {
		this.sno = sno;
		this.itemName = itemName;
		this.quantity = quantity;
		this.unitRate = unitRate;
		this.sgst = sgst;
		this.cgst = cgst;
		this.amount = amount;
	}

	
	public Float getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Float orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Float getSgstTotal() {
		return sgstTotal;
	}


	public void setSgstTotal(Float sgstTotal) {
		this.sgstTotal = Float.valueOf(new DecimalFormat("#.##").format(sgstTotal));
	}


	public Float getCgstTotal() {
		return cgstTotal;
	}


	public void setCgstTotal(Float cgstTotal) {
		this.cgstTotal = Float.valueOf(new DecimalFormat("#.##").format(cgstTotal));
	}


	public Integer getSno() {
		return sno;
	}


	public void setSno(Integer sno) {
		this.sno = sno;
	}


	public TextField getItemName() {
		return itemName;
	}


	public void setItemName(TextField itemName) {
		this.itemName = itemName;
	}


	public TextField getQuantity() {
		return quantity;
	}


	public void setQuantity(TextField quantity) {
		this.quantity = quantity;
	}


	public TextField getUnitRate() {
		return unitRate;
	}


	public void setUnitRate(TextField unitRate) {
		this.unitRate = unitRate;
	}


	public TextField getSgst() {
		return sgst;
	}


	public void setSgst(TextField sgst) {
		this.sgst = sgst;
	}


	public TextField getCgst() {
		return cgst;
	}


	public void setCgst(TextField cgst) {
		this.cgst = cgst;
	}


	public TextField getAmount() {
		return amount;
	}


	public void setAmount(TextField amount) {
		this.amount = amount;
	}


	

}

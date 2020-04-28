package com.bill.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.bill.beans.Address;
import com.bill.beans.GoodsInStock;
import com.bill.beans.Inventory;
import com.bill.beans.SalesMade;
import com.bill.exception.DatabaseException;
import com.bill.exception.ExcelGeneratorException;
import com.bill.generator.ExcelGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class FromDatabase {
	
	
	public static Map<String, Float[]> getProductDetails() throws Exception{
		
		Map<String, Float[]> productInfo = new HashMap<>();
		String query = "select * from productDetails";
		try (PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				Float[] rateAndTax = new Float[4];
				rateAndTax[0] = Float.valueOf(resultSet.getString("rate"));
				rateAndTax[1] = Float.valueOf(resultSet.getString("sgst"));
				rateAndTax[2] = Float.valueOf(resultSet.getString("cgst"));
				rateAndTax[3] = Float.valueOf(resultSet.getString("prodId"));
				productInfo.put(resultSet.getString("prodName"), rateAndTax);
			}
			
		} 
		
		return productInfo;
	}

	public static String getLastInvoiceNumber()throws Exception {
		String query = "select max(invoiceNumber) from invoiceData";
		try (PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
				if(resultSet.getString("max(invoiceNumber)") == null)
					throw new DatabaseException("");

				return resultSet.getString("max(invoiceNumber)");
				
		}catch(Exception e) {
			throw e;
		}

	}
	
	/*
	 * 
	 */
	public static void getDataToCreateExcel(int fromDateDiff, int toDateDiff) throws Exception{
		String query = "SELECT ID.invoiceNumber, date, billFrom, billTo, ID.orderAmount, sgst, cgst, total, productName, "
				+ "qty, unitRate, BP.orderAmount as amountExTax, sgstTotal, sgstPercentage, cgstTotal, cgstPercentage, amount "
				+ "FROM (SELECT * FROM invoiceData where daysDifference >= ? and daysDifference <= ?) as ID "
				+ "INNER JOIN billedProducts as BP "
				+ "ON ID.invoiceNumber = BP.invoiceNumber";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setInt(1, fromDateDiff);
			preparedStmt.setInt(2, toDateDiff);
			ResultSet resultSet = preparedStmt.executeQuery();
			if(!resultSet.isBeforeFirst()) 
				throw new ExcelGeneratorException("No data available with in the provided date..");
			while(resultSet.next()) {
				ExcelGenerator.writeContent(resultSet);
			}
			
			resultSet.close();
		}
	}
	
	/*
	 * 
	 */
	public static Integer getLastProductId() throws Exception{
		String query = "select max(prodId) from productDetails";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			return resultSet.getInt("max(prodId)");
			
		} 
		
	}
	
	/*
	 * 
	 */
	public static void getProduct(int prodId, TextField updateProdName, TextField updateUnitRate,
			TextField updateSgst, TextField updateCgst)throws Exception {
		String query = "select prodName, rate, sgst, cgst from productDetails where prodId = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setInt(1, prodId);
			
			ResultSet resultSet = preparedStmt.executeQuery();
			
			updateProdName.setText(resultSet.getString("prodName"));
			updateUnitRate.setText(resultSet.getString("rate"));
			updateSgst.setText(resultSet.getString("sgst"));
			updateCgst.setText(resultSet.getString("cgst"));
			
			resultSet.close();
			
		} 
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getFromAddressShopNames()throws Exception{
		
		ObservableList<String> shopNames = FXCollections.observableArrayList();
		String query = "select shopName from fromAddress";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				shopNames.add(resultSet.getString("shopName"));
			}
			
		}
		return shopNames;
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getToAddressShopNames()throws Exception{
		
		ObservableList<String> shopNames = FXCollections.observableArrayList();
		shopNames.add("Counter Sales");
		
		String query = "select shopName from toAddress";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				shopNames.add(resultSet.getString("shopName"));
			}
			
		} 
		return shopNames;
	}
	
	/*
	 * 
	 */
	public static Address getFromAddress(String shopName)throws Exception{
		
		Address address = new Address();
		String query = "select * from fromAddress where shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setString(1, shopName);
			ResultSet resultSet = preparedStmt.executeQuery();
			resultSet = preparedStmt.executeQuery();
			
			address.setShopName(resultSet.getString("shopName"));
			address.setAddressLine1(resultSet.getString("addressLine1"));
			address.setAddressLine2(resultSet.getString("addressLine2"));
			address.setCity(resultSet.getString("city"));
			address.setDistrict(resultSet.getString("district"));
			address.setState(resultSet.getString("state"));
			address.setPincode(resultSet.getString("pincode"));
			address.setTelephone(resultSet.getString("telephone"));
			address.setMobile(resultSet.getString("mobile"));
			address.setGstNo(resultSet.getString("gstNo"));
			
			resultSet.close();
		} 
		return address;
	}
	
	/*
	 * 
	 */
	public static Address getToAddress(String shopName)throws Exception{
		
		Address address = new Address();
		String query = "select * from toAddress where shopName = ?";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			preparedStmt.setString(1, shopName);
			ResultSet resultSet = preparedStmt.executeQuery();
			
			address.setShopName(resultSet.getString("shopName"));
			address.setAddressLine1(resultSet.getString("addressLine1"));
			address.setAddressLine2(resultSet.getString("addressLine2"));
			address.setCity(resultSet.getString("city"));
			address.setDistrict(resultSet.getString("district"));
			address.setState(resultSet.getString("state"));
			address.setPincode(resultSet.getString("pincode"));
			address.setTelephone(resultSet.getString("telephone"));
			address.setMobile(resultSet.getString("mobile"));
			address.setGstNo(resultSet.getString("gstNo"));
			
			resultSet.close();
			
		} 
		return address;
	}
	
	/*
	 * 
	 */
	public static ObservableList<String> getInvoiceNumbers()throws Exception{
		
		ObservableList<String> invoice = FXCollections.observableArrayList();
		String query = "select invoiceNumber from invoiceData";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				invoice.add(resultSet.getString("invoiceNumber"));
			}
			
		}
		return invoice;
	}
	
	public static ObservableList<GoodsInStock> getGoodsInStock()throws Exception{
		
		ObservableList<GoodsInStock> goodsInStock = FXCollections.observableArrayList();
		
		String query = "select * from goodsProduced";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				GoodsInStock goods = new GoodsInStock();
				goods.setItemName(resultSet.getString("itemName"));
				goods.setDate(resultSet.getString("date"));
				goods.setLotNo(resultSet.getString("lotNo"));
				goods.setGoodsInStock(resultSet.getString("goodsProduced"));
				
				goodsInStock.add(goods);
			}
			
		}
		return goodsInStock;
	}
	
	public static ObservableList<GoodsInStock> getGoodsTracker()throws Exception{
		
		ObservableList<GoodsInStock> goodsInStock = FXCollections.observableArrayList();
		
		String query = "select * from goodsProducedTracker";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				GoodsInStock goods = new GoodsInStock();
				goods.setItemName(resultSet.getString("itemName"));
				goods.setDate(resultSet.getString("date"));
				goods.setLotNo(resultSet.getString("lotNo"));
				goods.setGoodsInStock(resultSet.getString("goodsProduced"));
				
				goodsInStock.add(goods);
			}
			
		}
		return goodsInStock;
	}
	
	public static ObservableList<Inventory> getInventory()throws Exception{
		
		ObservableList<Inventory> inventory = FXCollections.observableArrayList();
		
		String query = "select * from inventory";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				Inventory goods = new Inventory();
				goods.setItemName(resultSet.getString("itemName"));
				goods.setQuantity(resultSet.getString("qty"));
				goods.setPrice(resultSet.getString("price"));
				goods.setDate(resultSet.getString("date"));
				goods.setCgst(resultSet.getString("cgst"));
				goods.setSgst(resultSet.getString("sgst"));
				goods.setIgst(resultSet.getString("igst"));
				goods.setTpCharge(resultSet.getString("transportCharge"));
				goods.setTotalAmount(resultSet.getString("totalAmount"));
				
				inventory.add(goods);
			}
			
		}
		return inventory;
	}
	
	public static Float getGoodsProducedForItem(String itemName)throws Exception{
		
		
		String query = "select goodsProduced from goodsProduced where itemName = ?";
		Float goodsProduced = 0f;
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setString(1, itemName);
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while(resultSet.next()){
				goodsProduced = resultSet.getFloat("goodsProduced");
			}
			
			resultSet.close();
		}
		
		return goodsProduced;
	}
	
	public static ObservableList<SalesMade> getSalesMade(String date)throws Exception{
		
		ObservableList<SalesMade> salesMade = FXCollections.observableArrayList();
		
		String query = "select * from salesMade where date = ?";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setString(1, date);
			
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while(resultSet.next()){
				SalesMade sales = new SalesMade();
				sales.setItemName(resultSet.getString("itemName"));
				sales.setDate(resultSet.getString("date"));
				sales.setLotNo(resultSet.getString("lotNo"));
				sales.setGoodsTaken(resultSet.getString("goodsTakenForSale"));
				sales.setSalesmanName(resultSet.getString("salesManName"));
				sales.setGoodsReturned(resultSet.getString("goodsReturned"));
				sales.setGoodsSold(resultSet.getString("goodsSold"));
				
				
				salesMade.add(sales);
			}
			resultSet.close();
		}
		return salesMade;
	}
	
	public static ObservableList<SalesMade> getSalesMade()throws Exception{
		
		ObservableList<SalesMade> salesMade = FXCollections.observableArrayList();
		
		String query = "select * from salesMade";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while(resultSet.next()){
				SalesMade sales = new SalesMade();
				sales.setItemName(resultSet.getString("itemName"));
				sales.setDate(resultSet.getString("date"));
				sales.setLotNo(resultSet.getString("lotNo"));
				sales.setGoodsTaken(resultSet.getString("goodsTakenForSale"));
				sales.setSalesmanName(resultSet.getString("salesManName"));
				sales.setGoodsReturned(resultSet.getString("goodsReturned"));
				sales.setGoodsSold(resultSet.getString("goodsSold"));
				
				
				salesMade.add(sales);
			}
			resultSet.close();
		}
		return salesMade;
	}
	
	public static String getPassword()throws Exception{
		
		String password = "0000";
		
		String query = "select value from credentials where key = 'password'";
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);
				ResultSet resultSet = preparedStmt.executeQuery();){
			
			while(resultSet.next()){
				password = resultSet.getString("value");
			}
			
		}
		return password;
	}
	
	public static ObservableList<SalesMade> getBilledProducts(String invoiceNo)throws Exception{
		
		ObservableList<SalesMade> billedProducts = FXCollections.observableArrayList();
		
		String query = "select * from billedProducts where invoiceNumber = ?";
		
		try(PreparedStatement preparedStmt = GetConnection.connection.prepareStatement(query);){
			
			preparedStmt.setString(1, invoiceNo);
			
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while(resultSet.next()){
				SalesMade sales = new SalesMade();
				sales.setItemName(resultSet.getString("productName"));
				sales.setGoodsTaken(resultSet.getString("qty"));
				
				billedProducts.add(sales);
			}
			resultSet.close();
		}
		return billedProducts;
	}
}

package com.bill.invoicewindow;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.controlsfx.control.textfield.TextFields;

import com.bill.beans.Address;
import com.bill.beans.BilledProducts;
import com.bill.pdf.PDFGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceController implements Initializable{
	
	
	public static void main(String[] args) {
		
		new InvoiceController().generatePdf();
		
	}
	
	
	public ObservableList<String> fromItems = FXCollections.observableArrayList();
	public ObservableList<String> placeItems = FXCollections.observableArrayList();
	public ObservableList<String> toItems = FXCollections.observableArrayList();
	public ObservableList<BilledProducts> billRow = FXCollections.observableArrayList();
	public Map<String, ArrayList<Float>> productInfo = new HashMap<>();
	
	public String[] suggestions = {"Kulambu milagai thool", "Groundnut oil","Sesame oil", "Turmeric powder", "Chilli powder", 
							"Coriander powder", "Pepper powder", "Kumin powder"};
	
	public Address fromAddress = new Address("261", "No.2 Main Road", "Sitharkadum", "Mayiladuthurai", "", "609003","04364-259338", "9442419772");
	public Address toAddress = new Address("261", "No.2 Main Road", "Sitharkadum", "Mayiladuthurai", "", "609003","04364-259338", "9442419772");
	
	

	
	@FXML private Label invoiceNumber;
	@FXML private Label invoiceDate;
	@FXML private Label total;
	
	@FXML private ComboBox<String> fromComboBox;
	
	@FXML private TextArea fromTextArea;

	@FXML private ComboBox<String> toComboBox;

	@FXML private TextArea toTextArea;

	@FXML private ComboBox<String> placeComboBox;
	
	@FXML private TableView<BilledProducts> tableView;
	@FXML private TableColumn<BilledProducts, Integer> sno;
	@FXML private TableColumn<BilledProducts, TextField> itemName;
	@FXML private TableColumn<BilledProducts, TextField> quantity;
	@FXML private TableColumn<BilledProducts, TextField> unitRate;
	@FXML private TableColumn<BilledProducts, TextField> sgst;
	@FXML private TableColumn<BilledProducts, TextField> cgst;
	@FXML private TableColumn<BilledProducts, TextField> amount;



	/**
	 * this method will initialize the values in the scenes
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		initializeDropdowns();
		initializeBillingTable();
		invoiceDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
		invoiceNumber.setText("INV-500123");
		total.setText("0.0");
		tableView.selectionModelProperty().getValue().selectFirst();
		
		productInfo.put("Kulambu milagai thool",new ArrayList<>(Arrays.asList(75.0f,2.5f,2.5f)));
		productInfo.put("Groundnut oil",new ArrayList<>(Arrays.asList(120f,2.5f,2.5f)));
		productInfo.put("Sesame oil",new ArrayList<>(Arrays.asList(200f,2.5f,2.5f)));
		productInfo.put("Turmeric powder",new ArrayList<>(Arrays.asList(80f,2.5f,2.5f)));
		productInfo.put("Chilli powder",new ArrayList<>(Arrays.asList(65f,2.5f,2.5f)));
		productInfo.put("Coriander powder",new ArrayList<>(Arrays.asList(70f,2.5f,2.5f)));
		productInfo.put("Pepper powder",new ArrayList<>(Arrays.asList(90f,2.5f,2.5f)));
		productInfo.put("Kumin powder",new ArrayList<>(Arrays.asList(85f,2.5f,2.5f)));
	}	
	
	/**
	 * this method will initialize the dropdowns
	 */
	public void initializeDropdowns() {
		
		fromItems.addAll("S.K.P Foods and Masalas", "Pillai Masalas");
		fromComboBox.getItems().addAll(fromItems);
		fromComboBox.selectionModelProperty().getValue().selectFirst();
		fromTextArea.setText(fromAddress.toString());
		
		toItems.addAll("S.K.P Foods and Masalas", "Pillai Masalas");
		toComboBox.getItems().addAll(toItems);
		toComboBox.selectionModelProperty().getValue().selectFirst();
		toTextArea.setText(toAddress.toString());
				
		placeItems.addAll("Tamil Nadu", "Karaikkal");
		placeComboBox.getItems().addAll(placeItems);
		placeComboBox.selectionModelProperty().getValue().selectFirst();
	}
	
	/**
	 * this method will initialize the billing table
	 */
	public void initializeBillingTable() {
		
		sno.setCellValueFactory(new PropertyValueFactory<BilledProducts, Integer>("sno"));
		itemName.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("itemName"));
		quantity.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("quantity"));
		unitRate.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("unitRate"));
		sgst.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("sgst"));
		cgst.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("cgst"));
		amount.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("amount"));
		tableView.setItems(createNewRow());
	}
	
	
	/**
	 * 
	 this method inserts the address to the text area when user selects the company in 
	 "From" dropdown.
	 */
	@FXML
	public void onClickFromComboBox(ActionEvent event){
		fromTextArea.setText(fromAddress.toString());
		
	}
	
	/**
	 * 
	 this method inserts the address to the text area when user selects the company in 
	 "To" dropdown.
	 */
	@FXML
	public void onClickToComboBox(ActionEvent event){
		toTextArea.setText(toAddress.toString());
		
	}
	
	/**
	 * this method will create a new row for the billing table each time it is called.
	 */
	public ObservableList<BilledProducts> createNewRow(){
		
		TextField itemNameField = new TextField("");
		TextField quantityField = new TextField("1");
		TextField unitRateField = new TextField("0.0");
		TextField sgstField = new TextField("0.0");
		TextField cgstField = new TextField("0.0");
		TextField amountField = new TextField("0.0");
		
		setActionProperties(itemNameField,quantityField,unitRateField, sgstField,cgstField,amountField);

		billRow.add(new BilledProducts(billRow.size()+1, itemNameField,quantityField,unitRateField, sgstField,cgstField,amountField));
		return billRow;
	}
	
	/**
	 * this method will set the action properties for the newly created billrow
	 */
	public void setActionProperties(TextField itemNameField, TextField quantityField, TextField unitRateField, TextField sgstField, TextField cgstField, TextField amountField) {
		
		itemNameField.setOnAction((actionEvent) -> {
				BilledProducts currentlySelected = tableView.getSelectionModel().getSelectedItem();
				currentlySelected.setSgstTotal((productInfo.get(currentlySelected.getItemName().getText()).get(1)*
						(productInfo.get(currentlySelected.getItemName().getText()).get(0) * Float.parseFloat(currentlySelected.getQuantity().getText())))/100);
				
				currentlySelected.setCgstTotal((productInfo.get(currentlySelected.getItemName().getText()).get(2)*
						(productInfo.get(currentlySelected.getItemName().getText()).get(0) * Float.parseFloat(currentlySelected.getQuantity().getText())))/100);
				
				currentlySelected.getAmount().setText(new DecimalFormat("#.##").format(
					(productInfo.get(currentlySelected.getItemName().getText()).get(0) * Float.parseFloat(currentlySelected.getQuantity().getText()))
					+ currentlySelected.getSgstTotal() 
					+ currentlySelected.getCgstTotal()));
				currentlySelected.getUnitRate().setText(productInfo.get(currentlySelected.getItemName().getText()).get(0).toString());
				currentlySelected.getSgst().setText(productInfo.get(currentlySelected.getItemName().getText()).get(1).toString());
				currentlySelected.getCgst().setText(productInfo.get(currentlySelected.getItemName().getText()).get(2).toString());
				
				Float sum = billRow.stream().map(x -> Float.parseFloat(x.getAmount().getText())).reduce(0.0f, (a,b) -> a+b);		
				total.setText(new DecimalFormat("#.##").format(sum)); 
				
		});
	
		itemNameField.textProperty().addListener((observableValue,oldValue,newValue) -> {
				TextFields.bindAutoCompletion(itemNameField, suggestions);	
		});
		
		itemNameField.focusedProperty().addListener((observableValue,oldValue,newValue) -> {
			if(newValue) {
					List<BilledProducts> currentlyFocused = billRow.stream().filter(x -> x.getItemName().equals(itemNameField)).collect(Collectors.toList());
					tableView.selectionModelProperty().getValue().select(currentlyFocused.get(0));
				}
		});
		
		quantityField.setOnAction((actionEvent) -> {
			
				BilledProducts currentlySelected = tableView.getSelectionModel().getSelectedItem();				
				currentlySelected.setSgstTotal((productInfo.get(currentlySelected.getItemName().getText()).get(1)*
						(productInfo.get(currentlySelected.getItemName().getText()).get(0) * Float.parseFloat(currentlySelected.getQuantity().getText())))/100);
				
				currentlySelected.setCgstTotal((productInfo.get(currentlySelected.getItemName().getText()).get(2)*
						(productInfo.get(currentlySelected.getItemName().getText()).get(0) * Float.parseFloat(currentlySelected.getQuantity().getText())))/100);
				
				currentlySelected.getAmount().setText(new DecimalFormat("#.##").format(
					(productInfo.get(currentlySelected.getItemName().getText()).get(0) * Float.parseFloat(currentlySelected.getQuantity().getText()))
					+ currentlySelected.getSgstTotal() 
					+ currentlySelected.getCgstTotal()));
				
				Float sum = billRow.stream().map(x -> Float.parseFloat(x.getAmount().getText())).reduce(0.0f, (a,b) -> a+b);
				total.setText(new DecimalFormat("#.##").format(sum)); 
		});
		
		quantityField.focusedProperty().addListener((observableValue,oldValue,newValue) -> {
			if(newValue) {
					List<BilledProducts> currentlyFocused = billRow.stream().filter(x -> x.getQuantity().equals(quantityField)).collect(Collectors.toList());
					tableView.selectionModelProperty().getValue().select(currentlyFocused.get(0));
				}
		});
		
		unitRateField.focusedProperty().addListener((observableValue,oldValue,newValue) -> {
			if(newValue) {
				List<BilledProducts> currentlyFocused = billRow.stream().filter(x -> x.getUnitRate().equals(unitRateField)).collect(Collectors.toList());
				tableView.selectionModelProperty().getValue().select(currentlyFocused.get(0));
			}
		});
		
		sgstField.focusedProperty().addListener((observableValue,oldValue,newValue) -> {
			if(newValue) {
					List<BilledProducts> currentlyFocused = billRow.stream().filter(x -> x.getSgst().equals(sgstField)).collect(Collectors.toList());
					tableView.selectionModelProperty().getValue().select(currentlyFocused.get(0));
				}
			
		});
		
		cgstField.focusedProperty().addListener((observableValue,oldValue,newValue) -> {
			if(newValue) {
					List<BilledProducts> currentlyFocused = billRow.stream().filter(x -> x.getCgst().equals(cgstField)).collect(Collectors.toList());
					tableView.selectionModelProperty().getValue().select(currentlyFocused.get(0));
				}
		});
		
		amountField.focusedProperty().addListener((observableValue,oldValue,newValue) -> {
			if(newValue) {
					List<BilledProducts> currentlyFocused = billRow.stream().filter(x -> x.getAmount().equals(amountField)).collect(Collectors.toList());
					tableView.selectionModelProperty().getValue().select(currentlyFocused.get(0));
				}
		});
		
	}
	
	/**
	 * this method will delete any selected bill row
	 */
	public void deleteRow() {
		BilledProducts currentlySelected = tableView.getSelectionModel().getSelectedItem();
		if(currentlySelected != null) {
			int i = billRow.indexOf(currentlySelected);
			total.setText(new DecimalFormat("#.##").format(Float.parseFloat(total.getText()) - Float.parseFloat(currentlySelected.getAmount().getText())));
			billRow.remove(currentlySelected);	
					
			for(int j = i; j < billRow.size(); j++) {
				billRow.get(j).setSno(billRow.get(j).getSno()-1);
			}
		}
		
	}
	
	/**
	 * this method will be called when "print" button is clicked
	 */
	public void generatePdf() {
		
		
		try {
			
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			
			PDFGenerator.drawTitleTable(fromComboBox.getValue(), fromAddress, document, page);
			PDFGenerator.drawInvoiceTable(toComboBox.getValue(), toAddress, invoiceNumber.getText(), invoiceDate.getText()
					, placeComboBox.getValue(), document, page);
			float yPosition = PDFGenerator.drawProductsTable(billRow, document, page);
			PDFGenerator.drawTotalTable(billRow, Float.parseFloat(total.getText()), document, page, yPosition);
			
			document.addPage(page);
			document.save("C:\\Users\\welcome\\Desktop\\"+ invoiceNumber.getText() +".pdf");
			document.close();
			
			System.out.println("Invoice generated");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.bill.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.controlsfx.control.textfield.TextFields;

import com.bill.beans.Address;
import com.bill.beans.BilledProducts;
import com.bill.generator.PDFGenerator;
import com.bill.popus.ShowPopups;
import com.bill.utility.Utility;
import com.bill.validator.FromDatabasevalidator;
import com.bill.validator.ToDatabaseValidator;
import com.bill.validator.ValidateUserInputs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvoiceController implements Initializable{
	

	
	public ObservableList<String> placeItems = FXCollections.observableArrayList();
	public ObservableList<BilledProducts> billRow = FXCollections.observableArrayList();
	
	public Float tempQty;
	public Float[] tempPrdDetail;
	Float tempSum;
	public DecimalFormat decimalFormat = new DecimalFormat("#.##");
	public String[] suggestions = null;
	
	public Address fromAddress;
	public Address toAddress;
	
	@FXML private TextField invoiceNumber;
	@FXML private DatePicker invoiceDate;
	@FXML private Label total;
	@FXML private ComboBox<String> fromComboBox;
	@FXML private TextArea fromTextArea;
	@FXML private ComboBox<String> toComboBox;
	@FXML private TextArea toTextArea;
	@FXML private ComboBox<String> placeComboBox;
	@FXML private Button print;
	@FXML private Button printAndSave;
	@FXML private ProgressBar progressBar;
	@FXML private Label progressLabel;
	
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
		
		try {
			suggestions = Utility.productInfo.keySet().toArray(new String[Utility.productInfo.size()]);
			initializeDropdowns();
			initializeBillingTable();
			//invoiceDate.set(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")));
			invoiceDate.setValue(LocalDate.now());
			invoiceNumber.setText(FromDatabasevalidator.getLastInvoiceNumber());
			total.setText("0.0");
			tableView.selectionModelProperty().getValue().selectFirst();

			progressBar.setOpacity(0);
			progressLabel.setOpacity(0);
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}	
	
	/**
	 * this method will initialize the dropdowns
	 */
	public void initializeDropdowns() {
		
		try {
			fromComboBox.getItems().addAll(Utility.fromAddressShopNames);
			
			toComboBox.getItems().addAll(Utility.toAddressShopNames);
					
			placeItems.addAll("Tamil Nadu", "Karaikkal");
			placeComboBox.getItems().addAll(placeItems);
			placeComboBox.selectionModelProperty().getValue().selectFirst();
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/**
	 * this method will initialize the billing table
	 */
	public void initializeBillingTable() {
		
		try {
			sno.setCellValueFactory(new PropertyValueFactory<BilledProducts, Integer>("sno"));
			itemName.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("itemName"));
			quantity.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("quantity"));
			unitRate.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("unitRate"));
			sgst.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("sgst"));
			cgst.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("cgst"));
			amount.setCellValueFactory(new PropertyValueFactory<BilledProducts, TextField>("amount"));
			tableView.setItems(createNewRow());
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/*
	 * 
	 */
	@FXML
	public void newBill() {
		try {
			if(ShowPopups.showPopups(AlertType.CONFIRMATION, "This action will overwrite the old bill. Want to Continue?", "")) {
				billRow.clear();
				total.setText("0.0");
				invoiceDate.setValue(LocalDate.now());
				invoiceNumber.setText(FromDatabasevalidator.getLastInvoiceNumber());
				createNewRow();
			}
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
	}
	
	/**
	 * 
	 this method inserts the address to the text area when user selects the company in 
	 "From" dropdown.
	 */
	@FXML
	public void onClickFromComboBox(ActionEvent event){
		try {
			fromAddress = FromDatabasevalidator.getFromAddress(fromComboBox.getValue());
			fromTextArea.setText(fromAddress.toString());
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	/**
	 * 
	 this method inserts the address to the text area when user selects the company in 
	 "To" dropdown.
	 */
	@FXML
	public void onClickToComboBox(ActionEvent event){
		try {
			if(toComboBox.getValue().equals("Counter Sales")) {
				toAddress = null;
				toTextArea.setText("");
			}
			else {
				toAddress = FromDatabasevalidator.getToAddress(toComboBox.getValue());
				toTextArea.setText(toAddress.toString());
			}
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
	}
	
	/**
	 * this method will create a new row for the billing table each time it is called.
	 */
	@FXML
	public ObservableList<BilledProducts> createNewRow(){
		
		try {
			TextField itemNameField = new TextField("");
			TextField quantityField = new TextField("1");
			TextField unitRateField = new TextField("0.0");
			unitRateField.setEditable(false);
			TextField sgstField = new TextField("0.0");
			sgstField.setEditable(false);
			TextField cgstField = new TextField("0.0");
			cgstField.setEditable(false);
			TextField amountField = new TextField("0.0");
			amountField.setEditable(false);
			
			setActionProperties(itemNameField,quantityField,unitRateField, sgstField,cgstField,amountField);

			billRow.add(new BilledProducts(billRow.size()+1, itemNameField,quantityField,unitRateField, sgstField,cgstField,amountField));
			
		} catch (Exception e) {
			ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
		}
		
		return billRow;
	}
	
	/**
	 * this method will set the action properties for the newly created billrow
	 */
	public void setActionProperties(TextField itemNameField, TextField quantityField, TextField unitRateField, TextField sgstField, TextField cgstField, TextField amountField) {
		
		itemNameField.setOnAction((actionEvent) -> {
				try {
					BilledProducts currentlySelected = tableView.getSelectionModel().getSelectedItem();
					
					tempQty = Float.parseFloat(currentlySelected.getQuantity().getText());
					tempPrdDetail = Utility.productInfo.get(currentlySelected.getItemName().getText());
					
					currentlySelected.setOrderAmount(tempPrdDetail[0] * tempQty);
					currentlySelected.setSgstTotal((tempPrdDetail[1]*(tempPrdDetail[0] * tempQty))/100);
					currentlySelected.setCgstTotal((tempPrdDetail[2]*(tempPrdDetail[0] * tempQty))/100);
					currentlySelected.getAmount().setText(new DecimalFormat("#.##").format(
						(tempPrdDetail[0] * tempQty)+ currentlySelected.getSgstTotal() + currentlySelected.getCgstTotal()));
					currentlySelected.getUnitRate().setText(tempPrdDetail[0].toString());
					currentlySelected.getSgst().setText(tempPrdDetail[1].toString());
					currentlySelected.getCgst().setText(tempPrdDetail[2].toString());
					
					tempSum = billRow.stream().map(x -> Float.parseFloat(x.getAmount().getText())).reduce(0.0f, (a,b) -> a+b);		
					total.setText(decimalFormat.format(tempSum));
				} catch (NumberFormatException e) {
					ShowPopups.showPopups(AlertType.ERROR, "Please Provide Valid Quantity..", "");
				} catch(NullPointerException e) {
					ShowPopups.showPopups(AlertType.ERROR, "Please Provide Valid Product Name..", "");
				}catch (Exception e) {
					ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
				}
				
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
			
				try {
					BilledProducts currentlySelected = tableView.getSelectionModel().getSelectedItem();		
					
					tempQty = Float.parseFloat(currentlySelected.getQuantity().getText());
					tempPrdDetail = Utility.productInfo.get(currentlySelected.getItemName().getText());
					
					if(tempQty < 0)
						throw new NumberFormatException();
					
					currentlySelected.setOrderAmount(tempPrdDetail[0] * tempQty);
					currentlySelected.setSgstTotal((tempPrdDetail[1]*(tempPrdDetail[0] * tempQty))/100);
					currentlySelected.setCgstTotal((tempPrdDetail[2]*(tempPrdDetail[0] * tempQty))/100);
					currentlySelected.getAmount().setText(new DecimalFormat("#.##").format(
						(tempPrdDetail[0] * tempQty) + currentlySelected.getSgstTotal() + currentlySelected.getCgstTotal()));
					tempSum = billRow.stream().map(x -> Float.parseFloat(x.getAmount().getText())).reduce(0.0f, (a,b) -> a+b);
					total.setText(decimalFormat.format(tempSum));
					
				} catch (NumberFormatException e) {
					ShowPopups.showPopups(AlertType.ERROR, "Please Provide Valid Quantity..", "");
				} catch(NullPointerException e) {
					ShowPopups.showPopups(AlertType.ERROR, "Please Provide Valid Product Name..", "");
				}catch (Exception e) {
					ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
				}
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
	@FXML
	public void deleteRow() {
		BilledProducts currentlySelected = tableView.getSelectionModel().getSelectedItem();
		if(currentlySelected != null) {
			int i = billRow.indexOf(currentlySelected);
			total.setText(decimalFormat.format(Float.parseFloat(total.getText()) - Float.parseFloat(currentlySelected.getAmount().getText())));
			billRow.remove(currentlySelected);	
					
			for(int j = i; j < billRow.size(); j++) {
				billRow.get(j).setSno(billRow.get(j).getSno()-1);
			}
		}
		
	}
	
	@FXML
	public void print() {
		generatePdf(false);
	}
	
	@FXML
	public void printAndSave() {
		generatePdf(true);
	}
	
	/**
	 * 
	 */
	public void generatePdf(boolean save) {

			if(ValidateUserInputs.validateInvoiceDetails(fromComboBox.getValue(),toComboBox.getValue(),invoiceNumber.getText())
					&& ValidateUserInputs.validateBilledRow(billRow)) {
				try {
					
					print.setDisable(true);
					printAndSave.setDisable(true);
					progressBar.setOpacity(1);
					progressLabel.setOpacity(1);

					
					Task<String> task = new Task<String>() {

						@Override
						protected String call() throws Exception {

							try {
								
								updateMessage("Please Wait..");
								updateProgress(20, 100);

								Float sgstTotal = (float)billRow.stream().mapToDouble(x -> x.getSgstTotal()).reduce(0, (a, b) -> a+b);
								Float cgstTotal = (float)billRow.stream().mapToDouble(x -> x.getCgstTotal()).reduce(0, (a, b) -> a+b);
								Float orderAmount = (float)billRow.stream().mapToDouble(x -> x.getOrderAmount()).reduce(0, (a,b) -> a+b);
								Integer roundTotal = Math.round(Float.parseFloat(total.getText()));

								PDDocument document = new PDDocument();
								PDPage page = new PDPage();

								updateProgress(40, 100);

								float yPosition = PDFGenerator.drawTitleTable(fromComboBox.getValue(), fromAddress, document, page);
								yPosition = PDFGenerator.drawInvoiceTable(toComboBox.getValue(), toAddress, invoiceNumber.getText(), invoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))
										, placeComboBox.getValue(), document, page, yPosition);

								updateProgress(60, 100);

								yPosition = PDFGenerator.drawProductsTable(billRow, document, page, yPosition);
								PDFGenerator.drawTotalTable(orderAmount, sgstTotal, cgstTotal, roundTotal, document, page,yPosition);

								document.addPage(page);
								document.save(Utility.invoicePath + invoiceNumber.getText() +".pdf");
								document.close();

								updateProgress(80, 100);
								if(save) {
									ToDatabaseValidator.insertInvoiceDataAndBilledProducts(billRow, invoiceNumber.getText(), invoiceDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")), fromAddress, 
											toAddress,fromComboBox.getValue(), toComboBox.getValue(), orderAmount, sgstTotal, cgstTotal, roundTotal);
								}

								updateProgress(100, 100);
								
								return "SUCCESS";
							} catch (Exception e) {
								throw e;
							}
						}
					};
					
					task.setOnFailed(new EventHandler<WorkerStateEvent>() {

						@Override
						public void handle(WorkerStateEvent event) {
							
							progressBar.setOpacity(0);
							progressLabel.setOpacity(0);
							print.setDisable(false);
							printAndSave.setDisable(false);
							
							Throwable e = task.getException();
							
							if(e.getMessage().contains("UNIQUE"))
								ShowPopups.showPopups(AlertType.ERROR, "This invoice number '"+invoiceNumber.getText()+"' is already"
										+ " present in the database. Please provide the next number and try again..", "");
							else
								ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
							
						}
						
					});
					task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						
						@Override
						public void handle(WorkerStateEvent event) {
							
							progressBar.setOpacity(0);
							progressLabel.setOpacity(0);
							print.setDisable(false);
							printAndSave.setDisable(false);
							
							ShowPopups.showPopups(AlertType.INFORMATION, "Success....", (save)?"Invoice is Generated and Saved Successfully....":"Invoice is Generated Successfully....");
							
						}
					});
					
					progressBar.progressProperty().bind(task.progressProperty());
					progressLabel.textProperty().bind(task.messageProperty());
					
					ExecutorService executorService = Executors.newSingleThreadExecutor();
					executorService.execute(task);
					
					
				} catch (Exception e) {
					ShowPopups.showPopups(AlertType.ERROR, e.toString(), "");
				} 
			}

	}

	
}

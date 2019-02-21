package storeUpdatePage;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import storePage.ProductsData;

public class StoreUpdatePageOptions {
	
	public static boolean isConnectedToDB(Connection conn, Label label) {
		if (conn != null) {
			label.setText("");
			System.out.println("Class StoreUpdatePageModel: Store app controller connected.");
			return true;
		} else {
			label.setText("Error: Not connected to DB");
			System.out.println("Class StoreSalePageModel: Store app controller not connected.");
			return false;
		}	
	}
	
	public static void currentTableRowClicked(TableView<ProductsData> table, Label id, Label name, Label price, Label quantity) {
		if (table.getSelectionModel().isEmpty()) {
			name.setText("Product name: ");
			id.setText("Product ID: ");
			price.setText("Product price: ");
			quantity.setText("Product quantity: ");
		} else {
			name.setText("Product name: " + table.getSelectionModel().getSelectedItem().getName());
			id.setText("Product ID: " + table.getSelectionModel().getSelectedItem().getId());
			price.setText("Product price: " + table.getSelectionModel().getSelectedItem().getPrice() + " $");
			quantity.setText("Product quantity: " + table.getSelectionModel().getSelectedItem().getQuantity() + " piece(s)");
		}
		
	}
	
	public static void showUpdateTextfields(TableView<ProductsData> table, TextArea newNameTextarea, TextField newIdTextfield, TextField newQuantityTextfield, TextField newPriceTextfield, 
										    Label productUpdateInfo) {
		if (table.getSelectionModel().isEmpty()) {
			newNameTextarea.setEditable(false);
			newIdTextfield.setEditable(false);
			newQuantityTextfield.setEditable(false);
			newPriceTextfield.setEditable(false);
			
			newNameTextarea.setOpacity(0.5);
			newIdTextfield.setOpacity(0.5);
			newQuantityTextfield.setOpacity(0.5);
			newPriceTextfield.setOpacity(0.5);
			
			productUpdateInfo.setText("Please select an item from the table in order to edit it!");
		} else {
			newNameTextarea.setEditable(true);
			newIdTextfield.setEditable(true);
			newQuantityTextfield.setEditable(true);
			newPriceTextfield.setEditable(true);
			
			newNameTextarea.setOpacity(1);
			newIdTextfield.setOpacity(1);
			newQuantityTextfield.setOpacity(1);
			newPriceTextfield.setOpacity(1);
			
			productUpdateInfo.setText("Item " + table.getSelectionModel().getSelectedItem().getName() + ", with ID " + table.getSelectionModel().getSelectedItem().getId() + ", is ready to be updated");
		}
	}
	
	public static boolean checkIdInput(TextField newIdTextfield, Label productUpdateInfo) {
		
		
		if (newIdTextfield.getText().isEmpty()) {
			return true;	//empty field, it remains the same			
		} else {
			String id = newIdTextfield.getText();
			if (id.length() != 15) {
				productUpdateInfo.setText("Please input a 15 digits ID! Currently there are " + id.length()+ " digits.");
				return false;
			}
			
			boolean firstDigit = newIdTextfield.getText().substring(0, 1).matches("^[0-9]+$");
			if (!firstDigit) {
				productUpdateInfo.setText("Invalid first digit for the ID serial code. Please consult the manual for the correct ID input!");
				return false;				
			}
						
			boolean checkId = validateId(id, productUpdateInfo);
			
			if (checkId) {
				return true;
			} else {
				return false;
			}
		}
		
		
	}
	
	private static boolean validateId(String id, Label productUpdateInfo) {
		
		//check if date is correct
		boolean correctDate = true;
		String date = id.substring(1, 9);
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDate checkDate = null;
		
		try {
			checkDate = LocalDate.parse(date, dateFormat);
			System.out.println("Correct date " + checkDate);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			productUpdateInfo.setText("Invalid date for the ID serial code. Please consult the manual for the correct ID input!");			
			correctDate = false;
			System.out.println("Wrong date. Date " + date + " is not valid according to ddMMyyyy pattern");
		}

		//check if HEX serial is correct
		boolean isHex = id.substring(9).matches("^[0-9a-fA-F]+$");		
		
		//return true if the date and hex serial code are valid
		if (isHex) {
			System.out.println("Correct hex number: " + id.substring(9));
			if (correctDate) {
				return true;
			} else {
				return false;
			}
		} else {
			productUpdateInfo.setText("Invalid hex for the ID serial code. Please consult the manual for the correct ID input!");
			return false;
		}
		
	}
	
	public static void decipherId(TextField newIdTextfield) {
		//decipher ID according to the comment below the method
	}
	
	/*
	products ID formula:
		ADDMMYYYYNNNNNN
		replace A with:
			CPU: 1
			GPU: 2
			RAM: 3
			HDD: 4
			SSD: 5
			MOTHERBOARD: 6
			COOLER: 7
			CASE: 8
			POWER SUPPLY: 9
			OTHERS: 0
		replace DDMMYYYY with fabrication date
		replace NNNNNN with a base16 serial number
	*/
	
	public static boolean checkQuantityInput(TextField newQuantityTextfield, Label productUpdateInfo) {
		if (newQuantityTextfield.getText().isEmpty()) {
			return true;
		} else {
			long quantity = 0;
			try {
				quantity = Long.parseLong(newQuantityTextfield.getText());
				System.out.println("input quantity " + quantity);
			} catch (NumberFormatException e) {
				productUpdateInfo.setText("Wrong quantity. Please input only integer numbers!");
				e.printStackTrace();
				return false;
			}
			
			return true;
		}		
		
	}
	
	public static boolean checkPriceInput(TextField newPriceTextfield, Label productUpdateInfo) {
		if (newPriceTextfield.getText().isEmpty()) {
			return true;
		} else {
			long price = 0;
			try {
				price = Long.parseLong(newPriceTextfield.getText());
				System.out.println("input price " + price);
			} catch (NumberFormatException e) {
				productUpdateInfo.setText("Wrong price. Please input a float or integer number!");
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		
	}
	
	
	
	

}

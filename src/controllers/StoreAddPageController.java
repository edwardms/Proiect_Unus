package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbUtil.ConnectToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import storeAddPage.StoreAddPageOptions;
import storePage.ProductsData;
import storeSalePage.StoreSalePageOptions;
import storeUpdatePage.StoreUpdatePageOptions;

public class StoreAddPageController implements Initializable {
	
	@FXML
	private TableView<ProductsData> productsAddTable;
	
	@FXML
	private TableColumn<ProductsData, String> idAddColumn;
	
	@FXML
	private TableColumn<ProductsData, String> nameAddColumn;
	
	@FXML
	private TableColumn<ProductsData, String> priceAddColumn;
	
	@FXML
	private TableColumn<ProductsData, String> quantityAddColumn;
	
	@FXML
	private TextField searchFieldAdd;
	
	@FXML
	private Button searchButtonAdd;
	
	@FXML
	private Button displayAllProductsButtonAdd;
	
	@FXML
	private Label addedProductName;
	
	@FXML
	private Label addedProductId;
	
	@FXML
	private Label addedProductQuantity;
	
	@FXML
	private Label addedProductPrice;
	
	@FXML
	private TextArea addNameTextarea;
	
	@FXML
	private TextField addIdTextfield;
	
	@FXML
	private TextField addQuantityTextfield;
	
	@FXML
	private TextField addPriceTextfield;
	
	@FXML
	private Button addProductButton;
	
	@FXML
	private Label productAddInfo;
	
	private String sql;
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	private ObservableList<ProductsData> productsList;
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		productAddInfo.setText("Complete all the boxes above and press 'Add product' button in order to add an item in the database\n" +
							   "Tip: first search if the item is already in the database\n\n" + 
							   "Select an item from the right table and press 'Delete product' button to delete an item from the database");
		try {
			displayAllProductsAdd();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void displayAllProductsAdd() throws SQLException {
		
		sql = "SELECT * FROM products WHERE id";
		
		try {
			conn = ConnectToDB.connectToDataBase();
			StoreAddPageOptions.isConnectedToDB(conn, productAddInfo);
			productsList = FXCollections.observableArrayList();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			
			while (rs.next()) {
				productsList.add(new ProductsData(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		
		idAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("id"));
		nameAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
		priceAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
		quantityAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
		
		productsAddTable.setItems(null);
		productsAddTable.setItems(productsList);	
		
	}
	
	@FXML
	private void searchProductAdd() throws SQLException {
		
		sql = "SELECT * FROM products WHERE id LIKE('%" + searchFieldAdd.getText() + "%') OR name LIKE('%" + searchFieldAdd.getText() + "%')";
		
		if (searchFieldAdd.getText().isEmpty()) {
			productAddInfo.setText("WARNING: Search field is empty");
		} else {
			
			try {
				conn = ConnectToDB.connectToDataBase();
				StoreSalePageOptions.isConnectedToDB(conn, productAddInfo);	
				productsList = FXCollections.observableArrayList();
				
				rs = conn.prepareStatement(sql).executeQuery();
				
				while (rs.next()) {
					productsList.add(new ProductsData(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}

				for (ProductsData a : productsList) {
					System.out.println(a.getId() + " " + a.getName() + " " + a.getPrice() + " " + a.getQuantity());
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
			
			idAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("id"));
			nameAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
			priceAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
			quantityAddColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
			
			productsAddTable.setItems(null);
			productsAddTable.setItems(productsList);
			
			if (productsList.isEmpty()) {
				productAddInfo.setText("There are no results for the searched item");
			}
			
		}
		
	}	
	
	@FXML
	private void addProductButtonClick() throws SQLException {
		
		if (addNameTextarea.getText().isEmpty() || addIdTextfield.getText().isEmpty() || addQuantityTextfield.getText().isEmpty() || addPriceTextfield.getText().isEmpty()) {
			productAddInfo.setText("Complete all the boxes above and press 'Add product' button in order to add an item in the database\n" +
								   "Tip: first search if the item is already in the database\n\n" + 
								   "Select an item from the right table and press 'Delete product' button to delete an item from the database\n\n" + 
								   "ERROR: fill all the boxes before adding an item to the database");
			return;
		} else {
			
			String addName = addNameTextarea.getText();
			String addId;
			String addPrice;
			String addQuantity;
			
			
			addIdTextfield.setText(addIdTextfield.getText().toUpperCase().trim());			
								
			if (!StoreUpdatePageOptions.checkIdInput(addIdTextfield, productAddInfo)) {
				return;
			}			
			addId = addIdTextfield.getText();
			
			if (!StoreAddPageOptions.checkDuplicateItem(addId)) {
				productAddInfo.setText("ID " + addId + " is already in the database");
				return;
			}
			
							
			if (!StoreUpdatePageOptions.checkQuantityInput(addQuantityTextfield, productAddInfo)) {
				return;
			}
			addQuantity = addQuantityTextfield.getText();
			
			if (!StoreUpdatePageOptions.checkPriceInput(addPriceTextfield, productAddInfo)) {
				return;
			}
			addPrice = addPriceTextfield.getText();			
			
			String addSuccesful = "Item succesfully added to the database\n" + 
							 	  "\n Name: " + addName + 
								  "\n ID: " + addId + 
								  "\n Price: " + addPrice + " $" + 
								  "\n Quantity: " + addQuantity + " piece(s)";
			
			//connect to DB and change the item properties			
			try {
				conn = ConnectToDB.connectToDataBase();				
				sql = "INSERT INTO products (id, name, price, quantity) VALUES (?, ?, ?, ?)";
				ps = conn.prepareStatement(sql);
				
				ps.setString(1, addId);
				ps.setString(2, addName);
				ps.setString(3, addPrice);
				ps.setString(4, addQuantity);
				
				ps.executeUpdate();
				
				productAddInfo.setText(addSuccesful);
				
				addNameTextarea.setText("");
				addIdTextfield.setText("");
				addPriceTextfield.setText("");
				addQuantityTextfield.setText("");
				
				displayAllProductsAdd();
				
			} catch (SQLException e) {
				e.printStackTrace();
				productAddInfo.setText("Error: could not perform the item addition to the database");
			} finally {
				if (conn != null) {
					conn.close();
				} if (ps != null) {
					ps.close();
				}
			}
			
		}
		
	}
	
	@FXML
	public void deleteProductButtonClick() {
		System.out.println("item deleted from database");
	}

}

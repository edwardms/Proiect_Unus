package storePage;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StoreSalePageController implements Initializable {
	
	@FXML
	private TextField searchField;
	
	@FXML
	private TableView<ProductsData> productsTable;
	
	@FXML
	private TableColumn<ProductsData, String> idColumn;
	
	@FXML
	private TableColumn<ProductsData, String> nameColumn;
	
	@FXML
	private TableColumn<ProductsData, String> priceColumn;
	
	@FXML
	private TableColumn<ProductsData, String> quantityColumn;
	
	@FXML
	private Button searchButton;
	
	@FXML
	private Button displayAllProductsButton;
	
	@FXML
	private TextField quantityField;
	
	@FXML
	private Button addProductButton;
	
	@FXML
	private TableView<AddedProduct> selectedItemsTable;
	
	@FXML
	private TableColumn<AddedProduct, String> selectedItemsColumn;
	
	@FXML
	private TableColumn<AddedProduct, Integer> selectedItemsQuantityColumn;
	
	@FXML
	private TableColumn<AddedProduct, Double> selectedItemsPriceColumn;
	
	@FXML
	private Button deleteProductButton;
	
	@FXML
	private Label productInfoLabel;
	
	@FXML
	private Label totalPriceLabel;
	
	@FXML
	private TextField endTransactionTextfield;	
	
	/*@FXML
	private Label endTransactionLabel;*/
	
	private String sql;
	private ObservableList<ProductsData> productsList;
	//private ObservableList<AddedProduct> selectedItemsList = FXCollections.observableArrayList();
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	private double totalPrice = 0;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			displayAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	@FXML
	private void displayAllProducts() throws SQLException {
		
		sql = "SELECT * FROM products WHERE id";
		productInfoLabel.setText("");
		
		try {
			conn = ConnectToDB.connectToDataBase();
			StoreSalePageModel.isConnectedToDB(conn, productInfoLabel);		
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
		
		idColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
		
		productsTable.setItems(null);
		productsTable.setItems(productsList);
		
	}
	
	@FXML
	private void searchProduct() throws SQLException {
		
		sql = "SELECT * FROM products WHERE id LIKE('%" + searchField.getText() + "%') OR name LIKE('%" + searchField.getText() + "%')";
		
		if (searchField.getText().isEmpty()) {
			productInfoLabel.setText("WARNING: Search field is empty");
		} else {
			productInfoLabel.setText("");
			
			try {
				conn = ConnectToDB.connectToDataBase();
				StoreSalePageModel.isConnectedToDB(conn, productInfoLabel);	
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
			
			idColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
			quantityColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
			
			productsTable.setItems(null);
			productsTable.setItems(productsList);
			
			if (productsList.isEmpty()) {
				productInfoLabel.setText("There are no results for the searched item");
			}
			
		}		
		
	}
	
	@FXML 
	private void addProduct() throws SQLException {
		
		try {
			conn = ConnectToDB.connectToDataBase();			
			
			if (StoreSalePageModel.isConnectedToDB(conn, productInfoLabel)) {
				if (!productsTable.getSelectionModel().isEmpty()) {
					productInfoLabel.setText("");
					String selectedProductName = productsTable.getSelectionModel().getSelectedItem().getName();
					String selectedProductPrice = productsTable.getSelectionModel().getSelectedItem().getPrice();
					String selectedProductQuantity = productsTable.getSelectionModel().getSelectedItem().getQuantity();
					String selectedQuantity = quantityField.getText();
					
					int productQuantityToInteger = Integer.parseInt(selectedProductQuantity);
					int selectedQuantitytoInteger = Integer.parseInt(selectedQuantity);
					
					if (productQuantityToInteger < selectedQuantitytoInteger) {
						productInfoLabel.setText("Error: Products quantity lower than selected quantity");
					} else if (selectedQuantitytoInteger < 1) {
						productInfoLabel.setText("Error: Selected quantity lower than 1");
					} else {
						
						double priceToDouble = Double.parseDouble(selectedProductPrice) * selectedQuantitytoInteger;
						totalPrice += priceToDouble;
						totalPriceLabel.setText(String.valueOf(totalPrice));
						
						AddedProduct addProd = new AddedProduct(selectedProductName, Integer.parseInt(selectedQuantity), priceToDouble);
																								
						selectedItemsColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, String>("name"));
						selectedItemsQuantityColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Integer>("quantity"));
						selectedItemsPriceColumn.setCellValueFactory(new PropertyValueFactory<AddedProduct, Double>("price"));
						
						selectedItemsTable.getItems().add(addProd);
						
						String newQuantity = String.valueOf(productQuantityToInteger - selectedQuantitytoInteger);
						StoreSalePageModel.updateQuantity(selectedProductName, newQuantity);
						displayAllProducts();
										
					}					
					
				} else {
					productInfoLabel.setText("WARNING: Please select an item and quantity");
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
	}
	
	@FXML
	private void deleteProduct() throws SQLException {
		
		try {
			conn = ConnectToDB.connectToDataBase();			
			
			if (StoreSalePageModel.isConnectedToDB(conn, productInfoLabel)) {
				if (!selectedItemsTable.getSelectionModel().isEmpty()) {
					
					int selectedProductQuantity = selectedItemsTable.getSelectionModel().getSelectedItem().getQuantity();
					String selectedProductName = selectedItemsTable.getSelectionModel().getSelectedItem().getName();
					double selectedProductPrice = selectedItemsTable.getSelectionModel().getSelectedItem().getPrice();
					
					sql = "UPDATE products SET quantity = (quantity + ?) WHERE name = ?";					
					ps = conn.prepareStatement(sql);
					ps.setInt(1, selectedProductQuantity);
					ps.setString(2, selectedProductName);
					ps.executeUpdate();					
					
					selectedItemsTable.getItems().remove(selectedItemsTable.getSelectionModel().getSelectedItem());
					
					displayAllProducts();
					totalPrice -= selectedProductPrice;
					totalPriceLabel.setText(String.valueOf(totalPrice));
					
					productInfoLabel.setText("Item deleted");
					
				} else {
					productInfoLabel.setText("WARNING: Select an item from above table to delete");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
		}	
		
	}
	
	@FXML
	private void endTransaction() {
		
		
		
		
		selectedItemsTable.getItems().removeAll(selectedItemsTable.getItems());
	}

}

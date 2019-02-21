package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbUtil.ConnectToDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import storePage.ProductsData;
import storeSalePage.StoreSalePageOptions;
import storeUpdatePage.StoreUpdatePageOptions;

public class StoreUpdatePageController implements Initializable {
	
	@FXML
	private TableView<ProductsData> productsUpdateTable;
	
	@FXML
	private TableColumn<ProductsData, String> idUpdateColumn;
	
	@FXML
	private TableColumn<ProductsData, String> nameUpdateColumn;
	
	@FXML
	private TableColumn<ProductsData, String> priceUpdateColumn;
	
	@FXML
	private TableColumn<ProductsData, String> quantityUpdateColumn;
	
	@FXML
	private TextField searchFieldUpdate;
	
	@FXML
	private Button searchButtonUpdate;
	
	@FXML
	private Button displayAllProductsButtonUpdate;
	
	@FXML
	private Label currentProductName;
	
	@FXML
	private Label currentProductId;
	
	@FXML
	private Label currentProductQuantity;
	
	@FXML
	private Label currentProductPrice;
	
	@FXML
	private TextArea newNameTextarea;
	
	@FXML
	private TextField newIdTextfield;
	
	@FXML
	private TextField newQuantityTextfield;
	
	@FXML
	private TextField newPriceTextfield;
	
	@FXML
	private Button updateProductButton;
	
	@FXML
	private Label productUpdateInfo;
	
	private String sql;
	private Connection conn = null;
	private ResultSet rs = null;
	private ObservableList<ProductsData> productsList;
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		try {
			displayAllProductsUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		StoreUpdatePageOptions.showUpdateTextfields(productsUpdateTable, newNameTextarea, newIdTextfield, newQuantityTextfield, newPriceTextfield, productUpdateInfo);
		tableClick();
	}
	
	@FXML
	public void tableClick() {
		productsUpdateTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				StoreUpdatePageOptions.currentTableRowClicked(productsUpdateTable, currentProductId, currentProductName, currentProductPrice, currentProductQuantity);
				StoreUpdatePageOptions.showUpdateTextfields(productsUpdateTable, newNameTextarea, newIdTextfield, newQuantityTextfield, newPriceTextfield, productUpdateInfo);
				System.out.println("clicked");
			}
			
		});
	}
	
	@FXML
	private void displayAllProductsUpdate() throws SQLException {
		
		sql = "SELECT * FROM products WHERE id";
		productUpdateInfo.setText("");
		
		try {
			conn = ConnectToDB.connectToDataBase();
			StoreSalePageOptions.isConnectedToDB(conn, productUpdateInfo);		
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
		
		idUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("id"));
		nameUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
		priceUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
		quantityUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
		
		productsUpdateTable.setItems(null);
		productsUpdateTable.setItems(productsList);
		
	}
	
	@FXML
	private void searchProductUpdate() throws SQLException {
		
		sql = "SELECT * FROM products WHERE id LIKE('%" + searchFieldUpdate.getText() + "%') OR name LIKE('%" + searchFieldUpdate.getText() + "%')";
		
		if (searchFieldUpdate.getText().isEmpty()) {
			productUpdateInfo.setText("WARNING: Search field is empty");
		} else {
			productUpdateInfo.setText("");
			
			try {
				conn = ConnectToDB.connectToDataBase();
				StoreSalePageOptions.isConnectedToDB(conn, productUpdateInfo);	
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
			
			idUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("id"));
			nameUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("name"));
			priceUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("price"));
			quantityUpdateColumn.setCellValueFactory(new PropertyValueFactory<ProductsData, String>("quantity"));
			
			productsUpdateTable.setItems(null);
			productsUpdateTable.setItems(productsList);
			
			if (productsList.isEmpty()) {
				productUpdateInfo.setText("There are no results for the searched item");
			}
			
		}
		
	}
	
	@FXML
	private void updateProductButton() {
		
		if (newNameTextarea.getText().isEmpty() && newIdTextfield.getText().isEmpty() && newQuantityTextfield.getText().isEmpty() && newPriceTextfield.getText().isEmpty()) {
			productUpdateInfo.setText("Please fill at least one of the input boxes above! The ones that are unfilled will remain the same.");
		} else {
			
			productUpdateInfo.setText("");
			newIdTextfield.setText(newIdTextfield.getText().toUpperCase().trim());			
			StoreUpdatePageOptions.checkIdInput(newIdTextfield, productUpdateInfo);			
			StoreUpdatePageOptions.checkQuantityInput(newQuantityTextfield, productUpdateInfo);			
			StoreUpdatePageOptions.checkPriceInput(newPriceTextfield, productUpdateInfo);
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
	}

}

package storePage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbUtil.ConnectToDB;
import javafx.scene.control.Label;

public class StoreSalePageModel {
	
	public static boolean isConnectedToDB(Connection conn, Label label) {
		if (conn != null) {
			label.setText("");
			System.out.println("Class StoreSalePageModel: Login app controller connected.");
			return true;
		} else {
			label.setText("Error: Not connected to DB");
			System.out.println("Class StoreSalePageModel: Login app controller not connected.");
			return false;
		}	
	}
	
	public static void updateQuantity(String name, String newQuantity) {
		String sql = "UPDATE products SET quantity = ? WHERE name = ?";
		try (Connection conn = ConnectToDB.connectToDataBase();
			 PreparedStatement prst = conn.prepareStatement(sql)) {
			prst.setString(1, newQuantity);
			prst.setString(2, name);
			
			prst.executeUpdate();
			System.out.println("quantity updated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

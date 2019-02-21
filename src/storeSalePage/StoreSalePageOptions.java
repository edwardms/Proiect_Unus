package storeSalePage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbUtil.ConnectToDB;
import javafx.scene.control.Label;

public class StoreSalePageOptions {
	
	public static boolean isConnectedToDB(Connection conn, Label label) {
		if (conn != null) {
			label.setText("");
			System.out.println("Class StoreSalePageModel: Store app controller connected.");
			return true;
		} else {
			label.setText("Error: Not connected to DB");
			System.out.println("Class StoreSalePageModel: Store app controller not connected.");
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
	
	public static void createBillTextFile(String fileName, String fileData) {
		String filePath = "src/billToTextFile/" + fileName + ".txt";
		Path file = Paths.get(filePath);
		byte[] data = fileData.getBytes();
		try ( OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) ) {
			out.write(data, 0, fileData.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

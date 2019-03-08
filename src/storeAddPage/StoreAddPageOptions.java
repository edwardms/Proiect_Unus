package storeAddPage;

import java.sql.Connection;

import javafx.scene.control.Label;

public class StoreAddPageOptions {
	
	public static boolean isConnectedToDB(Connection conn, Label label) {
		if (conn != null) {
			System.out.println("Class StoreUpdatePageModel: Store app controller connected.");
			return true;
		} else {
			label.setText("Error: Not connected to DB");
			System.out.println("Class StoreSalePageModel: Store app controller not connected.");
			return false;
		}	
	}
	
	public static boolean checkDuplicateItem(String id) {
		
		//TO-DO
		
		
		return true;
	}

}

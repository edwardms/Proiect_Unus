package loginApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.ConnectToDB;

public class LoginAppModel {
	
	Connection connect;
	
	public LoginAppModel() {		
		this.connect = ConnectToDB.connectToDataBase();
		
	}
	
	public boolean isDatabaseConnected() {
		return this.connect != null;
	}
	
	public boolean isLogin(String user, String pass) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sqlQuerry = "SELECT * FROM loginaccounts WHERE username=? AND password=?";
		
		try {
			ps = connect.prepareStatement(sqlQuerry);
			ps.setString(1, user);
			ps.setString(2, pass);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
	}	

}

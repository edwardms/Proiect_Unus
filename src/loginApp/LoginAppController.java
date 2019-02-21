package loginApp;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fxUtil.CloseAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginAppController implements Initializable {
	
	LoginAppModel loginModel = new LoginAppModel();
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Label dbStatusLabel;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (loginModel.isDatabaseConnected()) {
			dbStatusLabel.setText("Connected to DB");
			System.out.println("Class LoginAppController: Login app controller connected.");
		} else {
			dbStatusLabel.setText("Error: Not connected to DB");
			System.out.println("Class LoginAppController: Login app controller not connected.");
		}
	}
	
	@FXML
	public void loginButtonAction() {
		
		try {
			if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
				if (loginModel.isLogin(usernameField.getText(), passwordField.getText())) {					
					Stage stage = (Stage)loginButton.getScene().getWindow();
					stage.close();
					salePageLogin();
				} else {
					dbStatusLabel.setText("Login failed! Check username/password");
				}
			} else {
				dbStatusLabel.setText("Empty username/password field");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void salePageLogin() {
		Stage salePageStage;
		ScrollPane root;
		String fxmlStoreSalePageLocation;
		try {
			salePageStage = new Stage();
			fxmlStoreSalePageLocation = "../storePage/StoreSalePageSceneBuilder.fxml";
			root = FXMLLoader.load(getClass().getResource(fxmlStoreSalePageLocation));
			
			Scene salePageScene = new Scene(root);
			salePageStage.setScene(salePageScene);
			salePageStage.setTitle("PC Store AG");
			salePageStage.getIcons().add(new Image("icons/pc_store_icon.png"));
			salePageStage.setResizable(false);
			salePageStage.show();
								
			salePageStage.setOnCloseRequest(e -> {
				e.consume();
				Boolean answer = CloseAlert.displayCloseMessage("Close PC Store AG app", "Are you sure you want to close the window?", "Yes", "No");
				if (answer == true) {
					salePageStage.close();
					System.out.println("Class SaleStorePage: JavaFx app close.");
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	

}

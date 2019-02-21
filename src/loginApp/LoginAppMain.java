package loginApp;

import java.io.IOException;

import fxUtil.CloseAlert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginAppMain extends Application {
	
	public static void main(String[] args) {
		LoginAppMain.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		
		String fxmlLoginFileLocation;
		Parent root;
		Scene scene;
		try {
			fxmlLoginFileLocation ="LoginAppSceneBuilder.fxml";
			root = FXMLLoader.load(getClass().getResource(fxmlLoginFileLocation));
			
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("PC Store AG - Login");
			stage.getIcons().add(new Image("icons/pc_store_icon.png"));
			stage.setResizable(false);
			stage.show();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Class LoginAppMain: JavaFx app run.");
		}
		
		stage.setOnCloseRequest(e -> {
			e.consume();
			Boolean answer = CloseAlert.displayCloseMessage("Close PC Store AG app", "Are you sure you want to close the window?", "Yes", "No");
			if (answer == true) {
				stage.close();
				System.out.println("Class LoginAppMain: JavaFx app close.");
			}
		});
		
	}

}

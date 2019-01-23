package storePage;

import java.io.IOException;

import fxUtil.CloseAlert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StorePageMain extends Application {
	
	public static void main(String[] args) {
		StorePageMain.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		
		String fxmlLoginFileLocation;
		Parent root;
		Scene scene;
		try {
			fxmlLoginFileLocation ="StoreSalePageSceneBuilder.fxml";
			root = FXMLLoader.load(getClass().getResource(fxmlLoginFileLocation));
			
			scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("PC Store AG - store page");
			stage.getIcons().add(new Image("icons/pc_store_icon.png"));
			stage.setResizable(false);
			stage.show();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}finally {
			System.out.println("Class StorePageMain: JavaFx app run.");
		}
		
		/*stage.setOnCloseRequest(e -> {
			e.consume();
			Boolean answer = CloseAlert.displayCloseMessage();
			if (answer == true) {
				stage.close();
				System.out.println("Class LoginAppMain: JavaFx app close.");
			}
		});*/
		
	}

}

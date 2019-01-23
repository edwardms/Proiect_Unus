package fxUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CloseAlert {
	
	static boolean answer;
	
	public static boolean displayCloseMessage() {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Closing PC Store Application");
		
		window.setWidth(360);
		window.setHeight(240);
		window.setResizable(false);
		
		Label message = new Label("Are you sure you want to close the application?");
		message.setFont(new Font(15));
		Button yesButton = new Button("Yes");
		yesButton.setFont(new Font(15));
		Button noButton = new Button("No");
		noButton.setFont(new Font(15));
		
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		HBox buttonsBox = new HBox(10);
		buttonsBox.getChildren().addAll(yesButton, noButton);
		buttonsBox.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(message, buttonsBox);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

}
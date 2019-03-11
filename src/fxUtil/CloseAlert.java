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
	
	public static boolean displayCloseMessage(String windowTitle, String messageToDisplay, String yesButtonText, String noButtonText) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(windowTitle);
		
		window.setWidth(360);
		window.setHeight(240);
		window.setResizable(false);
		
		Label message = new Label(messageToDisplay);
		message.setFont(new Font(15));
		message.setPadding(new Insets(0, 0, 30, 0));
		Button yesButton = new Button(yesButtonText);
		yesButton.setFont(new Font(15));
		yesButton.setPadding(new Insets(0, 40, 0, 40));
		Button noButton = new Button(noButtonText);
		noButton.setFont(new Font(15));
		noButton.setPadding(new Insets(0, 40, 0, 40));
		
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
		buttonsBox.setSpacing(50);
		layout.getChildren().addAll(message, buttonsBox);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

}

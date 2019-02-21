package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {
	
	@FXML
	private TabPane mainTabPane;
	
	@FXML
	private Tab saleTabPage;
	
	@FXML	
	private StoreSalePageController storeSalePageController;
	
	
	@FXML
	private Tab updateTabPage;
	
	@FXML	
	private StoreUpdatePageController storeUpdatePageController;

}

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * This is a simple class that provides functionality for the Filters Pop Up.
 * @author Ivan Ivanov & Kenneth Kreindler
 * @version 1
 */
public class FiltersController implements Initializable {

	public static int nights = 1;
	public static int minRange = 0;
	public static int maxRange = ScreensFramework.con.getMAXPrice().get(0).getPrice();

	public static String room = "Any type";

	@FXML
	private ComboBox<Integer> numberNights;

	@FXML
	private ComboBox<Integer> minPrice;

	@FXML
	private ComboBox<Integer> maxPrice;

	@FXML
	private ComboBox<String> roomType;

	@FXML 
	private Button applyFilter;

	@FXML 
	private Button close;

	@FXML
	void applyFilters(ActionEvent event) {
		//set the nights dropdown value on select
		if(!validrange(minPrice.getValue(), maxPrice.getValue())) {
			return;
		}
		nights = numberNights.getValue();

		//set the room type dropdown
		try {
		if(roomType.getValue().equals("Any type")) {
			room = null;
		}else room = roomType.getValue();
		}
		catch(Exception e) {
			showAlert("Invalid room type", "Please select a room type");
			return;
		}

		//update the table
		ScreensFramework.tableViewer.updateTable();
		ScreensFramework.mapViewer.updateColours();
		ScreensFramework.mapViewer.filterBlock.setDisable(true);
		ScreensFramework.mapViewer.filterBlock.setVisible(false);

		ScreensFramework.accViewer.setNextVis(true);
		ScreensFramework.accViewer.setPrevVis(true);
		ScreensFramework.accViewer.setMapVis(true);
		ScreensFramework.accViewer.setListVis(true);


		ScreensFramework.tableViewer.setNextVis(true);
		ScreensFramework.tableViewer.setPrevVis(true);
		ScreensFramework.tableViewer.setMapVis(true);
		ScreensFramework.tableViewer.setListVis(true);


		ScreensFramework.mapViewer.setNextVis(true);
		ScreensFramework.mapViewer.setPrevVis(true);
		ScreensFramework.mapViewer.setMapVis(true);
		ScreensFramework.mapViewer.setListVis(true);
		


		ScreensFramework.favViewer.setNextVis(false);
		ScreensFramework.favViewer.setPrevVis(false);
		ScreensFramework.favViewer.setMapVis(false);
		ScreensFramework.favViewer.setListVis(false);
		
		Stage stage = (Stage) applyFilter.getScene().getWindow();
		stage.close();
	}

	void closeFilters(ActionEvent event) {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();

	}
	
	@FXML
	void setMax(ActionEvent event) {
		int maxRangetemp = maxPrice.getValue();
		validrange(minRange, maxRangetemp);
		System.out.println(maxPrice.getValue());
	}
	
	@FXML
    void setMin(ActionEvent event) {
    	int minRangeTemp = minPrice.getValue();
    	if(maxRange < 0 || validrange(minRangeTemp, maxRange)) {
    		maxPrice.setDisable(false);
    		minRange = minPrice.getValue();
    	}
 	
    }

	public void setminDrowpdown(int i) {
		minPrice.setValue(i);
	}

	public void setmaxDrowpdown(int i) {
		maxPrice.setValue(i);
	}

	/**
	 * Check if the price range selected is valid
	 * @param min the min boundary of the price
	 * @param max the max boundary of the price
	 * @return true if Price range selected is valid
	 */
	private boolean validrange(int min, int max) {
		if(min <= max) {
			minRange = min;
			maxRange = max;
			minPrice.setValue(minRange);
			maxPrice.setValue(maxRange);
			maxPrice.setDisable(false);
			minPrice.setDisable(false);



			return true;
		}
		else {
			showAlert("Invalide Range", "Please make sure that your range is positive");
			return false;
		}
	}

	public void showAlert(String title, String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public void filtersInitialize() {
		//Disable the second dropdown
		minPrice.getItems().removeAll(minPrice.getItems());

		//Add items to the dropdown menus
		roomType.getItems().addAll("Any type", "Entire home/apt", "Private room", "Shared room");
		roomType.setValue(room);
		for(int i = 0; i < 10; i++) {
			numberNights.getItems().add(i);
		}

		for(int i = 0; i < 60; i++) {
			int temp = i * 150;	
			minPrice.getItems().add(temp);
			maxPrice.getItems().add(temp);
		}

		//set default values for dropdown
		minPrice.setValue(minRange);
		maxPrice.setValue(maxRange);
		numberNights.setValue(nights);
		roomType.setValue(room);

		//set button actions
		applyFilter.setOnAction(this::applyFilters);
		close.setOnAction(this::closeFilters);
		minPrice.setOnAction(this::setMin);
		maxPrice.setOnAction(this::setMax);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		filtersInitialize();
	}
}
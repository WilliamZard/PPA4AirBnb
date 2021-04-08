import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author Kenneth Kreindler

 * Extends mainInterfaceController as the table is built on top of the main interface
 */

public class tableViewController extends listPane implements Initializable, ControlledScreen{

	private static String neighborhood = null;


	@FXML
	private Label neighbourhoodLabel;

	@FXML
	private ImageView refresh;

	/**
	 * Returns the current neighbourhood that the property list is showing
	 * @return String
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * Sets the neighbourhood of the property list view
	 * @param property ID of property
	 * @return
	 */
	public void setNeighborhood(String n) {
		neighborhood = n;
		neighbourhoodLabel.setText(n);
	}

	/**
	 * Update the table
	 * @return void
	 */
	public void updateTable() {
		tableView.getItems().clear();
		if(FiltersController.room!=null) {
			ArrayList<AirbnbListing> data = ScreensFramework.con.loadFromBorough(neighborhood, FiltersController.minRange, FiltersController.maxRange, FiltersController.nights, FiltersController.room);
			for(int i = 0; i < data.size();i++) {
				AirbnbListing tempListing = data.get(i);
				tableView.getItems().add(tempListing);

			}
		}else {
			ArrayList<AirbnbListing> data = ScreensFramework.con.loadFromBorough(neighborhood, FiltersController.minRange, FiltersController.maxRange, FiltersController.nights);
			for(int i = 0; i < data.size();i++) {
				AirbnbListing tempListing = data.get(i);
				tableView.getItems().add(tempListing);

			}
		}
	}

	/*
	 * Make sure a neighbourhood has been selected
	 */
	public boolean chechNeighborhood() {
		if(neighborhood==null) {
			showAlert(Alert.AlertType.ERROR, "Error", "Please select a neighbourhood in the map");
			return false;
		}
		return true;
	}

	private static void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

	/*
	 * override prev button
	 * @see mainInterfaceController#prev(javafx.scene.input.MouseEvent)
	 */
	@FXML
	void prev(MouseEvent event) {

		myController.setScreen(ScreensFramework.map);
	}

	@FXML
	void refreshTable(MouseEvent event) {
		updateTable();
	}

	@FXML
	void next(MouseEvent event) {
		myController.setScreen(ScreensFramework.account);
	}

	/*
	 * Additional elements to initialise
	 * @see listPane#initializeMore()
	 */
	public void initializeMore() {
		refresh.setOnMouseClicked(this::refreshTable);
	}; //Overwrite by subclass


}

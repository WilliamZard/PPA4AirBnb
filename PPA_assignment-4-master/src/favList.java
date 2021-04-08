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
 * Other child lists extend this class.
 */

public class favList extends listPane implements Initializable, ControlledScreen{

    @FXML
    private ImageView refreash;

	
	/**
	 * Update the table
	 * @return void
	 */
	public void updateTable() {

		tableView.getItems().clear();
		ArrayList<AirbnbListing> data = ScreensFramework.con.loadFavProperties(); 
		for(int i = 0; i < data.size();i++) {
        	AirbnbListing tempListing = data.get(i);
        		tableView.getItems().add(tempListing);
	        	        	
        }
	}
	
	@FXML
    void refreshTable(MouseEvent event) {
		updateTable();
    }
	
	@FXML
    void prev(MouseEvent event) {
    	
    	myController.setScreen(ScreensFramework.map);
    }

    @FXML
    void next(MouseEvent event) {
    	myController.setScreen(ScreensFramework.account);
    }
    
    public void initializeMore() {
    	refreash.setOnMouseClicked(this::refreshTable);
    }; //Overwrite by subclass

}

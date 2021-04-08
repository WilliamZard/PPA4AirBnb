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
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author Kenneth Kreindler

 * Extends mainInterfaceController as the table is built on top of the main interface
 */

public class listPane extends mainInterfaceController implements Initializable, ControlledScreen{

	
    @FXML
    public TableView<AirbnbListing> tableView;
    
    @FXML
    public TableColumn reviewsCol;

    @FXML
    public TableColumn priceCol;
    
    @FXML
    public TableColumn hostCol;


    @FXML
    public TableColumn nameCol;

    @FXML
    public TableColumn favCol;
    
    @FXML
    public TableColumn viewCol;
    
    @FXML
    public TableColumn avalibilityCol;
    
    @FXML
    public TableColumn typeCol;
    
	/**
	 * Creates table and adds all of the elements to it
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// INitialize main interface elements
		super.mainInterfaceInitialize();

		//create table cols
		//set the propertyValueFactory corresponding to a property listing
		//set the size of the col
        nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        nameCol.prefWidthProperty().bind(tableView.widthProperty().divide(3.1));
        nameCol.setMaxWidth(1/tableView.widthProperty().divide(3.1).doubleValue());
        
        hostCol = new TableColumn("Host");
        hostCol.setCellValueFactory(new PropertyValueFactory<>("Host_name"));
        hostCol.prefWidthProperty().bind(tableView.widthProperty().divide(12));
        hostCol.setMaxWidth(1/tableView.widthProperty().divide(12).doubleValue());
        
        typeCol = new TableColumn("Room Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Room_type"));
        typeCol.prefWidthProperty().bind(tableView.widthProperty().divide(6));
        typeCol.setMaxWidth(1/tableView.widthProperty().divide(6).doubleValue());
        
        reviewsCol = new TableColumn("Reviews");
        reviewsCol.setCellValueFactory(new PropertyValueFactory<>("NumberOfReviews"));
        reviewsCol.prefWidthProperty().bind(tableView.widthProperty().divide(8));
        reviewsCol.setMaxWidth(1/tableView.widthProperty().divide(8).doubleValue());
        
        avalibilityCol = new TableColumn("Minimum Stay");
        avalibilityCol.setCellValueFactory(new PropertyValueFactory<>("MinimumNights"));
        avalibilityCol.prefWidthProperty().bind(tableView.widthProperty().divide(12));
        avalibilityCol.setMaxWidth(1/tableView.widthProperty().divide(12).doubleValue());
        
        priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        priceCol.prefWidthProperty().bind(tableView.widthProperty().divide(12));
        priceCol.setMaxWidth(1/tableView.widthProperty().divide(12).doubleValue());

        viewCol = new TableColumn("View");
        viewCol.setCellValueFactory(new PropertyValueFactory<>("View"));
        viewCol.prefWidthProperty().bind(tableView.widthProperty().divide(12));
        viewCol.setMaxWidth(1/tableView.widthProperty().divide(12).doubleValue());
        viewCol.setResizable(false);
        
        favCol = new TableColumn("Fav");
        favCol.setCellValueFactory(new PropertyValueFactory<>("Fav"));
        favCol.prefWidthProperty().bind(tableView.widthProperty().divide(22));
        favCol.setMaxWidth(1/tableView.widthProperty().divide(22).doubleValue());
        
        //add cols to table
        tableView.getColumns().setAll(nameCol, hostCol, typeCol,reviewsCol,avalibilityCol,priceCol,viewCol,favCol);
        
        initializeMore();
		
	}
	
	public void updateTable() {}; //Overwrite by subclass
	
	public void initializeMore() {}; //Overwrite by subclass
	

}

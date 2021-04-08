import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class propertyWindow {

	/**
	 * @author Kenneth Kreindler
	 * 
	 * Constructor that opens a new property in a new window.
	 * @param property ID of property
	 * @return Object
	 */
	
    public propertyWindow(String propertyId) throws IOException {
        	FXMLLoader root = new FXMLLoader (getClass().getResource("xml/propertyItem.fxml"));
        	propertyItemController setController = new propertyItemController();
        	setController.setPropertyId(propertyId);
        	root.setController(setController);
			Parent loadScreen = (Parent) root.load();
			Stage stage = new Stage();
            stage.setTitle("Property Viewer");
            stage.setScene(new Scene(loadScreen, 600, 400));
            stage.show();
 
    } 
}

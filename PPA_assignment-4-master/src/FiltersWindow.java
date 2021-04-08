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

/**
 * Simple class to load the GUI for the Fitlters Pop UP
 * @author Ivan Ivanov
 *
 */
public class FiltersWindow {

	/**
	 * Constructor that opens a the stats window
	 * @param 
	 * @return
	 */
	
    public FiltersWindow() throws IOException {

        	FXMLLoader root = new FXMLLoader (getClass().getResource("xml/filters.fxml"));
			Parent loadScreen = (Parent) root.load();
			Stage stage = new Stage();
            stage.setTitle("Filters");
            stage.setScene(new Scene(loadScreen, 385, 225));
            stage.show();

    }
    
}

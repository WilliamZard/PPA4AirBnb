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

public class statsWindow {

	/**
	 * Constructor that opens a the stats window
	 * @param 
	 * @return
	 */
	
    public statsWindow() throws IOException {

        	FXMLLoader root = new FXMLLoader (getClass().getResource("xml/statsView.fxml"));
			Parent loadScreen = (Parent) root.load();
			Stage stage = new Stage();
            stage.setTitle("Statistics Window");
            stage.setScene(new Scene(loadScreen, 600, 400));
            stage.show();

    }
    
}

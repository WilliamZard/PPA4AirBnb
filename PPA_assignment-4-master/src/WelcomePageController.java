
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class provides implementation for the Welcome Page.
 * There are two options: To log in or to Register in the system.
 * This class sends input data to the DB using the MysqlCon class,
 * and manipulates the data that is sent to and returned from the DB.
 * 
 * @author Ivan Ivanov
 * @version 2
 */

public class WelcomePageController implements Initializable, ControlledScreen{
	private MysqlCon SQL = ScreensFramework.con;
	ScreensController myController;
	//assign the FXML objects and their ids to objects in the class
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField usernameFieldReg;

	@FXML
	private TextField nameFieldReg;

	@FXML
	private PasswordField passwordFieldReg;

	@FXML
	private Button logInButt;

	@FXML
	private Button regButt;
	
	@FXML
    private CheckBox agreeCheckBox;

	@FXML
	void logIn(MouseEvent event) {
		
		//check if any of the fields are empty
		if(usernameField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Please enter your username.");
			return;
		}
		if(passwordField.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Please enter a password.");
			return;
		}
		
		//check if the pass is the correct one for that username
		if(SQL.checkPass(usernameField.getText(), passwordField.getText())) {
			showAlert(Alert.AlertType.INFORMATION, 
					"Log In successful", "Welcome, " + SQL.getName(usernameField.getText())+". "
							+ "Please apply filters to start browsing properties!");
			ScreensFramework.username=usernameField.getText();
			myController.setScreen(ScreensFramework.map);
			
			//sets the user details for all the windows
			try {
			ScreensFramework.userimage = SQL.getPhoto(usernameField.getText());
			ScreensFramework.accViewer.updateUserImage();
			ScreensFramework.mapViewer.updateUserImage();
			ScreensFramework.tableViewer.updateUserImage();
			ScreensFramework.favViewer.updateUserImage();
			ScreensFramework.accViewer.updateImage();
			ScreensFramework.accViewer.updteUserDetails();
			ScreensFramework.favViewer.updateTable();
			
			}
			catch(Exception e) {
				
			}
			usernameField.setText("");
			passwordField.setText("");
		}else {
			showAlert(Alert.AlertType.ERROR, 
				"ERROR", "Wrong username or password! Please enter correct username or password.");
			usernameField.setText("");
			passwordField.setText("");
		}

	}
	
	@FXML
	void register(MouseEvent event) {
		//check if any of the fields are empty
		if(usernameFieldReg.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Please choose a username");
			return;
		}
		if(nameFieldReg.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Please enter your name");
			return;
		}
		if(passwordFieldReg.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Please choose password");
			
		}
		//check if the password is more than 6 symblos
		if(passwordFieldReg.getText().length() <= 6) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Your password should be at least 6 characters!");
			passwordFieldReg.setText("");
			return;
		}
		if(!agreeCheckBox.isSelected()) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Please agree to your personal data being stored in our system.");
			passwordFieldReg.setText("");
			return;
		}

		//successful registration
		SQL.createUser(usernameFieldReg.getText(), passwordFieldReg.getText(), nameFieldReg.getText());

		showAlert(Alert.AlertType.INFORMATION, 
				"Registration Successful!", "Thank you for registering! Now you can start exploring the Properties we offer in our app.");
		ScreensFramework.username = usernameField.getText();
		usernameFieldReg.setText("");
		nameFieldReg.setText("");
		passwordFieldReg.setText("");
		agreeCheckBox.setSelected(false);
	}
	
	/**
	 * Simple alert method
	 */
	private static void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

	@Override
	public void setScreenParent(ScreensController screensController) {
		// TODO Auto-generated method stub
		myController = screensController; 
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logInButt.setOnMouseClicked(this::logIn);
		regButt.setOnMouseClicked(this::register);
		
	}

}


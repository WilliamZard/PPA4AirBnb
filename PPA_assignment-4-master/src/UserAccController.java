import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

/**
 * This class provides implementation for the Welcome Page.
 * There are two options: To log in or to Register in the system.
 * This class sends input data to the DB using the MysqlCon class,
 * and manipulates the data that is sent to and returned from the DB.
 * 
 * @author Ivan Ivanov
 * @version 2
 */

public class UserAccController extends mainInterfaceController  implements Initializable, ControlledScreen{
	private MysqlCon SQL = new MysqlCon();
	//assign the FXML objects and their ids to objects in the class
	@FXML
	private Label usernameLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label addressLabel;

	@FXML
	private PasswordField newPass;

	@FXML
	private PasswordField confPass;

	@FXML
	private Button uploadImg;

	@FXML
	private Button savePass;

	@FXML
	private Button changeAddress;

	@FXML
	private ImageView userImg;

	@FXML
	void updatePass(MouseEvent event) {
		if(newPass.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Error!", "Please enter your new password.");
			return;
		}
		if(newPass.getText().length() < 6) {
			showAlert(Alert.AlertType.ERROR, 
					"Form Error!", "Your new password should be at least 6 characters!");
			newPass.setText("");
			return;
		}
		if(confPass.getText().isEmpty()) {
			showAlert(Alert.AlertType.ERROR, 
					"Error!", "Please confirm your password.");
			return;
		}

		if(newPass.getText().equals(confPass.getText())) {
			SQL.changePass(ScreensFramework.username, newPass.getText());
			showAlert(Alert.AlertType.INFORMATION, 
					"Password changed!", "Your password was changed!");
			newPass.setText("");
			confPass.setText("");
		}else {
			showAlert(Alert.AlertType.ERROR, 
					"ERROR", "Passwords do not match, please enter your new password again!");
			newPass.setText("");
			confPass.setText("");
		}

	}

	@FXML
	void updateAddress(MouseEvent event) {
		TextInputDialog dialog = new TextInputDialog("Your address");
		dialog.setTitle("Change of address");
		dialog.setHeaderText("Enter your new address.");
		dialog.setContentText("Your address: ");

		// Get the response value from the text field.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			SQL.changeAddress(ScreensFramework.username, result.get());
			showAlert(Alert.AlertType.INFORMATION, 
					"Address is changed", "Your address has been updated!");
			addressLabel.setText(SQL.getAddress(ScreensFramework.username));
		}
	}

	@FXML
	void updateImg(MouseEvent event) {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);
		String mimetype= new MimetypesFileTypeMap().getContentType(file);
		String type = mimetype.split("/")[0];
		if (file != null) {
			if(type.equals("image")) {
				if(file.length()<16777215) { //File needs to be under 16MB to upload to DB
					Image userPic = new Image(file.toURI().toString());

					SQL.changePhoto(ScreensFramework.username, file.toPath().toString());

					userImg.setImage(userPic);
					ScreensFramework.userimage = userPic;
					ScreensFramework.accViewer.updateUserImage();
					ScreensFramework.mapViewer.updateUserImage();
					ScreensFramework.tableViewer.updateUserImage();
					ScreensFramework.favViewer.updateUserImage();
					showAlert(Alert.AlertType.INFORMATION, 
							"Image changed!", "Your profile picture has been updated!");
				}
				else showAlert(Alert.AlertType.ERROR, 
						"ERROR", "File is too large, MAX photo size 16MB.");
			}else showAlert(Alert.AlertType.ERROR, 
					"ERROR", "Please upload a JPG file!");
		}
	}

	private static void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initiate maininterface
		super.mainInterfaceInitialize();
		//Initialise button actions
		savePass.setOnMouseClicked(this::updatePass);
		changeAddress.setOnMouseClicked(this::updateAddress);
		uploadImg.setOnMouseClicked(this::updateImg);

		//Set the user info Labels
		nameLabel.setText(SQL.getName(ScreensFramework.username));
		usernameLabel.setText(ScreensFramework.username);

		if(SQL.getAddress(ScreensFramework.username)==null) {
			addressLabel.setText("No address added.");
		}else addressLabel.setText(SQL.getAddress(ScreensFramework.username));

		//set the users photo
		if(ScreensFramework.userimage!=null) {
			userImg.setImage(ScreensFramework.userimage);
		}
		//super.mainInterfaceInitialize();
	}
	
	public void updateImage() {
		userImg.setImage(ScreensFramework.userimage);
	}
	
	public void updteUserDetails() {
		nameLabel.setText(SQL.getName(ScreensFramework.username));
		usernameLabel.setText(ScreensFramework.username);
		addressLabel.setText(SQL.getAddress(ScreensFramework.username));
	}
	
	void prev(MouseEvent event) {
		if(ScreensFramework.tableViewer.chechNeighborhood()) {
			myController.setScreen(ScreensFramework.propertyList);
		}
    }

    @FXML
    void next(MouseEvent event) {
    	myController.setScreen(ScreensFramework.map);
    }

}


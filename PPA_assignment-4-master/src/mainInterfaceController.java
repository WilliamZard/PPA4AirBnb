import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * This class provides the methods of the main interface
 * Other windows that are inside of the mainInterface can inherit these variables and methods
 * Then these variables can be initialised from the child class
 * 
 * @author Kenneth Kreindler
 * @version 2
 */

public class mainInterfaceController implements Initializable, ControlledScreen {
	/*
	 * Variables
	 */
	public static int minRange = 0;
	public static int maxRange = ScreensFramework.con.getMAXPrice().get(0).getPrice();
	ScreensController myController;
	

	@FXML
	private ImageView listBtn;
	
	@FXML
	private ImageView mapBtn;
	
	@FXML
	private ImageView next;
	
	@FXML
	private Circle userCircle;
	
	@FXML
	private MenuButton dropdownMenu;
	
	@FXML
	private MenuButton accountBtn;
	
	@FXML
	private Button filtersBtn;
	
	@FXML
	private ImageView prev;
	
	@FXML
	private AnchorPane mainAnchor;
	
	@FXML
	private BorderPane variablePane;
	/*
	 * Menu items
	 */
	MenuItem menuAccount = new MenuItem("Account");
	MenuItem menuFav = new MenuItem("Favorite");
	MenuItem menuStats = new MenuItem("Statistics");
	MenuItem menuMap = new MenuItem("Map");
	MenuItem menuSignOut = new MenuItem("Sign Out");
	
	/*
	 * Button viability
	 */
	public void setNextVis(boolean t) {
		next.setVisible(t);
	}
	
	public void setPrevVis(boolean t) {
		prev.setVisible(t);
	}
	public void setMapVis(Boolean t) {
		mapBtn.setVisible(t);
	}
	
	public void setListVis(Boolean t) {
		listBtn.setVisible(t);
	}
	
	@FXML
	/*
	 * prev button override
	 */
	void prev(MouseEvent event) {
	
		myController.setScreen(ScreensFramework.login);
	}
	
	@FXML
	/*
	 * Next button override
	 */
	void next(MouseEvent event) {
		myController.setScreen(ScreensFramework.propertyList);
	}
	
	@FXML
	/*
	 * open account pane
	 */
	void accountBtn(ActionEvent event) {
		myController.setScreen(ScreensFramework.account);
	}
	
	@FXML
	/*
	 * Open fav table
	 */
	void favBtn(ActionEvent event) {
		ScreensFramework.favViewer.updateTable();
		myController.setScreen(ScreensFramework.fav);
	}
	
	@FXML
	/*
	 * open stats window
	 */
	void statsBtn(ActionEvent event) {
		try {
			statsWindow sW = new statsWindow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	/*
	 * Open map
	 * */
	void mapBtn(ActionEvent event) {
		myController.setScreen(ScreensFramework.map);
	
	}
	/*
	 * Open filter window
	 * */
	void applyFilters(ActionEvent event) {
		try {
			FiltersWindow filters = new FiltersWindow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
	@FXML
	/*
	 * Sign out
	 * */
	void signOutBtn(ActionEvent event) {
		ScreensFramework.logOutUser();
		myController.setScreen(ScreensFramework.login);
	}
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		myController = screenPage; 
	
	}
	/*
	 * Open map view
	 * */
	private void map() {
		myController.setScreen(ScreensFramework.map);
	}
	/*
	 * Open list view
	 * */
	private void list() {
		if(ScreensFramework.tableViewer.chechNeighborhood()) {
			myController.setScreen(ScreensFramework.propertyList);
		}
	}	
	/*
	 * Call this method in order to update the user icon
	 * */
	public void updateUserImage() {
		if(ScreensFramework.userimage==null) {
			Image defaultImg = new Image("img/blank_profile.png");
			userCircle.setFill(new ImagePattern(defaultImg));
	}else userCircle.setFill(new ImagePattern(ScreensFramework.userimage));
	}

	@Override
	/*
	 * Is overwritten by child classes
	 * */
	public void initialize(URL location, ResourceBundle resources) {
		mainInterfaceInitialize();	
	}
	
	/*
	 * This method should be called by child classes when they are initialised
	 * */
	public void mainInterfaceInitialize() {
		//set button actions
		next.setOnMouseClicked(this::next);
		prev.setOnMouseClicked(this::prev);
		next.setVisible(false);
		prev.setVisible(false);
		dropdownMenu.getItems().clear();
		dropdownMenu.getItems().addAll(menuAccount,menuFav,menuStats,menuMap,menuSignOut);
	
		menuAccount.setOnAction(this::accountBtn);
		menuFav.setOnAction(this::favBtn);
		menuStats.setOnAction(this::statsBtn);
		menuSignOut.setOnAction(this::signOutBtn);
		menuMap.setOnAction(this::mapBtn);
		filtersBtn.setOnAction(this::applyFilters);
		
		listBtn.setVisible(false);
		mapBtn.setVisible(false);
		
		listBtn.setOnMouseClicked(evt -> list());
		mapBtn.setOnMouseClicked(evt -> map());

	
	}
	
	public void showAlert(String title, String message) {
	
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}


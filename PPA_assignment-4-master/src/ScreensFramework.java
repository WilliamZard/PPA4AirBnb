import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This is the main class that should run.
 * In this class all of the windows are loaded and the application is started 
 * 
 * @author Kenneth Kreindler
 
 */

public class ScreensFramework extends Application {

	//Current user data
	public static String username = null;
	public static Image userimage = null;
	
	//Give every view a name and the corresponding path to the FXML file
	public static String login = "welcome";
	public static String loginFile = "xml/welcomePage.fxml";
	
	public static String propertyList = "propertyList";
	public static String propertyListFile = "xml/propertyList.fxml";
	
	public static String map = "map";
	public static String mapFile = "xml/mapPage.fxml";

	public static String account = "account";
	public static String accountFile = "xml/userPage.fxml";
	
	public static String fav = "Favourite";
	public static String favFile = "xml/favList.fxml";
	
	//Create the MYSQL object
    public static MysqlCon con = new MysqlCon();
    
    //create controller classes to be set later. This allows interaction with the controller from other classes.
    public static tableViewController tableViewer = new tableViewController();
    public static mapController mapViewer = new mapController();
    public static UserAccController accViewer = new UserAccController();
    public static favList favViewer = new favList();
    
	@Override
	/*
	 * Modified from JFX Multiscreen Angie
	 */
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ScreensController mainController = new ScreensController();
		// Add views to Screen Controller using : mainController.loadScreen(this.screen1, this.screen1File)
		mainController.loadScreen(this.login, new FXMLLoader (getClass().getResource(this.loginFile)));
		mainController.loadScreen(this.propertyList, new FXMLLoader (getClass().getResource(this.propertyListFile)),tableViewer);
		mainController.loadScreen(this.map, new FXMLLoader (getClass().getResource(this.mapFile)),mapViewer);
		mainController.loadScreen(this.account, new FXMLLoader (getClass().getResource(this.accountFile)),accViewer);
		mainController.loadScreen(this.fav, new FXMLLoader (getClass().getResource(this.favFile)),favViewer);
		
		mainController.setScreen(this.login);
		
		Group root = new Group();
		root.getChildren().addAll(mainController);
		Scene scene  = new Scene (root);
		scene.getStylesheets().add("/style/style.css");
		
		//set stylesheet
		//scene.getStylesheets().add("/style/propertyList.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("London Property Viewer");
		primaryStage.show();
		
	}
	
	public static void logOutUser() {
		username = null;
		userimage = null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch();
	}
	
	

}

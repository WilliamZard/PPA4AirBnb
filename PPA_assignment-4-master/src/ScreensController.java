import java.io.IOException;
import java.util.HashMap;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * The purpose of this class is to keep records of all the screens,
 * Load, unload and set screens
 * 
 * @author Kenneth Kreindler  

 *
 */


public class ScreensController extends StackPane{
	private HashMap<String, Node> screens = new HashMap <>();
	
	public ScreensController () {
		super();
	}
	
	/**
	 * Add a screen to the hashmap
	 * @param name of the view
	 * @param Screen node
	 */
	public void addScreen (String name , Node screen) {
		screens.put(name, screen);		
	}
	
	/**
	 * Get the screen node of a particular screen 
	 * @param name of the screen to get
	 * @return screen node
	 */
	public Node getScreen (String name) {
		
		return screens.get(name);
	}
	
	/**
	 * Load screen into hashmap 
	 * @param name of the screen
	 * @param the root element of the screen as FXML loader
	 * @return Boolean if load is successful 
	 */
	public Boolean loadScreen(String name, FXMLLoader root) {
		try {
			Parent loadScreen = (Parent) root.load();
			ControlledScreen myScreenController = (ControlledScreen) root.getController();
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * Add the controller to the screen and then loadscreen
	 * @param name of the screen
	 * @param the root element of the screen as FXML loader
	 * @param the controller object for that screen
	 * @return Boolean if load is successful 
	 */
	public Boolean loadScreen(String name, FXMLLoader root, Object controllerObj) {
		root.setController(controllerObj);
		loadScreen(name, root);
		return true;
		
	}
	
	
	/**
	 * Set the current screen
	 * @param name of the screen
	 */
	public Boolean setScreen (final String name) {
		if(screens.get(name)!=null) {		
			if(!getChildren().isEmpty()) {
					getChildren().remove(0);
					getChildren().add(0, screens.get(name));
					return true;
					
			}
				getChildren().add(0, screens.get(name));
			return true;	
			}
		else {
			System.out.println("Screen does not exist");
		return false;
		}
		}
	
	/**
	 * Remove screen from current view
	 * @param name of the screen
	 * @return Boolean if unload is successful
	 */
	
	public Boolean unloadScreen (String name) {
		if(screens.remove(name)==null) {
			System.out.println("Screen does not exist");
			return false;
		}
		else {
			return true;
		}
		
		
	}


}


/*
 * Modified from JFX Multiscreen Angie
 */


import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.shapes.Circle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class controls the Map page and its functionality within the application, 
 * such as adapting to different filters set by the user and opening the property list
 * 
 * @author Jay 
 * Help From: Kenneth :)
 * Class that controls the map pane
 *
 */

public class mapController extends mainInterfaceController implements Initializable , ControlledScreen{
	
	//default rgb colours
	private int r = 0;
	private int g = 184;
	private int b = 148;
	

    // defining all the buttons from the fxml file for the map created on Scene Builder
    @FXML
    public ImageView filterBlock;
    
    @FXML
    public Font x1;

    @FXML
    public Color x2;

    @FXML
    public Button enfield;

    @FXML
    public Button islington;

    @FXML
    public Button hackney;

    @FXML
    public Button camden;

    @FXML
    public Button waltham;

    @FXML
    public Button barnet;

    @FXML
    public Button haringey;

    @FXML
    public Button brent;

    @FXML
    public Button harrow;

    @FXML
    public Button ealing;

    @FXML
    public Button kensington;

    @FXML
    public Button westminster;

    @FXML
    public Button newham;

    @FXML
    public Button havering;

    @FXML
    public Button redbridge;

    @FXML
    public Button barking;

    @FXML
    public Button hillingdon;

    @FXML
    public Button hounslow;

    @FXML
    public Button hammersmith;

    @FXML
    public Button wandsworth;

    @FXML
    public Button city;

    @FXML
    public Button greenwich;

    @FXML
    public Button bexley;

    @FXML
    public Button richmond;

    @FXML
    public Button merton;

    @FXML
    public Button lambeth;

    @FXML
    public Button southwark;

    @FXML
    public Button lewisham;

    @FXML
    public Button kingston;

    @FXML
    public Button sutton;

    @FXML
    public Button croydon;

    @FXML
    public Button bromley;

    @FXML
    public Button tower;

    // initialising all the buttons from the fxml file for the map created on Scene Builder
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		barnet.setOnMouseClicked((MouseEvent event) -> {openList("Barnet");});
		haringey.setOnMouseClicked((MouseEvent event) -> {openList("Haringey");});
		waltham.setOnMouseClicked((MouseEvent event) -> {openList("Waltham");});
		harrow.setOnMouseClicked((MouseEvent event) -> {openList("Harrow");});
		brent.setOnMouseClicked((MouseEvent event) -> {openList("Brent Cross");});
		camden.setOnMouseClicked((MouseEvent event) -> {openList("Camden");});
		islington.setOnMouseClicked((MouseEvent event) -> {openList("Islington");});
		enfield.setOnMouseClicked((MouseEvent event) -> {openList("Enfield");});		
		hackney.setOnMouseClicked((MouseEvent event) -> {openList("Hackney");});
		redbridge.setOnMouseClicked((MouseEvent event) -> {openList("Redbridge");});
		havering.setOnMouseClicked((MouseEvent event) -> {openList("Havering");});
		hillingdon.setOnMouseClicked((MouseEvent event) -> {openList("Hillingdon");});
		ealing.setOnMouseClicked((MouseEvent event) -> {openList("Ealing");});
		kensington.setOnMouseClicked((MouseEvent event) -> {openList("Kensington and Chelsea");});
		westminster.setOnMouseClicked((MouseEvent event) -> {openList("Westminster");});
		tower.setOnMouseClicked((MouseEvent event) -> {openList("Tower Hamlets");});
		newham.setOnMouseClicked((MouseEvent event) -> {openList("Newham");});
		barking.setOnMouseClicked((MouseEvent event) -> {openList("Barking");});
		hounslow.setOnMouseClicked((MouseEvent event) -> {openList("Hounslow");});
		hammersmith.setOnMouseClicked((MouseEvent event) -> {openList("Hammersmith and Fulham");});
		wandsworth.setOnMouseClicked((MouseEvent event) -> {openList("wandsworth");});
		city.setOnMouseClicked((MouseEvent event) -> {openList("City of London");});
		greenwich.setOnMouseClicked((MouseEvent event) -> {openList("Greenwich");});
		bexley.setOnMouseClicked((MouseEvent event) -> {openList("Bexley");});
		richmond.setOnMouseClicked((MouseEvent event) -> {openList("Richmond upon Thames");});
		merton.setOnMouseClicked((MouseEvent event) -> {openList("Merton");});
		lambeth.setOnMouseClicked((MouseEvent event) -> {openList("Lambeth");});
		southwark.setOnMouseClicked((MouseEvent event) -> {openList("Southwark");});
		lewisham.setOnMouseClicked((MouseEvent event) -> {openList("Lewisham");});
		kingston.setOnMouseClicked((MouseEvent event) -> {openList("Kingston upon Thames");});
		sutton.setOnMouseClicked((MouseEvent event) -> {openList("Sutton");});
		croydon.setOnMouseClicked((MouseEvent event) -> {openList("Croydon");});
		bromley.setOnMouseClicked((MouseEvent event) -> {openList("Bromley");});
		super.mainInterfaceInitialize();
				
	}
	
	/*
	 * Update the colours of the map while taking into account price range, room type 
	 * and the nights stayed
	 * This method adapts the opacity of the button colours depending on the filters of the user. 
	 * With different filters the opacity of the buttons which have the most properties will be higher than those with the least properties
	 * 
	 */
	public void updateColours() {
		int min = FiltersController.minRange;
		int max = FiltersController.maxRange;
		Map<Button, String> neighbourhoodMap = new HashMap<Button, String>();
		neighbourhoodMap.put(enfield, "Enfield");
		neighbourhoodMap.put(haringey, "Haringey");
		neighbourhoodMap.put(waltham, "Waltham Forest");
		neighbourhoodMap.put(harrow, "Harrow");
		neighbourhoodMap.put(barnet, "Barnet");
		neighbourhoodMap.put(brent, "Brent");
		neighbourhoodMap.put(camden, "Camden");
		neighbourhoodMap.put(islington, "Islington");
		neighbourhoodMap.put(hackney, "Hackney");
		neighbourhoodMap.put(redbridge, "Redbridge");
		neighbourhoodMap.put(havering, "Havering");
		neighbourhoodMap.put(hillingdon, "Hillingdon");
		neighbourhoodMap.put(ealing, "Ealing");
		neighbourhoodMap.put(kensington, "Kensington and Chelsea");
		neighbourhoodMap.put(westminster, "Westminster");
		neighbourhoodMap.put(tower, "Tower Hamlets");
		neighbourhoodMap.put(newham, "Newham");
		neighbourhoodMap.put(barking, "Barking and Dagenham");
		neighbourhoodMap.put(hounslow, "Hounslow");
		neighbourhoodMap.put(hammersmith, "Hammersmith and Fulham");
		neighbourhoodMap.put(wandsworth, "Wandsworth");
		neighbourhoodMap.put(city, "City of London");
		neighbourhoodMap.put(greenwich, "Greenwich");
		neighbourhoodMap.put(bexley, "Bexley");
		neighbourhoodMap.put(richmond, "Richmond upon Thames");
		neighbourhoodMap.put(merton, "Merton");
		neighbourhoodMap.put(lambeth, "Lambeth");
		neighbourhoodMap.put(southwark, "Southwark");
		neighbourhoodMap.put(lewisham, "Lewisham");
		neighbourhoodMap.put(kingston, "Kingston upon Thames");
		neighbourhoodMap.put(sutton, "Sutton");
		neighbourhoodMap.put(croydon, "Croydon");
		neighbourhoodMap.put(bromley, "Bromley");
		
		// This finds the maximum number of properties within the given filter options by the user 
		Map<String, Integer> numberOfProperties = ScreensFramework.con.getNumberOfPropertiesInNeighbourhoods(min, max, FiltersController.room, FiltersController.nights);
		double maxProperties = 0.0;
		Iterator<Map.Entry<String, Integer>> it = numberOfProperties.entrySet().iterator();
		//Search for max property
	    while(it.hasNext()) {
			Map.Entry<String, Integer> pair = it.next();
			if(pair.getValue()>maxProperties) {
				maxProperties = pair.getValue();
			}
		}
	    // This uses the maximum property number to find a ratio for each borough's number of properties compared to the total.
	    // From this ratio, button opacity is decided depending on the number of properties withing the borough, properties with the least will have a default opacity of 0.06
		Iterator<Map.Entry<Button, String>> it1 = neighbourhoodMap.entrySet().iterator();
	    while(it1.hasNext()) {
	    	Map.Entry<Button, String> pair1 = it1.next();
	    	if(numberOfProperties.containsKey(pair1.getValue())) {
	    	double buttonNumProperties = numberOfProperties.get(pair1.getValue());	
	    	double buttonOpacity = ((double)buttonNumProperties/(double)maxProperties);
			buttonOpacity = buttonOpacity + 0.1;
			pair1.getKey().setStyle("-fx-background-color: rgba("+r+","+g+","+b+","+buttonOpacity+")");
	    	}else {
	    		pair1.getKey().setStyle("-fx-background-color: rgba("+r+","+g+","+b+","+0.06+")");
	    	}
	    }	
		
	}

	/*
	 * This method is used to add a mouse event to a button
	 */
	void prev(MouseEvent event) {
    	
    	myController.setScreen(ScreensFramework.account);
    }
	/*
	 * This method allows the user to open a property list of any given borough upon button click, alongside filters or without
	 */
    private void openList(String n) {
		ScreensFramework.tableViewer.setNeighborhood(n);
		ScreensFramework.tableViewer.updateTable();
    	//open window
    	myController.setScreen(ScreensFramework.propertyList);
    }
    /*
     * This method opens up a property list on the next borough clicked on by the user
     */
    @FXML 
    void next(MouseEvent event) {
    	if(ScreensFramework.tableViewer.chechNeighborhood()) {
    		myController.setScreen(ScreensFramework.propertyList);
    	}
    }
    
   
   
    
    
    

	

}

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * This class is the controller class for the property window.
 * All of the elements and actions are contained within this class
 * 
 * @author Kenneth Kreindler
 *
 *	
 */

public class propertyItemController implements Initializable {

	//get mysql connection 
	private MysqlCon con = ScreensFramework.con;
	
	private String propertyId;
	
	//reference fxml elements
	@FXML
    private Label hostLbl;

    @FXML
    private Label priceLbl;

    @FXML
    private WebView webView;

    @FXML
    private Label nameLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private Label neighborhoodLbl;

    @FXML
    private Label reviewLbl;

    @FXML
    private CheckBox favoriteCheckBox;

    @FXML
    private Button bookBtn;
    
    @FXML
    private WebView propertyImage;
    
    //Initialise all of the elements in correspondence to the property id
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//get property List by ID
		ArrayList<AirbnbListing> propertyList = con.loadById(propertyId);
    	
		//get property
    	AirbnbListing property = propertyList.get(0);
    	
    	//add data to all of the fxml elements
    	hostLbl.setText("Your Host: "+ property.getHost_name());
    	priceLbl.setText("Price per night: £"+property.getPrice());
    	nameLbl.setText(property.getName());
    	typeLbl.setText(property.getRoom_type());
    	neighborhoodLbl.setText(property.getNeighbourhood());
    	
    	
    	String reviewString = property.getNumberOfReviews()+" Reviews.";
    	if(property.getLastReview()!=null || property.getLastReview() != "" || property.getLastReview() != " ") {
    		reviewString = reviewString +  " Last review: "+property.getLastReview();
    	}
    	reviewLbl.setText(reviewString);
    	favoriteCheckBox.setOnAction(this::favoriteProperty);
    	
    	
    	//set web view to show the long and lat in google maps. This uses a custom web site 
    	String url = "https://trust-clubs.com/googleMapsIframe?long="+property.getLongitude()+"&lat="+property.getLatitude();
    	WebEngine webEngine = webView.getEngine();
    	webEngine.load(url);
    	bookBtn.setOnAction(this::viewOnline);
    	
    	//Set if check box is selected
    	favoriteCheckBox.setSelected(property.getFavorite());
    	
    	
    	//Scrape image
    	try {
    		imageScraper tempImageScraper = new imageScraper(this.propertyId);
    		tempImageScraper.setUrl("https://www.airbnb.co.uk/rooms/"+propertyId);
    		String urlImg = tempImageScraper.scrapeImages();
    		if(urlImg==null) {
    			//if there is no image available, set "no image" image
    			urlImg = "http://denrakaev.com/wp-content/uploads/2015/03/no-image-800x511.png";
    		}
    			//set image with webview
    			WebEngine webEngineImg = propertyImage.getEngine();
	    		webEngineImg.load(urlImg);
    	}
    	catch(Exception e) {
    		//catch failed load exception 
    	}
    	
	}
	
	/**
	 * Add Airbnb url to button
	 * @param Actionevt
	 * @return
	 */
	
	 public void viewOnline(ActionEvent evt) {
		 String url = "https://www.airbnb.co.uk/rooms/"+propertyId;
		 try {
			    Desktop.getDesktop().browse(new URL(url).toURI());
			} catch (Exception e) {
				System.out.println(e);
			}
		 
	 }
	 
	 
 	/**
	 * Set the property id to be displayed
	 * @param property ID of property
	 * @return
	 */
	 
	public void setPropertyId(String id) {
		propertyId = id;
	}
	
	
	/*
	 * Update database if fav checkbox is toggled
	 */
	public void favoriteProperty(ActionEvent evt) {
		//add to database
		if(favoriteCheckBox.isSelected()) {
			ScreensFramework.con.addFavourite(ScreensFramework.username, propertyId);
		}
		
		if(!favoriteCheckBox.isSelected()) {
			ScreensFramework.con.removeFavourite(ScreensFramework.username, propertyId);
		}

    }
    
}

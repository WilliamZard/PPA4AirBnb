import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */ 

public class AirbnbListing {
    /**
     * The id and name of the individual property
     */
    private String id;
    private String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private String host_id;
    private String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private String neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private double latitude;
    private double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private String room_type;

    /**
     * The price per night's stay
     */
    private int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private int minimumNights;
    private int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private String lastReview;
    private double reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     *
     */
    private int availability365;
    
    //favorited
    private boolean favorite;
    
    //button for property list
    private Button viewBtn;
    CheckBox favCb ;

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365, boolean fav) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
        this.favorite = fav;
        this.viewBtn = new Button("View");
        this.favCb = new CheckBox();
        favCb.setSelected(fav);
        viewBtn.setOnAction(this::openPropertyView);
        viewBtn.setMinSize(60, 25);
        
        favCb.setOnAction(this::favoriteProperty);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHost_id() {
        return host_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRoom_type() {
        return room_type;
    }

    public int getPrice() {
        return price;
    }

    public int getMinimumNights() {
        return minimumNights;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public String getLastReview() {
        return lastReview;
    }

    public double getReviewsPerMonth() {
        return reviewsPerMonth;
    }

    public int getCalculatedHostListingsCount() {
        return calculatedHostListingsCount;
    }

    public int getAvailability365() {
        return availability365;
    }
    
    public void setView(Button b) {
    	viewBtn = b;
    }
    
    public Button getView() {
    	return viewBtn;
    }
    
    public void setFav(CheckBox c) {
    	favCb = c;
    }
    
    public CheckBox getFav() {
    	return favCb;
    }
    
    /*
     * Open item viewer
     */
    public void openPropertyView(ActionEvent evt) {
    	try {
			propertyWindow test = new propertyWindow(this.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    }
    
    /*
     * Add action for check box
     */
    public void favoriteProperty(ActionEvent evt) {
    	Boolean tempFavorite = this.getFavorite();
    	tempFavorite = !tempFavorite;
    	this.setFavorite(tempFavorite);
    	
    	if(favCb.isSelected()) {
			ScreensFramework.con.addFavourite(ScreensFramework.username, this.id);
		}
		
		if(!favCb.isSelected()) {
			ScreensFramework.con.removeFavourite(ScreensFramework.username, this.id);
		}

    }
    
    public void setFavorite(Boolean f) {
    	//add to database
    	System.out.println(this.getName()+" "+this.getId());
    	favorite = f;
    }
    
    public Boolean getFavorite() {
    	return favorite;
    }

    @Override
    public String toString() {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
}

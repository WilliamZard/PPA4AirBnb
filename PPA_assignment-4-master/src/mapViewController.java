import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class mapViewController extends mainInterfaceController implements Initializable, ControlledScreen, MapComponentInitializedListener {

	@FXML
    GoogleMapView mapView;
	
	GoogleMap map;

	
	@Override
	public void setScreenParent(ScreensController screensController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		mapView = new GoogleMapView();
	    mapView.addMapInializedListener(this);
	   
		
	}

	@Override
	public void mapInitialized() {
	    //Set the initial properties of the map.
		super.mainInterfaceInitialize();
	    MapOptions mapOptions = new MapOptions();

	    mapOptions.center(new LatLong(47.6097, -122.3331))
	           
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .zoom(12);

	    map = mapView.createMap(mapOptions);

	    //Add a marker to the map
	    MarkerOptions markerOptions = new MarkerOptions();

	    markerOptions.position( new LatLong(47.6, -122.3) )
	                .visible(Boolean.TRUE)
	                .title("My Marker");

	    Marker marker = new Marker( markerOptions );

	    map.addMarker(marker);

	}

}

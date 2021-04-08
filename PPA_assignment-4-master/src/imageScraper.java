import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * This class provides the methods used for scraping images from the airBnb website
 * When a property is opened an instance of this class is created
 * 
 * @author Kenneth Kreindler
 * @version 2
 */



public class imageScraper {
    private ArrayList < String > images;
    private String url;

    /**
	 * Consructor 
	 * @param URL of the property
	 * @return object
	 */
    public imageScraper(String url) {
        images = new ArrayList < > ();
        this.url = url;
    }


    /**
	 * Get the list of image urls
	 * @return Arraylist of strings
	 */
    public ArrayList < String > getImages() {
        return images;
    }


    /**
	 * Add images to the image list for this property. 
	 * @param string url of image
	 * @return void
	 */
    public void setImages(ArrayList < String > images) {
        this.images = images;
    }


    /**
	 * Get the url of this objectt
	 * @return string
	 */
    public String getUrl() {
        return url;
    }


    /**
	 * Set the url for this object 
	 * @param string url
	 * @return void
	 */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
	 * Scrape the first image of the property from the airBnb website
	 * 
	 * @return url of the image of this proeprty. If non exists, a no image image link
	 */
    public String scrapeImages() {
    	//create document in order to process html elements 
        Document document;
        if (url != null) {
            try {
            	
            	//read html from url
                URL urltemp = new URL(url);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urltemp.openStream(), "UTF-8"))) {
                    String html = "";
                    for (String line;
                        (line = reader.readLine()) != null;) {
                        html = html + line;
                    }
                    
                    //parse html using jsoup
                    Document doc = Jsoup.parse(html);
                    Elements withAttr = new Elements();
                    Element found = null;
                    
                    //use Jsoup in order to find html element with specific attribute 
                    breakLoop1:
                        for (Element element: doc.getAllElements()) {
                            breakLoop2: for (Attribute attribute: element.attributes()) {
                                if (attribute.getValue().equalsIgnoreCase("image")) {
                                	//set found to this element
                                    found = element;
                                    //exit loop
                                    break breakLoop1;

                                }
                            }
                        }
                    if (found != null) {
                    	//get the value of this attribute
                        String imageulr = found.attr("content");
                        images.add(imageulr);
                        //return url
                        return imageulr;
                    } else {
                        return null;
                    }
                }
            } catch (IOException e) {
            	//catch errors
                e.printStackTrace();
                return null;
            }
        } else {
        	//if no url is set
            System.out.println("Please set URL to scrape");
            return null;
        }

    }



}
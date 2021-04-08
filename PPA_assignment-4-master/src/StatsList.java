import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class StatsList here.
 *
 * @author William Z
 * @version 1
 */
public class StatsList
{
    private MysqlCon SQL = ScreensFramework.con;
    public static ArrayList <AirbnbListing> data;	//create ArrayList data.

    
    public StatsList()
    {
        data = SQL.loadAll();	// to get data from MysqlCon.
    }



    /**
     * Iterates through the CSV file and finds the most expensive Borough by multiplying
     * the price of each property.
     *
     * @return the most expensive borough.
     */

    public int boroughPrice()
    {
        ArrayList<Integer> mostExpensiveBorough = new ArrayList<>();
        int maxPrice = -1;
        int max = Integer.MIN_VALUE;
        for (AirbnbListing listing : data)
        {
            Integer price = listing.getPrice()*listing.getMinimumNights();
            mostExpensiveBorough.add(price);
        }

        for (int i=0; i < mostExpensiveBorough.size(); i++)
        {
            int value = mostExpensiveBorough.get(i);
            if (value > max)
            {
                max = value;
                maxPrice = i;
            }
        }
        return maxPrice;
    }

    /**
     * Iterate through the listing and add all boroughs to a list. Then use the method boroughPrice()
     *To get the highest price.
     *
     * @return the borough with the highest price.
     */
    public String mostExpensiveBorough()
    {
        ArrayList <String> borough = new ArrayList<>();
        for (AirbnbListing listing : data)
        {
            String boroughs = listing.getNeighbourhood();
            borough.add(boroughs);
        }
        return borough.get(boroughPrice());
    }

    /**
     * Iterate through the listing and adds the number of reviews for each property to a list.
     *
     * @return The number of reviews.
     */
    public ArrayList <Integer> getReviews()
    {
        ArrayList <Integer> noOfReviews = new ArrayList<>();
        for (AirbnbListing listing : data)
        {
            Integer reviews = listing.getNumberOfReviews();
            noOfReviews.add(reviews);
        }
        return noOfReviews;
    }

    /**
     * Iterate through the csv file and checks the availabilty of the properties and adds them to the list
     * availableProperties i.e. if its greater than 0.
     *
     * @return Array List of integers
     */
    public ArrayList <Integer> availableProperties()
    {
        ArrayList <Integer> availableProperties = new ArrayList<>();
        for(AirbnbListing listing : data)
        {
            int available = listing.getAvailability365();
            if (available > 0)
            {
                availableProperties.add(available);
            }
        }
        return availableProperties;
    }

    /**
     * Iterates through listing, gets the prices and adds them to "pricesOfAvailableProperties"
     * if they are available properties.
     *
     * @return Prices Of Available Properties, type integer.
     */
    public ArrayList<Integer> pricesOfAvailableProperties()
    {

        ArrayList <Integer> pricesOfAvailableProperties = new ArrayList<>();
        for(AirbnbListing listing : data)
        {
            int available = listing.getAvailability365();
            int price = listing.getPrice()*listing.getMinimumNights();
            if (available > 0 && price > -1)
            {
                pricesOfAvailableProperties.add(price);
            }
        }
        return pricesOfAvailableProperties;
    }

    /**
     * Iterate through the CSV file and adds the room type of the property to the array list if
     * it is of type "Entire home/apt".
     *
     * @return number of entireHomesAndApartments from the array list.
     */

    public ArrayList <String> entireHomesAndApartments()
    {
        ArrayList <String> entireHomesAndApartments = new ArrayList<>();
        for (AirbnbListing listing : data)
        {
            String roomType = listing.getRoom_type();
            if(roomType.equals("Entire home/apt"))
            {
                entireHomesAndApartments.add(roomType);
            }
        }
        return entireHomesAndApartments;
    }

    /**
     * Create a HashMap and store the data from the existing listing.
     * Count the number of times a certain Borough is repeated to get the most frequent borough.
     *
     * @return element, as type AirbnbListing, repeated the most.
     */
    public String modalBorough()
    {
        Map <String, Integer> mb = new HashMap <String, Integer>();
        for (int i=0; i< data.size(); i++)
        {
            String borough = data.get(i).getNeighbourhood();
            if (mb.get(borough) == null)
            {
                mb.put(borough, 1);
            }
            else
            {
                int newFrequency = mb.get(borough) + 1;
                mb.replace(borough, newFrequency);
            }
        }
        String element = null;
        int maxValue = -1;
        for(Map.Entry<String, Integer> entry: mb.entrySet()) {
            if(entry.getValue() > maxValue) {
                element = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        return element;
    }

    /**
     * Goes through listing and adds to a new list the minimum nights needed to spend in
     * entire homes and apartments.
     *
     * @return Minimum number of nights per property, type integer.
     */
    public ArrayList <Integer> minNights()
    {
        ArrayList <Integer> minNightsOfEH = new ArrayList<>();
        for(AirbnbListing listing : data)
        {
            int minNights = listing.getMinimumNights();
            if (minNights > -1)
            {
                minNightsOfEH.add(minNights);
            }
        }
        return minNightsOfEH;
    }

    public String modalHost()

    {
        Map <String, Integer> mb = new HashMap <String, Integer>();
        for (int i=0; i< data.size(); i++)
        {
            String host = data.get(i).getHost_name();
            if (mb.get(host) == null)
            {
                mb.put(host, 1);
            }
            else
            {
                int newFrequency = mb.get(host) + 1;
                mb.replace(host, newFrequency);
            }
        }
        String element = null;
        int maxValue = -1;
        for(Map.Entry<String, Integer> entry: mb.entrySet()) {
            if(entry.getValue() > maxValue) {
                element = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        return element;
    }

    public HashMap<String,Integer> avgReviewGraphData()
    {
        HashMap<String, Integer> graphData = new HashMap<>();
        data.stream().map(AirbnbListing::getNeighbourhood).distinct().forEach(boroughName -> data.stream()
                .filter(e -> e.getNeighbourhood().equals(boroughName))
                .mapToInt(AirbnbListing::getNumberOfReviews).average()
                .ifPresent(avgInt -> graphData.put(boroughName, (int) avgInt)));
        return graphData;
    }
}

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class controls the Stats window, allowing the user to switch between stats and view information
 * @author William Zard
 *
 */

public class statsController implements Initializable {

	private MysqlCon con = ScreensFramework.con;
	ScreensController myController;

	@FXML
	private Button next2;

	@FXML
	private Button next1;

	@FXML
	private Label label21;

	@FXML
	private Label label31;

	@FXML
	private Label label41;

	@FXML
	private Label label1;

	@FXML
	private Label label2;

	@FXML
	private Label label11;

	@FXML
	private Label label3;

	@FXML
	private Label label4;

	@FXML
	private Button previous2;

	@FXML
	private Button previous1;

	@FXML
	private Button next4;

	@FXML
	private Button next3;

	@FXML
	private Button previous4;

	@FXML
	private Button previous3;



	private String viewName = "Statistics";
	private StatsList SL = new StatsList();
	private ArrayList <StatItem> stats = new ArrayList<>();
	private LinkedList<StatItem> LL = new LinkedList();
	int index1 = 0;
	int index2 =1;
	int index3 =2;
	int index4 =3;



	//Opens new window displaying a specific property
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Create a new StatItem with what to display in each pane (statistic box).
		StatItem s1 = new StatItem("        Most Expensive Borough", getMostExpensiveBorough());
		StatItem s2 = new StatItem("        Average Number of Reviews", getAverageNumberOfReviews());
		StatItem s3 = new StatItem("           Available Properties", getNoOfAvailableProperties());
		StatItem s4 = new StatItem("           Entire home/apartments", getEntireHomesAndApartments());
		StatItem s5 = new StatItem("        Host with most Properties", getModalHost());
		StatItem s6 = new StatItem(         "Most Common Borough", getModalBorough());
		StatItem s7 = new StatItem(         "Average Price of Available Properties", getAveragePriceOfAvailableProperties());
		StatItem s8 = new StatItem("Avg. Min. Nights in Entire Homes/ apt" , getAverageMinNights());


		stats.add(s1);
		stats.add(s2);
		stats.add(s3);
		stats.add(s4);
		LL.add(s5);
		LL.add(s6);
		LL.add(s7);
		LL.add(s8);

		next1.setOnAction(this::action1next);
		previous1.setOnAction(this::action1prev);

		next2.setOnAction(this::action2next);
		previous2.setOnAction(this::action2prev);

		next3.setOnAction(this::action3next);
		previous3.setOnAction(this::action3prev);

		next4.setOnAction(this::action4next);
		previous4.setOnAction(this::action4prev);


		label21.setText("Average Number of Reviews");


		label31.setText("Available Properties");


		label41.setText("Entire home/apartments");


		label1.setText(getMostExpensiveBorough());


		label2.setText(getAverageNumberOfReviews());


		label11.setText("Most Expensive Borough");


		label3.setText(getNoOfAvailableProperties());


		label4.setText(getEntireHomesAndApartments());

	}
	@FXML
	private void displayGraph()
	{
		BoroughChart.showBoroughChart(SL.avgReviewGraphData());
	}

	/*
	 * Next button pressed
	 * rotates statistics 
	 * */
	@FXML
	void action1next(ActionEvent evt) {
		LL.add(stats.get(index1));
		stats.remove(index1);
		stats.add(index1,LL.pop());
		label1.setText(stats.get(index1).getStat());
		label11.setText(stats.get(index1).getTitle());

	}

	@FXML
	void action2next(ActionEvent evt) {
		LL.add(stats.get(index2));
		stats.remove(index2);
		stats.add(index2,LL.pop());
		label2.setText(stats.get(index2).getStat());
		label21.setText(stats.get(index2).getTitle());
	}

	@FXML
	void action3next(ActionEvent evt) {
		LL.add(stats.get(index3));
		stats.remove(index3);
		stats.add(index3,LL.pop());
		label3.setText(stats.get(index3).getStat());
		label31.setText(stats.get(index3).getTitle());
	}

	@FXML
	void action4next(ActionEvent evt) {
		LL.add(stats.get(index4));
		stats.remove(index4);
		stats.add(index4,LL.pop());
		label4.setText(stats.get(index4).getStat());
		label41.setText(stats.get(index4).getTitle());

	}

	@FXML
	void action1prev(ActionEvent evt) {
		StatItem currenttemp = stats.get(index1);
		addToTop(currenttemp);
		stats.remove(index1);
		stats.add(index1,getLast());
		label1.setText(stats.get(index1).getStat());
		label11.setText(stats.get(index1).getTitle());
	}

	@FXML
	void action2prev(ActionEvent evt) {
		StatItem currenttemp = stats.get(index2);
		addToTop(currenttemp);
		stats.remove(index2);
		stats.add(index2,getLast());
		label2.setText(stats.get(index2).getStat());
		label21.setText(stats.get(index2).getTitle());
	}

	@FXML
	void action3prev(ActionEvent evt) {
		StatItem currenttemp = stats.get(index3);
		addToTop(currenttemp);
		stats.remove(index3);
		stats.add(index3,getLast());
		label3.setText(stats.get(index3).getStat());
		label31.setText(stats.get(index3).getTitle());
	}

	@FXML
	void action4prev(ActionEvent evt) {

		StatItem currenttemp = stats.get(index4);
		addToTop(currenttemp);
		stats.remove(index4);
		stats.add(index4,getLast());
		label4.setText(stats.get(index4).getStat());
		label41.setText(stats.get(index4).getTitle());
	}

	/**
	 * Add an element to LL.
	 *
	 * @param s
	 */
	public void addTo(StatItem s){
		LL.add(s);
	}

	/**
	 * Retrieve the last element and remove it from LL.
	 *
	 * @return Last element in LL.
	 */
	public StatItem getLast(){
		StatItem tempLast = LL.removeLast();
		return tempLast;
	}

	/**
	 * Adds an element to index 0 of LL.
	 *
	 * @param s
	 */
	public void addToTop(StatItem s){
		LL.add(0, s);
	}

	/**
	 * @return The most expensive borough from the data in the CSV.
	 */
	public String getMostExpensiveBorough()
	{
		return SL.mostExpensiveBorough();
	}

	/**
	 *Go through and add all the reviews for each property.
	 *Then compute an average by dividing by the number of properties.
	 *
	 *@return The final average converted into a String to be displayed.
	 */
	public String getAverageNumberOfReviews()
	{
		int sum = 0;
		for (int reviews : SL.getReviews())
		{
			sum += reviews;
		}
		sum = sum/SL.getReviews().size();
		return String.valueOf(sum);
	}

	/**
	 * Get the size (number) of the list of available properties.
	 *
	 * @return The size converted to a string.
	 */
	public String getNoOfAvailableProperties()
	{
		return String.valueOf(SL.availableProperties().size());
	}

	/**
	 * Gets the total number of properties from the StatsList class.
	 *
	 * @return The number of all properties as a string.
	 */
	public String getModalHost()
	{
		return String.valueOf(SL.modalHost());
	}

	/**
	 * Gets the number of "Entire home/apt" from the StatsList class.
	 *
	 * @return the size of array list entireHomesAndApartments() as a string.
	 */
	public String getEntireHomesAndApartments()
	{
		return String.valueOf(SL.entireHomesAndApartments().size());
	}



	/**
	 * Obtain the name of the borough with the most properties using the "modalBorough()" method
	 * from the StatsList class.
	 *
	 * @return Neighbourhood name as a String.
	 */
	public String getModalBorough()
	{
		return String.valueOf(SL.modalBorough());
	}

	/**
	 * Goes through all available properties and sums their prices.
	 * Then obtains the average by dividing with the size of "availableProperties".
	 *
	 * @return the average price of available properties as a String.
	 */
	public String getAveragePriceOfAvailableProperties()
	{
		int sum = 0;
		for (int price : SL.pricesOfAvailableProperties())
		{
			sum += price;
		}
		sum = sum/ SL.availableProperties().size();
		return String.valueOf(sum);
	}

	/**
	 * Get the average minimum nights of "Entire homes/apt" by iterating
	 * through the list made in class StatsList in method "minNightsOfEH()" and dividing by its size.
	 * @return
	 */
	public String getAverageMinNights()
	{
		int sum = 0;
		for (int minNights : SL.minNights())
		{
			sum += minNights;
		}
		sum = sum/ SL.minNights().size();
		return String.valueOf(sum);
	}
	



}


/**
 * Write a description of class StatItem here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatItem
{
    // instance variables - replace the example below with your own
    private String title;
    private String statistic;

    /**
     * Constructor for objects of class StatItem
     */
    public StatItem(String t, String s)
    {
        // initialise instance variables
        title= t;
        statistic = s;
    }

    /**
     * Method to get the title of the statistic
     *
     * @return tit;e, type String.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Method to get the statistic.
     *
     * @return statistic, type String.
     */
    public String getStat(){
        return statistic;
    }

}
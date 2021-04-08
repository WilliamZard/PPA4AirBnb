import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

class statsControllerTest {

    private statsController stat = new statsController();
    

@Before
public void setUp() throws Exception {


}

@After
public void tearDown() throws Exception {
}

@Test
public void getMostExpensiveBorough() {

    String name = "Richmond upon Thames";
    assertEquals(name, stat.getMostExpensiveBorough());
}

@Test
public void getAverageNumberOfReviews() {
    assertEquals("12", stat.getAverageNumberOfReviews());
}

@Test
public void getNoOfAvailableProperties() {
    assertEquals("41940", stat.getNoOfAvailableProperties());
}

@Test
public void getModalHost() {
    assertEquals("Tom", stat.getModalHost());
}

@Test
public void getEntireHomesAndApartments() {
    assertEquals("27175", stat.getEntireHomesAndApartments());
}

@Test
public void getModalBorough() {
    assertEquals("Tower Hamlets", stat.getModalBorough());
}

@Test
public void getAveragePriceOfAvailableProperties() {
    assertEquals("406", stat.getAveragePriceOfAvailableProperties());
}

@Test
public void getAverageMinNights() {
    assertEquals("3", stat.getAverageMinNights());
}
}

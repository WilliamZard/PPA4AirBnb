import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.HashMap;


public class BoroughChart {

    public static void showBoroughChart (HashMap<String,Integer> graphData) {
        Stage stage = new Stage();
        stage.setTitle("Average Reviews per Neighbourhood");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);

        XYChart.Series series1 = new XYChart.Series();
        graphData.entrySet().forEach(e ->  series1.getData().add(new XYChart.Data(e.getKey(), e.getValue())));
        bc.getData().add(series1);

        bc.setTitle("Average Reviews per Neighbourhood");
        xAxis.setLabel("Neighbourhood");
        yAxis.setLabel("Avg. Reviews");
        Scene scene = new Scene(bc, 800, 600);
        stage.setScene(scene);
        stage.show();

    }

}

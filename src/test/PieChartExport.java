package test;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

public class PieChartExport extends Application {
  @Override public void start(Stage stage) throws IOException {
    // create a chart.
    ObservableList<PieChart.Data> pieChartData =
      FXCollections.observableArrayList(
        new PieChart.Data("Grapefruit", 13),
        new PieChart.Data("Oranges", 25),
        new PieChart.Data("Plums", 10),
        new PieChart.Data("Pears", 22),
        new PieChart.Data("Apples", 30)
      );
    final PieChart chart = new PieChart(pieChartData);
    chart.setTitle("Imported Fruits");

    // render an image of the chart to a file.
    Pane chartContainer = new Pane();
    chartContainer.getChildren().add(chart);
    chart.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    chart.setPrefSize(2000, 2000);
    chart.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    chart.setStyle("-fx-font-size: 36px;");
    Scene snapshotScene = new Scene(chartContainer); 
    SnapshotParameters params = new SnapshotParameters();
    params.setFill(Color.ALICEBLUE);
    Image image = chartContainer.snapshot(params, null);
    File file = new File("chart.png");
    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

    // layout the scene.
    VBox layout = new VBox(5);
    layout.getChildren().addAll(
      new Label("Wrote: " + file.getCanonicalPath()),
      new ImageView(new Image(file.toURI().toString(), 400, 0, true, true))
    );
    layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

    // show the scene.
    stage.setScene(new Scene(layout));
    stage.show();
  }
  
  public static void main(String[] args) { launch(args); }
}
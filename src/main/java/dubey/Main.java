package dubey;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Dubey using FXML.
 */
public class Main extends Application {

    private Dubey dubey = new Dubey("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        assert stage != null : "Stage should be initialized before use";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Dubey");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(dubey); // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

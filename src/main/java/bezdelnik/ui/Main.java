package bezdelnik.ui;

import java.io.IOException;

import bezdelnik.utils.Bezdelnik;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



/**
 * A GUI for Bezdelnik using FXML.
 */
public class Main extends Application {

    private Bezdelnik bezdelnik = new Bezdelnik();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBezdelnik(bezdelnik);
            stage.setTitle("Bezdelnik");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

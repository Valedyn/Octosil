package GUI;

import Vital.Calendar;
import Vital.Task;
import Vital.Writer;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
    private final String title = "Task Viewer";
    private final Root root = new Root();


    @Override
    public void start(Stage window) throws Exception {
        window = root.window(title);
    }

    public static void main(String[] args) {
        launch();
    }



}

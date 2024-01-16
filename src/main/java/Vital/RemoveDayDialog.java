package Vital;

import GUI.Root;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RemoveDayDialog extends Dialog<Task> {
    private Day day;
    private Writer writer = new Writer();
    private VBox contents;
    public RemoveDayDialog(Day day, VBox contents){
        super();
        this.day = day;
        setWidth(400);
        setHeight(400);
        this.setTitle("Remove Task");
        this.contents = contents;
        ui();

    }

    private void ui(){
        Pane pane = new Pane();
        pane.prefWidthProperty().bind(widthProperty().subtract(100));
        pane.prefHeightProperty().bind(heightProperty());
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(contents);
    scrollPane.prefWidthProperty().bind(pane.prefWidthProperty());
    scrollPane.prefHeightProperty().bind(pane.prefHeightProperty());
        contents.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        contents.prefHeightProperty().bind(scrollPane.prefHeightProperty());
    ArrayList<Label> labels = getLabels(contents);
    contents.getChildren().addAll(labels);
    pane.getChildren().add(scrollPane);
    Button close = new Button("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                close();
            }
        });
        close.layoutXProperty().bind(pane.prefWidthProperty().add(15));
        close.layoutYProperty().bind(pane.prefHeightProperty().multiply(0.3));
        pane.getChildren().add(close);

    getDialogPane().setContent(pane);
    }

   private ArrayList<Label> getLabels(VBox vbox){
       Task[] tasks_ascending = writer.getTasks_ascending();
       ArrayList<Label> labels = new ArrayList<>();
       if(tasks_ascending != null) {
           for (Task task : tasks_ascending) {
               if(task.getOccurence_date().isEqual(day.getDate())) {

                   String format_string = task.toString();
                   Label task_label = new Label(format_string);
                   String finished = "Task not finished!";
                   if (task.isFinished()) {
                       finished = "Task finished!";
                   }
                   task_label.setText(task_label.getText() + finished);
                   task_label.prefWidthProperty().bind(vbox.prefWidthProperty().subtract(1));
                   if (task.isFinished()) {
                       task_label.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
                   } else {
                       task_label.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                   }
                   task_label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                       @Override
                       public void handle(MouseEvent mouseEvent) {
                           vbox.getChildren().remove(task_label);
                           writer.deleteTask(task);
                       }
                   });
                   labels.add(task_label);
               }
           }
       }
       return labels;
   }

   public VBox getContents(){
        return contents;
   }
}

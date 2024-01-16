package Vital;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddDayDialog extends Dialog<Task> {
    private Task task;
    private LocalDate localDate;
    private boolean closed_normally = false;
    public AddDayDialog(Task task, LocalDate localDate){
        super();
        setWidth(200);
        setHeight(500);
        this.setTitle("Add Task");
        this.task = task;
        this.localDate = localDate;
        ui();
    }

    private void ui(){
        GridPane pane = new GridPane();
        Label name_label = new Label("Enter the name of your task!");
        TextField name = new TextField();
        Label description_label = new Label("Describe your task!");
        TextField description = new TextField();
        Label timeField_label = new Label("At what time does this task take place?");
        TextField timeField = new TextField();

        Button okay = new Button("Okay");
        Button cancel = new Button("Cancel");
        cancel.setTranslateX(150);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                close();
            }
        });

        Label error = new Label("");
        okay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String error_message = "";
                if(name.getText().isEmpty() || name.getText().isBlank()){
                    error_message = "ERR: Name can't be empty!\n";
                }
                if(description.getText().isEmpty() || description.getText().isBlank()){
                    error_message += "ERR: Description can't be empty!\n";
                }
                LocalTime time = null;
                try{
                    time = LocalTime.parse(timeField.getText());
                }catch(java.time.format.DateTimeParseException e){
                    error_message += "ERR: Allowed time formatting is HH:MM\n, or HH:MM:SS!";
                }
                error.setText(error_message);
                if(error_message.isEmpty()){
                    task = new Task(name.getText(), description.getText(), localDate, time, 1, false);
                    getDialogPane().getButtonTypes().addAll(ButtonType.YES);
                    closed_normally = true;
                    close();
                }
            }
        });
        error.setTextFill(Color.RED);
        pane.add(name_label, 0, 0);
        pane.add(name, 0, 1);
        pane.add(description_label, 0, 2);
        pane.add(description, 0, 3);
        pane.add(timeField_label, 0, 4);
        pane.add(timeField, 0, 5);
        pane.add(okay, 0, 6);
        pane.add(cancel, 0, 6);
        pane.add(error, 0, 7);

        getDialogPane().setContent(pane);
    }
    public Task getTask(){
        return task;
    }

    public boolean closedNormally(){
        return this.closed_normally;
    }

}

package GUI;

import Vital.*;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;

public class Root{
    private final double def_window_height = 700;
    private final double def_window_width = 1000;
    private final double min_window_width = 600;
    private final double min_window_height = 600;
    private final Writer writer = new Writer();
    private Calendar calendar = new Calendar();
    private VBox left_contents = new VBox();

    public Stage window(String title){
        Stage window = new Stage();
        Group gui_elements = new Group();

        Scene start = new Scene(gui_elements, def_window_width, def_window_height);
        Pane screen = new Pane();
        screen.prefWidthProperty().bind(window.widthProperty());
        screen.prefHeightProperty().bind(window.heightProperty());
        VBox foundation = foundation();
        ScrollPane left = left(left_contents);
        Line menubar_separator = new Line();
        menubar_separator.setStartY(37.5);
        menubar_separator.setEndY(37.5);
        menubar_separator.endXProperty().bind(window.widthProperty());
        menubar_separator.setFill(Color.LIGHTGRAY);
        screen.getChildren().add(menubar_separator);

        left.setTranslateY(45);
        left.setPrefWidth(200);
        left.prefHeightProperty().bind(window.heightProperty().multiply(0.8));

        screen.getChildren().add(left);
        screen.getChildren().add(foundation);
        gui_elements.getChildren().add(screen);


        GridPane center = center(window);
        calendarSetup(center);

        ChoiceBox<String> months = new ChoiceBox<String>();
        for(int month = 1; month <= 12; month++){
            months.getItems().add(calendar.month_name(month));
        }
        months.setValue(calendar.month_name());
        months.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old, Number updated) {
                LocalDate selected_date = calendar.getSelected_date();

                selected_date = selected_date.minusMonths(selected_date.getMonthValue() - calendar.month_name_to_number(months.getItems().get((Integer) updated)));
                calendar.setSelected_date(selected_date);
                calendarSetup(center);
            }
        });



        months.layoutXProperty().bind(center.layoutXProperty().add(center.widthProperty().multiply(0.3)));
        months.layoutYProperty().bind(center.layoutYProperty().subtract(40));
        Label month = new Label("Month: ");
        month.layoutXProperty().bind(months.layoutXProperty().subtract(50));
        month.layoutYProperty().bind(months.layoutYProperty().add(5));

        Label year = new Label("Year: ");
        year.layoutXProperty().bind(months.layoutXProperty().add(100));
        year.layoutYProperty().bind(month.layoutYProperty());
        TextField year_field = new TextField();
        year_field.layoutXProperty().bind(year.layoutXProperty().add(40));
        year_field.layoutYProperty().bind(months.layoutYProperty());
        year_field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    year_field.setText(newValue.replaceAll("\\D+", ""));

                }
            }
        });

        year_field.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER){
                        LocalDate selected_date = calendar.getSelected_date();
                        selected_date = LocalDate.of(Integer.parseInt(year_field.getText()), selected_date.getMonth().getValue(), selected_date.getDayOfMonth());
                        calendar.setSelected_date(selected_date);
                        calendarSetup(center);
                }
            }
        });

        screen.getChildren().addAll(month, months, year, year_field, center);

        window.setMinHeight(min_window_height);
        window.setMinWidth(min_window_width);

        window.setScene(start);
        window.setTitle(title);
        window.show();
        return window;
    }

    public VBox foundation(){
        VBox elements = new VBox();
        elements.getChildren().add(menubar());


        return elements;
    }


    public MenuBar menubar(){
        MenuBar menubar = new MenuBar();
        menubar.setPrefHeight(30);
        menubar.getMenus().add(options());
        return menubar;
    }

    public Menu options(){
        Menu options = new Menu("Options");
        MenuItem appearance = new MenuItem("Appearance");
        options.getItems().add(appearance);
        return options;
    }
    public ScrollPane left(VBox contents){
        ScrollPane tasks = new ScrollPane();
        tasks.setTranslateY(10);
        tasks.setFocusTraversable(false);
        contents.prefWidthProperty().bind(tasks.widthProperty().subtract(1));
        contents.setSpacing(10);
        for(Label label : getTaskLabels(contents)){
            contents.getChildren().add(label);
        }
        tasks.setContent(contents);
        return tasks;
    }

    public ArrayList<Label> getTaskLabels(VBox left_contents){
        Task[] tasks_ascending = writer.getTasks_ascending();
        ArrayList<Label> labels = new ArrayList<>();
        if(tasks_ascending != null) {
            for (Task task : tasks_ascending) {
                String format_string = String.format("%s\n%s\n%s - %s\n", task.getName(), task.getDefinition(), task.getOccurence_date().toString(), task.getOccurrence_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                Label task_label = new Label(format_string);
                String finished = "Task not finished!";
                if(task.isFinished()){
                    finished = "Task finished!";
                }
                task_label.setText(task_label.getText() + finished);
                task_label.prefWidthProperty().bind(left_contents.prefWidthProperty().subtract(1));
                if(task.isFinished()){
                    task_label.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
                }else {
                    task_label.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
                task_label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        writer.deleteTask(task);
                        task.toggleFinished();
                        if(task.isFinished()){
                            task_label.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
                            writer.addTaskData(task);
                            task_label.setText(format_string + "Task finished!");
                        }else {
                            task_label.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                            writer.addTaskData(task);
                            task_label.setText(format_string + "Task not finished!");
                        }
                    }
                });
                labels.add(task_label);
            }
        }
        return labels;
    }

    public GridPane center(Stage stage){
        GridPane center = new GridPane();
        center.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        final int layout_x = 240;
        final int layout_y = 100;
        center.setLayoutX(layout_x);
        center.setLayoutY(layout_y);
        center.prefWidthProperty().bind(stage.widthProperty().multiply(0.938).subtract(layout_x));
        center.prefHeightProperty().bind(stage.heightProperty().multiply(0.8).subtract(layout_y));

        return center;
    }

    public void calendarSetup(GridPane center){
        if(center.getChildren().size() != 0){
                center.getChildren().removeAll(center.getChildren());
        }
        int amount_of_rows = 4;
        int days_per_row = calendar.days_in_month()/amount_of_rows;
        double last_row_days = calendar.days_in_month() % 10;

        int row_counter = 0;
        int day_pos = 0;
        center.setHgap(5);
        center.setVgap(5);
        for(int day_counter = 1; day_counter <= calendar.days_in_month(); day_counter++){
            Day day = new Day(LocalDate.of(calendar.getSelected_date().getYear(), calendar.getSelected_date().getMonth(), day_counter));
            day.loadTasks(calendar.tasks_on_exact_date(day.getDate()));

            Pane day_pane = new Pane();
            DoubleBinding pane_size = center.widthProperty().divide(days_per_row);
            if(row_counter == amount_of_rows){
                pane_size = center.widthProperty().divide(days_per_row + last_row_days).add(2);
            }
            day_pane.prefWidthProperty().bind(pane_size);
            Label weekday = new Label(day.dayOfTheWeek());

            day_pane.getChildren().add(weekday);


            int amount_of_tasks = day.amount_of_tasks();
            String task_string = "No tasks!";
            String date = day.getDate().toString();
            Border day_pane_border = new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            if(amount_of_tasks > 0){
                if(amount_of_tasks == 1){
                    task_string = String.format("%s Task!", amount_of_tasks);
                }else{
                    task_string = String.format("%s Tasks!", amount_of_tasks);
                }
                day_pane_border = new Border(new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            }
            Label date_label = new Label(date);
            date_label.layoutYProperty().bind(weekday.prefHeightProperty().add(20));

            day_pane.getChildren().add(date_label);
            Label task_label = new Label(task_string);
            task_label.layoutYProperty().bind(date_label.layoutYProperty().add(12));
            day_pane.getChildren().add(task_label);
            Line weekday_underline = new Line();
            weekday_underline.setFill(Color.GRAY);

            day_pane.getChildren().add(weekday_underline);
            if(!calendar.containsDay(day)){
                calendar.addDay(day);
            }
            if(day_pos == days_per_row){
                if(row_counter < amount_of_rows) {
                    row_counter++;
                    day_pos = 0;
                }
            }
            center.getChildren().add(day_pane);

            GridPane.setConstraints(day_pane, day_pos, row_counter);
            weekday_underline.startYProperty().bind(weekday.heightProperty().add(weekday.layoutYProperty()));
            weekday_underline.endYProperty().bind(weekday.heightProperty().add(weekday.layoutYProperty()));
            weekday_underline.startXProperty().bind(weekday.layoutXProperty());
            weekday_underline.endXProperty().bind(weekday.layoutXProperty().add(day_pane.widthProperty()));
            day_pane.setBorder(day_pane_border);
            day_pane.prefWidthProperty().add(10);
            day_pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GridPane day_content = new GridPane();
                    ScrollPane tasks = new ScrollPane();
                    VBox tasks_content = new VBox();
                    tasks.setTranslateY(10);
                    tasks.setFocusTraversable(false);
                    tasks_content.prefWidthProperty().bind(tasks.widthProperty().subtract(1));
                    tasks_content.setSpacing(10);

                    tasks_content.getChildren().addAll(dayContent(day.getDate()));

                    tasks.setContent(tasks_content);
                    Button addTask = new Button("Add Task");

                    Button removeTask = new Button("Remove Task");
                    removeTask.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                        //TODO: implement the RemoveDayDialog
                            VBox contents = new VBox();
                        RemoveDayDialog removeDayDialog = new RemoveDayDialog(day, contents);
                        removeDayDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
                            @Override
                            public void handle(DialogEvent dialogEvent) {
                                if(left_contents.getChildren().size() != 0){
                                    left_contents.getChildren().removeAll(left_contents.getChildren());
                                }
                                left_contents.getChildren().addAll(getTaskLabels(left_contents));
                                if(tasks_content.getChildren().size() != 0){
                                    tasks_content.getChildren().removeAll(tasks_content.getChildren());
                                }
                                tasks_content.getChildren().addAll(dayContent(day.getDate()));
                            }
                        });
                        removeDayDialog.show();
                        }
                    });
                    addTask.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            AddDayDialog dayDialog = new AddDayDialog(new Task(null, null, null, null, -1, false), day.getDate());
                            dayDialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
                                @Override
                                public void handle(DialogEvent dialogEvent) {
                                    if(dayDialog.closedNormally()) {
                                        Task task = dayDialog.getTask();
                                        writer.addTaskData(task);
                                        if (!day.doesTaskExist(task)) {
                                            day.addTask(task);
                                            task_label.setText(String.format("%s Tasks!", day.amount_of_tasks()));
                                            if(left_contents.getChildren().size() != 0){
                                                left_contents.getChildren().removeAll(left_contents.getChildren());
                                            }
                                            left_contents.getChildren().addAll(getTaskLabels(left_contents));
                                            if(tasks_content.getChildren().size() != 0){
                                                tasks_content.getChildren().removeAll(tasks_content.getChildren());
                                            }
                                            tasks_content.getChildren().addAll(dayContent(day.getDate()));

                                            removeTask.setDisable(false);
                                        }

                                    }

                                }
                            });
                            dayDialog.showAndWait();
                        }
                    });


                    Button closeDialog = new Button("Close Window");

                    if(tasks_content.getChildren().size() == 0){
                        removeTask.setDisable(true);
                    }else{
                        removeTask.setDisable(false);
                    }
                    day_content.add(tasks, 0, 0);
                    day_content.add(addTask, 1, 0);
                    day_content.add(removeTask, 1, 0);
                    day_content.add(closeDialog, 1, 0);
                    addTask.setTranslateY(-75);

                    removeTask.setTranslateY(50);

                    closeDialog.setTranslateY(100);

                    day_content.setPrefHeight(300);
                    day_content.setPrefWidth(325);
                    tasks.setPrefHeight(300);
                    tasks.setPrefWidth(200);

                    Dialog<Object> dialog = new Dialog<>();
                    DialogPane dialogPane = new DialogPane();
                    dialogPane.setContent(day_content);
                    dialog.setDialogPane(dialogPane);
                   // dialog.initOwner(window);
                    dialog.show();
                    closeDialog.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
                            dialog.close();
                        }
                    });

                }
            });
            day_pos++;
        }
    }
    public ArrayList<Label> dayContent(LocalDate date){
        Task[] tasks_ascending = writer.getTasks_ascending();
        ArrayList<Label> labels = new ArrayList<>();
        if(tasks_ascending != null) {
            for (Task task : tasks_ascending) {
                if(task.getOccurence_date().isEqual(date)) {
                    String format_string = task.toString();
                    Label task_label = new Label(format_string);
                    String finished = "Task not finished!";
                    if (task.isFinished()) {
                        finished = "Task finished!";
                    }
                    task_label.setText(task_label.getText() + finished);
                    task_label.prefWidthProperty().bind(left_contents.prefWidthProperty().subtract(1));
                    if (task.isFinished()) {
                        task_label.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
                    } else {
                        task_label.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    }
                    task_label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            writer.deleteTask(task);
                            task.toggleFinished();
                            if (task.isFinished()) {
                                task_label.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
                                writer.addTaskData(task);
                                task_label.setText(format_string + "Task finished!");
                            } else {
                                task_label.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                writer.addTaskData(task);
                                task_label.setText(format_string + "Task not finished!");
                            }
                            if(left_contents.getChildren().size() != 0){
                                left_contents.getChildren().removeAll(left_contents.getChildren());
                            }
                            left_contents.getChildren().addAll(getTaskLabels(left_contents));
                        }
                    });
                    labels.add(task_label);
                }
            }
        }
        return labels;
    }

    public Writer getWriter(){
        return writer;
    }


}
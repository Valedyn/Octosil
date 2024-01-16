package Vital;

import Setup.Directory;
import Setup.File;
import Testing.Error;
import Testing.Validator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Writer {
    private final String project_name = "Octosil";
    private final File octosil = new File(Directory.return_Appdata_Location() + "\\" + project_name, "task_data", "txt");
    public Writer(){
        if(!new java.io.File(Directory.return_Appdata_Location() + "\\" + project_name).exists()){
            Directory.setup(project_name);
        }
       if(!octosil.exists()){
           try {
               octosil.create();
           }catch(IOException exception){
               Error.println("Something went wrong creating the Octosil Task_Data file.");
           }
       }
    }
    public boolean deleteTask(Task task){
        Task[] tasks = getTasks_ascending();
        boolean check_if_task_available = false;
        int task_pos = -1;
       for(int pos = 0; pos < tasks.length; pos++){
           if(tasks[pos].equals(task)){
                check_if_task_available = true;
                task_pos = pos;
                break;
           }
       }
       if(check_if_task_available){
           octosil.overwrite("");
           for(int pos = 0; pos < tasks.length; pos++){
               if(pos != task_pos){
                   addTaskData(tasks[pos]);
               }
           }
       }

        return check_if_task_available;
    }

    public boolean addTaskData(Task task){
        Validator.println(String.format("Added a Task with the following information: %s | to the task_data file.", task.entry()));
        /*if(!octosil.read().contains("\n")) {
            octosil.write("\n");
        }*/
        boolean was_added = octosil.write(task.entry(), '|');
        octosil.write("\n\r");
       return was_added;
    }

    public Task[] getTasks(){
        String file_data = octosil.read();
        if(file_data.equals("") || file_data.equals(" ")){
                return null;
        }

        String[] task_strings = file_data.split("\\|"); //TO-DO: check if this works
        if(task_strings.length == 0){
            return null;
        }
        ArrayList<Task> task_arraylist = new ArrayList<>();
        Parser parser = new Parser();
        for(String task_string : task_strings){
            try {
                if(!task_string.equals("") && !task_string.equals(" ") && !task_string.equals("\n")) {
                    task_arraylist.add(parser.parseTask(task_string));
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        Task[] tasks = new Task[task_arraylist.size()];
        for(int tasks_pos = 0; tasks_pos < task_arraylist.size(); tasks_pos++){
            tasks[tasks_pos] = task_arraylist.get(tasks_pos);
        }

        return tasks;
    }

    public Task[] getTasks_ascending(){
        Task[] tasks = getTasks();
        if(tasks != null) {
            LocalDateTime[] task_times = new LocalDateTime[tasks.length];
            for (int task_time_pos = 0; task_time_pos < tasks.length; task_time_pos++) {
                LocalDateTime time = LocalDateTime.of(tasks[task_time_pos].getOccurence_date(), tasks[task_time_pos].getOccurrence_time());
                task_times[task_time_pos] = time;
            }
            Arrays.sort(task_times);
            Task[] sorted_tasks = new Task[tasks.length];
            for (int task_times_pos = 0; task_times_pos < task_times.length; task_times_pos++) {
                LocalDate date = task_times[task_times_pos].toLocalDate();
                LocalTime time = task_times[task_times_pos].toLocalTime();
                for (int tasks_pos = 0; tasks_pos < tasks.length; tasks_pos++) {
                    if (tasks[tasks_pos].getOccurence_date().equals(date) && tasks[tasks_pos].getOccurrence_time().equals(time)) {
                        sorted_tasks[task_times_pos] = tasks[tasks_pos];
                        break;
                    }
                }
            }
            return sorted_tasks;

        }else{
            return null;
        }
    }


}

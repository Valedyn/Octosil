package Vital;

import java.time.LocalDate;
import java.util.ArrayList;

public class Day {
    private LocalDate date;
    private ArrayList<Task> tasks = new ArrayList<>();

    public Day(LocalDate date){
        setDate(date);
    }
    public void loadTasks(ArrayList<Task> tasks_to_load){
        tasks = tasks_to_load;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void addTask(Task task){
        tasks.add(task);
    }
    public boolean doesTaskExist(Task task){
        if(tasks.contains(task)){
            return true;
        }else {
            return false;
        }
    }
    public boolean doesTaskExist(int identifier){
        for(Task oneTask : tasks){
            if(oneTask.getIdentifier() == identifier){
                return true;
            }
        }
        return false;
    }

    public Task getTask(int idx){
       return tasks.get(idx);
    }
    public boolean removeTask(Task task){
        return tasks.remove(task);
    }
    public boolean removeTask(int identifier){
        for(Task aTask : tasks){
            if(aTask.getIdentifier() == identifier){
                tasks.remove(aTask);
                return true;
            }
        }
        return false;
    }

    public String dayOfTheWeek(){
        return date.getDayOfWeek().name();
    }
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    public int amount_of_tasks(){
        return tasks.size();
    }
}

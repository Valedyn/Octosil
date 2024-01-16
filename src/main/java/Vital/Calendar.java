package Vital;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Calendar {
    private LocalTime current_time = LocalTime.now();
    private LocalDate current_date = LocalDate.now();

    private LocalDate selected_date;
    //private Timer time_to_new_day = new Timer();
    private ArrayList<Day> days = new ArrayList<>();
    private Writer writer = new Writer();

    public Calendar(){
        setSelected_date(LocalDate.of(current_date.getYear(), current_date.getMonth(), 1));
    }
    public void addDay(Day day){
        days.add(day);
    }

    public int days_in_month(int month){
        if(month > 0 && month < 13) {
            return LocalDate.of(2000, month, 1).lengthOfMonth();
        }else{
            return 0;
        }
    }
    public int days_in_month(){
        try{
            return LocalDate.of(selected_date.getYear(), selected_date.getMonth(), selected_date.getDayOfMonth()).lengthOfMonth();
        }catch(DateTimeException dateTimeException){
            return 0;
        }

    }
    public boolean containsDay(Day day){
        return days.contains(day);
    }
    public ArrayList<Task> tasks_on_month_and_year(LocalDate date){
        Task[] tasks = writer.getTasks();
        ArrayList<Task> tasks_on_date = new ArrayList<>();
        if(tasks != null) {
            for (Task aTask : tasks) {
                if (aTask.getOccurence_date().getMonth().getValue() == date.getMonth().getValue() && aTask.getOccurence_date().getYear() == date.getYear()) {
                    tasks_on_date.add(aTask);
                }
            }
        }
        return tasks_on_date;
    }

    public ArrayList<Task> tasks_on_exact_date(LocalDate date){
        Task[] tasks = writer.getTasks();
        ArrayList<Task> tasks_on_date = new ArrayList<>();
        if(tasks != null) {
            for (Task aTask : tasks) {
                if (aTask.getOccurence_date().equals(date)) {
                    tasks_on_date.add(aTask);
                }
            }
        }
        return tasks_on_date;
    }

    public String month_name(){
       return month_name(getCurrent_date().getMonth().getValue());
    }

    public String month_name(int month){
            switch(month){
                case 1:
                   return "January";
                case 2:
                    return "February";
                case 3:
                    return "March";
                case 4:
                    return "April";
                case 5:
                    return "May";
                case 6:
                    return "June";
                case 7:
                    return "July";
                case 8:
                    return "August";
                case 9:
                    return "September";
                case 10:
                    return "October";
                case 11:
                    return "November";
                case 12:
                    return "December";
                default:
                    return "";
        }
    }

    public int month_name_to_number(String month){
        String month_upper = month.toUpperCase();
        try {
            Month month_object = Month.valueOf(month_upper);
            return month_object.getValue();
        }catch(IllegalArgumentException wrongString){
            return -1;
        }
    }
    public long time_until_next_day_in_seconds(){
        return (24*60*60) - current_time.toSecondOfDay();
    }
    public LocalTime getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(LocalTime current_time) {
        this.current_time = current_time;
    }

    public LocalDate getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(LocalDate current_date) {
        this.current_date = current_date;
    }

    public LocalDate getSelected_date() {
        return selected_date;
    }

    public void setSelected_date(LocalDate selected_date) {
        this.selected_date = selected_date;
    }
}


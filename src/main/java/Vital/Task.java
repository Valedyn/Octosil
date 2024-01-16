package Vital;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    private String name;
    private String definition;
    private LocalDate occurrence_date;
    private LocalTime occurrence_time;
    private boolean finished;
    private final int identifier;

    public Task(String name, String definition, LocalDate occurrence_date, LocalTime occurrence_time, int identifier, boolean finished){
        setName(name);
        setDefinition(definition);
        setOccurence_date(occurrence_date);
        setOccurrence_time(occurrence_time);
        this.identifier = identifier;
        this.finished = finished;
    }
    public boolean past_due(){
        if(LocalDate.now().isAfter(occurrence_date)){
            return true;
        }else if(LocalDate.now().isEqual(getOccurence_date()) && LocalTime.now().isAfter(getOccurrence_time())){
            return true;
        }else {
            return false;
        }
    }
    public String entry(){
        return String.format("%s␜ %s␜ %s␜ %s␜ %d␜ %b", name, definition, occurrence_date.toString(), occurrence_time.toString(), identifier, finished);
    }

    public String toString(){
        return String.format("%s\n%s\n%s - %s\n", getName(), getDefinition(), getOccurence_date().toString(), getOccurrence_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void toggleFinished(){
        this.finished = !this.finished;
    }

    public boolean isFinished(){
        return this.finished;
    }
    public LocalTime getOccurrence_time() {
        return occurrence_time;
    }

    public void setOccurrence_time(LocalTime occurence_time) {
        this.occurrence_time = occurence_time;
    }

    public LocalDate getOccurence_date() {
        return occurrence_date;
    }

    public void setOccurence_date(LocalDate occurence_date) {
        this.occurrence_date = occurence_date;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdentifier(){
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return finished == task.finished && identifier == task.identifier && Objects.equals(name, task.name) && Objects.equals(definition, task.definition) && Objects.equals(occurrence_date, task.occurrence_date) && Objects.equals(occurrence_time, task.occurrence_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, definition, occurrence_date, occurrence_time, finished, identifier);
    }
}

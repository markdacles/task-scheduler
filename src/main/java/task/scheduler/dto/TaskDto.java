package task.scheduler.dto;

import java.time.LocalDate;
import java.util.List;

public class TaskDto {

    private Long id;
    private String name;
    private String duration;
    private List<Long> dependencies;
    private LocalDate startDate;
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Long> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Long> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return "\nTask ID: " + id +
                "\nTask Name: " + name +
                "\nTask Duration (in days): " + duration +
                "\nTask Dependencies (Task ID separated by ','): " + dependencies +
                "\n";
    }

    public String getSchedule() {
        return "\nTask ID: " + id +
                "\nTask Name: " + name +
                "\nStart Date: " + startDate +
                "\nEnd Date: " + endDate +
                "\n";
    }
}

package task.scheduler.service;


import task.scheduler.dto.TaskDto;

import java.time.LocalDate;
import java.util.List;

public interface SchedulerService {

    List<TaskDto> addTask(List<TaskDto> scheduler, LocalDate startDate);

    List<TaskDto> removeTask(List<TaskDto> scheduler);

    LocalDate setStartDate(LocalDate localDate);

    List<TaskDto> generateSchedule(List<TaskDto> scheduler);
}

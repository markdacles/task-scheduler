package task.scheduler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.scheduler.dto.TaskDto;
import task.scheduler.helper.DateFormatter;
import task.scheduler.helper.Messages;
import task.scheduler.helper.InputHelper;
import task.scheduler.service.SchedulerService;
import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    Messages messages;

    @Autowired
    public SchedulerServiceImpl(Messages messages) {
        this.messages = messages;
    }

    @Override
    public List<TaskDto> addTask(List<TaskDto> scheduler, LocalDate startDate) {
        try {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(getId(scheduler));
            System.out.println(messages.get("new.task.id") + taskDto.getId());
            System.out.print(messages.get("new.task.name"));
            taskDto.setName(InputHelper.getInput());
            System.out.print(messages.get("new.task.duration"));
            taskDto.setDuration(InputHelper.getDuration());
            System.out.print(messages.get("new.task.dependencies"));
            taskDto.setDependencies(InputHelper.parseInput());
            if (validate(taskDto, scheduler)) {
                taskDto.setStartDate(startDate);
                scheduler.add(taskDto);
                System.out.println(messages.get("add.task.success"));
            }
        } catch (Exception e) {
            System.out.println(messages.get("invalid.input"));
        }
        return scheduler;

    }

    @Override
    public List<TaskDto> removeTask(List<TaskDto> scheduler) {
        System.out.print(messages.get("remove.task"));
        try {
            Long id = Long.parseLong(InputHelper.getInput());
            if(scheduler.stream().noneMatch(taskDto -> taskDto.getId().equals(id))) {
                System.out.println(messages.get("invalid.task"));
            } else {
                scheduler.removeIf(taskDto ->
                    taskDto.getId().equals(id));
                System.out.println(messages.get("remove.task.success"));
            }
        } catch (Exception e) {
            System.out.println(messages.get("invalid.input"));
        }
        return scheduler;
    }

    private boolean validate(TaskDto taskDto, List<TaskDto> taskList) {

        if(taskDto.getName().isEmpty()) {
            System.out.println(messages.get("task.name.empty"));
            return false;
        } else if(taskList.stream().anyMatch(task -> taskDto.getName().equals(task.getName()))) {
            System.out.println(messages.get("task.name.exists"));
            return false;
        } else if(taskDto.getDependencies().stream().anyMatch(task -> task.equals(taskDto.getId()))) {
            System.out.println(messages.get("cannot.depend"));
            return false;
        } else if(!taskDto.getDuration().chars().allMatch(Character::isDigit) || Integer.parseInt(taskDto.getDuration()) < 0) {
            System.out.println(messages.get("invalid.duration"));
            return false;
        }
        if(!taskDto.getDependencies().isEmpty()) {
            for (Long id : taskDto.getDependencies()) {
                   if(taskList.stream().noneMatch(taskDto1 -> taskDto1.getId().equals(id))) {
                        System.out.println(messages.get("cannot.depend"));
                        return false;
                }
            }
        }

        return true;
    }

    @Override
    public LocalDate setStartDate(LocalDate localDate) {
        try {
            System.out.println(messages.get("old.start.date") + DateFormatter.parseLocalDate(localDate));
            System.out.print(messages.get("new.start.date"));
            LocalDate newStartDate = DateFormatter.getLocalDate(InputHelper.getInput());
            if(newStartDate.isBefore(LocalDate.now())) {
                System.out.println(messages.get("old.invalid.date"));
                localDate = setStartDate(localDate);
            }
            return newStartDate;
        } catch (Exception e) {
            System.out.println(messages.get("invalid.date"));
            localDate = setStartDate(localDate);
        }
        return localDate;
    }

    @Override
    public List<TaskDto> generateSchedule(List<TaskDto> scheduler) {
        scheduler.stream().filter(taskDto -> taskDto.getDependencies().isEmpty()).forEach(taskDto -> taskDto.setEndDate(getEndDate(taskDto)));

        scheduler.forEach(task -> {
            if(!task.getDependencies().isEmpty()) {
                task.setStartDate(getStartDate(task.getDependencies(), scheduler, task.getStartDate()));
            }
            task.setEndDate(getEndDate(task));
        });

        return scheduler;
    }

    private LocalDate getStartDate(List<Long> dependencies, List<TaskDto> scheduler, LocalDate startDate) {
        for(Long id : dependencies) {
            TaskDto task = scheduler.stream().filter(taskDto -> taskDto.getId().equals(id)).findFirst().get();
            if(task.getDependencies().isEmpty()) {
                startDate = task.getEndDate();
            } else if (task.getEndDate().isAfter(startDate)) {
                startDate = getEndDate(task);
            } else {
                startDate = getStartDate(task.getDependencies(), scheduler, task.getStartDate());
            }
        }
        return startDate;
    }

    private LocalDate getEndDate(TaskDto task) {
        return task.getStartDate().plusDays(Long.parseLong(task.getDuration()));
    }

    private Long getId(List<TaskDto> scheduler) {
        long id = scheduler.isEmpty() ? 0L : scheduler.get(scheduler.size() - 1).getId();
        return ++id;
    }

}

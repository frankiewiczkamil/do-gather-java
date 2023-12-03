package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskListTmpRepository implements TaskListRepository<TaskListDbDto> {
  private final Map<UUID, TaskListDbDto> taskLists = new HashMap<>();

  @Override
  public Optional<TaskListDbDto> findById(UUID taskListId) {
    return Optional.of(taskLists.get(taskListId));
  }

  @Override
  public TaskListDbDto save(TaskListDbDto taskList) {
    taskLists.put(taskList.getId(), taskList);
    return taskList;
  }
}

package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskListFakeRepository implements TaskListRepository<TaskListDbDtoFakeImpl> {
  private final Map<UUID, TaskListDbDtoFakeImpl> taskLists = new HashMap<>();

  @Override
  public Optional<TaskListDbDtoFakeImpl> findById(UUID taskListId) {
    return Optional.of(taskLists.get(taskListId));
  }

  @Override
  public TaskListDbDtoFakeImpl save(TaskListDbDtoFakeImpl taskList) {
    taskLists.put(taskList.getId(), taskList);
    return taskList;
  }
}

package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TaskListFakeRepository implements TaskListRepository {
  private final Map<UUID, TaskListDbDto> taskLists = new HashMap<>();

  @Override
  public Optional<TaskListDbDto> findById(UUID taskListId) {
    return Optional.of(taskLists.get(taskListId));
  }

  public TaskListDbDto save(TaskListDbDto taskList) {
    taskLists.put(taskList.id(), taskList);
    return taskList;
  }
}

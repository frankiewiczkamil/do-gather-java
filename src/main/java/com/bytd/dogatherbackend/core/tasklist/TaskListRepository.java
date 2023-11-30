package com.bytd.dogatherbackend.core.tasklist;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListDbDto;
import java.util.Optional;
import java.util.UUID;

public interface TaskListRepository {

  Optional<TaskListDbDto> findById(UUID taskListId);

  void save(TaskListDbDto taskList);
}

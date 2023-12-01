package com.bytd.dogatherbackend.core.tasklist.app;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.Optional;
import java.util.UUID;

public interface TaskListRepository {

  Optional<TaskListDbDto> findById(UUID taskListId);

  void save(TaskListDbDto taskList);
}

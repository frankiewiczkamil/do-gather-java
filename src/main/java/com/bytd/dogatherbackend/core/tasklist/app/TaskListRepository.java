package com.bytd.dogatherbackend.core.tasklist.app;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.Optional;
import java.util.UUID;

public interface TaskListRepository<T extends TaskListDbDto> {

  Optional<T> findById(UUID taskListId);

  T save(T taskList);
}

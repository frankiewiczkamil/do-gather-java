package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class TaskListRepositoryH2ProxyImpl implements TaskListRepository {
  private TaskListRepositoryH2Impl taskListRepositoryH2;

  @Override
  public Optional<TaskListDbDto> findById(UUID taskListId) {
    return taskListRepositoryH2.findById(taskListId).map(TaskListDbDtoH2ProxyImpl::fromNativeImpl);
  }

  @Override
  public TaskListDbDto save(TaskListDbDto taskList) {
    if (taskList instanceof TaskListDbDtoH2ProxyImpl taskListDbDtoH2Proxy) {
      taskListRepositoryH2.save(taskListDbDtoH2Proxy.toNativeImpl());
      return taskListDbDtoH2Proxy;
    } else {
      throw new IllegalArgumentException(
          "TaskListDbDtoH2ProxyImpl is the only supported implementation");
    }
  }
}

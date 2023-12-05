package com.bytd.dogatherbackend.core.tasklist.infra;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListFakeRepository;
import com.bytd.dogatherbackend.core.tasklist.infra.db.h2.TaskDbDtoH2Impl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.h2.TaskListDbDtoH2Impl;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TaskListServiceConfig {

  private TaskListRepository<TaskListDbDtoH2Impl> taskListH2Repository;

  @ConditionalOnProperty(name = "dogather.db", havingValue = "h2")
  @Bean
  TaskListService<TaskListDbDtoH2Impl, TaskDbDtoH2Impl> createTaskListService() {
    return new TaskListService<>(
        taskListH2Repository, TaskListDbDtoH2Impl::new, TaskDbDtoH2Impl::new);
  }

  public static TaskListService<TaskListDbDtoFakeImpl, TaskDbDtoFakeImpl>
      createTaskListServiceWithFakeRepo() {
    return new TaskListService<>(
        new TaskListFakeRepository(), TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new);
  }
}

package com.bytd.dogatherbackend.core.tasklist.infra.db.fake;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "dogather.db", havingValue = "fake")
@Configuration
public class TaskListFakeRepoConfig {

  @Bean
  TaskListService<TaskListDbDtoFakeImpl, TaskDbDtoFakeImpl> taskListService() {
    return new TaskListService<>(
        new TaskListFakeRepository(), TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new);
  }
}

package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "dogather.db", havingValue = "h2")
@Configuration
@AllArgsConstructor
public class TaskListH2RepoConfig {

  private TaskListRepository<TaskListDbDtoH2Impl> taskListRepository;

  @Bean
  TaskListService<TaskListDbDtoH2Impl, TaskDbDtoH2Impl> taskListService() {
    return new TaskListService<>(
        taskListRepository, TaskListDbDtoH2Impl::new, TaskDbDtoH2Impl::new);
  }
}

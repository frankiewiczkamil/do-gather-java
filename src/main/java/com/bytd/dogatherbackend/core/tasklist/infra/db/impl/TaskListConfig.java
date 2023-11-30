package com.bytd.dogatherbackend.core.tasklist.infra.db.impl;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskListConfig {

  @Bean
  TaskListService taskListService() {
    return new TaskListService(
        new TaskListTmpRepository(), TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
  }
}

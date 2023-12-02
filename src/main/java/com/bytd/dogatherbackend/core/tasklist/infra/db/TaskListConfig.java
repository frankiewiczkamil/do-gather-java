package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskListConfig {

  @Autowired TaskListRepository taskListRepository;

  @Bean
  TaskListService taskListService() {
    //    TaskListRepository taskListRepository = new TaskListTmpRepository();

    return new TaskListService(taskListRepository, TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
  }
}

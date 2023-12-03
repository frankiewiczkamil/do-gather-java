package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskListConfig {

  @Autowired TaskListRepository<TaskListDbDtoImpl> taskListRepository;

  @Bean
  TaskListService<TaskListDbDtoImpl, TaskDbDtoImpl> taskListService() {
    //  TaskListService taskListService() {
    //    TaskListRepository taskListRepository = new TaskListTmpRepository();

    // todo check why is sonarlint complaining about parameterized types here?
    return new TaskListService(taskListRepository, TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
  }
}

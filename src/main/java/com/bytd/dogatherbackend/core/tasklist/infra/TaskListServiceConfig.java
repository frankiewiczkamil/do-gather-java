package com.bytd.dogatherbackend.core.tasklist.infra;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.PermissionDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListFakeRepository;
import com.bytd.dogatherbackend.core.tasklist.infra.db.h2.PermissionDbDtoH2Impl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.h2.TaskDbDtoH2Impl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.h2.TaskListDbDtoH2ProxyImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TaskListServiceConfig {

  private TaskListRepository taskListH2ProxyRepository;

  @ConditionalOnProperty(name = "dogather.db", havingValue = "h2")
  @Bean
  TaskListService createTaskListService() {
    return new TaskListService(
        taskListH2ProxyRepository,
        TaskListDbDtoH2ProxyImpl::create,
        TaskDbDtoH2Impl::new,
        PermissionDbDtoH2Impl::new);
  }

  public static TaskListService createTaskListServiceWithFakeRepo() {
    return new TaskListService(
        new TaskListFakeRepository(),
        TaskListDbDtoFakeImpl::new,
        TaskDbDtoFakeImpl::new,
        PermissionDbDtoFakeImpl::new);
  }
}

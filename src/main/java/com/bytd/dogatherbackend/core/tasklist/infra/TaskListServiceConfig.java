package com.bytd.dogatherbackend.core.tasklist.infra;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.app.TaskListService;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.PermissionDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListFakeRepository;
import com.bytd.dogatherbackend.core.tasklist.infra.db.h2.*;
import java.util.UUID;
import java.util.function.Supplier;
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
        TaskDbDtoH2ProxyImpl::create,
        PermissionDbDtoH2ProxyImpl::create,
        UUID::randomUUID,
        UUID::randomUUID,
        UUID::randomUUID);
  }

  public static TaskListService createTaskListServiceWithFakeRepo(
      Supplier<UUID> taskListIdGenerator,
      Supplier<UUID> taskIdGenerator,
      Supplier<UUID> participantIdGenerator) {
    return new TaskListService(
        new TaskListFakeRepository(),
        TaskListDbDtoFakeImpl::new,
        TaskDbDtoFakeImpl::new,
        PermissionDbDtoFakeImpl::new,
        taskListIdGenerator,
        taskIdGenerator,
        participantIdGenerator);
  }
}

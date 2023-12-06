package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TaskListDbDtoH2ProxyImpl implements TaskListDbDto {
  private TaskListDbDtoH2Impl taskListDbDtoH2;

  public static TaskListDbDtoH2ProxyImpl create() {
    return new TaskListDbDtoH2ProxyImpl(new TaskListDbDtoH2Impl());
  }

  public static TaskListDbDtoH2ProxyImpl fromNativeImpl(TaskListDbDtoH2Impl taskListDbDtoH2) {
    return new TaskListDbDtoH2ProxyImpl(taskListDbDtoH2);
  }

  @Override
  public void setId(UUID id) {
    taskListDbDtoH2.setId(id);
  }

  @Override
  public UUID getId() {
    return taskListDbDtoH2.getId();
  }

  @Override
  public void setName(String name) {
    taskListDbDtoH2.setName(name);
  }

  @Override
  public String getName() {
    return taskListDbDtoH2.getName();
  }

  @Override
  public void setDescription(String description) {
    taskListDbDtoH2.setDescription(description);
  }

  @Override
  public String getDescription() {
    return taskListDbDtoH2.getDescription();
  }

  @Override
  public void setCreatorId(UUID creatorId) {
    taskListDbDtoH2.setCreatorId(creatorId);
  }

  @Override
  public UUID getCreatorId() {
    return taskListDbDtoH2.getCreatorId();
  }

  @Override
  public void setParticipants(List<Participant> participants) {
    taskListDbDtoH2.setParticipants(participants);
  }

  @Override
  public List<Participant> getParticipants() {
    return taskListDbDtoH2.getParticipants();
  }

  @Override
  public void setTasks(List<TaskDbDto> tasks) {
    var taskz = tasks.stream().map(TaskListDbDtoH2ProxyImpl::toH2TaskImpl).toList();
    taskListDbDtoH2.setTasks(taskz);
  }

  @Override
  public List<TaskDbDto> getTasks() {
    return taskListDbDtoH2.getTasks().stream()
        .map(TaskListDbDtoH2ProxyImpl::hideTaskDetails)
        .toList();
  }

  private static TaskDbDto hideTaskDetails(TaskDbDtoH2Impl task) {
    return task;
  }

  public TaskListDbDtoH2Impl toNativeImpl() {
    return taskListDbDtoH2;
  }

  public static TaskDbDtoH2Impl toH2TaskImpl(TaskDbDto task) {
    if (task instanceof TaskDbDtoH2Impl taskDbDtoH2Impl) {
      return taskDbDtoH2Impl;
    } else {
      throw new InvalidDbDtoException("TaskDbDtoH2Proxy.setTasks: task is not TaskDbDtoH2Proxy");
    }
  }
}
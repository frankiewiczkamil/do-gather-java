package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
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
  public void setPermissions(List<PermissionDbDto> participants) {
    taskListDbDtoH2.setParticipants(
        participants.stream().map(TaskListDbDtoH2ProxyImpl::toH2ParticipantImpl).toList());
  }

  @Override
  public List<PermissionDbDto> getPermissions() {
    return taskListDbDtoH2.getParticipants().stream()
        .map(TaskListDbDtoH2ProxyImpl::hideParticipantDetails)
        .toList();
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

  private static PermissionDbDto hideParticipantDetails(PermissionDbDtoH2Impl participant) {
    return participant;
  }

  public TaskListDbDtoH2Impl toNativeImpl() {
    return taskListDbDtoH2;
  }

  public static TaskDbDtoH2Impl toH2TaskImpl(TaskDbDto task) {
    if (task instanceof TaskDbDtoH2Impl taskDbDtoH2Impl) {
      return taskDbDtoH2Impl;
    } else {
      throw new InvalidDbDtoException(
          "TaskListDbDtoH2Proxy.setTasks: task is not TaskDbDtoH2Proxy");
    }
  }

  public static PermissionDbDtoH2Impl toH2ParticipantImpl(PermissionDbDto participantDbDto) {
    if (participantDbDto instanceof PermissionDbDtoH2Impl participantDbDtoH2) {
      return participantDbDtoH2;
    } else {
      throw new InvalidDbDtoException(
          "TaskListDbDtoH2Proxy.setParticipants: participant is not ParticipantDbDtoH2Proxy");
    }
  }
}

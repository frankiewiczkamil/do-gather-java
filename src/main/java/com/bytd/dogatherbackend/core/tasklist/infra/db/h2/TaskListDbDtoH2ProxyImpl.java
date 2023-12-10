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
  public void id(UUID id) {
    taskListDbDtoH2.setId(id);
  }

  @Override
  public UUID id() {
    return taskListDbDtoH2.getId();
  }

  @Override
  public void name(String name) {
    taskListDbDtoH2.setName(name);
  }

  @Override
  public String name() {
    return taskListDbDtoH2.getName();
  }

  @Override
  public void description(String description) {
    taskListDbDtoH2.setDescription(description);
  }

  @Override
  public String description() {
    return taskListDbDtoH2.getDescription();
  }

  @Override
  public void creatorId(UUID creatorId) {
    taskListDbDtoH2.setCreatorId(creatorId);
  }

  @Override
  public UUID creatorId() {
    return taskListDbDtoH2.getCreatorId();
  }

  @Override
  public void permissions(List<PermissionDbDto> participants) {
    List<PermissionDbDtoH2Impl> permissionsH2Dto =
        participants.stream().map(TaskListDbDtoH2ProxyImpl::toH2ParticipantImpl).toList();
    taskListDbDtoH2.setParticipants(permissionsH2Dto);
  }

  @Override
  public List<PermissionDbDto> permissions() {
    return taskListDbDtoH2.getParticipants().stream()
        .map(TaskListDbDtoH2ProxyImpl::toPermissionDbDto)
        .toList();
  }

  @Override
  public void tasks(List<TaskDbDto> tasks) {
    List<TaskDbDtoH2Impl> tasksH2Dto =
        tasks.stream().map(TaskListDbDtoH2ProxyImpl::toH2TaskImpl).toList();
    taskListDbDtoH2.setTasks(tasksH2Dto);
  }

  @Override
  public List<TaskDbDto> tasks() {
    return taskListDbDtoH2.getTasks().stream().map(TaskListDbDtoH2ProxyImpl::toTaskDbDto).toList();
  }

  private static TaskDbDto toTaskDbDto(TaskDbDtoH2Impl task) {
    return TaskDbDtoH2ProxyImpl.from(task);
  }

  private static PermissionDbDto toPermissionDbDto(PermissionDbDtoH2Impl participant) {
    return PermissionDbDtoH2ProxyImpl.from(participant);
  }

  public TaskListDbDtoH2Impl toNativeImpl() {
    return taskListDbDtoH2;
  }

  public static TaskDbDtoH2Impl toH2TaskImpl(TaskDbDto task) {
    if (task instanceof TaskDbDtoH2ProxyImpl taskDbDtoH2Proxy) {
      return taskDbDtoH2Proxy.toNativeImpl();
    } else {
      throw new InvalidDbDtoException(
          "TaskListDbDtoH2Proxy.setTasks: task is not TaskDbDtoH2Proxy");
    }
  }

  public static PermissionDbDtoH2Impl toH2ParticipantImpl(PermissionDbDto participantDbDto) {
    if (participantDbDto instanceof PermissionDbDtoH2ProxyImpl participantDbDtoH2Proxy) {
      return participantDbDtoH2Proxy.toNativeImpl();
    } else {
      throw new InvalidDbDtoException(
          "TaskListDbDtoH2Proxy.setParticipants: participant is not ParticipantDbDtoH2Proxy");
    }
  }
}

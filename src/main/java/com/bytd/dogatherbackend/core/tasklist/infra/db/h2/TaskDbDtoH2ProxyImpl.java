package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskDbDtoH2ProxyImpl implements TaskDbDto {
  private final TaskDbDtoH2Impl taskDbDtoH2Impl;

  public static TaskDbDtoH2ProxyImpl create() {
    return new TaskDbDtoH2ProxyImpl(new TaskDbDtoH2Impl());
  }

  public static TaskDbDtoH2ProxyImpl from(TaskDbDtoH2Impl taskDbDtoH2Impl) {
    return new TaskDbDtoH2ProxyImpl(taskDbDtoH2Impl);
  }

  public TaskDbDtoH2Impl toNativeImpl() {
    return taskDbDtoH2Impl;
  }

  @Override
  public void id(UUID id) {
    taskDbDtoH2Impl.setId(id);
  }

  @Override
  public UUID id() {
    return taskDbDtoH2Impl.getId();
  }

  @Override
  public void state(TaskState state) {
    taskDbDtoH2Impl.setState(state);
  }

  @Override
  public TaskState state() {
    return taskDbDtoH2Impl.getState();
  }

  @Override
  public void progressTotal(short progressTotal) {
    taskDbDtoH2Impl.setProgressTotal(progressTotal);
  }

  @Override
  public short progressTotal() {
    return taskDbDtoH2Impl.getProgressTotal();
  }

  @Override
  public void timeSpent(int timeSpent) {
    taskDbDtoH2Impl.setTimeSpent(timeSpent);
  }

  @Override
  public int timeSpent() {
    return taskDbDtoH2Impl.getTimeSpent();
  }

  @Override
  public void name(String name) {
    taskDbDtoH2Impl.setName(name);
  }

  @Override
  public String name() {
    return taskDbDtoH2Impl.getName();
  }

  @Override
  public void description(String description) {
    taskDbDtoH2Impl.setDescription(description);
  }

  @Override
  public String description() {
    return taskDbDtoH2Impl.getDescription();
  }

  @Override
  public void taskListId(UUID taskListId) {
    taskDbDtoH2Impl.setTaskListId(taskListId);
  }

  @Override
  public UUID taskListId() {
    return taskDbDtoH2Impl.getTaskListId();
  }
}

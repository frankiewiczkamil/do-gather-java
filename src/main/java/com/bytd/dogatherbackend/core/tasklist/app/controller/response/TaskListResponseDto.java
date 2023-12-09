package com.bytd.dogatherbackend.core.tasklist.app.controller.response;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.List;
import java.util.UUID;

public record TaskListResponseDto(
    UUID id, String name, List<PermissionResponseDto> permissions, List<TaskResponseDto> tasks) {

  public static TaskListResponseDto from(TaskListDbDto taskList) {
    return new TaskListResponseDto(
        taskList.getId(),
        taskList.getName(),
        taskList.getPermissions().stream().map(PermissionResponseDto::from).toList(),
        taskList.getTasks().stream().map(TaskResponseDto::from).toList());
  }
}

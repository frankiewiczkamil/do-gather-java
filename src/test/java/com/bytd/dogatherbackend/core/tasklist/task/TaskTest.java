package com.bytd.dogatherbackend.core.tasklist.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bytd.dogatherbackend.core.tasklist.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.Task;
import com.bytd.dogatherbackend.core.tasklist.infra.db.impl.TaskDbDtoImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskTest {

  @Test
  void shouldCreateTaskInstanceWithoutThrowingIfPayloadIsValid() {
    var dto = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    assertDoesNotThrow(() -> Task.create(dto, UUID.randomUUID()));
  }

  @Test
  void shouldCreateTaskInstance() {
    var dto = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    var task = Task.create(dto, UUID.randomUUID());
    var taskDbDto = task.toDbDto(() -> new TaskDbDtoImpl() {});
    assertEquals(dto.id(), taskDbDto.getId());
  }
}

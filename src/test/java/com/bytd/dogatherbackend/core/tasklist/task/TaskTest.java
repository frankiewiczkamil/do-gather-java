package com.bytd.dogatherbackend.core.tasklist.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bytd.dogatherbackend.core.tasklist.domain.Task;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskTest {

  @Test
  void shouldCreateTaskInstanceWithoutThrowingIfPayloadIsValid() {
    var dto = new CreateTaskDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    assertDoesNotThrow(() -> Task.create(dto));
  }

  @Test
  void shouldCreateTaskInstance() {
    var dto = new CreateTaskDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    var task = Task.create(dto);
    var taskDbDto = task.toDbDto(() -> new TaskDbDtoFakeImpl() {});
    assertEquals(dto.id(), taskDbDto.id());
  }
}

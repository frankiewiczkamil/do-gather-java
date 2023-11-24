package com.bytd.dogatherbackend.core.tasklist.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.bytd.dogatherbackend.core.tasklist.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.Task;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskTest {

  @Test
  void shouldCreateTaskInstance() {
    var dto = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    assertDoesNotThrow(() -> Task.create(dto, UUID.randomUUID()));
  }
}

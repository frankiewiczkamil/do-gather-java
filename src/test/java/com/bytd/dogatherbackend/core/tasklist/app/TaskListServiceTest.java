package com.bytd.dogatherbackend.core.tasklist.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bytd.dogatherbackend.core.tasklist.*;
import com.bytd.dogatherbackend.core.tasklist.infra.db.impl.TaskDbDtoImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.impl.TaskListDbDtoImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.impl.TaskListTmpRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskListServiceTest {

  @Test
  void itShouldCreateNewList() {
    var repo = new TaskListTmpRepository();
    var taskListService = new TaskListService(repo, TaskListDbDtoImpl::new, TaskDbDtoImpl::new);

    var createListDto =
        new CreateTaskListDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    taskListService.createTaskList(createListDto);
    var expected = new TaskListDbDtoImpl();
    expected.setId(createListDto.id());
    expected.setName("name");
    expected.setDescription("description");
    expected.setCreatorId(createListDto.creatorId());
    expected.setParticipants(
        List.of(new Participant(createListDto.creatorId(), List.of(Role.OWNER))));
    expected.setTasks(List.of());
    assertTrue(repo.findById(createListDto.id()).isPresent());
    assertEquals(expected, repo.findById(createListDto.id()).get());
  }

  @Test
  void itShouldAddNewTaskToList() {
    var repo = new TaskListTmpRepository();
    var taskListService = new TaskListService(repo, TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
    var taskListDto =
        new CreateTaskListDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    taskListService.createTaskList(taskListDto);

    var addTaskDto = new CreateTaskDto(UUID.randomUUID(), "name", "description", taskListDto.id());
    taskListService.addTask(addTaskDto);
    var expected = new TaskListDbDtoImpl();
    expected.setId(taskListDto.id());
    expected.setName("name");
    expected.setDescription("description");
    expected.setCreatorId(taskListDto.creatorId());
    expected.setParticipants(
        List.of(new Participant(taskListDto.creatorId(), List.of(Role.OWNER))));

    var expectedTask = new TaskDbDtoImpl();
    expectedTask.setId(addTaskDto.id());
    expectedTask.setName("name");
    expectedTask.setDescription("description");
    expectedTask.setTaskListId(taskListDto.id());
    expectedTask.setState(TaskState.NEW);
    expectedTask.setProgressTotal((short) 0);
    expectedTask.setTimeSpent(0);

    expected.setTasks(List.of(expectedTask));
    assertTrue(repo.findById(taskListDto.id()).isPresent());
    assertEquals(expected, repo.findById(taskListDto.id()).get());
  }

  @Test
  void itShouldAddNewParticipant() {
    var repo = new TaskListTmpRepository();
    var taskListService = new TaskListService(repo, TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
    var taskListDto =
        new CreateTaskListDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    taskListService.createTaskList(taskListDto);

    var addParticipantDto =
        new AddParticipantDto(
            UUID.randomUUID(), List.of(Role.EDITOR), taskListDto.creatorId(), taskListDto.id());
    taskListService.addParticipant(addParticipantDto);
    var expected = new TaskListDbDtoImpl();
    expected.setId(taskListDto.id());
    expected.setName("name");
    expected.setDescription("description");
    expected.setCreatorId(taskListDto.creatorId());
    expected.setParticipants(
        List.of(
            new Participant(taskListDto.creatorId(), List.of(Role.OWNER)),
            new Participant(addParticipantDto.participantId(), List.of(Role.EDITOR))));

    expected.setTasks(List.of());
    assertTrue(repo.findById(taskListDto.id()).isPresent());
    assertEquals(expected, repo.findById(taskListDto.id()).get());
  }
}

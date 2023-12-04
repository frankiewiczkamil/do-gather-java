package com.bytd.dogatherbackend.core.tasklist.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListFakeRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskListServiceTest {

  @Test
  void itShouldCreateNewList() {
    var repo = new TaskListFakeRepository();
    var taskListService =
        new TaskListService<>(repo, TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new);

    var createListDto =
        new CreateTaskListDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    taskListService.createTaskList(createListDto);
    var expected = new TaskListDbDtoFakeImpl();
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
    var repo = new TaskListFakeRepository();
    var taskListService =
        new TaskListService<>(repo, TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new);
    var taskListDto =
        new CreateTaskListDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    taskListService.createTaskList(taskListDto);

    var addTaskDto = new CreateTaskDto(UUID.randomUUID(), "name", "description", taskListDto.id());
    taskListService.addTask(addTaskDto);
    var expected = new TaskListDbDtoFakeImpl();
    expected.setId(taskListDto.id());
    expected.setName("name");
    expected.setDescription("description");
    expected.setCreatorId(taskListDto.creatorId());
    expected.setParticipants(
        List.of(new Participant(taskListDto.creatorId(), List.of(Role.OWNER))));

    var expectedTask = new TaskDbDtoFakeImpl();
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
    var repo = new TaskListFakeRepository();
    var taskListService =
        new TaskListService<>(repo, TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new);
    var taskListDto =
        new CreateTaskListDto(UUID.randomUUID(), "name", "description", UUID.randomUUID());
    taskListService.createTaskList(taskListDto);

    var addParticipantDto =
        new AddParticipantDto(
            UUID.randomUUID(), List.of(Role.EDITOR), taskListDto.creatorId(), taskListDto.id());
    taskListService.addParticipant(addParticipantDto);
    var expected = new TaskListDbDtoFakeImpl();
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

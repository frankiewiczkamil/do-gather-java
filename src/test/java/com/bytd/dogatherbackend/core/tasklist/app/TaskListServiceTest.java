package com.bytd.dogatherbackend.core.tasklist.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Participant;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import com.bytd.dogatherbackend.core.tasklist.infra.TaskListServiceConfig;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListServiceTest {
  private TaskListService taskListService;

  @BeforeEach
  void setUp() {
    taskListService = TaskListServiceConfig.createTaskListServiceWithFakeRepo();
  }

  @Test
  void itShouldCreateNewList() {
    var createTaskListDto = generateCreateTaskListDto();
    var expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);

    var returnedList = taskListService.createTaskList(createTaskListDto);

    assertEquals(expectedTaskList, returnedList);
    assertEquals(expectedTaskList, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  @Test
  void itShouldAddNewTaskToList() {
    var createTaskListDto = generateCreateTaskListDto();
    taskListService.createTaskList(createTaskListDto);
    TaskListDbDto expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);
    var addTaskDto =
        new CreateTaskDto(
            UUID.randomUUID(), "task's name", "task's description", createTaskListDto.id());

    taskListService.addTask(addTaskDto);

    TaskDbDtoFakeImpl expectedTask = new TaskDbDtoFakeImpl();
    expectedTask.setId(addTaskDto.id());
    expectedTask.setName(addTaskDto.name());
    expectedTask.setDescription(addTaskDto.description());
    expectedTask.setTaskListId(createTaskListDto.id());
    expectedTask.setState(TaskState.NEW);
    expectedTask.setProgressTotal((short) 0);
    expectedTask.setTimeSpent(0);

    expectedTaskList.setTasks(List.of(expectedTask));

    assertEquals(expectedTaskList, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  @Test
  void itShouldAddNewParticipant() {
    var createTaskListDto = generateCreateTaskListDto();
    taskListService.createTaskList(createTaskListDto);

    var addParticipantDto =
        new AddParticipantDto(
            UUID.randomUUID(),
            List.of(Role.EDITOR),
            createTaskListDto.creatorId(),
            createTaskListDto.id());
    var expectedTaskListDto = createTaskListDtoFromCreateCommand(createTaskListDto);
    expectedTaskListDto.setParticipants(
        List.of(
            new Participant(createTaskListDto.creatorId(), List.of(Role.OWNER)),
            new Participant(addParticipantDto.participantId(), List.of(Role.EDITOR))));

    taskListService.addParticipant(addParticipantDto);

    assertEquals(expectedTaskListDto, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  private TaskListDbDto createTaskListDtoFromCreateCommand(CreateTaskListDto createTaskListDto) {
    var expected = new TaskListDbDtoFakeImpl();
    expected.setId(createTaskListDto.id());
    expected.setName(createTaskListDto.name());
    expected.setDescription(createTaskListDto.description());
    expected.setCreatorId(createTaskListDto.creatorId());
    expected.setParticipants(
        List.of(new Participant(createTaskListDto.creatorId(), List.of(Role.OWNER))));
    expected.setTasks(List.of());
    return expected;
  }

  private CreateTaskListDto generateCreateTaskListDto() {
    return new CreateTaskListDto(
        UUID.randomUUID(),
        "name_" + UUID.randomUUID(),
        "description_" + UUID.randomUUID(),
        UUID.randomUUID(),
        null);
  }
}

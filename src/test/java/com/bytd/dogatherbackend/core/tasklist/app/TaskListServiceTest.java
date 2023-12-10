package com.bytd.dogatherbackend.core.tasklist.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import com.bytd.dogatherbackend.core.tasklist.domain.model.task.TaskState;
import com.bytd.dogatherbackend.core.tasklist.infra.TaskListServiceConfig;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.PermissionDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListServiceTest {
  private TaskListService taskListService;
  final UUID taskListId = UUID.randomUUID();
  final UUID taskId = UUID.randomUUID();
  final UUID participantId = UUID.randomUUID();

  @BeforeEach
  void setUp() {
    taskListService =
        TaskListServiceConfig.createTaskListServiceWithFakeRepo(
            () -> taskListId, () -> taskId, () -> participantId);
  }

  @Test
  void itShouldCreateNewListWithIdFromPayload() {
    var createTaskListDto = generateCreateTaskListDto();
    var expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);

    var returnedList = taskListService.createTaskList(createTaskListDto);

    expectedTaskList.permissions().getLast().id(participantId);

    assertEquals(expectedTaskList, returnedList);
    assertEquals(expectedTaskList, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  @Test
  void itShouldCreateNewListWithNewIdIfNotPresentInPayload() {
    UUID customId = UUID.randomUUID();
    var createTaskListDto = generateCreateTaskListDto(customId);
    var expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);

    var returnedList = taskListService.createTaskList(createTaskListDto);

    expectedTaskList.permissions().getLast().id(participantId);

    assertEquals(expectedTaskList, returnedList);
    assertEquals(expectedTaskList, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  @Test
  void itShouldAddNewTaskToListUsingProvidedId() {
    var createTaskListDto = generateCreateTaskListDto();
    taskListService.createTaskList(createTaskListDto);
    TaskListDbDto expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);
    var addTaskDto =
        new CreateTaskDto(
            UUID.randomUUID(), "task's name", "task's description", createTaskListDto.id());

    taskListService.addTask(addTaskDto);

    TaskDbDtoFakeImpl expectedTask = new TaskDbDtoFakeImpl();
    expectedTask.id(addTaskDto.id());
    expectedTask.name(addTaskDto.name());
    expectedTask.description(addTaskDto.description());
    expectedTask.taskListId(createTaskListDto.id());
    expectedTask.state(TaskState.NEW);
    expectedTask.progressTotal((short) 0);
    expectedTask.timeSpent(0);

    expectedTaskList.tasks(List.of(expectedTask));

    assertEquals(expectedTaskList, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  @Test
  void itShouldAddNewTaskToListWithNewIdIfNotPresentInPayload() {
    var createTaskListDto = generateCreateTaskListDto();
    taskListService.createTaskList(createTaskListDto);
    TaskListDbDto expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);
    var addTaskDto =
        new CreateTaskDto(null, "task's name", "task's description", createTaskListDto.id());

    taskListService.addTask(addTaskDto);

    TaskDbDtoFakeImpl expectedTask = new TaskDbDtoFakeImpl();
    expectedTask.id(taskId);
    expectedTask.name(addTaskDto.name());
    expectedTask.description(addTaskDto.description());
    expectedTask.taskListId(createTaskListDto.id());
    expectedTask.state(TaskState.NEW);
    expectedTask.progressTotal((short) 0);
    expectedTask.timeSpent(0);

    expectedTaskList.tasks(List.of(expectedTask));

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
    var p1 = new PermissionDbDtoFakeImpl();
    p1.participantId(createTaskListDto.creatorId());
    p1.role(Role.OWNER);
    p1.taskListId(createTaskListDto.id());
    var p2 = new PermissionDbDtoFakeImpl();
    p2.participantId(addParticipantDto.participantId());
    p2.role(Role.EDITOR);
    p2.taskListId(createTaskListDto.id());
    List<PermissionDbDto> expectedPermissions = new LinkedList<>();
    expectedPermissions.add(p1);
    expectedPermissions.add(p2);
    expectedTaskListDto.permissions(expectedPermissions);

    taskListService.addParticipant(addParticipantDto);

    assertEquals(expectedTaskListDto, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  private TaskListDbDto createTaskListDtoFromCreateCommand(CreateTaskListDto createTaskListDto) {
    var expected = new TaskListDbDtoFakeImpl();
    expected.id(createTaskListDto.id());
    expected.name(createTaskListDto.name());
    expected.description(createTaskListDto.description());
    expected.creatorId(createTaskListDto.creatorId());
    var permission = new PermissionDbDtoFakeImpl();
    permission.participantId(createTaskListDto.creatorId());
    permission.role(Role.OWNER);
    permission.taskListId(createTaskListDto.id());
    LinkedList<PermissionDbDto> permissions = new LinkedList<>();
    permissions.add(permission);
    expected.permissions(permissions);
    expected.tasks(List.of());
    return expected;
  }

  private CreateTaskListDto generateCreateTaskListDto(UUID taskListId) {
    return new CreateTaskListDto(
        taskListId,
        "name_" + UUID.randomUUID(),
        "description_" + UUID.randomUUID(),
        UUID.randomUUID(),
        null);
  }

  private CreateTaskListDto generateCreateTaskListDto() {
    return generateCreateTaskListDto(taskListId);
  }
}

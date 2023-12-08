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

    expectedTaskList.getPermissions().getLast().setId(participantId);

    assertEquals(expectedTaskList, returnedList);
    assertEquals(expectedTaskList, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  @Test
  void itShouldCreateNewListWithNewIdIfNotPresentInPayload() {
    UUID customId = UUID.randomUUID();
    var createTaskListDto = generateCreateTaskListDto(customId);
    var expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);

    var returnedList = taskListService.createTaskList(createTaskListDto);

    expectedTaskList.getPermissions().getLast().setId(participantId);

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
  void itShouldAddNewTaskToListWithNewIdIfNotPresentInPayload() {
    var createTaskListDto = generateCreateTaskListDto();
    taskListService.createTaskList(createTaskListDto);
    TaskListDbDto expectedTaskList = createTaskListDtoFromCreateCommand(createTaskListDto);
    var addTaskDto =
        new CreateTaskDto(null, "task's name", "task's description", createTaskListDto.id());

    taskListService.addTask(addTaskDto);

    TaskDbDtoFakeImpl expectedTask = new TaskDbDtoFakeImpl();
    expectedTask.setId(taskId);
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
    var p1 = new PermissionDbDtoFakeImpl();
    p1.setParticipantId(createTaskListDto.creatorId());
    p1.setRole(Role.OWNER);
    p1.setTaskListId(createTaskListDto.id());
    var p2 = new PermissionDbDtoFakeImpl();
    p2.setParticipantId(addParticipantDto.participantId());
    p2.setRole(Role.EDITOR);
    p2.setTaskListId(createTaskListDto.id());
    List<PermissionDbDto> expectedPermissions = new LinkedList<>();
    expectedPermissions.add(p1);
    expectedPermissions.add(p2);
    expectedTaskListDto.setPermissions(expectedPermissions);

    taskListService.addParticipant(addParticipantDto);

    assertEquals(expectedTaskListDto, taskListService.getTaskList(createTaskListDto.id()).get());
  }

  private TaskListDbDto createTaskListDtoFromCreateCommand(CreateTaskListDto createTaskListDto) {
    var expected = new TaskListDbDtoFakeImpl();
    expected.setId(createTaskListDto.id());
    expected.setName(createTaskListDto.name());
    expected.setDescription(createTaskListDto.description());
    expected.setCreatorId(createTaskListDto.creatorId());
    var permission = new PermissionDbDtoFakeImpl();
    permission.setParticipantId(createTaskListDto.creatorId());
    permission.setRole(Role.OWNER);
    permission.setTaskListId(createTaskListDto.id());
    LinkedList<PermissionDbDto> permissions = new LinkedList<>();
    permissions.add(permission);
    expected.setPermissions(permissions);
    expected.setTasks(List.of());
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

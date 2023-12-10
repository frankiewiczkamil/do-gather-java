package com.bytd.dogatherbackend.core.tasklist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.bytd.dogatherbackend.core.tasklist.domain.*;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.AuthorIsNotAParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.GuestNotAllowedToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantAlreadyAdded;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantRoleTooLowToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.PermissionDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskDbDtoFakeImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.fake.TaskListDbDtoFakeImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskListTest {
  final UUID taskListId = UUID.randomUUID();
  final UUID creatorId = UUID.randomUUID();
  final CreateTaskListDto createTaskListDto =
      new CreateTaskListDto(taskListId, "name", "description", creatorId, null);

  @Test
  void shouldRunCreateTaskListInstanceWithoutExceptionWhenPayloadIsValid() {
    assertDoesNotThrow(() -> TaskList.create(createTaskListDto));
  }

  @Test
  void shouldCreateTaskListWithValidState() {
    var taskList = TaskList.create(createTaskListDto);
    var taskListDbDto =
        taskList.toDbDto(
            TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new, PermissionDbDtoFakeImpl::new);
    assertEquals(taskListId, taskListDbDto.id());
    assertEquals("name", taskListDbDto.name());
    assertEquals("description", taskListDbDto.description());
  }

  @Test
  void shouldAddTasksToTheListWithoutExceptionWhenPayloadIsValid() {
    var taskList = TaskList.create(createTaskListDto);
    var task1 = new CreateTaskDto(UUID.randomUUID(), "name", "description", createTaskListDto.id());
    var task2 = new CreateTaskDto(UUID.randomUUID(), "name", "description", createTaskListDto.id());

    assertDoesNotThrow(() -> taskList.addTask(task1));
    assertDoesNotThrow(() -> taskList.addTask(task2));
  }

  @Test
  void shouldAddTasksToTheList() {
    var taskList = TaskList.create(createTaskListDto);
    var task1 = new CreateTaskDto(UUID.randomUUID(), "name", "description", createTaskListDto.id());
    var task2 = new CreateTaskDto(UUID.randomUUID(), "name", "description", createTaskListDto.id());

    taskList.addTask(task1);
    taskList.addTask(task2);

    var taskListDbDto =
        taskList.toDbDto(
            TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new, PermissionDbDtoFakeImpl::new);

    assertEquals(2, taskListDbDto.tasks().size());

    var taskDbDto1 = taskListDbDto.tasks().get(0);
    var taskDbDto2 = taskListDbDto.tasks().get(1);

    assertThat(taskListDbDto.tasks()).isEqualTo(List.of(taskDbDto1, taskDbDto2));
  }

  @Test
  void shouldNotAllowAddingTheSameTaskTwice() {
    var createTaskDto =
        new CreateTaskDto(UUID.randomUUID(), "name", "description", createTaskListDto.id());
    var taskList = TaskList.create(createTaskListDto);

    taskList.addTask(createTaskDto);
    assertThrows(IllegalArgumentException.class, () -> taskList.addTask(createTaskDto));
  }

  @Test
  void shouldAddParticipantsToTheListWithoutExceptionWhenPayloadIsValid() {
    var taskList = TaskList.create(createTaskListDto);

    var addGuestParticipantDto =
        new AddParticipantDto(UUID.randomUUID(), List.of(Role.GUEST), creatorId, taskListId);
    var addEditorParticipantDto =
        new AddParticipantDto(UUID.randomUUID(), List.of(Role.EDITOR), creatorId, taskListId);
    var addOwnerParticipantDto =
        new AddParticipantDto(UUID.randomUUID(), List.of(Role.OWNER), creatorId, taskListId);

    assertDoesNotThrow(() -> taskList.addParticipant(addGuestParticipantDto));
    assertDoesNotThrow(() -> taskList.addParticipant(addEditorParticipantDto));
    assertDoesNotThrow(() -> taskList.addParticipant(addOwnerParticipantDto));
  }

  @Test
  void shouldAddParticipantToTheList() {
    var taskList = TaskList.create(createTaskListDto);
    var id = UUID.randomUUID();
    var expectedPermission = new PermissionDbDtoFakeImpl();
    expectedPermission.participantId(id);
    expectedPermission.role(Role.OWNER);
    expectedPermission.taskListId(taskListId);

    var addParticipantDto = new AddParticipantDto(id, List.of(Role.OWNER), creatorId, taskListId);

    taskList.addParticipant(addParticipantDto);

    var taskListDbDto =
        taskList.toDbDto(
            TaskListDbDtoFakeImpl::new, TaskDbDtoFakeImpl::new, PermissionDbDtoFakeImpl::new);
    assertEquals(2, taskListDbDto.permissions().size());
    assertEquals(expectedPermission, taskListDbDto.permissions().get(1));
  }

  @Test
  void shouldNotAllowAddingTheSameParticipantTwice() {
    var taskList = TaskList.create(createTaskListDto);
    var id = UUID.randomUUID();
    var addParticipantDto = new AddParticipantDto(id, List.of(Role.OWNER), creatorId, taskListId);

    taskList.addParticipant(addParticipantDto);
    assertThrows(ParticipantAlreadyAdded.class, () -> taskList.addParticipant(addParticipantDto));
  }

  @Test
  void shouldNotAllowAddingParticipantWhenAuthorDoesNotExist() {
    var taskList = TaskList.create(createTaskListDto);
    var id = UUID.randomUUID();
    var addParticipantDto = new AddParticipantDto(id, List.of(Role.OWNER), id, taskListId);

    assertThrows(AuthorIsNotAParticipant.class, () -> taskList.addParticipant(addParticipantDto));
  }

  @Test
  void shouldNotAllowAddingByGuestAnotherGuest() {
    var taskList = TaskList.create(createTaskListDto);
    var existingGuest = UUID.randomUUID();
    var newGuest = UUID.randomUUID();

    taskList.addParticipant(
        new AddParticipantDto(existingGuest, List.of(Role.GUEST), creatorId, taskListId));

    var addGuestByAnotherGuestAttemptDto =
        new AddParticipantDto(newGuest, List.of(Role.OWNER), existingGuest, taskListId);

    assertThrows(
        GuestNotAllowedToAddAnotherParticipant.class,
        () -> taskList.addParticipant(addGuestByAnotherGuestAttemptDto));
  }

  @Test
  void shouldNotAllowAddingParticipantWithGreaterRole() {
    var taskList = TaskList.create(createTaskListDto);
    var newOwnerId = UUID.randomUUID();
    var newEditorId = UUID.randomUUID();

    var addEditorDto =
        new AddParticipantDto(newEditorId, List.of(Role.EDITOR), creatorId, taskListId);
    taskList.addParticipant(addEditorDto);

    var addOwnerByEditorAttemptDto =
        new AddParticipantDto(newOwnerId, List.of(Role.OWNER), newEditorId, taskListId);

    assertThrows(
        ParticipantRoleTooLowToAddAnotherParticipant.class,
        () -> taskList.addParticipant(addOwnerByEditorAttemptDto));
  }
}

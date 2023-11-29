package com.bytd.dogatherbackend.core.tasklist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.AuthorIsNotAParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.GuestNotAllowedToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantAlreadyAdded;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantRoleTooLowToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.infra.db.impl.TaskDbDtoImpl;
import com.bytd.dogatherbackend.core.tasklist.infra.db.impl.TaskListDbDtoImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskListTest {
  UUID taskListId = UUID.randomUUID();
  UUID creatorId = UUID.randomUUID();
  CreateTaskListDto createTaskListDto =
      new CreateTaskListDto(taskListId, "name", "description", creatorId);

  @Test
  void shouldRunCreateTaskListInstanceWithoutExceptionWhenPayloadIsValid() {
    assertDoesNotThrow(() -> TaskList.create(createTaskListDto));
  }

  @Test
  void shouldCreateTaskListWithValidState() {
    var taskList = TaskList.create(createTaskListDto);
    var taskListDbDto = taskList.toDbDto(TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
    assertEquals(taskListId, taskListDbDto.getId());
    assertEquals("name", taskListDbDto.getName());
    assertEquals("description", taskListDbDto.getDescription());
  }

  @Test
  void shouldAddTasksToTheListWithoutExceptionWhenPayloadIsValid() {
    var taskList = TaskList.create(createTaskListDto);
    var task1 = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    var task2 = new CreateTaskDto(UUID.randomUUID(), "name", "description");

    assertDoesNotThrow(() -> taskList.addTask(task1));
    assertDoesNotThrow(() -> taskList.addTask(task2));
  }

  @Test
  void shouldAddTasksToTheList() {
    var taskList = TaskList.create(createTaskListDto);
    var task1 = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    var task2 = new CreateTaskDto(UUID.randomUUID(), "name", "description");

    taskList.addTask(task1);
    taskList.addTask(task2);

    var taskListDbDto = taskList.toDbDto(TaskListDbDtoImpl::new, TaskDbDtoImpl::new);

    assertEquals(2, taskListDbDto.getTasks().size());

    var taskDbDto1 = taskListDbDto.getTasks().get(0);
    var taskDbDto2 = taskListDbDto.getTasks().get(1);

    assertThat(taskListDbDto.getTasks()).isEqualTo(List.of(taskDbDto1, taskDbDto2));
  }

  @Test
  void shouldNotAllowAddingTheSameTaskTwice() {
    var createTaskDto = new CreateTaskDto(UUID.randomUUID(), "name", "description");
    var taskList = TaskList.create(createTaskListDto);

    taskList.addTask(createTaskDto);
    assertThrows(IllegalArgumentException.class, () -> taskList.addTask(createTaskDto));
  }

  @Test
  void shouldAddParticipantsToTheListWithoutExceptionWhenPayloadIsValid() {
    var taskList = TaskList.create(createTaskListDto);

    var addGuestParticipantDto =
        new AddParticipantDto(UUID.randomUUID(), List.of(Role.GUEST), creatorId);
    var addEditorParticipantDto =
        new AddParticipantDto(UUID.randomUUID(), List.of(Role.EDITOR), creatorId);
    var addOwnerParticipantDto =
        new AddParticipantDto(UUID.randomUUID(), List.of(Role.OWNER), creatorId);

    assertDoesNotThrow(() -> taskList.addParticipant(addGuestParticipantDto));
    assertDoesNotThrow(() -> taskList.addParticipant(addEditorParticipantDto));
    assertDoesNotThrow(() -> taskList.addParticipant(addOwnerParticipantDto));
  }

  @Test
  void shouldAddParticipantToTheList() {
    var taskList = TaskList.create(createTaskListDto);
    var id = UUID.randomUUID();
    var expectedParticipant = new Participant(id, List.of(Role.OWNER));
    var addParticipantDto = new AddParticipantDto(id, List.of(Role.OWNER), creatorId);

    taskList.addParticipant(addParticipantDto);

    var taskListDbDto = taskList.toDbDto(TaskListDbDtoImpl::new, TaskDbDtoImpl::new);
    assertEquals(2, taskListDbDto.getParticipants().size());
    assertEquals(expectedParticipant, taskListDbDto.getParticipants().get(1));
  }

  @Test
  void shouldNotAllowAddingTheSameParticipantTwice() {
    var taskList = TaskList.create(createTaskListDto);
    var id = UUID.randomUUID();
    var addParticipantDto = new AddParticipantDto(id, List.of(Role.OWNER), creatorId);

    taskList.addParticipant(addParticipantDto);
    assertThrows(ParticipantAlreadyAdded.class, () -> taskList.addParticipant(addParticipantDto));
  }

  @Test
  void shouldNotAllowAddingParticipantWhenAuthorDoesNotExist() {
    var taskList = TaskList.create(createTaskListDto);
    var id = UUID.randomUUID();
    var addParticipantDto = new AddParticipantDto(id, List.of(Role.OWNER), id);

    assertThrows(AuthorIsNotAParticipant.class, () -> taskList.addParticipant(addParticipantDto));
  }

  @Test
  void shouldNotAllowAddingByGuestAnotherGuest() {
    var taskList = TaskList.create(createTaskListDto);
    var existingGuest = UUID.randomUUID();
    var newGuest = UUID.randomUUID();

    taskList.addParticipant(new AddParticipantDto(existingGuest, List.of(Role.GUEST), creatorId));

    var addGuestByAnotherGuestAttemptDto =
        new AddParticipantDto(newGuest, List.of(Role.OWNER), existingGuest);

    assertThrows(
        GuestNotAllowedToAddAnotherParticipant.class,
        () -> taskList.addParticipant(addGuestByAnotherGuestAttemptDto));
  }

  @Test
  void shouldNotAllowAddingParticipantWithGreaterRole() {
    var taskList = TaskList.create(createTaskListDto);
    var newOwnerId = UUID.randomUUID();
    var newEditorId = UUID.randomUUID();

    var addEditorDto = new AddParticipantDto(newEditorId, List.of(Role.EDITOR), creatorId);
    taskList.addParticipant(addEditorDto);

    var addOwnerByEditorAttemptDto =
        new AddParticipantDto(newOwnerId, List.of(Role.OWNER), newEditorId);

    assertThrows(
        ParticipantRoleTooLowToAddAnotherParticipant.class,
        () -> taskList.addParticipant(addOwnerByEditorAttemptDto));
  }
}

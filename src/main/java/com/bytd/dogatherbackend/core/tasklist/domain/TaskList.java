package com.bytd.dogatherbackend.core.tasklist.domain;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.AddParticipantDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskDto;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.command.CreateTaskListDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Permission;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.AuthorIsNotAParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.GuestNotAllowedToAddAnotherParticipant;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantAlreadyAdded;
import com.bytd.dogatherbackend.core.tasklist.exceptions.participant.ParticipantRoleTooLowToAddAnotherParticipant;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskList {

  private UUID id;
  private String name;
  private String description;
  private List<Permission> permissions;
  private List<Task> tasks;
  private UUID creatorId;

  public static TaskList create(CreateTaskListDto dto) {
    var instance = new TaskList();
    instance.id = dto.id();
    instance.name = dto.name();
    instance.description = dto.description();
    instance.creatorId = dto.creatorId();
    instance.permissions = new LinkedList<>();
    instance.permissions.add(new Permission(dto.creatorId(), Role.OWNER));

    instance.tasks = new LinkedList<>();
    if (dto.tasks() != null) {
      dto.tasks().forEach(instance::addTask);
    }

    return instance;
  }

  public void addTask(CreateTaskDto taskDto) {
    if (tasks.stream().anyMatch(t -> t.isIdConflict(taskDto.id()))) {
      throw new IllegalArgumentException("Task already exists in the list");
    } else {
      addTask(Task.create(taskDto));
    }
  }

  private void addTask(Task task) {
    tasks = Stream.concat(tasks.stream(), Stream.of(task)).toList();
  }

  public void addParticipant(AddParticipantDto dto) {
    var authorPermissions = getPermissions(dto.authorId());
    if (authorPermissions.isEmpty()) {
      throw new AuthorIsNotAParticipant(dto.authorId());
    } else if (this.permissions.stream()
        .anyMatch(p -> p.participantId().equals(dto.participantId()))) {
      throw new ParticipantAlreadyAdded(dto.participantId());
    } else if (authorPermissions.stream().map(Permission::role).allMatch(Role::isGuest)) {
      throw new GuestNotAllowedToAddAnotherParticipant(dto.authorId());
    } else if (isRoleToBeAddedHigherThanAuthorRole(dto)) {
      throw new ParticipantRoleTooLowToAddAnotherParticipant(
          authorPermissions.stream()
              .map(Permission::role)
              .map(Role::toString)
              .collect(Collectors.joining(", ")));
    } else {
      Function<Role, Permission> roleToPermission = roleToPermissionFactory(dto.participantId());
      addPermissions(dto.roles().stream().map(roleToPermission));
    }
  }

  private Function<Role, Permission> roleToPermissionFactory(UUID participantId) {
    return role -> new Permission(participantId, role);
  }

  private void addPermissions(Stream<Permission> newPermissions) {
    permissions = Stream.concat(this.permissions.stream(), newPermissions).toList();
  }

  private boolean isRoleToBeAddedHigherThanAuthorRole(AddParticipantDto dto) {
    var authorPermissions = getPermissions(dto.authorId());
    if (authorPermissions.isEmpty()) {
      throw new AuthorIsNotAParticipant(dto.authorId());
    }
    var authorRoles = authorPermissions.stream().map(Permission::role).toList();
    var authorHighestRole = Role.findHighestRole(authorRoles);
    var roleToBeAddedHighestRole = Role.findHighestRole(dto.roles());
    return roleToBeAddedHighestRole.compareTo(authorHighestRole) > 0;
  }

  private List<Permission> getPermissions(UUID participantId) {
    return permissions.stream().filter(p -> p.participantId().equals(participantId)).toList();
  }

  public TaskListDbDto toDbDto(
      Supplier<TaskListDbDto> listDbDtoSupplier,
      Supplier<TaskDbDto> taskDbDtoSupplier,
      Supplier<PermissionDbDto> permissionDbDtoSupplier) {
    TaskListDbDto dto = listDbDtoSupplier.get();
    dto.setId(id);
    dto.setName(name);
    dto.setDescription(description);
    dto.setCreatorId(creatorId);
    dto.setPermissions(
        permissions.stream().map(p -> p.toDbDto(permissionDbDtoSupplier, id)).toList());
    dto.setTasks(tasks.stream().map(task -> task.toDbDto(taskDbDtoSupplier)).toList());
    return dto;
  }

  public static TaskList fromDbDto(TaskListDbDto dto) {
    List<TaskDbDto> tasks = dto.getTasks();
    var instance = new TaskList();
    instance.id = dto.getId();
    instance.name = dto.getName();
    instance.description = dto.getDescription();
    instance.creatorId = dto.getCreatorId();
    instance.permissions =
        dto.getPermissions() == null
            ? new LinkedList<>()
            : dto.getPermissions().stream().map(Permission::fromDbDto).toList();
    instance.tasks =
        tasks == null ? new LinkedList<>() : tasks.stream().map(Task::fromDbDto).toList();
    return instance;
  }
}

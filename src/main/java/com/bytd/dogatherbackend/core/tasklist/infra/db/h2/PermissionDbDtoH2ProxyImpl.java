package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.domain.dto.PermissionDbDto;
import com.bytd.dogatherbackend.core.tasklist.domain.model.participant.Role;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PermissionDbDtoH2ProxyImpl implements PermissionDbDto {
  private final PermissionDbDtoH2Impl permissionDbDtoH2Impl;

  public static PermissionDbDtoH2ProxyImpl create() {
    return new PermissionDbDtoH2ProxyImpl(new PermissionDbDtoH2Impl());
  }

  public static PermissionDbDtoH2ProxyImpl from(PermissionDbDtoH2Impl permissionDbDtoH2Impl) {
    return new PermissionDbDtoH2ProxyImpl(permissionDbDtoH2Impl);
  }

  public PermissionDbDtoH2Impl toNativeImpl() {
    return permissionDbDtoH2Impl;
  }

  @Override
  public void id(UUID id) {
    permissionDbDtoH2Impl.setId(id);
  }

  @Override
  public UUID id() {
    return permissionDbDtoH2Impl.getId();
  }

  @Override
  public void taskListId(UUID taskListId) {
    permissionDbDtoH2Impl.setTaskListId(taskListId);
  }

  @Override
  public UUID taskListId() {
    return permissionDbDtoH2Impl.getTaskListId();
  }

  @Override
  public void participantId(UUID participantId) {
    permissionDbDtoH2Impl.setParticipantId(participantId);
  }

  @Override
  public UUID participantId() {
    return permissionDbDtoH2Impl.getParticipantId();
  }

  @Override
  public void role(Role role) {
    permissionDbDtoH2Impl.setRole(role);
  }

  @Override
  public Role role() {
    return permissionDbDtoH2Impl.getRole();
  }
}

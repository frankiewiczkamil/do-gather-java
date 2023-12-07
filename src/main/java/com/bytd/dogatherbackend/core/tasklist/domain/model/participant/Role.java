package com.bytd.dogatherbackend.core.tasklist.domain.model.participant;

import java.util.Comparator;
import java.util.List;

public enum Role implements Comparable<Role> {
  GUEST,
  EDITOR,
  OWNER;
  private static final Comparator<Role> comparator = new RoleComparator();

  public boolean isGuest() {
    return this.equals(GUEST);
  }

  public static Role findHighestRole(List<Role> roles) {
    var max = roles.stream().max(comparator);
    return max.orElseThrow(() -> new IllegalStateException("Roles list is empty"));
  }

  static class RoleComparator implements Comparator<Role> {
    @Override
    public int compare(Role o1, Role o2) {
      return o1.compareTo(o2);
    }
  }
}
